package cn.fanlisky.api.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.fanlisky.api.domain.QrBeaSen;
import cn.fanlisky.api.enums.RedisPrefixEnum;
import cn.fanlisky.api.enums.StatusCode;
import cn.fanlisky.api.mapper.QrBeaSenMapper;
import cn.fanlisky.api.model.response.RestfulResponse;
import cn.fanlisky.api.utils.RedisCacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Random;

/**
 * 获取一言
 *
 * @author Gary
 * @since 2018/12/29 0:27
 */
@Slf4j
@Service
public class QrBeaSenService extends ServiceImpl<QrBeaSenMapper, QrBeaSen> {

    private static final String NO_RESULT = "请求过于频繁请稍后再试!";

    @PostConstruct
    public void setSentenceCount() {
        String countStr = RedisCacheUtil.getPre(RedisPrefixEnum.DATA_COUNT.getPrefix(), "QR_BEASEN");
        sentence_count = Integer.parseInt(countStr == null ? "0" : countStr);
    }

    public static Integer sentence_count;

    @Resource
    private RedisCacheUtil RedisCacheUtil;

    public RestfulResponse<String> getSentence() {
        Random random = new Random();
        Integer id = random.nextInt(sentence_count);
        QrBeaSen qrBeaSen = getById(id);
        String sentence = qrBeaSen == null ? NO_RESULT : qrBeaSen.getSentence();
        return RestfulResponse.getRestfulResponse(StatusCode.QUERY_SUCCESS, sentence);
    }

}
