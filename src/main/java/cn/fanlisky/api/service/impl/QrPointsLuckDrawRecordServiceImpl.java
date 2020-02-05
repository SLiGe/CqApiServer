package cn.fanlisky.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import cn.fanlisky.api.constant.MovieConfig;
import cn.fanlisky.api.domain.QrLotteryUser;
import cn.fanlisky.api.domain.QrPointsLuckDrawPrize;
import cn.fanlisky.api.domain.QrPointsLuckDrawRecord;
import cn.fanlisky.api.enums.RedisPrefixEnum;
import cn.fanlisky.api.enums.StatusCode;
import cn.fanlisky.api.mapper.QrLotteryUserMapper;
import cn.fanlisky.api.mapper.QrPointsLuckDrawPrizeMapper;
import cn.fanlisky.api.mapper.QrPointsLuckDrawProbabilityMapper;
import cn.fanlisky.api.mapper.QrPointsLuckDrawRecordMapper;
import cn.fanlisky.api.model.response.RestfulResponse;
import cn.fanlisky.api.utils.RedisCacheUtil;
import cn.fanlisky.api.service.IQrPointsLuckDrawRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.fanlisky.api.utils.DateUtil;
import cn.fanlisky.api.utils.ObjectUtil;
import cn.fanlisky.api.utils.QqUtil;
import cn.fanlisky.api.utils.TCPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * 抽奖记录表 服务实现类
 * </p>
 *
 * @author Gary
 * @since 2019-11-18
 */
@Slf4j
@Service
public class QrPointsLuckDrawRecordServiceImpl extends ServiceImpl<QrPointsLuckDrawRecordMapper, QrPointsLuckDrawRecord> implements IQrPointsLuckDrawRecordService {

    @Resource
    private QrLotteryUserMapper lotteryUserMapper;
    @Resource
    private QrPointsLuckDrawPrizeMapper pointsLuckDrawPrizeMapper;

    @Value(value = "${draw.lottery.point:500}")
    private Integer lotteryPoints;

    @Resource
    private RedisCacheUtil redisCacheUtil;

    @Resource
    private MovieConfig movieConfig;

    @Resource
    private QrPointsLuckDrawProbabilityMapper pointsLuckDrawProbabilityMapper;

    /**
     * QQ抽奖
     *
     * @param qrLotteryUser 用户参数
     * @return 返回结果
     */
    @Override
    public RestfulResponse lotteryFromQq(QrLotteryUser qrLotteryUser) {
        //校验QQ号以及群号
        if (!(QqUtil.checkQqNum(qrLotteryUser.getUserQq()) && QqUtil.checkQqNum(qrLotteryUser.getUserQqGroup()))) {
            return RestfulResponse.getRestfulResponse(StatusCode.LOTTERY_APPLY_FAIL);
        }
        //确认用户信息
        if (checkUserInfo(qrLotteryUser)) {
            //查询最近开奖
            String nowDateStr = DateUtil.parseDateToStr(DateUtil.YYYYMMDD, new Date());
            QrPointsLuckDrawPrize qrPointsLuckDrawPrize = pointsLuckDrawPrizeMapper.selectOne(Wrappers.<QrPointsLuckDrawPrize>lambdaQuery().eq(QrPointsLuckDrawPrize::getPhase, nowDateStr));
            if (ObjectUtil.isNullOrEmpty(qrPointsLuckDrawPrize)) {
                //如果没有,则选择最新的期数
                Integer count = pointsLuckDrawPrizeMapper.selectCount(null);
                qrPointsLuckDrawPrize = pointsLuckDrawPrizeMapper.selectById(count);
            }
            Integer prizeStatus = qrPointsLuckDrawPrize.getStatus();
            if (prizeStatus == 0) {
                //暂时没有抽奖活动
                return RestfulResponse.getRestfulResponse(StatusCode.LOTTERY_NOT_PRiZE);
            }
            //查询是否重复报名
            QrPointsLuckDrawRecord localRecordData = getOne(Wrappers.<QrPointsLuckDrawRecord>lambdaQuery().eq(QrPointsLuckDrawRecord::getMemberId, qrLotteryUser.getUserId())
                    .eq(QrPointsLuckDrawRecord::getPrizeId, qrPointsLuckDrawPrize.getId()));
            if (!ObjectUtil.isNullOrEmpty(localRecordData)) {
                return RestfulResponse.getRestfulResponse(StatusCode.LOTTERY_APPLY_REPEAT);
            }
            QrPointsLuckDrawRecord pointsLuckDrawRecord = new QrPointsLuckDrawRecord();
            pointsLuckDrawRecord.setMemberId(qrLotteryUser.getUserId());
            pointsLuckDrawRecord.setPoints(lotteryPoints);
            pointsLuckDrawRecord.setPrizeId(qrPointsLuckDrawPrize.getId());
            pointsLuckDrawRecord.setCreateTime(new Date());
            pointsLuckDrawRecord.setUpdateTime(new Date());
            save(pointsLuckDrawRecord);
            return RestfulResponse.getRestfulResponse(StatusCode.LOTTERY_APPLY_SUCCESS);
        }
        return RestfulResponse.getRestfulResponse(StatusCode.LOTTERY_APPLY_FAIL);
    }

    /**
     * 执行开奖
     *
     * @return 返回结果
     */
    @Override
    public RestfulResponse executeDraw() {
        //获取最新期数
        String latestPhase = redisCacheUtil.getPre(RedisPrefixEnum.LOTTERY_DRAW.getPrefix(), "LATEST_PHASE");
        QrPointsLuckDrawPrize latestLuckDrawPhase = pointsLuckDrawPrizeMapper.selectOne(Wrappers.<QrPointsLuckDrawPrize>lambdaQuery().eq(QrPointsLuckDrawPrize::getPhase, latestPhase));
        //获取所有的报名用户
        List<QrPointsLuckDrawRecord> pointsLuckDrawRecords = list(Wrappers.<QrPointsLuckDrawRecord>lambdaQuery().eq(QrPointsLuckDrawRecord::getPrizeId, latestLuckDrawPhase.getId()));
        //奖品数量
        Integer prizeNum = Integer.valueOf(latestLuckDrawPhase.getValue());
        //获取概率数据，暂时不做
        List<QrPointsLuckDrawRecord> luckyDoHuman = Lists.newArrayList();
        for (int i = 0; i < prizeNum; i++) {
            Random random = new Random();
            int indexStart = random.nextInt(pointsLuckDrawRecords.size());
            int indexEnd = random.nextInt(pointsLuckDrawRecords.size());
            List<QrPointsLuckDrawRecord> temp = pointsLuckDrawRecords.subList(Math.min(indexStart, indexEnd), Math.max(indexStart, indexEnd));
            temp.forEach(v -> {
                Random random2 = new Random();
                int i1 = random2.nextInt(temp.size());
                luckyDoHuman.add(temp.get(i1));
            });
        }
        //再次进行筛选
        List<QrPointsLuckDrawRecord> luckyHuman = Lists.newArrayList();
        for (int i = 0; i < prizeNum; i++) {
            Random random = new Random();
            int i1 = random.nextInt(luckyDoHuman.size());
            luckyHuman.add(luckyDoHuman.get(i1));
        }
        //发送到酷Q客户端
        TCPUtil.sendTCPRequest(movieConfig.socketCqpIp, movieConfig.socketCqpPort, JSONObject.toJSONString(luckyHuman), "gb2312");
        //更改中奖用户状态
        for (QrPointsLuckDrawRecord pointsLuckDrawRecord : luckyHuman) {
            pointsLuckDrawRecord.setResult(1);
            Calendar calendar = Calendar.getInstance();
            int month = calendar.get(Calendar.MONTH);
            pointsLuckDrawRecord.setMonth(String.valueOf(month));
            pointsLuckDrawRecord.setDaily(new Date());
            saveOrUpdate(pointsLuckDrawRecord);
        }
        //设置抽奖状态:已结束
        latestLuckDrawPhase.setStatus(0);
        pointsLuckDrawPrizeMapper.updateById(latestLuckDrawPhase);
        return RestfulResponse.getRestfulResponse(StatusCode.RESPONSE_SUCCESS);
    }


    /**
     * 确认用户信息
     *
     * @param qrLotteryUser 用户信息
     * @return 是否成功
     */
    private boolean checkUserInfo(QrLotteryUser qrLotteryUser) {
        //首先,查询是否有当前用户
        QrLotteryUser localLotteryUser = lotteryUserMapper.selectOne(Wrappers.<QrLotteryUser>lambdaQuery().eq(QrLotteryUser::getUserQq, qrLotteryUser.getUserQq()));
        if (ObjectUtil.isNullOrEmpty(localLotteryUser)) {
            //为该用户创建新记录
            qrLotteryUser.setCreateTime(new Date());
            qrLotteryUser.setUpdateTime(new Date());
            int insertResult = lotteryUserMapper.insert(qrLotteryUser);
            if (insertResult > 0) {
                log.info("[抽奖API]----添加用户成功,qq-> {}", qrLotteryUser.getUserQq());
            } else {
                log.warn("[抽奖API]----添加用户失败,qq-> {}", qrLotteryUser.getUserQq());
                return false;
            }
        }
        return true;
    }
}
