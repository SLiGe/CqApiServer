package cn.fanlisky.api.service;

import cn.fanlisky.api.domain.QrLotteryUser;
import cn.fanlisky.api.domain.QrPointsLuckDrawRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.fanlisky.api.model.response.RestfulResponse;

/**
 * <p>
 * 抽奖记录表 服务类
 * </p>
 *
 * @author Gary
 * @since 2019-11-18
 */
public interface IQrPointsLuckDrawRecordService extends IService<QrPointsLuckDrawRecord> {

    /**
     * QQ抽奖
     *
     * @param qrLotteryUser 用户参数
     * @return 返回结果
     */
    RestfulResponse lotteryFromQq(QrLotteryUser qrLotteryUser);

    /**
     * 执行开奖
     *
     * @return 返回结果
     */
    RestfulResponse executeDraw();
}
