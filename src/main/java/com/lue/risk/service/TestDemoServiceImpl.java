package com.lue.risk.service;

import com.lue.risk.utils.RedisUtil;
import com.lue.risk.constant.AMQPConstant;
import com.lue.risk.entity.Person;
import com.lue.risk.mapper.TestDemoMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class TestDemoServiceImpl implements TestDemoService{
    @Resource
    private TestDemoMapper testDemoMapper;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public Person getPerson(String id) {
        Person person = (Person) redisUtil.get(id);
        person.getName();
        return testDemoMapper.getPerson(id);
    }

    @Override
    public void addPerson(Person person) {
        person.setBirthday(new Date());
        redisUtil.set(person.getName(),person,60 * 5);
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
