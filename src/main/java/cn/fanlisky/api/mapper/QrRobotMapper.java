package cn.fanlisky.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.fanlisky.api.domain.QrRobot;

import java.util.List;

/**
 * @author Gary
 */
public interface QrRobotMapper extends BaseMapper<QrRobot> {

    /**
     * 获取机器人授权到期
     *
     * @return 返回结构钢
     */
    List<QrRobot> getAuthEnd();
}