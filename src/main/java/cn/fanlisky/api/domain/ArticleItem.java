package cn.fanlisky.api.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 微信发送实体类
 * @author Gary
 * @since 2019-04-06 12:50
 * <p>Code is my soul.<p/>
 */
@Accessors(chain = true)
@Data
public class ArticleItem {
    private String title;
    private String description;
    private String picUrl;
    private String url;
}
