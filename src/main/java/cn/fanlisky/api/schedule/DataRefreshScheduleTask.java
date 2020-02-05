package cn.fanlisky.api.schedule;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.fanlisky.api.config.DynamicDataSourceContextHolder;
import cn.fanlisky.api.domain.QrFortuneData;
import cn.fanlisky.api.domain.QrShortUrl;
import cn.fanlisky.api.enums.DataSourceType;
import cn.fanlisky.api.enums.RedisPrefixEnum;
import cn.fanlisky.api.mapper.QrDataMapper;
import cn.fanlisky.api.mapper.QrFortuneDataMapper;
import cn.fanlisky.api.mapper.QrShortUrlMapper;
import cn.fanlisky.api.utils.RedisCacheUtil;
import cn.fanlisky.api.utils.DateUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Gary
 * @since 2019-10-19 16:07
 * <p>Code is my soul.<p/>
 */
@Component
public class DataRefreshScheduleTask {

    @Resource
    private QrDataMapper qrDataMapper;
    @Resource
    private RedisCacheUtil redisCacheUtil;
    @Resource
    private QrShortUrlMapper qrShortUrlMapper;
    @Resource
    private QrFortuneDataMapper qrFortuneDataMapper;

    @Scheduled(cron = "0 0 10/4 * * ?")
    public void updateDataCount() {
        processData(tableName, qrDataMapper, redisCacheUtil);
    }

    @Scheduled(cron = "0 59 23 * * ?")
    public void clearFortuneGroupNumData(){
        qrFortuneDataMapper.update(null,Wrappers.<QrFortuneData>lambdaUpdate().set(QrFortuneData::getGroupNum,""));
    }

    @Scheduled(cron = "0 0 0 1/1 * ? ")
    public void clearShortUrlData() {
        qrShortUrlMapper.delete(Wrappers.<QrShortUrl>lambdaQuery().like(QrShortUrl::getExpireDate, DateUtil.parseDateToStr("yyyy-MM-dd", new Date())));
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void clearRedisData() {
        Set<String> keysByPattern = redisCacheUtil.keys("API-LIMIT:*");
        if (CollectionUtil.isNotEmpty(keysByPattern)) {
            for (String key : keysByPattern) {
                Long ttl = redisCacheUtil.getExpire(key);
                if (ttl != null && ttl == -1) {
                    redisCacheUtil.delete(key);
                }
            }
        }
    }

    public void processData(List<String> tableName, QrDataMapper qrDataMapper, RedisCacheUtil redisCacheUtil) {
        tableName.forEach(table -> {
            if ("mac_vod".equals(table)) {
                DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
                Integer vodCount = qrDataMapper.getDataCount(table);
                redisCacheUtil.setPre(RedisPrefixEnum.DATA_COUNT.getPrefix(), table.toUpperCase(), vodCount.toString());
                DynamicDataSourceContextHolder.clearDataSourceType();
            } else {
                Integer count = qrDataMapper.getDataCount(table);
                redisCacheUtil.setPre(RedisPrefixEnum.DATA_COUNT.getPrefix(), table.toUpperCase(), count.toString());
            }
        });
    }


    private List<String> tableName = new ArrayList<String>() {{
        add("mac_vod");
        add("qr_beasen");
        add("qr_fortune");
        add("qr_msg_of_day");
    }};
}
