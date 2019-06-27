package com.ontology.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ontology.controller.vo.InvokeDto;
import com.ontology.entity.Invoke;
import com.ontology.exception.AuthException;
import com.ontology.mapper.InvokeMapper;
import com.ontology.service.InvokeService;
import com.ontology.utils.ConfigParam;
import com.ontology.utils.ErrorInfo;
import com.ontology.utils.Helper;
import com.ontology.utils.SDKUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class InvokeServiceImpl implements InvokeService {
    @Autowired
    private InvokeMapper invokeMapper;
    @Autowired
    private SDKUtil sdkUtil;
    @Autowired
    private ConfigParam configParam;

    @Override
    public Map<String, Object> invokeContract(String action) {
        String id = UUID.randomUUID().toString();

        List<Map<String, Object>> args = new ArrayList<>();
        Map<String, Object> arg0 = new HashMap<>();
        arg0.put("name", "arg0-str");
        arg0.put("value", "String:invoke");
        Map<String, Object> arg1 = new HashMap<>();
        arg1.put("name", "arg1-str");
        arg1.put("value", "Address:%address");
        Map<String, Object> arg2 = new HashMap<>();
        arg2.put("name", "arg2-str");
        arg2.put("value", "Address:AcdBfqe7SG8xn4wfGrtUbbBDxw2x1e8UKm");
        Map<String, Object> arg3 = new HashMap<>();
        arg3.put("name", "arg3-int");
        arg3.put("value", 100000000);
        args.add(arg0);
        args.add(arg1);
        args.add(arg2);
        args.add(arg3);

        String params = Helper.getParams("invoke", id, "8b344a43204e60750e7ccc8c1b708a67f88f2c43",
                "transferOng", args, "%address", null, false);

        Invoke invoke = new Invoke();
        invoke.setId(id);
        invoke.setParams(params);
        invokeMapper.insert(invoke);

        String callback = String.format(configParam.CALLBACK_URL, "api/v1/invoke/callback");
        String qrcodeUrl = String.format(configParam.CALLBACK_URL, "api/v1/invoke/qrcode/%s");
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("callback", callback);
        map.put("qrcodeUrl", String.format(qrcodeUrl, id));

        return map;
    }

    @Override
    public JSONObject getParams(String action, String id) {
        Invoke exist = invokeMapper.selectByPrimaryKey(id);
        if (exist == null) {
            throw new AuthException(action, ErrorInfo.NOT_EXIST.descCN(), ErrorInfo.NOT_EXIST.descEN(), ErrorInfo.NOT_EXIST.code());
        }
        String paramsStr = exist.getParams();
        JSONObject params = JSONObject.parseObject(paramsStr);
        return params;
    }

    @Override
    public void invokeResult(String action, InvokeDto req) throws Exception {
        Invoke invoke = invokeMapper.selectByPrimaryKey(req.getId());
        if (invoke == null) {
            throw new AuthException(action, ErrorInfo.NOT_EXIST.descCN(), ErrorInfo.NOT_EXIST.descEN(), ErrorInfo.NOT_EXIST.code());
        }

        if (req.getError() == 0) {
            // 发送交易成功
            String txHash = req.getResult();

            Executors.newCachedThreadPool().submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Object event = null;
                        for (int i = 0; i < 5; i++) {
                            Thread.sleep(6 * 1000);
                            event = sdkUtil.checkEvent(txHash);
                            if (!Helper.isEmptyOrNull(event)) {
                                invoke.setSuccess(1);
                                break;
                            }
                            invoke.setSuccess(0);
                        }
                        log.info("event:{}", event);
                        invoke.setTxHash(txHash);
                        invokeMapper.updateByPrimaryKeySelective(invoke);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            // 发送交易失败
            invoke.setSuccess(0);
            invokeMapper.updateByPrimaryKeySelective(invoke);
        }
    }

    @Override
    public String getResult(String action, String id) {
        Invoke invoke = invokeMapper.selectByPrimaryKey(id);
        if (invoke == null) {
            throw new AuthException(action, ErrorInfo.NOT_EXIST.descCN(), ErrorInfo.NOT_EXIST.descEN(), ErrorInfo.NOT_EXIST.code());
        }
        Integer success = invoke.getSuccess();
        if (success != null && success.equals(1)) {
            return "1";
        } else if (success != null && success.equals(0)) {
            return "0";
        }
        return null;
    }

}
