package cn.fanlisky.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.fanlisky.api.domain.QrSignInData;
import cn.fanlisky.api.model.response.GetSignInDataResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * QQ签到表 Mapper 接口
 * </p>
 *
 * @author Gary
 * @since 2019-03-21
 */
public interface QrSignInDataMapper extends BaseMapper<QrSignInData> {

    /**
     * 查询签到数据
     * @param qq QQ号
     * @return 返回结果*/
    GetSignInDataResponse querySignInData(@Param("qq") String qq);

    /**
     * 获取签到列表
     * @param qrSignInData 签到参数
     * @return 返回列表*/
    List<GetSignInDataResponse> getSignList(QrSignInData qrSignInData);
}
