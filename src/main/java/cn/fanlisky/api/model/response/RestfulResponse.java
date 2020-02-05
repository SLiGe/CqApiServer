package cn.fanlisky.api.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import cn.fanlisky.api.enums.StatusCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author : Gary
 * @since 2018/12/15 15:34
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler", "statusCode"}, ignoreUnknown = true)
public class RestfulResponse<T> implements Serializable {

    private static final long serialVersionUID = -5224550463170765204L;

    @ApiModelProperty(value = "状态码")
    private int code;

    @ApiModelProperty(value = "接口状态码")
    private String status;

    @ApiModelProperty(value = "接口状态信息")
    private String msg;

    @ApiModelProperty(value = "响应数据")
    private T data;

    public static <T> RestfulResponse<T> getRestfulResponse(StatusCode statusCode) {
        return getRestfulResponse(statusCode, null);
    }

    public static <T> RestfulResponse<T> getRestfulResponse(StatusCode statusCode, T data) {
        RestfulResponse<T> restfulResponse = new RestfulResponse<T>();
        restfulResponse.setCode(200);
        restfulResponse.setStatus(statusCode.getCode());
        restfulResponse.setMsg(statusCode.getMsg());
        if (data != null) {
            restfulResponse.setData(data);
        }
        return restfulResponse;
    }

}
