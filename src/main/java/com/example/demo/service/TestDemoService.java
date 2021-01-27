package com.example.demo.service;

import com.example.demo.entity.Person;

public interface TestDemoService {
    public Person getPerson(String id);

    public void addPerson(Person person);

    public void updatePerson(Person person);

    public void deletePerson(String id);
}
