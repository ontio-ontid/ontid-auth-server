package com.ontology.service;

import com.alibaba.fastjson.JSONObject;
import com.ontology.controller.vo.InvokeDto;

import java.util.List;
import java.util.Map;

public interface OnsService {

    Map<String, Object> registerDomain(String action, String ons);

    JSONObject getParams(String action, String ons);

    String registerResult(String action, String id);

    void invokeResult(String action, InvokeDto req) throws Exception;

    List<String> getOnsList(String action, String ontid);
}
