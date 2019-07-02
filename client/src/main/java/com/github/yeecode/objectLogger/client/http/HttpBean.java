package com.github.yeecode.objectLogger.client.http;


import com.github.yeecode.objectLogger.client.config.ObjectLoggerConfigBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Component
public class HttpBean {
    private static final Log LOGGER = LogFactory.getLog(HttpBean.class);

    @Autowired(required = false)
    private ObjectLoggerConfigBean objectLoggerConfigBean;

    public synchronized void sendLog(String jsonString) {
        try {
            Map<String, Object> logParamMap = new HashMap<>();
            logParamMap.put("logJsonString", jsonString);
            sendPost(objectLoggerConfigBean.getAddLogApi(), logParamMap, "utf-8");
        } catch (Exception ex) {
            LOGGER.error("sendLog error!", ex);
        }
    }


    private synchronized String sendPost(String urlParam, Map<String, Object> params, String charset) {
        StringBuffer resultBuffer = null;
        // 构建请求参数
        StringBuffer sbParams = new StringBuffer();
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> e : params.entrySet()) {
                sbParams.append(e.getKey());
                sbParams.append("=");
                sbParams.append(e.getValue());
                sbParams.append("&");
            }
        }
        HttpURLConnection con = null;
        // 发送请求
        try {
            URL url = new URL(urlParam);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            if (sbParams.length() > 0) {
                try (OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), charset)) {
                    osw.write(sbParams.substring(0, sbParams.length() - 1));
                    osw.flush();
                }
            }
            // 读取返回内容
            resultBuffer = new StringBuffer();
            int contentLength = Integer.parseInt(con.getHeaderField("Content-Length"));
            if (contentLength > 0) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), charset))) {
                    String temp;
                    while ((temp = br.readLine()) != null) {
                        resultBuffer.append(temp);
                    }
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultBuffer.toString();
    }
}
