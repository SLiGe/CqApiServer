package cn.fanlisky.api.service;

import cn.fanlisky.api.domain.QrFile;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.fanlisky.api.model.response.RestfulResponse;

import java.io.InputStream;

/**
 * <p>
 * 文件表 服务类
 * </p>
 *
 * @author Gary
 * @since 2020-01-06
 */
public interface IQrFileService extends IService<QrFile> {

    /**
     * 写入文件
     *
     * @param path        写入路径
     * @param inputStream 写入流
     * @return 返回结果
     */
    RestfulResponse uploadFile(String path, InputStream inputStream);
}
