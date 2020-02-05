package cn.fanlisky.api.service;

import cn.fanlisky.api.domain.QrPointsLuckDrawPrize;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.fanlisky.api.model.response.RestfulResponse;

/**
 * <p>
 * 奖品表 服务类
 * </p>
 *
 * @author Gary
 * @since 2019-11-18
 */
public interface IQrPointsLuckDrawPrizeService extends IService<QrPointsLuckDrawPrize> {

    /**
     * 添加奖品
     *
     * @param pointsLuckDrawPrize 奖品信息
     * @return 返回结果
     */
    RestfulResponse addDrawPrize(QrPointsLuckDrawPrize pointsLuckDrawPrize);
}
