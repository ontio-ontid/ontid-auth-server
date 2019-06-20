package com.ontology.controller;

import com.ontology.bean.Result;
import com.ontology.controller.vo.MessageDto;
import com.ontology.service.MessageService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/api/v1/message")
@CrossOrigin
public class MessageController {

    @Autowired
    private MessageService messageService;


    @ApiOperation(value = "获取message", notes = "获取message", httpMethod = "GET")
    @GetMapping
    public Result getMessage() {
        String action = "getMessage";
        Map<String,Object> result = messageService.getMessage();
        return new Result(action,0, "SUCCESS", result);
    }

    @ApiOperation(value = "回调验证", notes = "回调验证", httpMethod = "POST")
    @PostMapping("/callback")
    public Map<String,Object> callback(@RequestBody MessageDto req) {
        String action = "login";
        Map<String,Object> result = messageService.callback(action,req);
        return result;
    }

    @ApiOperation(value = "查询登录是否成功", notes = "查询登录是否成功", httpMethod = "GET")
    @GetMapping("/result/{id}")
    public Result registerResult(@PathVariable String id) {
        String action = "loginResult";
        Map<String,Object> isSuccessful = messageService.loginResult(action,id);
        return new Result(action,0, "SUCCESS", isSuccessful);
    }
}