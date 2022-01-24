package org.ivan_smirnov.orm;

import org.ivan_smirnov.orm.entity.Person;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QueryGeneratorTest {

    @Test
    public void findAllTest(){
        String expectedQuery = "SELECT person_id, name, age FROM Person;";
        QueryGenerator queryGenerator = new DefaultQueryGenerator();
        String actualQuery = queryGenerator.findAll(Person.class);

        assertEquals(expectedQuery, actualQuery);
    }
}