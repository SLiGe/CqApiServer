package cn.fanlisky.api.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Gary
 * @since 2019-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MacUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    private Integer groupId;

    private String userName;

    private String userPwd;

    private String userNickName;

    private String userQq;

    private String userEmail;

    private String userPhone;

    private Boolean userStatus;

    private String userPortrait;

    private String userPortraitThumb;

    private String userOpenidQq;

    private String userOpenidWeixin;

    private String userQuestion;

    private String userAnswer;

    private Integer userPoints;

    private Integer userRegTime;

    private Integer userLoginTime;

    private Integer userLoginIp;

    private Integer userLastLoginTime;

    private Integer userLastLoginIp;

    private Integer userLoginNum;

    private Integer userExtend;

    private String userRandom;

    private Integer userEndTime;


}
