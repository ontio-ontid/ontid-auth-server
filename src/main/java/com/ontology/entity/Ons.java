package com.ontology.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tbl_ons")
@Data
public class Ons {
    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;

    private String ontid;
    private String domain;
    private String params;
    private String txHash;
    private Integer success;
}
