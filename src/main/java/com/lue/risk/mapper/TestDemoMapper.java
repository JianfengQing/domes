package com.lue.risk.mapper;

import com.lue.risk.entity.Person;
import org.apache.ibatis.annotations.*;

@Mapper
public interface TestDemoMapper {

    @Select("select * from Person where id = #{id}")
    public Person getPerson(@Param("id") String id);

    @Insert("insert into Person values(#{name},#{sex},#{birthday})")
    public void addPerson(Person person);

    @Update("update Person set name = #{name} where id = #{id}")
    public void updatePerson(Person person);

    @Delete("delete from Person where id = #{id}")
    public void deletePerson(@Param("id") String id);

}
