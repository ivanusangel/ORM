package org.ivan_smirnov.orm.entity;

import org.ivan_smirnov.orm.annotation.Column;
import org.ivan_smirnov.orm.annotation.Table;

@Table(name = "Person")
public class Person {
    @Column(name = "person_id")
    private  int id;

    @Column
    private String name;

    @Column
    private int age;
}