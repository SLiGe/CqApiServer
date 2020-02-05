package cn.fanlisky.api.service.impl;

import cn.fanlisky.api.domain.QrPointsLuckDrawPrize;
import cn.fanlisky.api.enums.RedisPrefixEnum;
import cn.fanlisky.api.enums.StatusCode;
import cn.fanlisky.api.mapper.QrPointsLuckDrawPrizeMapper;
import cn.fanlisky.api.model.response.RestfulResponse;
import cn.fanlisky.api.utils.RedisCacheUtil;
import cn.fanlisky.api.service.IQrPointsLuckDrawPrizeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 奖品表 服务实现类
 * </p>
 *
 * @author Gary
 * @since 2019-11-18
 */
@Service
public class QrPointsLuckDrawPrizeServiceImpl extends ServiceImpl<QrPointsLuckDrawPrizeMapper, QrPointsLuckDrawPrize> implements IQrPointsLuckDrawPrizeService {

    @Resource
    private RedisCacheUtil redisCacheUtil;

    /**
     * 添加奖品
     *
     * @param pointsLuckDrawPrize 奖品信息
     * @return 返回结果
     */
    @Override
    public RestfulResponse addDrawPrize(QrPointsLuckDrawPrize pointsLuckDrawPrize) {
        pointsLuckDrawPrize.setUpdateTime(new Date());
        save(pointsLuckDrawPrize);
        //更新Redis期数缓存
        redisCacheUtil.setPre(RedisPrefixEnum.LOTTERY_DRAW.getPrefix(), "LATEST_PHASE", Integer.toString(pointsLuckDrawPrize.getPhase()));
        return RestfulResponse.getRestfulResponse(StatusCode.ADD_SUCCESS);
    }
}
