package cn.fanlisky.api.service;

import cn.fanlisky.api.domain.QrShortUrl;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.fanlisky.api.model.response.ShortenUrlResponse;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Gary
 * @since 2019-11-17
 */
public interface IQrShortUrlService extends IService<QrShortUrl> {

    /**
     * 重定向Url
     *
     * @param code     url对于的随机字符
     * @param response 响应体
     */
    void redirectUrl(String code, HttpServletResponse response);

    /**
     * 生成链接
     *
     * @param urls 链接
     * @return 返回缩短内容
     */
    List<ShortenUrlResponse> generateUrl(List<String> urls);

}
