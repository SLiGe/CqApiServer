package cn.fanlisky.api.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**  
 * 【httpClient工具】
 * @author Gary
 * @date 2017年3月16日 上午10:36:11 
 * @version V1.0  
 */
@Slf4j
public class HttpClientUtil {
    
    public static String sendGet(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        InputStream is = null;
        //封装请求参数
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        String str = "";
        StringBuilder build = new StringBuilder();
        try {
            //转换为键值对
            str = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
            //创建Get请求
            HttpGet httpGet = new HttpGet(url+"?"+str);
            httpGet.setHeader("x-token","a12cbd145732dc7b62f495b4c0cf293f");
            //执行Get请求，
            response = httpClient.execute(httpGet);
            //得到响应体
            HttpEntity entity = response.getEntity();
            if(entity != null){
                is = entity.getContent();
                //转换为字节输入流
                BufferedReader br = new BufferedReader(new InputStreamReader(is, Consts.UTF_8));
                String body = null;
                while((body=br.readLine()) != null){
                    build.append(body);
                }
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
            log.error("[HttpClient]----请求失败,e:{}", ExceptionUtil.getStackTrace(e));
        } finally{
            //关闭输入流，释放资源
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //消耗实体内容
            if(response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //关闭相应 丢弃http连接
            if(httpClient != null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return build.toString();
        
    }
    public static String sendPost(String url,Map<String,String> paras){
        String result="";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        
        try {
            
            List<NameValuePair> list = new ArrayList<>();
            
            for (String key: paras.keySet()) {
                String value = paras.get(key);
                list.add(new BasicNameValuePair(key,value));
            }
            UrlEncodedFormEntity urlEntity = new UrlEncodedFormEntity(list);
            
            post.setEntity(urlEntity);
            CloseableHttpResponse response = httpClient.execute(post);
            if(response.getStatusLine().getStatusCode()==200){
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity,"utf-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                log.warn(ExceptionUtils.getStackTrace(e));
            }
        }
        return result;
    }
    
    public static String request(String urlstr, String req) {
        HttpURLConnection conn = null;
        InputStream in = null;
        OutputStream out = null;
        try {
            URL url = new URL(urlstr);
            byte[] info = req == null ? null : req.getBytes("UTF-8");

            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setReadTimeout(100000);
            conn.setConnectTimeout(100000);
            conn.setRequestProperty("Content-Type", "text/xml");
            if (info != null)
                conn.setRequestProperty("Content-Length",
                        String.valueOf(info.length));

            OutputStream up = conn.getOutputStream();
            if (info != null)
                up.write(info);
            up.flush();
            up.close();

            in = conn.getInputStream();
            return stringOf(in);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException ignored) {
            }

            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
            }

            if (conn != null)
                conn.disconnect();
        }

        return null;
    }
    
    public static String stringOf(InputStream in) {
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();

            byte[] b = new byte[2048];
            int c;
            while ((c = in.read(b)) >= 0) {
                out.write(b, 0, c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
            }
        }

        byte[] b = out.toByteArray();

        try {
            String str = new String(b, "UTF-8");
            return str == null || "".equals(str)
                    || "null".equalsIgnoreCase(str) ? null : str;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    
    /**
    * @Title: get
    * @Description: get方式请求
    * @param @param url
    * @param @param param
    * @param @return    参数
    * @return String    返回类型
    * @throws
    */
    public static String get(String url,String param){
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozuilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    
    public static String sendPost(String url, String params){
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		post.addHeader("Content-type","application/json;charset=utf-8");
		post.setHeader("Accept","application/json;charset=utf-8");
		post.setHeader("x-token","a12cbd145732dc7b62f495b4c0cf293f");
		HttpEntity entity = new StringEntity(params, Charset.forName("UTF-8"));
		post.setEntity(entity);
		HttpResponse response;
		HttpEntity resEntity ;
		try {
			response = client.execute(post);
			resEntity = response.getEntity();
			return EntityUtils.toString(resEntity); //响应信息
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				client.close(); //关闭连接
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		return null ; 
	}
}
