package cn.fanlisky.api.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 奖品表
 * </p>
 *
 * @author Gary
 * @since 2019-11-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class QrPointsLuckDrawPrize implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 奖品名称
     */
    private String name;

    /**
     * 图片地址
     */
    private String url;

    /**
     * 数量
     */
    private String value;

    /**
     * 类型1:红包2:积分3:礼物4:谢谢惠顾5:自定义
     */
    private Integer type;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 是否删除
     */
    private Boolean isDel;

    /**
     * 位置
     */
    private Integer position;

    /**
     * 期数
     */
    private Integer phase;

    private Date createTime;

    private Date updateTime;


}
