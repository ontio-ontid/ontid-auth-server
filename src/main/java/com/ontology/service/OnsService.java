package com.ontology.service;

import com.alibaba.fastjson.JSONObject;
import com.ontology.controller.vo.OnsLoginDto;
import com.ontology.controller.vo.TransactionDto;

import java.util.List;
import java.util.Map;

public interface OnsService {

    Map<String, Object> registerDomain(String action, String ons);

    JSONObject getParams(String action, String id);

    String registerResult(String action, String id);

    JSONObject invokeResult(String action, TransactionDto req) throws Exception;

    List<String> getOnsList(String action, OnsLoginDto onsLoginDto);
}
