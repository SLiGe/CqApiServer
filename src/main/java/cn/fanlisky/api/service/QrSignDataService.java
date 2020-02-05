package cn.fanlisky.api.service;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import cn.fanlisky.api.domain.QrMsgOfDay;
import cn.fanlisky.api.enums.QueueName;
import cn.fanlisky.api.enums.RedisPrefixEnum;
import cn.fanlisky.api.enums.ResponseCode;
import cn.fanlisky.api.mapper.QrMsgOfDayMapper;
import cn.fanlisky.api.model.request.SignInForQqRequest;
import cn.fanlisky.api.domain.QrSignInData;
import cn.fanlisky.api.domain.QrSignInDays;
import cn.fanlisky.api.enums.StatusCode;
import cn.fanlisky.api.mapper.QrSignInDataMapper;
import cn.fanlisky.api.mapper.QrSigninDaysMapper;
import cn.fanlisky.api.model.response.GetSignInDataResponse;
import cn.fanlisky.api.model.response.RestfulResponse;
import cn.fanlisky.api.model.response.TableResponse;
import cn.fanlisky.api.utils.RedisCacheUtil;
import cn.fanlisky.api.rabbitmq.RabbitmqService;
import cn.fanlisky.api.utils.ObjectUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

/**
 * 机器人签到业务层
 *
 * @author LiGe
 * @since 2019-03-23 00:46
 * <p>Code is my soul.<p/>
 */
@Service
public class QrSignDataService extends ServiceImpl<QrSignInDataMapper, QrSignInData> {

    @Resource
    private QrSigninDaysMapper qrSigninDaysMapper;
    @Resource
    private QrMsgOfDayMapper qrMsgOfDayMapper;
    @Resource
    private RabbitmqService rabbitmqService;

    @Resource
    private RedisCacheUtil redisCacheUtil;

    private static Integer count;

    @PostConstruct
    public void setMsgCount() {
        String countStr = redisCacheUtil.getPre(RedisPrefixEnum.DATA_COUNT.getPrefix(), "QR_MSG_OF_DAY");
        count = Integer.parseInt(countStr == null ? "0" : countStr);
    }

    /**
     * 签到接口,在签到后发送信息到消息队列,去同步库中信息
     *
     * @param request 签到参数
     * @return 返回结果
     */
    @Transactional(rollbackFor = Exception.class)
    public RestfulResponse addSignIn(SignInForQqRequest request) {
        RestfulResponse response = new RestfulResponse();
        response.setCode(200);
        String requestQq = request.getQq();
        QrSignInData qrSigninData = getOne(Wrappers.<QrSignInData>lambdaQuery().eq(QrSignInData::getQq, requestQq));
        QrSignInDays qrSignInDays = qrSigninDaysMapper.selectOne(Wrappers.<QrSignInDays>lambdaQuery().eq(QrSignInDays::getQq, requestQq));
        boolean isSign;
        if (qrSigninData != null && qrSignInDays != null) {
            String nowDate = DateUtil.format(new Date(), "yyyy-MM-dd");
            String localDate = DateUtil.format(qrSigninData.getCreateDate(), "yyyy-MM-dd");
            isSign = localDate.equals(nowDate);
        } else {
            isSign = false;
        }
        if (qrSigninData != null && !isSign && qrSignInDays != null) {
            //存在签到数据
            int resIntegral = qrSigninData.getIntegral() + Integer.parseInt(request.getIntegral());
            int resDays = Integer.parseInt(qrSigninData.getDays()) + 1;
            qrSigninData.setIntegral(resIntegral).setDays(Integer.toString(resDays));
            qrSigninData.setCreateDate(new Date());
            updateById(qrSigninData);
            int mDays = Integer.parseInt(qrSignInDays.getMdays()) + 1;
            qrSignInDays.setMdays(Integer.toString(mDays));
            qrSignInDays.setTdays(Integer.toString(resDays));
            qrSigninDaysMapper.updateById(qrSignInDays);
            response.setStatus("200").setMsg("签到成功");
            /*此处应该发送新增加的积分*/
            QrSignInData qrSignInData = getById(qrSigninData.getId()).setIntegral(Integer.parseInt(request.getIntegral()));
            sendToQueue(qrSignInData);
        } else if (isSign) {
            response.setStatus("203").setMsg("已经签到");
        } else {
            //新增签到数据
            QrSignInData newSignInData = new QrSignInData();
            newSignInData.setIntegral(Integer.parseInt(request.getIntegral())).setDays("1")
                    .setCreateDate(new Date()).setQq(requestQq);
            save(newSignInData);
            QrSignInDays newQrSignInDays = new QrSignInDays().setMdays("1").setTdays("1").setQq(requestQq);
            qrSigninDaysMapper.insert(newQrSignInDays);
            response.setStatus("200").setMsg("签到成功");
            QrSignInData qrSignInData = getById(newSignInData.getId()).setIntegral(Integer.parseInt(request.getIntegral()));
            sendToQueue(qrSignInData);
        }
        return response;
    }

    /**
     * 查询签到数据
     *
     * @param request 添加参数
     * @since 2019/4/2
     */
    public RestfulResponse<GetSignInDataResponse> querySignInData(SignInForQqRequest request) {
        final String format = DateUtil.format(new Date(), "yyyy-MM-dd");
        QrMsgOfDay qrMsgOfDay = qrMsgOfDayMapper.selectOne(Wrappers.<QrMsgOfDay>lambdaQuery().like(QrMsgOfDay::getSendDate, format));
        if (ObjectUtil.isNullOrEmpty(qrMsgOfDay)) {
            Random random = new Random();
            Integer msgRandom = random.nextInt(count);
            qrMsgOfDay = qrMsgOfDayMapper.selectById(msgRandom);
        }
        String qq = request.getQq();
        GetSignInDataResponse getSignInDataResponse = this.baseMapper.querySignInData(qq);
        if (!ObjectUtil.isNullOrEmpty(getSignInDataResponse)) {
            getSignInDataResponse.setTodayMsg(qrMsgOfDay.getSentence());
        }
        return RestfulResponse.getRestfulResponse(StatusCode.RESPONSE_SUCCESS, getSignInDataResponse);
    }

    /**
     * 增加积分操作
     *
     * @param request 添加参数
     * @since 2019/4/2
     */
    @Transactional(rollbackFor = Exception.class)
    public RestfulResponse addSignIntegral(SignInForQqRequest request) {
        String qq = request.getQq();
        String integral = request.getIntegral();
        QrSignInData qrSignInData = getOne(Wrappers.<QrSignInData>lambdaQuery().eq(QrSignInData::getQq, qq));
        if (qrSignInData != null) {
            qrSignInData.setIntegral(qrSignInData.getIntegral() + Integer.parseInt(integral));
            updateById(qrSignInData);
        } else {
            QrSignInData qrSignInData1 = new QrSignInData();
            qrSignInData1.setIntegral(Integer.parseInt(integral)).setQq(qq).setCreateDate(new Date()).setDays("1");
            save(qrSignInData1);
            QrSignInDays newQrSignInDays = new QrSignInDays().setMdays("1").setTdays("1").setQq(qq);
            qrSigninDaysMapper.insert(newQrSignInDays);
        }
        return RestfulResponse.getRestfulResponse(StatusCode.RESPONSE_SUCCESS);
    }

    private void sendToQueue(QrSignInData qrSignInData) {
        rabbitmqService.sendToQueue(QueueName.MAIN_QUEUE.getName(), qrSignInData);
    }

    /**
     * 查询积分排行榜
     */
    public RestfulResponse<List<QrSignInData>> getIntegralTop(Map<String, String> params) {
        String pageSizeStr = params.get("pageSize");
        int pageSize = Integer.parseInt(pageSizeStr);
        List<QrSignInData> qrSignInData = list(Wrappers.<QrSignInData>lambdaQuery().orderByDesc(QrSignInData::getIntegral).last("limit " + pageSize));
        return RestfulResponse.getRestfulResponse(StatusCode.QUERY_SUCCESS, qrSignInData);
    }

    /**
     * 获取签到列表
     *
     * @param qrSignInData 查询参数
     * @return 返回结果
     */
    public TableResponse<GetSignInDataResponse> getSignList(QrSignInData qrSignInData) {
        List<GetSignInDataResponse> signList = this.baseMapper.getSignList(qrSignInData);
        PageInfo pageInfo = new PageInfo<>(signList);
        return TableResponse.getTableResponse(ResponseCode.RESPONSE_SUCCESS, pageInfo.getTotal(), signList);
    }

    /**
     * 获取今日签到人数
     */
    public Map<String, Integer> getSignInCountOfToday() {
        Map<String, Integer> countMap = Maps.newHashMap();
        String nowDate = DateUtil.format(new Date(), "yyyy-MM-dd");
        Integer signInCountOfToday = count(Wrappers.<QrSignInData>lambdaQuery().like(QrSignInData::getCreateDate, nowDate));
        countMap.put("signInCountOfToday", signInCountOfToday);
        Integer signInCount = count(null);
        countMap.put("signInCount", signInCount);
        return countMap;
    }
}
