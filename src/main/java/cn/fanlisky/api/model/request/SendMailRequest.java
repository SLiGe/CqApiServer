package cn.fanlisky.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 发送邮件接收实体
 * @author Gary
 * @since 2018/12/4 10:14
 */
@Data
@Accessors(chain = true)
public class SendMailRequest{

    private PublicLockRequest publicKey;

    @ApiModelProperty(value = "欲发送邮箱地址")
    private String userMail;

    @ApiModelProperty(value = "欲发送邮箱地址")
    private String sendContent;

    @ApiModelProperty(value = "邮件标题")
    private String emailTitle;

}
