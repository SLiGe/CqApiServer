package cn.fanlisky.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 缩短域名响应实体
 * @author Gary
 * @since 2018-12-25 15:23
 */
@Data
public class ShortenUrlResponse {
    @ApiModelProperty(value = "长链接")
    private String longUrl;
    @ApiModelProperty(value = "短链接")
    private String shortUrl;
}
