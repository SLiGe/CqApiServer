package cn.fanlisky.api.service;

import cn.fanlisky.api.enums.StatusCode;
import cn.fanlisky.api.model.response.RestfulResponse;
import cn.fanlisky.api.utils.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *  用户个人中心获取业务
 * @author Gary
 * @date 2019-04-20 11:20
 */
@Slf4j
@Service
public class QrUserDataService {


    public RestfulResponse getPersonalData(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        String userJson = session.getAttribute(session.getId()) == null ? "" : session.getAttribute(session.getId()).toString();
        if (ObjectUtil.isNullOrEmpty(userJson)) {
            return RestfulResponse.getRestfulResponse(StatusCode.NOT_LOGIN);
        }
        //QrUser qrUser = JsonUtil.toBean(userJson, QrUser.class);
        //GetSignInDataResponse getSignInDataResponse = qrSigninDataMapper.querySignInData(qrUser.getName());
        //return RestfulResponse.getRestfulResponse(StatusCode.QUERY_SUCCESS, getSignInDataResponse);
        return null;
    }
}
