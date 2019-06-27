package com.ontology.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service("ConfigParam")
public class ConfigParam {

	/**
	 *  SDK参数
	 */
	@Value("${service.restfulUrl}")
	public String RESTFUL_URL;

	/**
	 *  回调地址
	 */
	@Value("${callback.url}")
	public String CALLBACK_URL;

	/**
	 *  注册合约地址
	 */
	@Value("${contract.hash.ons}")
	public String CONTRACT_HASH_ONS;

	/**
	 *  域名管理者
	 */
	@Value("${ons.owner}")
	public String ONS_OWNER;

}