package com.ontology.service;

import com.alibaba.fastjson.JSONObject;
import com.ontology.controller.vo.InvokeDto;

import java.util.Map;

public interface InvokeService {

    Map<String, Object> invokeContract(String action);

    JSONObject getParams(String action, String id);

    String getResult(String action, String id);

    void invokeResult(String action, InvokeDto req) throws Exception;

}
