package com.lve.service;

import com.lve.entity.Person;

public interface TestDemoService {
    public Person getPerson(String id);

    public void addPerson(Person person);

    public void updatePerson(Person person);

    public void deletePerson(String id);
}
