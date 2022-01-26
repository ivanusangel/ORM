package org.ivan_smirnov.orm;

import org.ivan_smirnov.orm.entity.Cat;
import org.ivan_smirnov.orm.entity.Person;
import org.ivan_smirnov.orm.entity.Terminator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QueryGeneratorTest {

    QueryGenerator queryGenerator;

    @Before
    public void init() {
        queryGenerator = new DefaultQueryGenerator();
    }

    @Test
    public void findAllAnnotatedWithTableName(){
        String expectedQuery = "SELECT person_id, name, age FROM Worker;";
        String actualQuery = queryGenerator.findAll(Person.class);

        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    public void findAllAnnotatedWithOutTableName(){
        String expectedQuery = "SELECT nickname, color FROM Cat;";
        String actualQuery = queryGenerator.findAll(Cat.class);
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    public void findAllNotAnnotatedTable(){
        Exception exception = Assert.assertThrows(
                IllegalArgumentException.class,
                () -> queryGenerator.findAll(Terminator.class)
        );
        String expectedMessage = "Terminator is not entity";
        String actualMessage = exception.getLocalizedMessage();
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void  findById() {
        String expectedQuery = "SELECT person_id, name, age FROM Worker WHERE person_id = 4;";
        QueryGenerator queryGenerator = new DefaultQueryGenerator();
        String actualQuery = queryGenerator.findById(4, Person.class);
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    public void  insert() throws IllegalAccessException {
        String expectedQuery = "INSERT INTO Worker (person_id, name, age) VALUES ('2', 'John', '15');";
        QueryGenerator queryGenerator = new DefaultQueryGenerator();
        String actualQuery = queryGenerator.insert(new Person(2, "John", 15));
    }

    @Test
    public void  delete(){

    }
}