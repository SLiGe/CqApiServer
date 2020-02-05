package cn.fanlisky.api.controller;


import cn.fanlisky.api.enums.StatusCode;
import cn.fanlisky.api.model.response.RestfulResponse;
import cn.fanlisky.api.service.IQrFileService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 文件表 前端控制器
 * </p>
 *
 * @author Gary
 * @since 2020-01-06
 */
@RestController
@RequestMapping("/api/qr-file")
public class QrFileController {

    private IQrFileService qrFileService;

    public QrFileController(IQrFileService qrFileService) {
        this.qrFileService = qrFileService;
    }

    @PostMapping("uploadApp")
    @ApiOperation(value = "上传APP接口", notes = "上传APP接口")
    public RestfulResponse uploadApp(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return RestfulResponse.getRestfulResponse(StatusCode.FILE_UPLOAD_FAIL);
        }
        String fileName = multipartFile.getOriginalFilename();
        return qrFileService.uploadFile(fileName, multipartFile.getInputStream());
    }
}

