package com.ontology.controller;

import com.alibaba.fastjson.JSONObject;
import com.ontology.bean.Result;
import com.ontology.controller.vo.InvokeDto;
import com.ontology.service.InvokeService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/api/v1/invoke")
@CrossOrigin
public class InvokeController {

    @Autowired
    private InvokeService invokeService;

    @ApiOperation(value = "调用合约", notes = "调用合约", httpMethod = "GET")
    @GetMapping
    public Result invokeContract() {
        String action = "invokeContract";
        Map<String,Object> result = invokeService.invokeContract(action);
        return new Result(action,0, "SUCCESS", result);
    }

    @ApiOperation(value = "获取交易参数", notes = "获取交易参数", httpMethod = "GET")
    @GetMapping("/qrcode/{id}")
    public JSONObject getParams(@PathVariable String id) {
        String action = "getParams";
        JSONObject params = invokeService.getParams(action,id);
        return params;
    }

    @ApiOperation(value = "发送交易hash", notes = "发送交易hash", httpMethod = "POST")
    @PostMapping("/callback")
    public void invokeResult(@RequestBody InvokeDto req) throws Exception {
        String action = "invokeResult";
        invokeService.invokeResult(action,req);
    }

    @ApiOperation(value = "查询交易结果", notes = "查询交易结果", httpMethod = "GET")
    @GetMapping("/result/{id}")
    public Result getResult(@PathVariable String id) {
        String action = "getResult";
        String isSuccessful = invokeService.getResult(action,id);
        return new Result(action,0, "SUCCESS", isSuccessful);
    }

}