package cn.fanlisky.api.mapper;

import cn.fanlisky.api.domain.QrRoute;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * Url拦截配置表 Mapper 接口
 * </p>
 *
 * @author LiGe
 * @since 2019-04-03
 */
public interface QrRouteMapper extends BaseMapper<QrRoute> {
    List<String> getHandledUrls();
}
