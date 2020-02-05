package cn.fanlisky.api.controller;

import cn.fanlisky.api.annotation.WebLog;
import cn.fanlisky.api.enums.Title;
import cn.fanlisky.api.model.request.FindMoviesRequest;
import cn.fanlisky.api.model.response.RestfulResponse;
import cn.fanlisky.api.service.QrMoviesService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 电影相关
 * @author : LiGe
 * @since 2018/12/4 14:36
 */
@RestController
@RequestMapping("/niuren")
public class QrMoviesController {
    @Resource
    private QrMoviesService qrMoviesService;

    @WebLog(title = Title.GET_MOVIE)
    @ApiOperation(notes = "获取电影链接接口",value = "获取电影链接接口")
    @PostMapping(value = "/findMovies")
    public RestfulResponse findMovies(@RequestBody FindMoviesRequest findMoviesRequest, HttpServletRequest httpServletRequest){

        return qrMoviesService.findMovie(findMoviesRequest,httpServletRequest);
    }

    @WebLog(title = Title.GET_RECOMMEND_MOVIE)
    @ApiOperation(notes = "获取推荐电影接口",value = "获取推荐电影接口")
    @GetMapping(value = "/getRecommendFilm")
    public RestfulResponse getRecommendFilm(){
        return qrMoviesService.getRecommendMovie();
    }


}
