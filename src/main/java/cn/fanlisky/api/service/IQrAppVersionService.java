package cn.fanlisky.api.service;

import cn.fanlisky.api.domain.QrAppVersion;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.fanlisky.api.exception.DownFileException;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * app版本表 服务类
 * </p>
 *
 * @author Gary
 * @since 2020-01-05
 */
public interface IQrAppVersionService extends IService<QrAppVersion> {

    void getApp(QrAppVersion appVersion, HttpServletResponse response);
}
