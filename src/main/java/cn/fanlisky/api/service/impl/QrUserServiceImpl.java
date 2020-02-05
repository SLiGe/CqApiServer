package cn.fanlisky.api.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.fanlisky.api.domain.QrUser;
import cn.fanlisky.api.enums.RedisPrefixEnum;
import cn.fanlisky.api.enums.StatusCode;
import cn.fanlisky.api.exception.UnknownAccountException;
import cn.fanlisky.api.mapper.QrUserMapper;
import cn.fanlisky.api.model.response.RestfulResponse;
import cn.fanlisky.api.utils.RedisCacheUtil;
import cn.fanlisky.api.service.IQrUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.fanlisky.api.utils.ObjectUtil;
import cn.fanlisky.api.utils.ServletUtils;
import cn.fanlisky.api.utils.UUIDUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author Gary
 * @since 2019-07-07
 */
@Service
public class QrUserServiceImpl extends ServiceImpl<QrUserMapper, QrUser> implements IQrUserService {

    @Resource
    private RedisCacheUtil redisCacheUtil;

    /**
     * 用户登陆
     *
     * @param qrUser 认证参数
     * @return 登陆结果
     */
    @Override
    public RestfulResponse login(QrUser qrUser, HttpServletRequest request) {
        String username = qrUser.getUserName();
        String password = qrUser.getPassword();
        if (ObjectUtil.isNullOrEmpty(username) || ObjectUtil.isNullOrEmpty(password)) {
            throw new UnknownAccountException();
        }
        QrUser localUser = getOne(Wrappers.<QrUser>lambdaQuery().eq(QrUser::getLoginName, username));
        if (ObjectUtil.isNullOrEmpty(localUser)) {
            throw new UnknownAccountException();
        }
        String newPass = DigestUtil.md5Hex(password + localUser.getSalt());
        if (newPass.equals(qrUser.getPassword())) {
            //登录成功
            //HttpSession session = request.getSession();
            //session.setAttribute("user",qrUser);
            String token = UUIDUtil.getUUID();
            ServletUtils.getResponse().setHeader("j-token",token);
            redisCacheUtil.setPreWithExpireTime(RedisPrefixEnum.LOGIN_TOKEN.getPrefix(), token, JSONObject.toJSONString(localUser),3600);
            return RestfulResponse.getRestfulResponse(StatusCode.LOGIN_SUCCESS);
        }
        /*String username = qrUser.getUserName();
        char[] passwordChars = usernamePasswordToken.getPassword();
        if (ObjectUtil.isNullOrEmpty(username) || ObjectUtil.isNullOrEmpty(passwordChars)) {
            throw new UnknownAccountException();
        }
        String password = String.valueOf(passwordChars);
        QrUser qrUser = getOne(Wrappers.<QrUser>lambdaQuery().eq(QrUser::getLoginName, username));
        if (ObjectUtil.isNullOrEmpty(qrUser)) {
            throw new IncorrectCredentialsException();
        }
        String newPass = DigestUtil.md5Hex(password + qrUser.getSalt());
        if (newPass.equals(qrUser.getPassword())) {
            //登录成功
            return qrUser;
        }*/

        //记住登录信息
        return RestfulResponse.getRestfulResponse(StatusCode.LOGIN_FAILED);
    }

    /**
     * 处理登陆异常信息
     *
     * @param e 异常
     * @return 返回结果
     */
    @Override
    public RestfulResponse doLoginMsg(Exception e) {
        /*final String unKnownAccount = UnknownAccountException.class.getName();
        final String incorrectCredentialsName = IncorrectCredentialsException.class.getName();
        final String lockedAccountName = LockedAccountException.class.getName();
        final String authenticationName =AuthenticationException.class.getName();
        String eName = e.getClass().getName();
        if (unKnownAccount.equals(eName)) {
            return RestfulResponse.getRestfulResponse(StatusCode.LOGIN_NO_USER);
        } else if (incorrectCredentialsName.equals(eName)) {
            return RestfulResponse.getRestfulResponse(StatusCode.LOGIN_PWD_FALSE);
        } else if (lockedAccountName.equals(eName)) {
            RestfulResponse.getRestfulResponse(StatusCode.LOGIN_USER_LOCKED);
        }else if (authenticationName.equals(eName)){
            return RestfulResponse.getRestfulResponse(StatusCode.LOGIN_PWD_FALSE);
        }*/
        return RestfulResponse.getRestfulResponse(StatusCode.LOGIN_UNKNOWN_ERROR);
    }

    @Override
    public QrUser getUser() {
        String token = ServletUtils.getHeader("j-token");
        String userJson = redisCacheUtil.getPre(RedisPrefixEnum.LOGIN_TOKEN.getPrefix(), token);
        return JSONObject.parseObject(userJson,QrUser.class);
    }

    private String generateRandomPasswordSalt() {
        return DigestUtils.sha256Hex(String.valueOf(System.nanoTime()));
    }

}
