package cn.fanlisky.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * QQ签到请求实体
 *
 * @author Gary
 * @since 2019-03-23 00:56
 * <p>Code is my soul.<p/>
 */
@Data
public class SignInForQqRequest {

    @ApiModelProperty(value = "QQ号")
    private String qq;

    /**
     * 积分
     */
    @ApiModelProperty(value = "积分")
    private String integral;
}
