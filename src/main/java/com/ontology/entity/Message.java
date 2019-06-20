package com.ontology.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tbl_message")
@Data
public class Message {
    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;

    private String ontid;

    private String message;

    private Integer isVerified;

}
