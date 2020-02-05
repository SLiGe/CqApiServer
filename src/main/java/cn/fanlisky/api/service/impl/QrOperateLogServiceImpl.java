package cn.fanlisky.api.service.impl;

import cn.fanlisky.api.domain.QrOperateLog;
import cn.fanlisky.api.mapper.QrOperateLogMapper;
import cn.fanlisky.api.service.IQrOperateLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志记录 服务实现类
 * </p>
 *
 * @author Gary
 * @since 2019-11-13
 */
@Service
public class QrOperateLogServiceImpl extends ServiceImpl<QrOperateLogMapper, QrOperateLog> implements IQrOperateLogService {

}
