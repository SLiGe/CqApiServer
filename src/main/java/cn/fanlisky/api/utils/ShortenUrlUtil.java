package cn.fanlisky.api.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import cn.fanlisky.api.model.response.SuoUrlResponse;
import cn.fanlisky.api.model.response.FindMoviesResponse;
import cn.fanlisky.api.model.response.ShortenUrlResponse;
import cn.fanlisky.api.model.response.WeiBoShortResponse;
import cn.fanlisky.api.service.IQrShortUrlService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

/**
 * 缩短域名工具类
 *
 * @author Gary
 * @since 2018-12-25 15:17
 */
@Component
public class ShortenUrlUtil {

    private static final String ENC = "UTF-8";
    /**
     * suo.im
     */
    private static final String CHANNEL_ONE = "http://suo.im/api.php?format=json&url=";
    /**
     * ft12.com
     */
    private static final String CHANNEL_TWO = "http://api.ft12.com/api.php?format=json&url=";
    /**
     * 新浪缩短域名地址:最安全,已停止服务
     */
    @Value(value = "${sina.shortUrl.api}")
    private String sinaApi;

    @Resource
    private IQrShortUrlService qrShortUrlService;

    /**
     * 缩短多个域名
     */
    public List<ShortenUrlResponse> formatUrls(List<String> list, String channelNo, boolean isArray, String link) throws UnsupportedEncodingException {
        List<ShortenUrlResponse> responses = Lists.newLinkedList();
        switch (channelNo) {
            case "1":
                for (String s : list) {
                    ShortenUrlResponse response = toGetUrlResBean(s, CHANNEL_ONE);
                    responses.add(response);
                }
                break;
            case "2":
                for (String s : list) {
                    ShortenUrlResponse response = toGetUrlResBean(s, CHANNEL_TWO);
                    responses.add(response);
                }
                break;
            case "3":
                StringBuilder sbPost = new StringBuilder();
                /*encode the urls*/
                String encodeUrl = StringUtils.EMPTY;
                sbPost.append(sinaApi);
                for (int i = 0; i < list.size(); i++) {
                    if (i == list.size() - 1) {
                        encodeUrl = URLEncoder.encode(list.get(i), ENC);
                        sbPost.append(encodeUrl);
                    } else {
                        encodeUrl = URLEncoder.encode(list.get(i), ENC);
                        sbPost.append(encodeUrl).append("&url_long=");
                    }
                }
                String suoResult = HttpClientUtil.sendGet(sbPost.toString());
                for (int i = 0; i < list.size(); i++) {
                    ShortenUrlResponse response = new ShortenUrlResponse();
                    List<WeiBoShortResponse> weiBoShortResponses = JSONObject.parseArray(suoResult, WeiBoShortResponse.class);
                    response.setLongUrl(list.get(i));
                    response.setShortUrl(weiBoShortResponses.get(i).getUrl_short());
                    responses.add(response);
                }
                break;
            default:
                break;
        }
        return responses;
    }


    private ShortenUrlResponse toGetUrlResBean(String url, String channelNo) {
        ShortenUrlResponse response = new ShortenUrlResponse();
        String suoResult = HttpClientUtil.sendGet(channelNo + url);
        SuoUrlResponse suoUrlResponse = JSON.parseObject(suoResult, SuoUrlResponse.class);
        response.setLongUrl(url);
        if (CHANNEL_ONE.equalsIgnoreCase(channelNo)) {
            response.setShortUrl(suoUrlResponse.getUrl().replaceAll("\\\\", ""));
        } else {
            response.setShortUrl(suoUrlResponse.getUrl());
        }
        return response;
    }

    private FindMoviesResponse getMovieShortUrlBean(String filmName, String url, String channelNo) {
        FindMoviesResponse findMoviesResponse = new FindMoviesResponse();
        String suoResult = HttpClientUtil.sendGet(channelNo + url);
        SuoUrlResponse suoUrlResponse = JSON.parseObject(suoResult, SuoUrlResponse.class);
        findMoviesResponse.setFilmName(filmName);
        if (CHANNEL_ONE.equalsIgnoreCase(channelNo)) {
            findMoviesResponse.setFilmUrl(suoUrlResponse.getUrl().replaceAll("\\\\", ""));
        } else {
            findMoviesResponse.setFilmUrl(suoUrlResponse.getUrl());
        }
        return findMoviesResponse;
    }

    /**
     * 缩短电影域名
     */
    public List<FindMoviesResponse> formatMovieUrls(List<FindMoviesResponse> list, String channel) throws UnsupportedEncodingException {
        List<FindMoviesResponse> responses = new LinkedList<>();
        // 发现这部的判断貌似没有用,因为这俩渠道都会封,新浪的可以考虑!
        switch (channel) {
            case "1":
                for (FindMoviesResponse findMoviesResponse1 : list) {
                    FindMoviesResponse findMoviesResponse = getMovieShortUrlBean(findMoviesResponse1.getFilmName(), findMoviesResponse1.getFilmUrl(), CHANNEL_ONE);
                    responses.add(findMoviesResponse);
                }
                break;
            case "2":
                for (FindMoviesResponse findMoviesResponse1 : list) {
                    FindMoviesResponse findMoviesResponse = getMovieShortUrlBean(findMoviesResponse1.getFilmName(), findMoviesResponse1.getFilmUrl(), CHANNEL_TWO);

                    responses.add(findMoviesResponse);
                }
                break;
            case "3":
                /*encode the urls*/
                String encodeUrl = StringUtils.EMPTY;
                StringBuilder post = new StringBuilder().append(sinaApi);
                for (int i = 0; i < list.size(); i++) {
                    if (i == list.size() - 1) {
                        encodeUrl = URLEncoder.encode(list.get(i).getFilmUrl(), ENC);
                        post.append(encodeUrl);
                    } else {
                        encodeUrl = URLEncoder.encode(list.get(i).getFilmUrl(), ENC);
                        post.append(encodeUrl).append("&url_long=");
                    }
                }
                String suoResult = HttpClientUtil.sendGet(post.toString());
                for (int i = 0; i < list.size(); i++) {
                    FindMoviesResponse findMoviesResponse = new FindMoviesResponse();
                    List<WeiBoShortResponse> weiBoShortResponses = JSONObject.parseArray(suoResult, WeiBoShortResponse.class);
                    findMoviesResponse.setFilmName(list.get(i).getFilmName());
                    findMoviesResponse.setFilmUrl(weiBoShortResponses.get(i).getUrl_short());
                    responses.add(findMoviesResponse);
                }
                break;
            case "4":
                List<String> urls = Lists.newArrayList();
                list.forEach(val -> {
                    urls.add(val.getFilmUrl());
                    List<ShortenUrlResponse> urlResponseList = qrShortUrlService.generateUrl(urls);
                    if (CollectionUtil.isNotEmpty(urlResponseList)) {
                        val.setFilmUrl(urlResponseList.get(0).getShortUrl());
                    }
                    urls.remove(val.getFilmUrl());
                    responses.add(val);
                });
                break;
            default:
                break;
        }
        return responses;
    }
}
