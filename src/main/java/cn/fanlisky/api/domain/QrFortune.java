package cn.fanlisky.api.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 运势表
 * </p>
 *
 * @author Gary
 * @since 2019-07-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("qr_fortune")
public class QrFortune implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 运情总结
     */
    @TableField("FORTUNE_SUMMARY")
    private String fortuneSummary;

    /**
     * 幸运星
     */
    @TableField("LUCKY_STAR")
    private String luckyStar;

    /**
     * 签文
     */
    @TableField("SIGN_TEXT")
    private String signText;

    /**
     * 解签
     */
    @TableField("UN_SIGN_TEXT")
    private String unSignText;


}
