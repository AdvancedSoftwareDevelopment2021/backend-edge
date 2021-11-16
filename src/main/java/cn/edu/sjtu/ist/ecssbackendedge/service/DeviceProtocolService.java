package cn.edu.sjtu.ist.ecssbackendedge.service;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;

import java.util.Map;

public interface DeviceProtocolService {
    Response insertProtocol(Map<String, String> request);
}
