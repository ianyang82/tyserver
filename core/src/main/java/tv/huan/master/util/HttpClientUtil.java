/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tv.huan.master.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


/**
 * @author Administrator
 */
public class HttpClientUtil {


    /**
     * 处理GET请求，返回整个页面
     *
     * @param url
     * @return
     */
    public static String doPostByJson(String url, String data) throws Exception {

        Integer statusCode;
        DefaultHttpClient client = new DefaultHttpClient();
        //        请求超时
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
//        读取超时
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
        HttpPost post = new HttpPost(url);
        StringEntity entity = new StringEntity(data, "UTF-8");
//        entity.setContentType("application/json;charset=UTF-8");
        post.setEntity(entity);
        post.addHeader("Content-Type", "application/json;charset=UTF-8");
        HttpResponse response = client.execute(post);
        statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            throw new HttpException("Http Status is error.");
        }
        return EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
    }
    /**
     * 处理GET请求，返回整个页面
     *
     * @param url
     * @return
     */
    public static String doPost(String url, Map<String,String> data) throws Exception {

        Integer statusCode;
        DefaultHttpClient client = new DefaultHttpClient();
        //        请求超时
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
//        读取超时
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
        HttpPost post = new HttpPost(url);
        if(data!=null)
        for(Map.Entry<String,String> e: data.entrySet())
        	post.getParams().setParameter(e.getKey(), e.getValue());
        HttpResponse response = client.execute(post);
        statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            throw new HttpException("Http Status is error.");
        }
        return EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
    }
    /**
     * 处理GET请求，返回整个页面
     *
     * @param url
     * @return
     */
    public static String doGet(String url, Map<String,String> data) throws Exception {

        Integer statusCode;
        DefaultHttpClient client = new DefaultHttpClient();
        //        请求超时
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
//        读取超时
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
        HttpGet post = new HttpGet(url);
        if(data!=null)
        for(Map.Entry<String,String> e: data.entrySet())
        	post.getParams().setParameter(e.getKey(), e.getValue());
        HttpResponse response = client.execute(post);
        statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            throw new HttpException("Http Status is error.");
        }
        return EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
    }
    /**
     * 执行一个HTTP POST请求，返回请求响应的HTML
     *
     * @param content 请求的查询参数
     * @return 返回请求响应的HTML
     */
    public static String doPostbyxml(String url, String content) throws Exception {
        Integer statusCode;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);
//        请求超时
        httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
//        读取超时
        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
        httppost.setEntity(new StringEntity(content));
        httppost.addHeader("Content-Type", "application/xml");
        HttpResponse response = httpclient.execute(httppost);
        statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            throw new HttpException("Http Status is error.");
        }
        return EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
    }

    public static String getPostContent(HttpServletRequest request) throws IOException {
        // 读取请求内容
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), HTTP.UTF_8));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        try {
        	HashMap m=new HashMap<String,String>();
        	m.put("dnums", "441787964");
            String result = doGet("http://106.14.199.155:8080/app/uc/app/finddevicemobile?dnums=441787964",m);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
