package org.ivan_smirnov.orm.entity;

import org.ivan_smirnov.orm.annotation.Column;
import org.ivan_smirnov.orm.annotation.Id;
import org.ivan_smirnov.orm.annotation.Table;

@Table(name = "Worker")
public class Person {
    @Id
    @Column(name = "person_id")
    private  int id;

    @Column
    private String name;

    @Column
    private int age;

    public Person(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}