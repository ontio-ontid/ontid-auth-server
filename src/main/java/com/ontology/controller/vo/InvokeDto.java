package com.ontology.controller.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class InvokeDto {
    @ApiModelProperty(name="action",value = "action")
    private String action;
    @ApiModelProperty(name="id",value = "id")
    private String id;
    @ApiModelProperty(name="error",value = "error")
    private int error;
    @ApiModelProperty(name="desc",value = "desc")
    private String desc;
    @ApiModelProperty(name="result",value = "result")
    private String result;
}
