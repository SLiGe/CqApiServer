package cn.fanlisky.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.fanlisky.api.domain.MacUser;
import cn.fanlisky.api.domain.QrSignInData;
import cn.fanlisky.api.model.response.RestfulResponse;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Gary
 * @since 2019-05-28
 */
public interface IMacUserService extends IService<MacUser> {

     /**
      * 更新用户积分
      * @param qrSignInData 参数
      * */
     void toUpdateUserPoint(QrSignInData qrSignInData);

     /**
      * 转让积分
      * @param params 参数
      * @return 返回结果*/
     RestfulResponse transferPoints(Map<String, String> params);

}
