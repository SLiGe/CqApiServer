package cn.fanlisky.api.service;

import cn.fanlisky.api.domain.QrFortune;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.fanlisky.api.domain.QrFortuneData;
import cn.fanlisky.api.model.response.RestfulResponse;
import cn.fanlisky.api.model.response.TableResponse;

import java.io.IOException;
import java.util.Map;

/**
 * <p>
 * 运势表 服务类
 * </p>
 *
 * @author Gary
 * @since 2019-07-09
 */
public interface IQrFortuneService extends IService<QrFortune> {

    /**
     * 抓取运势信息
     *
     * @throws IOException :Jsoup异常
     */
    @Deprecated
    void toGetFortune() throws IOException;

    /**
     * 获取今日运势
     *
     * @param qq       用户QQ
     * @param isOne    是否每天只发送一次
     * @param isGroup  是否为群
     * @param oldApi   是否老接口
     * @param groupNum 群号
     * @param isIntegral 是否积分制
     * @return 返回结果
     */
    RestfulResponse<QrFortune> getFortuneOfToday(String qq, int isOne, int isGroup, boolean oldApi, String groupNum,int isIntegral);

    /**
     * 获取用户今日运势列表
     *
     * @param request 列表请求参数
     * @return 返回LayUi表格数据
     */
    TableResponse getUserFortuneOfToday(QrFortuneData request);

    /**
     * 获取今日用户数量
     *
     * @return 返回数量
     */
    Map<String, Integer> getFortuneCountOfToday();
}
