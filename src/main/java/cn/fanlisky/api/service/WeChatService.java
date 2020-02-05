package cn.fanlisky.api.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cn.fanlisky.api.constant.WeChatConfig;
import cn.fanlisky.api.utils.ExceptionUtil;
import cn.fanlisky.api.utils.HttpClientUtil;
import cn.fanlisky.api.utils.WeChatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 微信公众号业务处理类
 *
 * @author Gary
 * @since 2019-04-06 12:25
 * <p>Code is my soul.<p/>
 */
@Slf4j
@Service
public class WeChatService {

    @Resource
    private WeChatUtil weChatUtil;
    @Resource
    private QrMoviesService qrMoviesService;

    /**
     * 茉莉机器人API
     */
    private static final String JASMINE_API = "http://i.itpk.cn/api.php";

    private static final String JASMINE_API_KEY = "9fd8ea57fe82ed18aa99d57fb200d48b";

    private static final String JASMINE_API_SECRET = "d6ebb3g1s0ls";


    public String sendWxMsg(HttpServletRequest httpServletRequest) {
        String toUserMsg = "";
        Map<String, String> map = Maps.newHashMap();
        try {
            Map<String, String> reqMap = weChatUtil.parseXml(httpServletRequest);
            map = reqMap;
            String msgType = reqMap.get(WeChatConfig.MSG_TYPE);
            switch (msgType) {
                case WeChatConfig.REQ_MESSAGE_TYPE_TEXT:
                    toUserMsg = getMoviesByName(reqMap);
                    break;
                case WeChatConfig.REQ_MESSAGE_TYPE_EVENT:
                    toUserMsg = subscribeMsg(reqMap);
                    break;
                default:
                    toUserMsg = weChatUtil.sendTextMsg(reqMap, "阿呆暂时不知道怎么回答哦");
                    break;
            }

        } catch (Exception e) {
            log.error(ExceptionUtil.getStackTrace(e));
        }
        if (toUserMsg == null) {
            toUserMsg = weChatUtil.sendTextMsg(map, "阿呆暂时不知道怎么回答哦");
        }
        return toUserMsg;
    }

    /**
     * 处理电影事件
     *
     * @param reqMsg
     * @date 2019/4/6
     */
    private String getMoviesByName(Map<String, String> reqMsg) {
        String content = reqMsg.get(WeChatConfig.CONTENT);
        final List<String> movieParams = Lists.newArrayList(Arrays.asList("我要看", "我想看", "搜", "看"));
        final int searchIndex = 2;
        final int lookIndex = 3;
        String movieName;
        if (content.indexOf(movieParams.get(0)) == 0 || content.indexOf(movieParams.get(1)) == 0) {
            movieName = content.substring(3);
        } else if (content.indexOf(movieParams.get(searchIndex)) == 0 || content.indexOf(movieParams.get(lookIndex)) == 0) {
            movieName = content.substring(1);
        } else {
            String robotMsg = getRobotMsg(content);
            return weChatUtil.sendTextMsg(reqMsg, robotMsg);
        }
        return qrMoviesService.getWxMoviesMsg(reqMsg, movieName);
    }

    /**
     * getRobotMsg
     * 获取自定义回复消息
     *
     * @param content
     * @return java.lang.String
     * @date 2019/4/9
     */
    private String getRobotMsg(String content) {
        StringBuilder sb = new StringBuilder();
        sb.append("question=").append(content).append("&api_key=")
                .append(JASMINE_API_KEY).append("&api_secret=").append(JASMINE_API_SECRET);
        log.info("[茉莉API]----接口请求:{}", sb.toString());
        String res = HttpClientUtil.get(JASMINE_API, sb.toString());
        log.info("[茉莉API]----接口返回:{}", res);
        return res;
    }

    /**
     * 订阅事件消息
     *
     * @param reqMsg
     * @date 2019/4/6
     */
    private String subscribeMsg(Map<String, String> reqMsg) {
        String event = reqMsg.get(WeChatConfig.EVENT);
        final String toUserMsg = "欢迎小可爱的关注哦!看电影请发送如:我要看海王";
        String sendTextMsg = "";
        if (WeChatConfig.EVENT_TYPE_SUBSCRIBE.equalsIgnoreCase(event)) {
            //订阅事件,暂时发送文字
            sendTextMsg = weChatUtil.sendTextMsg(reqMsg, toUserMsg);
        }
        return sendTextMsg;
    }
}
