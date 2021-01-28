package com.lue.risk.controller;

import com.lue.risk.entity.Person;
import com.lue.risk.exception.ResponseData;
import com.lue.risk.feign.TestDemoFeign;
import com.lue.risk.service.TestDemoService;
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

    @GetMapping(value = "persons/{id}")
    public ResponseData<Person> getPerson(@PathVariable("id") String id){
        return ResponseData.success(200, "success", testDemoService.getPerson(id));
    }

}
