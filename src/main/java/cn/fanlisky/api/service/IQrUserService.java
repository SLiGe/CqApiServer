package cn.fanlisky.api.service;

import cn.fanlisky.api.domain.QrUser;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.fanlisky.api.model.response.RestfulResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author Gary
 * @since 2019-07-07
 */
public interface IQrUserService extends IService<QrUser> {

    /**
     * 用户登陆
     * @param qrUser 认证参数
     * @return 登陆结果*/
    RestfulResponse login(QrUser qrUser, HttpServletRequest request);

     /**
      * 处理登陆异常信息
      * @param e 异常
      * @return 返回结果*/
     RestfulResponse doLoginMsg(Exception e);

     QrUser getUser();

}
