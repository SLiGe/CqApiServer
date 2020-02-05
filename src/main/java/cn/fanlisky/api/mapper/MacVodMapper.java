package cn.fanlisky.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.fanlisky.api.model.response.FindMoviesResponse;
import cn.fanlisky.api.domain.MacVod;
import cn.fanlisky.api.domain.RecommendMovie;
import cn.fanlisky.api.model.request.FindMoviesRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Gary
 */

public interface MacVodMapper extends BaseMapper<MacVod> {

    /**
     * 根据电影名称获取电影列表
     *
     * @param findMoviesRequest
     * @return
     */
    List<FindMoviesResponse> selectByMovieName(FindMoviesRequest findMoviesRequest);

    /**
     * 获取推荐电影
     *
     * @param id
     * @return
     */
    RecommendMovie getRecommendFilm(Integer id);

    /**
     * 根据电影名称获取推荐电影列表
     *
     * @param movieName 电影名称
     * @return
     */
    List<RecommendMovie> getMovieByName(@Param("movieName") String movieName);
}