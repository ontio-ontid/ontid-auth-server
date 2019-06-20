package com.ontology;

import com.alibaba.fastjson.JSON;
import com.ontology.entity.Ons;
import com.ontology.mapper.OnsMapper;
import com.ontology.utils.SDKUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootApplicationTests {

	@Autowired
	private OnsMapper onsMapper;
	@Autowired
	private SDKUtil sdkUtil;

	@Test
	public void updateOns() {
		Ons ons = onsMapper.selectByPrimaryKey("33e02987-5111-486a-8e9c-851ce992b7d1");
		ons.setSuccess(0);
		onsMapper.updateByPrimaryKeySelective(ons);
	}

	@Test
	public void selectOne() {
		Ons ons = new Ons();
		ons.setOntid("AGWYQHd4bzyhrbpeYCMsxXYQcJo95VtR5q");
		List<Ons> select = onsMapper.select(ons);
		log.info("{}",select);
	}

	@Test
	public void checkEvent() throws Exception {
		Object event = sdkUtil.checkEvent("d471693cc856830928a3ce24422c4cd3d0415e205125037a6f45615a297c2127");
		log.info("{}",event);
	}

}
