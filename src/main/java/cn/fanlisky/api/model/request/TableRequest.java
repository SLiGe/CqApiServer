package cn.fanlisky.api.model.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author Gary
 * @since 2019-07-14 18:47
 * <p>Code is my soul.<p/>
 */
@Data
class TableRequest {

    @ApiModelProperty(value = "页码")
    private Integer page;

    @ApiModelProperty(value = "页面容量")
    private Integer limit;

    @ApiModelProperty(value = "开始日期")
    private String startDate;

    @ApiModelProperty(value = "结束日期")
    private String endDate;

}
