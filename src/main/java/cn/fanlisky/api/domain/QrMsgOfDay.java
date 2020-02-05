package cn.fanlisky.api.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 每日一句
 * </p>
 *
 * @author Gary
 * @since 2019-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "qr_msg_of_day")
public class QrMsgOfDay implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @TableField("SENTENCE")
    private String sentence;

    @TableField("CREATE_DATE")
    private Date createDate;

    /**
     * 发送日期
     */
    @TableField("SEND_DATE")
    private String sendDate;


}
