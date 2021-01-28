package com.lve.risk.service;

import com.lve.risk.entity.Person;

public interface TestDemoService {
    public Person getPerson(String id);

    public void addPerson(Person person);

    public void updatePerson(Person person);

    public void deletePerson(String id);
}
