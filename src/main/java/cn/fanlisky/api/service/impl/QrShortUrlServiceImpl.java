package cn.fanlisky.api.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import cn.fanlisky.api.domain.QrShortUrl;
import cn.fanlisky.api.mapper.QrShortUrlMapper;
import cn.fanlisky.api.model.response.ShortenUrlResponse;
import cn.fanlisky.api.service.IQrShortUrlService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.fanlisky.api.utils.DateUtil;
import cn.fanlisky.api.utils.ExceptionUtil;
import cn.fanlisky.api.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Gary
 * @since 2019-11-17
 */
@Slf4j
@Service
public class QrShortUrlServiceImpl extends ServiceImpl<QrShortUrlMapper, QrShortUrl> implements IQrShortUrlService {

    @Value(value = "${api.short_url.host:http://127.0.0.1:8081/api/qr_short_url/redirect/}")
    private String urlPrefix;

    /**
     * 重定向Url
     *
     * @param code     url对于的随机字符
     * @param response 响应体
     */
    @Override
    public void redirectUrl(String code, HttpServletResponse response) {
        QrShortUrl shortUrl = getOne(Wrappers.<QrShortUrl>lambdaQuery().eq(QrShortUrl::getCode, code));
        try {
            response.sendRedirect(shortUrl.getOriginalUrl());
        } catch (IOException e) {
            log.error("[短链服务]----链接跳转失败,e: {}", ExceptionUtil.getStackTrace(e));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<ShortenUrlResponse> generateUrl(List<String> urls) {
        List<ShortenUrlResponse> responses = Lists.newLinkedList();
        List<QrShortUrl> shortUrls = Lists.newArrayList();
        if (CollectionUtil.isNotEmpty(urls)) {
            for (String url : urls) {
                ShortenUrlResponse urlResponse = new ShortenUrlResponse();
                String code = DigestUtil.md5Hex(UUIDUtil.getUUID() + System.currentTimeMillis());
                QrShortUrl shortUrl = new QrShortUrl();
                shortUrl.setCode(code).setCreateDate(new Date())
                        .setExpireDate(DateUtil.getAfterDate(30, new Date())).setOriginalUrl(url);
                shortUrls.add(shortUrl);
                urlResponse.setLongUrl(url);
                urlResponse.setShortUrl(urlPrefix + code);
                responses.add(urlResponse);
            }
            boolean saveBatchResult = saveBatch(shortUrls);
            if (saveBatchResult) {
                log.info("[短链服务]----短链保存成功");
            } else {
                log.warn("[短链服务]----短链保存失败");
            }
        }
        return responses;
    }

}
