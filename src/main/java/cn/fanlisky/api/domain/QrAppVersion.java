package cn.fanlisky.api.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.JdbcType;

/**
 * <p>
 * app版本表
 * </p>
 *
 * @author Gary
 * @since 2020-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "qr_app_version")
public class QrAppVersion implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    /**
     * 应用名称
     */
    @TableField(value = "app_name", jdbcType = JdbcType.VARCHAR)
    @ApiModelProperty(value = "应用名称")
    private String appName;

    /**
     * 应用当前版本
     */
    @TableField(value = "app_current_version")
    private String appCurrentVersion;

    /**
     * 应用下载地址
     */
    @TableField(value = "app_download_address")
    private String appDownloadAddress;

    @TableField(value = "app_file_id")
    private Long appFileId;

    /**
     * 是否强制更新
     */
    @TableField(value = "app_force_version")
    private int appForceVersion;

    /**
     * 应用代码
     */
    @TableField(value = "app_code")
    private String appCode;

    /**
     * 更新说明
     */
    @TableField(value = "update_description")
    private String updateDescription;

    /**
     * 更新时间
     */
    @TableField(value = "update_date")
    private Date updateDate;

    /**
     * 创建时间
     */
    @TableField(value = "create_date")
    private Date createDate;


}
