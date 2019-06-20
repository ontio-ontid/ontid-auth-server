package com.ontology.mapper;

import com.ontology.entity.Message;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;


@Component
public interface MessageMapper extends Mapper<Message> {

}
