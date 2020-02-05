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
 * 抽奖概率限制表
 * </p>
 *
 * @author Gary
 * @since 2019-11-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class QrPointsLuckDrawProbability implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 奖品ID
     */
    private Long pointsPrizeId;

    /**
     * 奖品期数
     */
    private Integer pointsPrizePhase;

    /**
     * 概率
     */
    private Float probability;

    /**
     * 商品抽中后的冷冻次数
     */
    private Integer frozen;

    /**
     * 该商品平台每天最多抽中的次数
     */
    private Integer prizeDayMaxTimes;

    /**
     * 每位用户每月最多抽中该商品的次数
     */
    private Integer userPrizeMonthMaxTimes;

    private Date createTime;

    private Date updateTime;


}
