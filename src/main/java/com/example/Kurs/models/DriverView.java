package com.example.Kurs.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import java.io.Serializable;
import java.util.Random;

@Entity
@Immutable
@Subselect("select ROW_NUMBER() OVER () as id, * from driver")
public class DriverView implements Serializable {

    @Id
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "first_name")
    private String first_name;
    @Column(name = "last_name")
    private String last_name;
    @Column(name = "parther_name")
    private String parther_name;


    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getParther_name() {
        return parther_name;
    }

    @Override
    public String toString() {
        return "\n" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", parther_name='" + parther_name + '\'';
    }
}
