package cn.fanlisky.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 运势请求实体
 *
 * @author Gary
 * @since 2019-10-10 21:47
 * <p>Code is my soul.<p/>
 */
@Data
public class GetFortuneRequest {

    @ApiModelProperty(value = "QQ号")
    private String qq;

    @ApiModelProperty(value = "是否每天一次")
    private int isOne;

    @ApiModelProperty(value = "是否为群聊")
    private int isGroup;

    @ApiModelProperty(value = "是否积分制")
    private int isIntegral;

    @ApiModelProperty(value = "群号")
    private String groupNum;

}
