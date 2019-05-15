package com.onescorpion.nova.util;

import com.onescorpion.nova.exception.DefaultException;
import com.onescorpion.nova.result.ResultEnum;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.*;

/**
 * 请求工具
 *
 * @author 李挺 【fengkuangdejava@outlook.com】
 * @date 2018/3/26 12:05
 */
public class MyHttpClient {


    private static Logger logger = LoggerFactory.getLogger(MyHttpClient.class);
    /**
     * 执行get请求
     *
     * @param url,header
     * @return map
     * @author 李挺 【fengkuangdejava@outlook.com】
     * @date 2018/3/26 12:01
     */
    public static Map doGet(String url, Map<String, String> header,int time) {
        Map map = new HashMap();
        //实例化httpclient
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //实例化get方法
        HttpGet httpget = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(time).setConnectionRequestTimeout(time)
                .setSocketTimeout(time).build();
        httpget.setConfig(requestConfig);
        int status = 0;
        //注入header
        Iterator<Map.Entry<String, String>>iterator = header.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> entry = iterator.next();
            httpget.setHeader(entry.getKey(), entry.getValue());
        }
        //请求结果
        CloseableHttpResponse response = null;
        String content = "";
        try {
            //执行get方法
            response = httpclient.execute(httpget);
            try {
                status = response.getStatusLine().getStatusCode();
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    content = EntityUtils.toString(entity, "utf-8");
                    if (status != 200) {
                        logger.error(content);
                    }
                    EntityUtils.consume(entity);
                }
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        } catch (HttpHostConnectException e) {
            throw new DefaultException(ResultEnum.SERVICE_CONNECT_ERROR);
        }catch (SocketTimeoutException e){
            throw new DefaultException(ResultEnum.SERVICE_CONNECT_ERROR);
        }catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpclient != null) {
                    httpclient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            map.put("status", status);
            map.put("data", content);
            return map;
        }
    }

    /**
     * 执行post请求
     *
     * @param url,String jsonParam,Map header
     * @return map
     * @author 李挺 【fengkuangdejava@outlook.com】
     * @date 2018/3/26 12:22
     */
    public static Map doPost(String url, String jsonStrParams, Map<String, String> header,int time) {
        Map map = new HashMap();
        //实例化httpClient
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //实例化post方法
        HttpPost httpPost = new HttpPost(url);

        RequestConfig requestConfig = RequestConfig.custom()
//                .setConnectTimeout(time).setConnectionRequestTimeout(time)
//                .setSocketTimeout(time)
                .build();
        httpPost.setConfig(requestConfig);

        int status = 0;
        //注入header
        Iterator<Map.Entry<String, String>>iterator = header.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> entry = iterator.next();
            httpPost.setHeader(entry.getKey(), entry.getValue());
        }

        //第三步：给httpPost设置JSON格式的参数
        StringEntity requestEntity = new StringEntity(jsonStrParams, "utf-8");


        //结果
        CloseableHttpResponse response = null;
        String content = "";
        try {
            //提交的参数
            requestEntity.setContentEncoding("UTF-8");
            //将参数给post方法
            httpPost.setEntity(requestEntity);
            //执行post方法
            response = httpclient.execute(httpPost);
            HttpEntity entity=null;

            if(response!=null) {
                status = response.getStatusLine().getStatusCode();
                entity = response.getEntity();
            }
            try {
                if (entity != null) {
                    content = EntityUtils.toString(entity, "utf-8");
                    if (status != 200) {
                        logger.error(content);
                    }
                    EntityUtils.consume(entity);
                }
            } finally {
                if (response != null) {
                    response.close();
                }
            }

        }catch (ClientProtocolException e) {
            e.printStackTrace();
        }catch (SocketTimeoutException e){
            throw new DefaultException(ResultEnum.SERVICE_CONNECT_ERROR);
        }catch  (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpclient != null) {
                    httpclient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            map.put("status", status);
            map.put("data", content);
            return map;
        }
    }

    /**
     * 执行delete请求
     *
     * @param url,Strng encode,Map header
     * @author 李挺 【fengkuangdejava@outlook.com】
     * @date 2018/3/26 12:26
     */
    public static Map doDelete(String url, String encode, Map<String, String> headers,int time) {
        Map map = new HashMap();
        int status = 0;
        if (encode == null) {
            encode = "utf-8";
        }
        String content = null;
        //since 4.3 不再使用 DefaultHttpClient
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpDelete httpdelete = new HttpDelete(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(time).setConnectionRequestTimeout(time)
                .setSocketTimeout(time).build();
        httpdelete.setConfig(requestConfig);
        //设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpdelete.setHeader(entry.getKey(), entry.getValue());
            }
        }
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = closeableHttpClient.execute(httpdelete);
            try {
                HttpEntity entity = httpResponse.getEntity();
                if (entity != null) {
                    content = EntityUtils.toString(entity, encode);
                    EntityUtils.consume(entity);
                } else {
                    content = "";
                }
                status = httpResponse.getStatusLine().getStatusCode();
            } finally {
                httpResponse.close();
            }
        }catch (ClientProtocolException e) {
            e.printStackTrace();
        }catch (SocketTimeoutException e){
            throw new DefaultException(ResultEnum.SERVICE_CONNECT_ERROR);
        }catch  (IOException e) {
            e.printStackTrace();
        }finally {
            try {   //关闭连接、释放资源
                if (closeableHttpClient != null) {
                    closeableHttpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            map.put("status", status);
            map.put("data", content);
            return map;
        }
    }

    /**
     * 模拟表单上传
     *
     * @param file
     * @param parser
     * @param url
     * @param header
     * @param time
     * @return
     */
    public static Map doPostFormData(File file, String parser, String url, Map<String, String> header,int time){
        String BOUNDARY = "----------" + UUID.randomUUID().toString().replaceAll("-", "");
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String content = "";
        int status = 500;
        Map map = new HashMap();
        try {
            String uri = url;
            HttpPost httppost = new HttpPost(uri);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(time).setConnectionRequestTimeout(time)
                    .setSocketTimeout(time).build();
            httppost.setConfig(requestConfig);
            FileBody bin = new FileBody(file, "application/vnd.ms-excel");
            StringBody coment = new StringBody(parser, ContentType.WILDCARD);

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("file", bin)
                    .addPart("parser", coment)
                    .setBoundary(BOUNDARY)
                    .build();
            httppost.setEntity(reqEntity);
            //注入header
            Iterator<Map.Entry<String, String>>iterator = header.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, String> entry = iterator.next();
                httppost.setHeader(entry.getKey(), entry.getValue());
            }
            httppost.setHeader("Content-Type", "multipart/form-data;charset=UTF-8;boundary=" + BOUNDARY);

            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                status = response.getStatusLine().getStatusCode();

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    content = EntityUtils.toString(entity, "utf-8");
                    if (status != 200) {
                        logger.error(content);
                    }
                    EntityUtils.consume(entity);
                }
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        }catch (ClientProtocolException e) {
            e.printStackTrace();
        }catch (SocketTimeoutException e){
            throw new DefaultException(ResultEnum.SERVICE_CONNECT_ERROR);
        }catch  (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("httpclient未成功关闭");
                }
            }
            map.put("data", content);
            map.put("status", status);
            return map;
        }
    }

}
