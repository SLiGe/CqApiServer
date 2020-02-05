package cn.fanlisky.api.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cn.fanlisky.api.constant.MovieConfig;
import cn.fanlisky.api.domain.QrRobot;
import cn.fanlisky.api.mapper.QrRobotMapper;
import cn.fanlisky.api.utils.TCPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author : Gary
 */

@Slf4j
@Service
public class QrRobotDataService extends ServiceImpl<QrRobotMapper, QrRobot> {

    @Resource
    private MovieConfig movieConfig;

    /**
     * 获取授权到期人员
     */
    public List<QrRobot> getEndDate() {
        List<QrRobot> qrRobots = this.baseMapper.getAuthEnd();
        TCPUtil.sendTCPRequest(movieConfig.socketCqpIp, movieConfig.socketCqpPort, JSON.toJSONString(qrRobots), "gb2312");
        if (qrRobots.isEmpty()) {
            log.info("[QQ机器人]----未有到期授权");
        } else {
            log.info("[QQ机器人]----到期人员:{}", qrRobots.size());
        }
        return qrRobots;
    }

    /**
     * 获取全部未到期人员
     */
    public List<Map<String, String>> getNoEnd() {
        Date currentDate = new Date();
        List<QrRobot> qrRobots = list(Wrappers.<QrRobot>lambdaQuery().gt(QrRobot::getEndDate, currentDate));
        List<Map<String, String>> data = Lists.newArrayList();
        if (!qrRobots.isEmpty()) {
            for (QrRobot robot : qrRobots) {
                Map<String, String> map = Maps.newHashMap();
                map.put("GroupNum", robot.getGroupNum());
                map.put("RobotQQ", robot.getRobotQq());
                data.add(map);
            }
        }
        return data;
    }


}
