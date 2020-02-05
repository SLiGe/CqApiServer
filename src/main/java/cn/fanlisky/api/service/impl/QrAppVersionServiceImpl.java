package cn.fanlisky.api.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.fanlisky.api.domain.QrAppVersion;
import cn.fanlisky.api.domain.QrFile;
import cn.fanlisky.api.exception.DownFileException;
import cn.fanlisky.api.mapper.QrAppVersionMapper;
import cn.fanlisky.api.mapper.QrFileMapper;
import cn.fanlisky.api.service.IQrAppVersionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.fanlisky.api.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * <p>
 * app版本表 服务实现类
 * </p>
 *
 * @author Gary
 * @since 2020-01-05
 */
@Slf4j
@Service
public class QrAppVersionServiceImpl extends ServiceImpl<QrAppVersionMapper, QrAppVersion> implements IQrAppVersionService {

    @Resource
    private QrFileMapper fileMapper;

    @Override
    public void getApp(QrAppVersion appVersion, HttpServletResponse response) {
        String ipAddress = IpUtil.getIpAddress(ServletUtils.getRequest());
        log.info("[文件服务]----下载者IP: {}", ipAddress);
        if (ObjectUtil.isNullOrEmpty(appVersion)) {
            throw new DownFileException("下载文件异常");
        }
        QrAppVersion localVersion = getOne(Wrappers.<QrAppVersion>lambdaQuery().eq(QrAppVersion::getAppCode, appVersion.getAppCode())
                .eq(QrAppVersion::getAppCurrentVersion, appVersion.getAppCurrentVersion()));
        if (ObjectUtil.isNullOrEmpty(localVersion)) {
            throw new DownFileException("下载文件不存在");
        }
        QrFile file = fileMapper.selectById(localVersion.getAppFileId());
        if (ObjectUtil.isNullOrEmpty(file)) {
            throw new DownFileException("下载文件不存在");
        }
        String fileAddress = file.getFileAddress();
        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition", "attachment;fileName="
                + file.getFileName());
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            File localFile = new File(fileAddress);
            fis = new FileInputStream(localFile);
            bis = new BufferedInputStream(fis);
            ServletOutputStream outputStream = response.getOutputStream();
            byte[] bytes = new byte[1024];
            int len;
            while ((len = bis.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
        } catch (IOException e) {
            log.warn("[文件服务]----下载文件错误, e-> {}", e.getMessage());
            throw new DownFileException("下载文件异常");
        } finally {
            try {
                Objects.requireNonNull(fis).close();
                Objects.requireNonNull(bis).close();
            } catch (IOException e) {
                log.warn("[文件服务]----下载流关闭异常,e-> {}", ExceptionUtil.getStackTrace(e));
            }
        }
    }
}
