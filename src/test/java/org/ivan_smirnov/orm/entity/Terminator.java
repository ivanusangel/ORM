package org.ivan_smirnov.orm.entity;

import org.ivan_smirnov.orm.annotation.Column;
import org.ivan_smirnov.orm.annotation.Id;

public class Terminator {
    @Id
    @Column(name = "id")
    private int id;

    String voice = "I'll be back";
}
