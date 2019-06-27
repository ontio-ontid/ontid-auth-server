package com.ontology.service.impl;

import com.ontology.controller.vo.MessageDto;
import com.ontology.entity.Login;
import com.ontology.entity.Ons;
import com.ontology.mapper.LoginMapper;
import com.ontology.mapper.OnsMapper;
import com.ontology.service.LoginService;
import com.ontology.utils.ConfigParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private ConfigParam configParam;

    @Override
    public Map<String, Object> getMessage() {
        String id = UUID.randomUUID().toString();
        String message = "hello " + System.currentTimeMillis();
//        Login msg = new Login();
//        msg.setId(id);
//        msg.setMessage(message);
//        messageMapper.insert(msg);

        String callback = String.format(configParam.CALLBACK_URL,"api/v1/login/callback");
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("message", message);
        map.put("callback", callback);
        return map;
    }

    @Override
    public Map<String, Object> callback(String action, MessageDto req) {
        String id = req.getId();
        String user = req.getParams().getUser();
        String domain = req.getParams().getDomain();

        Login msg = new Login();
        msg.setId(id);
        msg.setOntid(user);
        msg.setDomain(domain);
        msg.setMessage(req.getParams().getMessage());
        msg.setIsVerified(1);
        loginMapper.insert(msg);

        Map<String, Object> map = new HashMap<>();
        map.put("action", action);
        map.put("id", id);
        map.put("error", 0);
        map.put("desc", "SUCCESS");
        map.put("result", true);
        return map;
    }

    @Override
    public Map<String, Object> loginResult(String action, String id) {
        Login msg = loginMapper.selectByPrimaryKey(id);
        if (msg == null) {
            return null;
        }
        Integer isVerified = msg.getIsVerified();
        Map<String, Object> result = new HashMap<>();
        if (isVerified != null && isVerified.equals(1)) {
            String ontid = msg.getOntid();
            String domain = msg.getDomain();

            result.put("ontid", ontid);
            result.put("ons", domain);
            result.put("result", "1");

        } else if (isVerified != null && isVerified.equals(0)) {
            result.put("result", "0");
        }
        return result;
    }
}
