package cn.fanlisky.api.model.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 微博缩短域名返回实体
 * @author Gary
 * @since : 2018/12/23 0:34
 */
@Data
public class WeiBoShortResponse {

    private String url_short;

    private String url_long;

    private String type;

    private String result;
}
