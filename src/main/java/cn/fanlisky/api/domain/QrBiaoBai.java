package cn.fanlisky.api.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
@Accessors(chain = true)
@Data
@TableName("qr_biaobai")
public class QrBiaoBai {
    @TableId("id")
    private Integer id;

    private String content;

    private String qq;

    private String me;

    private String you;

    private Date addtime;

    private Date uptime;

}