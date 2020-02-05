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
 * 抽奖记录表
 * </p>
 *
 * @author Gary
 * @since 2019-11-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class QrPointsLuckDrawRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long memberId;

    /**
     * 中奖用户手机号
     */
    private String memberMobile;

    /**
     * 消耗积分
     */
    private Integer points;

    /**
     * 奖品ID
     */
    private Long prizeId;

    /**
     * 1:中奖 2:未中奖
     */
    private Integer result;

    /**
     * 中奖月份
     */
    private String month;

    /**
     * 中奖日期（不包括时间）
     */
    private Date daily;

    private Date createTime;

    private Date updateTime;


}
