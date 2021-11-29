package cn.edu.sjtu.ist.ecssbackendedge.utils.connect;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @brief 与云端通信的接口
 * @author rsp
 * @date 2021-11-25
 */
@Slf4j
@Component
public class ConnectCloudUtil {

    public static String CLOUD_SERVER_URL = "http://localhost:8080";

    @Value("${cloud.server.data.path}")
    public String DATA_PATH;

    /**
     * @brief 发送数据包到云端
     * @param filepath 数据包的本地绝对路径
     */
    public void sendDataPackage(String edgeId, String filepath) {
        // 获取本地文件
        try {
            HttpPost httpPost = new HttpPost(CLOUD_SERVER_URL + DATA_PATH + "/" + edgeId);
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();

            File file = new File(filepath);
            // 发送post请求，并获取返回信息
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            // 设置请求的编码格式
            builder.setCharset(StandardCharsets.UTF_8);
            // 设置浏览器兼容模式，加上此行代码解决返回中文乱码问题
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            // 文件流
            builder.addBinaryBody("file", file);

            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            log.info(String.format("url: %s", httpPost));
            // 执行提交
            HttpResponse response = httpClient.execute(httpPost);
            log.info(String.format("url: %s", response));
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                // 将响应内容转换为字符串
                String result = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
                log.info(result);
            }
        } catch (IOException e) {
            log.info("报错");
        }
    }
}
