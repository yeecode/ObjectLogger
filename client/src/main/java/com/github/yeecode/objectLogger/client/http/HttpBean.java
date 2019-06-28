package com.github.yeecode.objectLogger.client.http;


import com.alibaba.fastjson.JSON;
import com.github.yeecode.objectLogger.client.config.ObjectLoggerConfigBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Component
public class HttpBean {
    private static final Log LOGGER = LogFactory.getLog(HttpBean.class);

    @Autowired(required = false)
    private ObjectLoggerConfigBean objectLoggerConfigBean;

    public void sendLog(String jsonString) {
        try{
            Map<String,String> logParamMap = new HashMap<>();
            logParamMap.put("logJsonString", URLEncoder.encode(jsonString, "utf-8"));
            String response = jsonPost(objectLoggerConfigBean.getAddLogApi(),logParamMap);
        } catch (Exception ex) {
            LOGGER.error("sendLog error!", ex);
        }
    }


    private String jsonPost(String strURL, Map<String, String> params) {
        try {
            URL url = new URL(strURL);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-builtinType", "application/json"); // 设置发送数据的格式
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
            out.append(JSON.toJSONString(params));
            out.flush();
            out.close();

            int code = connection.getResponseCode();
            InputStream is = null;
            if (code == 200) {
                is = connection.getInputStream();
            } else {
                is = connection.getErrorStream();
            }

            // 读取响应
            int length = (int) connection.getContentLength();// 获取长度
            if (length != -1) {
                byte[] data = new byte[length];
                byte[] temp = new byte[512];
                int readLen = 0;
                int destPos = 0;
                while ((readLen = is.read(temp)) > 0) {
                    System.arraycopy(temp, 0, data, destPos, readLen);
                    destPos += readLen;
                }
                String result = new String(data, "UTF-8"); // utf-8编码
                return result;
            }

        } catch (IOException e) {
            LOGGER.error("Exception occur when send http post request!", e);
        }
        return "error"; // 自定义错误信息
    }

}
