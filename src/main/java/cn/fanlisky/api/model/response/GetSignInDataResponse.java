package cn.fanlisky.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取我的签到数据响应实体
 * @author Gary
 * @since  2019-03-23 01:39
 * <p>Code is my soul.<p/>
 */
@Data
public class GetSignInDataResponse {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "当前等级")
    private String qLevel;

    @ApiModelProperty(value = "QQ号")
    private String qq;

    @ApiModelProperty(value = "积分数")
    private String integral;

    @ApiModelProperty(value = "月签到天使")
    private String mDays;

    @ApiModelProperty(value = "总签到天数")
    private String  tDays;

    @ApiModelProperty(value = "每日一句")
    private String todayMsg;
}
