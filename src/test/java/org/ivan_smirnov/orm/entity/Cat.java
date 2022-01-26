package org.ivan_smirnov.orm.entity;

import org.ivan_smirnov.orm.annotation.Column;
import org.ivan_smirnov.orm.annotation.Id;
import org.ivan_smirnov.orm.annotation.Table;

@Table
public class Cat {
    @Id
    private int cat_id;

    @Column
    private String nickname;

    @Column
    private String color;
}
