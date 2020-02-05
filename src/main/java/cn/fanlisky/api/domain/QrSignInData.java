package cn.fanlisky.api.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * QQ签到表
 * </p>
 *
 * @author LiGe
 * @since 2019-03-21
 */
@Data
@Accessors(chain = true)
@TableName("qr_signIn_data")
@JsonIgnoreProperties({"startDate", "endDate"})
public class QrSignInData implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @TableField("QQ")
    private String qq;

    /**
     * 积分
     */
    @TableField("INTEGRAL")
    private Integer integral;

    /**
     * 连续签到天数
     */
    @TableField("DAYS")
    private String days;

    @TableField("CREATE_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createDate;

    @ApiModelProperty(value = "开始日期")
    @TableField(exist = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private String startDate;

    @ApiModelProperty(value = "结束日期")
    @TableField(exist = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private String endDate;
}
