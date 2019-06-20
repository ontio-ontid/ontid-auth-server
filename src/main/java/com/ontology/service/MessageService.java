package com.ontology.service;

import com.ontology.controller.vo.MessageDto;

import java.util.Map;

public interface MessageService {

    Map<String, Object> getMessage();

    Map<String, Object> callback(String action, MessageDto req);

    Map<String, Object> loginResult(String action, String id);

}
