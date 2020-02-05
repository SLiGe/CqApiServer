package cn.fanlisky.api.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.fanlisky.api.utils.DateUtil;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Gary
 */

@Data
@TableName("qr_robot")
public class QrRobot implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @TableField("id")
    private Integer id;

    @TableField("client_qq")
    private String clientQq;

    @TableField("robot_qq")
    private String robotQq;

    @TableField("group_num")
    private String groupNum;

    @TableField("start_date")
    @DateTimeFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    private Date startDate;

    @TableField("end_date")
    private Date endDate;

    @TableField(exist = false)
    private String endDateStr;

    public String getEndDateStr() {
        return DateUtil.parseDateToStr(DateUtil.YYYY_MM_DD_HH_MM_SS, this.endDate);
    }

}