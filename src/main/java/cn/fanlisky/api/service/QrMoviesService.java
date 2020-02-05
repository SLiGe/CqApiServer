package cn.fanlisky.api.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.huaban.analysis.jieba.JiebaSegmenter;
import cn.fanlisky.api.config.DynamicDataSourceContextHolder;
import cn.fanlisky.api.constant.MovieConfig;
import cn.fanlisky.api.domain.ArticleItem;
import cn.fanlisky.api.domain.MacVod;
import cn.fanlisky.api.enums.DataSourceType;
import cn.fanlisky.api.model.response.FindMoviesResponse;
import cn.fanlisky.api.domain.RecommendMovie;
import cn.fanlisky.api.enums.RedisPrefixEnum;
import cn.fanlisky.api.enums.StatusCode;
import cn.fanlisky.api.model.response.ShortenUrlResponse;
import cn.fanlisky.api.mapper.MacVodMapper;
import cn.fanlisky.api.model.request.FindMoviesRequest;
import cn.fanlisky.api.model.response.RestfulResponse;
import cn.fanlisky.api.utils.RedisCacheUtil;
import cn.fanlisky.api.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 电影相关业务
 *
 * @author : Gary
 * @date : 2018/12/4 14:25
 */
@Slf4j
@Service
public class QrMoviesService extends ServiceImpl<MacVodMapper, MacVod> {

    private RedisCacheUtil redisCacheUtil;
    private ShortenUrlUtil shortenUrlUtil;
    private WeChatUtil weChatUtil;

    private static Integer vodCount;

    private MovieConfig movieConfig;

    public QrMoviesService(RedisCacheUtil redisCacheUtil, ShortenUrlUtil shortenUrlUtil, WeChatUtil weChatUtil, MovieConfig movieConfig) {
        this.redisCacheUtil = redisCacheUtil;
        this.shortenUrlUtil = shortenUrlUtil;
        this.weChatUtil = weChatUtil;
        this.movieConfig = movieConfig;
    }


    @PostConstruct
    public final void setVodCount() {
        String countStr = redisCacheUtil.getPre(RedisPrefixEnum.DATA_COUNT.getPrefix(), "MAC_VOD");
        vodCount = Integer.parseInt(countStr == null ? "0" : countStr);
    }

    /**
     * 查找电影
     *
     * @param findMoviesRequest
     * @return : java.util.List<FindMoviesResponse>
     * @author : LiGe
     * @date : 12:47 2018/12/5
     **/
    public RestfulResponse findMovie(FindMoviesRequest findMoviesRequest, HttpServletRequest httpServletRequest) {
        //替换电影名称
        replaceMovieName(findMoviesRequest);
        RestfulResponse<List<FindMoviesResponse>> restfulResponse = new RestfulResponse<>();
        restfulResponse.setCode(200);
        final String noMovie = "没有找到影片!";
        boolean hasFilm = redisCacheUtil.hasKey(RedisPrefixEnum.MOVIE.getPrefix() + RedisCacheUtil.KEY_SPLIT + findMoviesRequest.getMovieName());
        /*如果缓存有电影数据*/
        if (hasFilm) {
            String filmData = redisCacheUtil.getPre(RedisPrefixEnum.MOVIE.getPrefix(), findMoviesRequest.getMovieName());
            //取出JSON会被转移，转为集合即可
            List<FindMoviesResponse> findMoviesResponses = JSON.parseArray(filmData, FindMoviesResponse.class);
            //更新缓存
            redisCacheUtil.expire(RedisPrefixEnum.MOVIE.getPrefix() + RedisCacheUtil.KEY_SPLIT + findMoviesRequest.getMovieName(), 3600, TimeUnit.SECONDS);
            return RestfulResponse.getRestfulResponse(StatusCode.QUERY_SUCCESS, findMoviesResponses);
        } else {
            /*如果缓存中没有电影数据*/
            List<FindMoviesResponse> findMoviesResponseList = Lists.newArrayList();
            try {
                DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
                findMoviesResponseList = selectByMovieName(findMoviesRequest);
                DynamicDataSourceContextHolder.clearDataSourceType();
                if (!ObjectUtil.isNullOrEmpty(findMoviesResponseList)) {
                    findMoviesResponseList.forEach(val -> val.setFilmUrl(movieConfig.moviePlayUrl + val.getFilmUrl() + ".html"));
                }
                findMoviesResponseList = shortenUrlUtil.formatMovieUrls(findMoviesResponseList, findMoviesRequest.getCno());
            } catch (Exception e) {
                log.error(ExceptionUtils.getStackTrace(e));
            }
            if (!ObjectUtil.isNullOrEmpty(findMoviesResponseList)) {
                String filmData = RedisCacheUtil.beanToString(findMoviesResponseList);
                redisCacheUtil.setPreWithExpireTime(RedisPrefixEnum.MOVIE.getPrefix(), findMoviesRequest.getMovieName(), filmData, 3600);
                return RestfulResponse.getRestfulResponse(StatusCode.QUERY_SUCCESS, findMoviesResponseList);
            }

            if (ObjectUtil.isNullOrEmpty(findMoviesResponseList)) {
                findMoviesResponseList.add(new FindMoviesResponse(noMovie, movieConfig.moviePicUrl + "index.php/vod/search.html"));
                restfulResponse.setData(findMoviesResponseList);
                restfulResponse.setMsg("未找到");
                restfulResponse.setStatus("200");
                log.info("[搜索服务]----请求IP:" + IpUtil.getIpAddress(httpServletRequest) + ",搜索的关键词为: " + findMoviesRequest.getMovieName() + ",无结果");
            }
            if (!noMovie.equals(findMoviesResponseList.get(0).getFilmName())) {
                log.info("[搜索服务]----请求IP:" + IpUtil.getIpAddress(httpServletRequest) + ",搜索的关键词为: " + findMoviesRequest.getMovieName() + ",结果数为:" + findMoviesResponseList.size());
            }
        }

        return restfulResponse;
    }


    /**
     * 根据名称搜索电影
     *
     * @param findMoviesRequest 查找电影实体
     * @return 返回内容
     */
    private List<FindMoviesResponse> selectByMovieName(FindMoviesRequest findMoviesRequest) {
        final String limitSql = "  limit " + findMoviesRequest.getPageSize();
        List<MacVod> macVods = list(Wrappers.<MacVod>lambdaQuery()
                .like(MacVod::getVodName, findMoviesRequest.getMovieName()).select(MacVod::getVodName, MacVod::getVodId)
                .last(limitSql));
        if (ObjectUtil.isNullOrEmpty(macVods)) {
            final JiebaSegmenter jiebaSegmenter = new JiebaSegmenter();
            List<String> names = jiebaSegmenter.sentenceProcess(findMoviesRequest.getMovieName());
            StringBuilder lastSql = new StringBuilder();
            int i = 0;
            for (String name : names) {
                if (i != 0) {
                    lastSql.append(" or vod_name like '%").append(name).append("%'");
                }
                i++;
            }
            lastSql.append(limitSql);
            List<MacVod> lastMacVods = list(Wrappers.<MacVod>lambdaQuery()
                    .like(MacVod::getVodName, names.get(0)).select(MacVod::getVodName, MacVod::getVodId).last(lastSql.toString()));
            return castVodResponse(lastMacVods);
        }
        return castVodResponse(macVods);
    }

    /**
     * 处理结果集
     */
    private List<FindMoviesResponse> castVodResponse(List<MacVod> macVods) {
        List<FindMoviesResponse> responses = Lists.newLinkedList();
        for (MacVod macVod : macVods) {
            FindMoviesResponse response = new FindMoviesResponse();
            response.setFilmUrl(Long.toString(macVod.getVodId()));
            response.setFilmName(macVod.getVodName());
            responses.add(response);
        }
        return responses;
    }

    /***
     *  getRecommendMovie
     *  获取推荐电影
     * @date 2019/3/29
     */
    public RestfulResponse<RecommendMovie> getRecommendMovie() {
        RecommendMovie recommendFilm;
        try {
            Random random = new Random();
            int id = random.nextInt(vodCount);
            recommendFilm = replaceFilmLink(this.baseMapper.getRecommendFilm(id));
        } catch (Exception e) {
            recommendFilm = replaceFilmLink(this.baseMapper.getRecommendFilm(vodCount));
        }
        return RestfulResponse.getRestfulResponse(StatusCode.QUERY_SUCCESS, recommendFilm);
    }


    /**
     * 获取微信发送电影
     *
     * @param reqMap
     * @param movieName
     * @date : 2019/4/6
     */
    String getWxMoviesMsg(Map<String, String> reqMap, String movieName) {
        final String moviePrefix = RedisPrefixEnum.WX_MOVIE.getPrefix() + RedisCacheUtil.KEY_SPLIT + movieName;
        final boolean hasData = redisCacheUtil.hasKey(moviePrefix);
        if (hasData) {
            String localMovie = redisCacheUtil.get(moviePrefix);
            redisCacheUtil.expire(moviePrefix, 3600,TimeUnit.SECONDS);
            return localMovie;
        }
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        List<RecommendMovie> moviesByName = this.baseMapper.getMovieByName(movieName);
        DynamicDataSourceContextHolder.clearDataSourceType();
        List<ArticleItem> sendMovieList = Lists.newArrayList();
        moviesByName.forEach(val -> {
            RecommendMovie recommendMovie = replaceFilmLink(val);
            ArticleItem articleItem = new ArticleItem();
            articleItem.setDescription(recommendMovie.getDetail()).setPicUrl(recommendMovie.getPic())
                    .setUrl(recommendMovie.getLink()).setTitle(recommendMovie.getName());
            sendMovieList.add(articleItem);
        });
        String sendMoviesMsg = weChatUtil.sendArticleMsg(reqMap, sendMovieList);
        log.info("[微信]---发送信息:{}", sendMoviesMsg);
        redisCacheUtil.setPreWithExpireTime(RedisPrefixEnum.WX_MOVIE.getPrefix(), movieName, sendMoviesMsg, 3600);
        return sendMoviesMsg;
    }

    /**
     * 处理传入的电影名
     *
     * @date 2018年12月24日17点01分
     */
    private void replaceMovieName(FindMoviesRequest findMoviesRequest) {
        String replacedName = findMoviesRequest.getMovieName().replace("《", "")
                .replace("》", "")
                .replace("。", "").replace(" ", "")
                .replace(".", "").replace("<", "").replace(">", "")
                .replace("\"", "").replace(",", "")
                .replace("，", "").replace("/", "").replace("\\", "");
        findMoviesRequest.setMovieName(replacedName);
    }

    /***
     *  replaceFilmLink
     *  处理推荐电影链接
     * @date 2019/3/29
     */
    private RecommendMovie replaceFilmLink(RecommendMovie movie) {
        String link = movieConfig.moviePlayUrl + movie.getLink() + ".html";
        List<ShortenUrlResponse> response = null;
        try {
            response = shortenUrlUtil.formatUrls(null, "3", false, link);
            movie.setLink(response.get(0).getShortUrl());
            movie.setPic(movieConfig.moviePicUrl + movie.getPic());
        } catch (UnsupportedEncodingException e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        return movie;
    }
}
