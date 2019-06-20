package com.ontology.controller;

import com.alibaba.fastjson.JSONObject;
import com.ontology.bean.Result;
import com.ontology.controller.vo.InvokeDto;
import com.ontology.service.OnsService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/api/v1/ons")
@CrossOrigin
public class OnsController {

    @Autowired
    private OnsService onsService;

    @ApiOperation(value = "注册ons", notes = "注册ons", httpMethod = "GET")
    @GetMapping("/{ons}")
    public Result registerDomain(@PathVariable String ons) {
        String action = "registerDomain";
        Map<String,Object> result = onsService.registerDomain(action,ons);
        return new Result(action,0, "SUCCESS", result);
    }

    @ApiOperation(value = "获取注册ons交易参数", notes = "获取注册ons交易参数", httpMethod = "GET")
    @GetMapping("/qrcode/{ons}")
    public JSONObject getParams(@PathVariable String ons) {
        String action = "getParams";
        JSONObject params = onsService.getParams(action,ons);
        return params;
    }

    @ApiOperation(value = "获取交易hash", notes = "获取交易hash", httpMethod = "POST")
    @PostMapping("/invoke")
    public void invokeResult(@RequestBody InvokeDto req) throws Exception {
        String action = "invokeResult";
        onsService.invokeResult(action,req);
    }

    @ApiOperation(value = "查询注册是否成功", notes = "查询注册是否成功", httpMethod = "GET")
    @GetMapping("/result/{id}")
    public Result registerResult(@PathVariable String id) {
        String action = "registerResult";
        String isSuccessful = onsService.registerResult(action,id);
        return new Result(action,0, "SUCCESS", isSuccessful);
    }

    @ApiOperation(value = "根据ontid获取ons列表", notes = "根据ontid获取ons列表", httpMethod = "GET")
    @GetMapping("/list/{ontid}")
    public Result getOnsList(@PathVariable String ontid) throws Exception {
        String action = "getOnsList";
        List<String> list = onsService.getOnsList(action,ontid);
        return new Result(action,0, "SUCCESS", list);
    }

}