package cn.fanlisky.api.utils;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Slf4j
public class FtpUtil {

    @Value(value = "${down.fanlisky.ftp.ip}")
    private String serverAddress;
    @Value(value = "${down.fanlisky.ftp.port:21}")
    private int port;
    @Value(value = "${down.fanlisky.ftp.user}")
    private String ftpUser;
    @Value(value = "${down.fanlisky.ftp.password}")
    private String password;


    /**
     * 获取Ftp连接
     */
    private FTPClient getFtpClient() {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(serverAddress, port);
            //设置文件格式
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ////ftp中文编码设置
            ftpClient.setControlEncoding("GBK");
            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
            conf.setServerLanguageCode("zh");
            ftpClient.configure(conf);

            //ftpClient.setCharset(Charset.forName("UTF-8"));
            //设置缓冲区大小
            ftpClient.setBufferSize(1024 * 1024 * 4);
            boolean isLogin = ftpClient.login(ftpUser, password);
            if (isLogin) {
                log.info("[FTP]----连接成功");
            } else {
                log.warn("[FTP]----连接失败");
                return null;
            }
        } catch (IOException e) {
            log.error(ExceptionUtils.getMessage(e));
            return null;
        }
        return ftpClient;
    }

    /**
     * 上传文件
     *
     * @param fileName    文件名
     * @param inputStream 文件输入流
     */
    public String uploadFile(String fileName, InputStream inputStream) throws IOException {
        FTPClient ftpClient = getFtpClient();
        String dirName = DateUtil.format(new Date(), "yyyyMMdd");
        if (ftpClient != null) {

            boolean makeDirectory = ftpClient.makeDirectory(dirName);
            if (makeDirectory) {
                log.info("[FTP]----创建文件夹成功,name: {}", dirName);
            }
            ftpClient.changeWorkingDirectory(dirName);
            String suffixName = fileName.substring(fileName.lastIndexOf("."));

            String nameUuid = UUID.randomUUID().toString().replace("-", "");
            String remote = "/" + dirName + "/" + dirName + "-" + nameUuid + suffixName;
            boolean appendRes = ftpClient.appendFile(remote, inputStream);
            if (appendRes) {
                log.info("[FTP]----文件上传成功,path: {}", remote);
                closeClient(ftpClient);
                return remote;
            }
            closeClient(ftpClient);
        }
        return StringUtils.EMPTY;
    }

    public void deleteFile(String name) {
        FTPClient ftpClient = getFtpClient();
        try {
            if (ftpClient != null) {
                ftpClient.deleteFile(name);
                closeClient(ftpClient);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭Ftp客户端连接
     */
    private void closeClient(FTPClient ftpClient) {
        if (ftpClient != null) {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.logout();
                    ftpClient.disconnect();
                    log.info("[FTP]----关闭连接成功!");
                } catch (IOException e) {
                    log.warn("[FTP]----关闭连接失败,e-> {}", ExceptionUtil.getStackTrace(e));
                }
            }
        }
    }

    public void getFile(String name, HttpServletResponse response) throws IOException {
        FTPClient ftpClient = getFtpClient();
        if (ftpClient != null) {
            BufferedInputStream bis = null;
            InputStream is = null;
            try {
                is = ftpClient.retrieveFileStream(name);
                String status = ftpClient.getStatus(name);
                ServletOutputStream outputStream = response.getOutputStream();
                bis = new BufferedInputStream(is);
                byte[] bytes = new byte[1024];
                int len;
                while ((len = bis.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, len);
                }
                log.info("[FTP]----当前文件状态----> {}", status);
            } catch (IOException e) {
                log.error("[FTP]----获取文件失败, e-> {}", ExceptionUtil.getStackTrace(e));
            } finally {
                Objects.requireNonNull(bis).close();
                Objects.requireNonNull(is).close();
                closeClient(ftpClient);
            }
        }
    }

    /**
     * 下载文件
     *
     * @param remote   远程文件路径
     * @param response 响应数据
     */
    @Deprecated
    public void downloadFile(String remote, HttpServletResponse response) {
        FTPClient ftpClient = getFtpClient();
        if (ftpClient != null) {
            //InputStream inputStream = ftpClient.retrieveFileStream(remote);
            try (InputStream is = ftpClient.retrieveFileStream(remote); OutputStream os = response.getOutputStream()) {
                String status = ftpClient.getStatus(remote);
                log.info("当前文件状态----> {}", status);
                response.setContentType("application/force-download");
                response.addHeader("Content-Disposition", "attachment;fileName="
                        + remote);
            } catch (IOException e) {
                log.error(ExceptionUtil.getStackTrace(e));
            }

            try {

                //  ftpClient.retrieveFile(remote, response.getOutputStream());
                InputStream inputStream = ftpClient.retrieveFileStream(remote);
                String[] split = remote.split("\\.");
                String suffix = split[split.length - 1];
                //response.getOutputStream().write();
                String nameUuid = UUID.randomUUID().toString() + "." + suffix;
                response.setHeader("Content-Disposition", "attachment; filename=\"" + nameUuid + "\"");
                //response.addHeader("Content-Length", "" + fileBytes.length);
                response.setContentType("application/octet-stream");
                byte[] bytes = new byte[1024];
                int len;
                while ((len = inputStream.read(bytes)) != -1) {
                    response.getOutputStream().write(len);
                }
                inputStream.close();
                response.getOutputStream().flush();
                response.getOutputStream().close();

                //application/octet-stream 二进制流 未知文件类型
                //response.setContentType("application/octet-stream");
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //ftpClient.retrieveFile(remote, response.getOutputStream());

        }
    }

    public static void main(String[] args) throws IOException {
       /* File file = new File("C:\\Users\\cnahz\\Desktop\\IMG6.jpg");
        InputStream inputStream = new FileInputStream(file);
        String s = uploadFile("IMG6.jpg", inputStream);
        System.out.println(s);*/
        String path = "C:\\Users\\Administrator\\Desktop\\0说明.docx";
        //String[] split = path.split(String.format("\\%s", File.separator));
        //System.out.println(1);
        RandomAccessFile randomAccessFile = new RandomAccessFile(path, "r");
        randomAccessFile.read();
    }

    private static void copyFile(InputStream is, OutputStream os) {
        int len;
        byte[] buff = new byte[1024];
        try (DataInputStream dataInputStream = new DataInputStream(is); DataOutputStream dataOutputStream = new DataOutputStream(os)) {
            while ((len = dataInputStream.read(buff, 0, buff.length)) != -1) {
                dataOutputStream.write(buff, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
