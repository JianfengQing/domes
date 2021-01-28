package com.lve.risk.service.impl;

import com.lve.risk.exception.BusinessException;
import com.lve.risk.service.TestDemoService;
import com.lve.risk.utils.RedisUtils;
import com.lve.risk.constant.AMQPConstant;
import com.lve.risk.entity.Person;
import com.lve.risk.mapper.TestDemoMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class TestDemoServiceImpl implements TestDemoService {
    @Resource
    private TestDemoMapper testDemoMapper;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private RedisUtils redisUtils;

    @Override
    public Person getPerson(String id) {
        if (StringUtils.isNotEmpty(id))
        throw  new BusinessException("200", "获取人员失败  异常抛出成功");
        return testDemoMapper.getPerson(id);
    }

    @Override
    public void addPerson(Person person) {
        person.setBirthday(new Date());
        redisUtils.set(person.getName(),person,60 * 5);
        mongoTemplate.save(person);
        rabbitTemplate.convertAndSend(AMQPConstant.SIMPLE_QUEUE_NAME,person);
        testDemoMapper.addPerson(person);
    }

    @Override
    public void updatePerson(Person person) {
        testDemoMapper.updatePerson(person);
    }

    @Override
    public void deletePerson(String id) {
        testDemoMapper.deletePerson(id);
    }
}
