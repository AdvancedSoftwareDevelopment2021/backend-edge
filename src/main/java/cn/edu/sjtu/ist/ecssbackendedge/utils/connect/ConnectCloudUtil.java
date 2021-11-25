package cn.edu.sjtu.ist.ecssbackendedge.utils.connect;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;

/**
 * @brief 与云端通信的接口
 * @author rsp
 * @date 2021-11-25
 */
@Slf4j
@Component
public class ConnectCloudUtil {

    @Value("${cloud.server.url}")
    private String CLOUD_SERVER_URL;

    /**
     * @brief 发送数据包到云端
     * @param filepath 数据包的本地绝对路径
     */
    public void sendDataPackage(String filepath) {
        RestTemplate rest = new RestTemplate();
        // 获取本地文件
        FileSystemResource resource = new FileSystemResource(new File(filepath));
        // 将本地文件编辑为 formdata 格式的数据
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("file", resource);

        // 发送post请求，并获取返回信息
        String string = rest.postForObject(CLOUD_SERVER_URL, param, String.class);
        // String 转 json格式，获取返回的url地址
        // JSONObject jsonObject = JSONObject.parseObject(string);
        // JSONObject data = JSONObject.parseObject(jsonObject.getString("data"));
        log.info(string);
    }

}
