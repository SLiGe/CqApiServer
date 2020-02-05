package cn.fanlisky.api.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 今日运势数据储存
 * </p>
 *
 * @author Gary
 * @since 2019-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonIgnoreProperties({"startDate", "endDate", "id"})
public class QrFortuneData implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * QQ号
     */
    @TableField("QQ")
    @ApiModelProperty(value = "QQ号")
    private String qq;

    /**
     * JSON数据
     */
    @TableField("JSON_DATA")
    private String jsonData;

    /**
     * QQ群号
     */
    @TableField("GROUP_NUM")
    @ApiModelProperty(value = "QQ群号")
    private String groupNum;

    /**
     * 创建日期
     */
    @TableField("CREATE_DATE")
    @ApiModelProperty(value = "创建日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createDate;

    /**
     * 修改日期
     */
    @TableField("UPDATE_DATE")
    @ApiModelProperty(value = "修改日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date updateDate;

    @ApiModelProperty(value = "开始日期")
    @TableField(exist = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private String startDate;

    @ApiModelProperty(value = "结束日期")
    @TableField(exist = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private String endDate;
}
