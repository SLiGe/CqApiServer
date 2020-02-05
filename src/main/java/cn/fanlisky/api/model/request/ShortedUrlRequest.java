package cn.fanlisky.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 缩短域名请求实体
 *
 * @author Gary
 * @since 2018-12-25 16:05
 */
@Data
public class ShortedUrlRequest {
    @ApiModelProperty(value = "链接列表")
    private List<String> urls;

    @ApiModelProperty(value = "短链渠道")
    private String cno;

}
