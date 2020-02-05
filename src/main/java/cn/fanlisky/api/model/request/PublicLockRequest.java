package cn.fanlisky.api.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 *  密钥公共实体类
 * @author Gary
 * @since 2018/12/4 10:28
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "秘钥实体")
public class PublicLockRequest {

    @ApiModelProperty(value = "密钥用户号")
    private String accessUser;

    @ApiModelProperty(value = "用户秘钥")
    private String accessPassword;
}
