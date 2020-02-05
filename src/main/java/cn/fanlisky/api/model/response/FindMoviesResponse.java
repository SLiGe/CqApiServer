package cn.fanlisky.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 查找电影实体类
 *
 * @author Gary
 * @since 2018/12/4 13:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindMoviesResponse {

    @ApiModelProperty(value = "电影名称")
    private String filmName;
    @ApiModelProperty(value = "电影地址")
    private String filmUrl;

}
