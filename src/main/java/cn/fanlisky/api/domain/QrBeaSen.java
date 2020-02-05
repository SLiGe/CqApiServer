package cn.fanlisky.api.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * @author Gary*/

@TableName("qr_beasen")
public class QrBeaSen implements Serializable {

    private static final long serialVersionUID = -335991289492614867L;

    @TableId("id")
    private Integer id;

    private String sentence;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence == null ? null : sentence.trim();
    }
}