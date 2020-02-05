package cn.fanlisky.api.utils;


import com.google.common.collect.Maps;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;


/**
 * @program: api
 * @description: 发送TCP请求的工具类
 * @author: ZhongJiaLi
 * @create: 2018-12-24 18:29
 */
public class TCPUtil {
    private TCPUtil(){}

    /**
     * 发送TCP请求
     * @see 本方法默认的连接超时和读取超时均为30秒
     * @see 编码与解码请求响应字节时,均采用双方约定的字符集,即本方法的第四个参数reqCharset
     * @param IP         远程主机地址
     * @param port       远程主机端口
     * @param reqData    待发送报文的中文字符串形式
     * @param reqCharset 该方法与远程主机间通信报文的编码字符集(编码为byte[]发送到Server)
     * @return localPort--本地绑定的端口,reqData--请求报文,respData--响应报文,respDataHex--远程主机响应的原始字节的十六进制表示
     */
    public static Map<String, String> sendTCPRequest(String IP, String port, String reqData, String reqCharset){
        Map<String, String> respMap = Maps.newHashMap();
        OutputStream out = null;      //写
        InputStream in = null;        //读
        String localPort = null;      //本地绑定的端口(java socket, client, /127.0.0.1:50804 => /127.0.0.1:9901)
        String respData = null;       //响应报文
        String respDataHex = null;    //远程主机响应的原始字节的十六进制表示
        Socket socket = new Socket(); //客户机
        try {
            socket.setTcpNoDelay(true);
            socket.setReuseAddress(true);
            socket.setSoTimeout(30000);
            socket.setSoLinger(true, 5);
            socket.setSendBufferSize(1024);
            socket.setReceiveBufferSize(1024);
            socket.setKeepAlive(true);
            socket.connect(new InetSocketAddress(IP, Integer.parseInt(port)), 30000);
            localPort = String.valueOf(socket.getLocalPort());
            /**
             * 发送TCP请求
             */
            out = socket.getOutputStream();
            out.write(reqData.getBytes(reqCharset));
            /**
             * 接收TCP响应
             */
            in = socket.getInputStream();
            byte[] buffer = new byte[1024];
            int len =0;
            String serverReceive = "";
            //暂时不接收请求

            /*while (true){
                if ((len = in.read(buffer)) != -1) {
                    String text = new String(buffer, 0, len);
                    serverReceive = StringUtil.binstrToStr(text);
                    break;
                }
            }*/
           respMap.put("result",serverReceive);
        } catch (Exception e) {
            System.out.println("与[" + IP + ":" + port + "]通信遇到异常,堆栈信息如下");
            e.printStackTrace();
        } finally {
            if (null!=socket && socket.isConnected() && !socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("关闭客户机Socket时发生异常,堆栈信息如下");
                    e.printStackTrace();
                }
            }
        }
        return respMap;
    }


}


