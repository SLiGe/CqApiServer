package cn.fanlisky.api.model.response;

import cn.fanlisky.api.enums.ResponseCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 前台表格渲染实体
 *
 * @author : Gary
 * @since : 2019-07-14 18:15
 * <p>Code is my soul.<p/>
 */
@Accessors(chain = true)
@Data
public class TableResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * status : 0
     * message :
     * total : 180
     * data : {"item":[{},{}]}
     */

    @ApiModelProperty(value = "状态")
    private int status;
    @ApiModelProperty(value = "状态信息")
    private String message;
    @ApiModelProperty(value = "数据总数")
    private long total;
    @ApiModelProperty(value = "响应数据")
    private List<T> data;

    public static <T> TableResponse<T> getTableResponse(ResponseCode responseCode, long total, List<T> data) {
        TableResponse<T> tableResponse = new TableResponse<>();
        tableResponse.setData(data).setMessage(responseCode.getMsg()).setStatus(responseCode.getCode()).setTotal(total);
        return tableResponse;
    }

}

