package com.lve.risk.controller;

import com.lve.risk.annotation.Authority;
import com.lve.risk.entity.Person;
import com.lve.risk.exception.ResponseData;
import com.lve.risk.feign.TestDemoFeign;
import com.lve.risk.service.TestDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class TestDemoController {

    @Autowired
    private TestDemoService testDemoService;

    @Resource
    private TestDemoFeign testDemoFeign;


    @PostMapping(value = "addPerson")
    public ResponseData addPerson(@RequestBody Person person){
        testDemoService.addPerson(person);
        return ResponseData.success(200, "success", "成功");
    }

    @DeleteMapping(value = "persons/{id}")
    public String deletePerson(@PathVariable("id") String id){
        testDemoService.deletePerson(id);
        return "删除成功";
    }

    @PutMapping(value = "persons/{id}")
    public String updatePerson(@RequestBody Person person){
        testDemoService.updatePerson(person);
        return "更新成功";
    }

    @GetMapping("getPersons")
    public ResponseData getPersons(){
        System.out.println(testDemoFeign.test());
        return ResponseData.success();
    }

    @Authority(authority = "test")
    @GetMapping(value = "persons/{id}")
    public ResponseData<Person> getPerson(@PathVariable("id") String id){
        return ResponseData.success(200, "success", testDemoService.getPerson(id));
    }

}
