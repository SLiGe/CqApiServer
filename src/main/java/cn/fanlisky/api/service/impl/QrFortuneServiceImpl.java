package cn.fanlisky.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cn.fanlisky.api.domain.QrFortune;
import cn.fanlisky.api.domain.QrFortuneData;
import cn.fanlisky.api.domain.QrSignInData;
import cn.fanlisky.api.enums.RedisPrefixEnum;
import cn.fanlisky.api.enums.ResponseCode;
import cn.fanlisky.api.enums.StatusCode;
import cn.fanlisky.api.mapper.QrFortuneDataMapper;
import cn.fanlisky.api.mapper.QrFortuneMapper;
import cn.fanlisky.api.mapper.QrSignInDataMapper;
import cn.fanlisky.api.model.response.RestfulResponse;
import cn.fanlisky.api.model.response.TableResponse;
import cn.fanlisky.api.utils.RedisCacheUtil;
import cn.fanlisky.api.service.IQrFortuneService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.fanlisky.api.utils.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 运势表 服务实现类
 * </p>
 *
 * @author Gary
 * @since 2019-07-09
 */
@Service
public class QrFortuneServiceImpl extends ServiceImpl<QrFortuneMapper, QrFortune> implements IQrFortuneService {


    private final RedisCacheUtil redisCacheUtil;

    private static Integer fortuneCount;

    private final QrFortuneDataMapper qrFortuneDataMapper;

    private final QrSignInDataMapper qrSignInDataMapper;

    @Autowired
    public QrFortuneServiceImpl(RedisCacheUtil redisCacheUtil, QrFortuneDataMapper qrFortuneDataMapper, QrSignInDataMapper qrSignInDataMapper) {
        this.redisCacheUtil = redisCacheUtil;
        this.qrFortuneDataMapper = qrFortuneDataMapper;
        this.qrSignInDataMapper = qrSignInDataMapper;
    }

    @PostConstruct
    public void setFortuneCountCount() {
        String countStr = redisCacheUtil.getPre(RedisPrefixEnum.DATA_COUNT.getPrefix(), "QR_FORTUNE");
        fortuneCount = Integer.parseInt(countStr == null ? "0" : countStr);
    }

    @Override
    public RestfulResponse<QrFortune> getFortuneOfToday(String qq, int isOne, int isGroup, boolean oldApi, String groupNum, int isIntegral) {
        //校验QQ号
        if (ObjectUtil.isNullOrEmpty(qq) || !QqUtil.checkQqNum(qq)) {
            return RestfulResponse.getRestfulResponse(StatusCode.QUERY_FAILED);
        }
        Date date = new Date();
        String todayDateStr = DateUtil.parseDateToStr(DateUtil.YYYY_MM_DD, date);
        //查询是否有今日数据,判断是否老接口
        QrFortuneData qrFortuneData = qrFortuneDataMapper.selectOne(Wrappers.<QrFortuneData>lambdaQuery().eq(QrFortuneData::getQq, qq).last(" and  DATE_FORMAT(`UPDATE_DATE`,'%Y-%m-%d') =  '" + todayDateStr + "'"));
        if (!ObjectUtil.isNullOrEmpty(qrFortuneData)) {
            if (isOne == 1 && isGroup == 1 && !oldApi) {
                //获取已操作的群号列表
                String groupNums = qrFortuneData.getGroupNum();
                if (!StringUtils.isBlank(groupNums) && groupNums.contains(",")) {
                    String[] groupArray = groupNums.split(",");
                    List<String> groupList = Arrays.asList(groupArray);
                    if (groupList.contains(groupNum)) {
                        //返回特殊约定 40010
                        return RestfulResponse.getRestfulResponse(StatusCode.FORTUNE_ALREADY);
                    }
                }
                //更新群号
                String qrFortuneDataGroupNum = qrFortuneData.getGroupNum() == null ? "" : qrFortuneData.getGroupNum();
                qrFortuneDataGroupNum = qrFortuneDataGroupNum + groupNum + ",";
                qrFortuneData.setGroupNum(qrFortuneDataGroupNum);
                qrFortuneDataMapper.updateById(qrFortuneData);
            }
            if (isIntegral == 1 && isGroup == 1) {
                minusIntegral(qq);
            }
            QrFortune qrFortune = JSONObject.parseObject(qrFortuneData.getJsonData(), QrFortune.class);
            return RestfulResponse.getRestfulResponse(StatusCode.QUERY_SUCCESS, qrFortune);
        }
        Random random = new Random();
        int randomId = random.nextInt(fortuneCount);
        //查询到结果后放入Redis中，key为QQ，值为运势结果   --已更换
        //查询结果后放入fortune_data表中
        QrFortune qrFortune = getById(randomId);
        //查询库中是否存在数据
        QrFortuneData localFortuneData = qrFortuneDataMapper.selectOne(Wrappers.<QrFortuneData>lambdaQuery().eq(QrFortuneData::getQq, qq));
        if (!ObjectUtil.isNullOrEmpty(localFortuneData)) {
            localFortuneData.setJsonData(JSONObject.toJSONString(qrFortune)).setUpdateDate(date);
            if (isGroup == 1 && isOne == 1) {
                String localFortuneDataGroupNum = localFortuneData.getGroupNum() == null ? "" : localFortuneData.getGroupNum();
                localFortuneDataGroupNum = localFortuneDataGroupNum + groupNum + ",";
                localFortuneData.setGroupNum(localFortuneDataGroupNum);
            }
            qrFortuneDataMapper.updateById(localFortuneData);
        } else {
            QrFortuneData newFortuneData = new QrFortuneData();
            if (isGroup == 1 && isOne == 1) {
                newFortuneData.setGroupNum(groupNum + ",");
            }
            newFortuneData.setCreateDate(date).setJsonData(JSONObject.toJSONString(qrFortune)).setQq(qq).setUpdateDate(date);
            qrFortuneDataMapper.insert(newFortuneData);
        }
        if (isIntegral == 1 && isGroup == 1) {
            minusIntegral(qq);
        }
        return RestfulResponse.getRestfulResponse(StatusCode.QUERY_SUCCESS, qrFortune);
    }

    /**
     * 减少积分
     *
     * @param qq QQ号
     */
    private void minusIntegral(String qq) {
        QrSignInData qrSignInData = qrSignInDataMapper.selectOne(Wrappers.<QrSignInData>lambdaQuery().eq(QrSignInData::getQq, qq));
        if (!ObjectUtil.isNullOrEmpty(qrSignInData)) {
            synchronized (this) {
                Integer localIntegral = qrSignInData.getIntegral();
                if (localIntegral > 10) {
                    qrSignInData.setIntegral(localIntegral - 10);
                    qrSignInDataMapper.updateById(qrSignInData);
                }
            }
        }
    }

    @Override
    public TableResponse getUserFortuneOfToday(QrFortuneData qrFortuneData) {
        List<QrFortuneData> fortuneDataList;
        if (qrFortuneData != null) {
            fortuneDataList = qrFortuneDataMapper.selectList(Wrappers.<QrFortuneData>lambdaQuery()
                    .eq(qrFortuneData.getQq() != null && !"".equals(qrFortuneData.getQq())
                            , QrFortuneData::getQq, qrFortuneData.getQq())
                    .gt(qrFortuneData.getStartDate() != null && !"".equals(qrFortuneData.getStartDate())
                            , QrFortuneData::getUpdateDate, qrFortuneData.getStartDate())
                    .lt(qrFortuneData.getEndDate() != null && !"".equals(qrFortuneData.getEndDate())
                            , QrFortuneData::getUpdateDate, qrFortuneData.getEndDate()));
        } else {
            fortuneDataList = qrFortuneDataMapper.selectList(null);
        }
        PageInfo<QrFortuneData> pageInfo = new PageInfo<>(fortuneDataList);
        return TableResponse.getTableResponse(ResponseCode.RESPONSE_SUCCESS, pageInfo.getTotal(), fortuneDataList);
    }

    /**
     * 获取今日用户数量
     *
     * @return 返回数量
     */
    @Override
    public Map<String, Integer> getFortuneCountOfToday() {
        Map<String, Integer> countMap = Maps.newHashMap();
        String nowDate = DateUtil.parseDateToStr(DateUtil.YYYY_MM_DD, new Date());
        Integer count = qrFortuneDataMapper.selectCount(Wrappers.emptyWrapper());
        countMap.put("count", count);
        Integer countOfToday = qrFortuneDataMapper.selectCount(Wrappers.<QrFortuneData>lambdaQuery().like(QrFortuneData::getUpdateDate, nowDate));
        countMap.put("countOfToday", countOfToday);
        Integer fortuneDataCount = count(Wrappers.emptyWrapper());
        countMap.put("fortuneDataCount", fortuneDataCount);
        return countMap;
    }

    @Override
    public void toGetFortune() throws IOException {
        Random random = new Random();
        final int origin = 10000;
        final int round = 999999999;
        final int[] ints = random.ints(origin, round).limit(2000).toArray();
        List<Map<String, String>> list = Lists.newLinkedList();
        List<QrFortune> yunShis = Lists.newArrayList();
        for (int anInt : ints) {
            Document document = Jsoup.connect("http://qq.link114.cn/" + anInt).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36").get();
            Elements dd = document.getElementsByTag("dd");
            Map<String, String> map = Maps.newHashMap();
            for (int i = 0; i < dd.size(); i++) {
                map.put("value" + i, dd.get(i).text());
            }
            list.add(map);
        }
        for (Map<String, String> map : list) {
            QrFortune yunShi = new QrFortune();
            yunShi.setFortuneSummary(map.get("value0"));
            yunShi.setLuckyStar(map.get("value1"));
            yunShi.setSignText(map.get("value2"));
            yunShi.setUnSignText(map.get("value3"));
            yunShis.add(yunShi);
        }
        List<QrFortune> list1 = Lists.newArrayList();
        for (int i = 0; i < yunShis.size(); i++) {
            if (i == 0) {
                list1.add(yunShis.get(i));
            } else {
                if (!comTo(list1, yunShis.get(i))) {
                    list1.add(yunShis.get(i));
                }
            }
        }

        list1.forEach(this::save);
    }

    private boolean comTo(List<QrFortune> list, QrFortune yunShi1) {
        boolean res = Boolean.FALSE;
        for (QrFortune yunShi : list) {
            if (yunShi.getFortuneSummary().equals(yunShi1.getFortuneSummary())) {
                res = true;
            } else if (yunShi.getLuckyStar().equals(yunShi1.getLuckyStar())) {
                res = true;
            } else if (yunShi.getSignText().equals(yunShi1.getSignText())) {
                res = true;
            } else if (yunShi.getUnSignText().equals(yunShi1.getUnSignText())) {
                res = true;
            } else {
                res = false;
            }
        }
        return res;

    }

}
