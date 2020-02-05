package cn.fanlisky.api.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 签到天数关联表
 * </p>
 *
 * @author LiGe
 * @since 2019-03-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("qr_signIn_days")
public class QrSignInDays implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @TableField("QQ")
    private String qq;

    /**
     * 月连续签到天数
     */
    @TableField("MDAYS")
    private String mdays;

    /**
     * 总天数
     */
    @TableField("TDAYS")
    private String tdays;


}
