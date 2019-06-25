package com.ontology.service.impl;

import com.ontology.controller.vo.MessageDto;
import com.ontology.entity.Message;
import com.ontology.entity.Ons;
import com.ontology.mapper.MessageMapper;
import com.ontology.mapper.OnsMapper;
import com.ontology.service.MessageService;
import com.ontology.utils.ConfigParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private OnsMapper onsMapper;
    @Autowired
    private ConfigParam configParam;

    @Override
    public Map<String, Object> getMessage() {
        String id = UUID.randomUUID().toString();
        String message = "hello " + System.currentTimeMillis();
//        Message msg = new Message();
//        msg.setId(id);
//        msg.setMessage(message);
//        messageMapper.insert(msg);

        String callback = String.format(configParam.CALLBACK_URL,"api/v1/message/callback");
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

        Message msg = new Message();
        msg.setId(id);
        msg.setOntid("did:ont:"+user);
        msg.setMessage(req.getParams().getMessage());
        msg.setIsVerified(1);
        messageMapper.insert(msg);

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
        Message msg = messageMapper.selectByPrimaryKey(id);
        if (msg == null) {
            return null;
        }
        Integer isVerified = msg.getIsVerified();
        Map<String, Object> result = new HashMap<>();
        if (isVerified != null && isVerified.equals(1)) {
            String ontid = msg.getOntid();
            Ons ons = new Ons();
            ons.setOntid(ontid);
            ons.setSuccess(1);
            List<Ons> onsList = onsMapper.select(ons);

            result.put("ontid", ontid);
            result.put("ons", CollectionUtils.isEmpty(onsList) ? null : onsList.get(0).getDomain());
            result.put("result", "1");

        } else if (isVerified != null && isVerified.equals(0)) {
            result.put("result", "0");
        }
        return result;
    }
}
