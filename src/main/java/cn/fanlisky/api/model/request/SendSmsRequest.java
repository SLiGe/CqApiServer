package cn.fanlisky.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 发送短信请求实体
 *
 * @author Gary
 * @since 2019-04-09 22:36
 * <p>Code is my soul.<p/>
 */
@Data
public class SendSmsRequest {

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "发送内容")
    private String content;
}
