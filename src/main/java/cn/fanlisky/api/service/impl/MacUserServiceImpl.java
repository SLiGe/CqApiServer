package cn.fanlisky.api.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.fanlisky.api.config.DynamicDataSourceContextHolder;
import cn.fanlisky.api.domain.MacUser;
import cn.fanlisky.api.domain.QrSignInData;
import cn.fanlisky.api.enums.DataSourceType;
import cn.fanlisky.api.enums.StatusCode;
import cn.fanlisky.api.mapper.MacUserMapper;
import cn.fanlisky.api.model.response.RestfulResponse;
import cn.fanlisky.api.service.IMacUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Gary
 * @date 2019-05-28
 */
@Slf4j
@Service
public class MacUserServiceImpl extends ServiceImpl<MacUserMapper, MacUser> implements IMacUserService {

    @Override
    public void toUpdateUserPoint(QrSignInData qrSignInData) {
        try {
            DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
            MacUser macUser = getOne(Wrappers.<MacUser>lambdaQuery().eq(MacUser::getUserQq, qrSignInData.getQq()));
            if (ObjectUtil.isNotNull(macUser)) {
                Integer localUserPoints = macUser.getUserPoints();
                Integer newUserPoints = qrSignInData.getIntegral();
                int resultPoints = localUserPoints + newUserPoints;
                macUser.setUserPoints(resultPoints);
                updateById(macUser);
                log.info("[MACCMS]----更新积分成功!");
            }
        } catch (Exception e) {
            log.error("[MACCMS]----更新积分失败!");
        } finally {
            DynamicDataSourceContextHolder.clearDataSourceType();
        }
    }

    /**
     * 转让积分
     *
     * @param params 参数
     * @return 返回结果
     */
    @Override
    public RestfulResponse transferPoints(Map<String, String> params) {
        String jiFen = params.get("jifen");
        String username = params.get("username");
        String password = DigestUtil.md5Hex(params.get("password"));
        String otherUser = params.get("otherUser");
        QueryWrapper<MacUser> masterWrapper = new QueryWrapper<>();
        masterWrapper.lambda().eq(MacUser::getUserName, username).eq(MacUser::getUserPwd, password);
        MacUser macUser = getOne(masterWrapper);
        QueryWrapper<MacUser> solverWrapper = new QueryWrapper<>();
        solverWrapper.lambda().eq(MacUser::getUserName, otherUser);
        MacUser macUser1 = getOne(solverWrapper);
        if (ObjectUtil.isNotNull(macUser) && ObjectUtil.isNotNull(macUser1)) {
            try {
                int localJiFen = Integer.parseInt(jiFen);
                if (localJiFen > macUser.getUserPoints()) {
                    return RestfulResponse.getRestfulResponse(StatusCode.TRANS_LESS);
                }
                int masterJiFen = macUser.getUserPoints() - localJiFen;
                macUser.setUserPoints(masterJiFen);
                updateById(macUser);
                int otherJiFen = macUser1.getUserPoints() + localJiFen;
                macUser1.setUserPoints(otherJiFen);
                updateById(macUser1);
            } catch (Exception e) {
                log.info("[积分转让]---积分转让异常");
                return RestfulResponse.getRestfulResponse(StatusCode.TRANS_FAIL);
            }
        } else if (!ObjectUtil.isNotNull(macUser)) {
            return RestfulResponse.getRestfulResponse(StatusCode.TRANS_FAIL);
        } else if (!ObjectUtil.isNotNull(macUser1)) {
            return RestfulResponse.getRestfulResponse(StatusCode.TRANS_FAIL);
        }

        return RestfulResponse.getRestfulResponse(StatusCode.TRANS_SUCCESS);
    }
}
