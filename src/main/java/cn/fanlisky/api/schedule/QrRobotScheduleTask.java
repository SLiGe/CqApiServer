package cn.fanlisky.api.schedule;

import com.alibaba.fastjson.JSON;
import cn.fanlisky.api.constant.MovieConfig;
import cn.fanlisky.api.model.request.SendMailRequest;
import cn.fanlisky.api.domain.QrRobot;
import cn.fanlisky.api.domain.QrSignInDays;
import cn.fanlisky.api.mapper.QrRobotMapper;
import cn.fanlisky.api.mapper.QrSigninDaysMapper;
import cn.fanlisky.api.utils.ObjectUtil;
import cn.fanlisky.api.utils.SendMailUtil;
import cn.fanlisky.api.utils.TCPUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 机器人定时任务类
 *
 * @author Gary
 * @since 2018-12-25 11:45
 */
@Slf4j
@Component("qrRobotScheduledTask")
public class QrRobotScheduleTask {

    @Resource
    private QrRobotMapper qrRobotMapper;
    @Resource
    private QrSigninDaysMapper qrSigninDaysMapper;
    @Resource
    private SendMailUtil sendMailUtil;
    @Resource
    private MovieConfig movieConfig;


    private static final long START_TIME = 60000 * 60 * 24;

    /**
     * 每间隔进行查库并发送到服务端
     * 分隔符约定 $1$ $2$ $3$ $4$
     * 0 0 12 * * ?
     */
    @Scheduled(cron = "0 0 9/3 * * ?")
    public void getRobotEnd() {

        List<QrRobot> qrRobots = qrRobotMapper.getAuthEnd();
        if (Objects.nonNull(qrRobots) && qrRobots.size() == 0) {
            log.info("[QQ机器人]----未有客户授权到期");
            return;
        }
        //发送给酷Q客户端
        TCPUtil.sendTCPRequest(movieConfig.socketCqpIp, movieConfig.socketCqpPort, JSON.toJSONString(qrRobots), "gb2312");
        for (QrRobot qrRobot : qrRobots) {
            log.info("[QQ机器人]----客户:{},授权已到期", qrRobot.getClientQq());
            //发送邮件
            SendMailRequest sendMailRequest = new SendMailRequest();
            sendMailRequest.setEmailTitle("主人：电影机器人到期提醒");
            sendMailRequest.setUserMail(qrRobot.getClientQq() + "@qq.com");
            sendMailRequest.setSendContent("您好，电影机器人:" + qrRobot.getRobotQq() + "将于:"
                    + qrRobot.getEndDate() + "到期，请及时续费！");
            try {
                sendMailUtil.sendMailFun(sendMailRequest);
            } catch (Exception e) {
                log.error(ExceptionUtils.getStackTrace(e));
                log.error("[发信服务]----发信异常");
            }
        }

    }

    /**
     * 每月自动清除月签到数
     *
     * @date 2019/3/23
     */
    @Scheduled(cron = "0 0 0 1 * ?")
    public void plushSignInDays() {
        log.info("[批处理]---月更新签到任务已开始！");
        SendMailRequest sendMailRequest = new SendMailRequest().setUserMail("357078415@qq.com")
                .setEmailTitle("[批处理]---月更新签到任务已开始").setSendContent("[批处理]---月更新签到任务已开始");
        try {
            sendMailUtil.sendMailFun(sendMailRequest);
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        List<QrSignInDays> qrSignInDays = qrSigninDaysMapper.selectList(null);
        if (!ObjectUtil.isNullOrEmpty(qrSignInDays)) {
            for (QrSignInDays oldDays : qrSignInDays) {
                oldDays.setMdays("0");
                qrSigninDaysMapper.updateById(oldDays);
            }
        }
    }
}
