package cn.fanlisky.api.service.impl;

import cn.fanlisky.api.domain.QrFile;
import cn.fanlisky.api.enums.StatusCode;
import cn.fanlisky.api.mapper.QrFileMapper;
import cn.fanlisky.api.model.response.RestfulResponse;
import cn.fanlisky.api.service.IQrFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.fanlisky.api.utils.ExceptionUtil;
import cn.fanlisky.api.utils.FtpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 文件表 服务实现类
 * </p>
 *
 * @author Gary
 * @since 2020-01-06
 */
@Slf4j
@Service
public class QrFileServiceImpl extends ServiceImpl<QrFileMapper, QrFile> implements IQrFileService {


    @Override
    public RestfulResponse uploadFile(String path, InputStream inputStream) {
        try {
            String file = "";
            Map<String, String> res = new HashMap<String, String>(1) {{
                put("path", file);
            }};
            return RestfulResponse.getRestfulResponse(StatusCode.FILE_UPLOAD_SUCCESS, res);
        } catch (Exception e) {
            log.error("[文件服务]----写入失败, e-> {}", ExceptionUtil.getStackTrace(e));
        }
        return RestfulResponse.getRestfulResponse(StatusCode.FILE_UPLOAD_FAIL);
    }
}
