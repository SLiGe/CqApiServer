package cn.fanlisky.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Gary
 * @since 2018/12/4 14:17
 */
@Data
public class FindMoviesRequest {
    @ApiModelProperty(value = "电影名称")
    private String movieName;
    /**
     * 方式
     */
    @ApiModelProperty(value = "短链渠道")
    private String cno;

    @ApiModelProperty(value = "分页参数")
    private Integer pageSize;
}
