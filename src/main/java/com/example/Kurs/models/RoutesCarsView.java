package com.example.Kurs.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import java.io.Serializable;
import java.util.Random;

@Entity
@Immutable
@Subselect("select ROW_NUMBER() OVER () as id, * from route_cars")
public class RoutesCarsView implements Serializable {

    @Id
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getNum_cars() {
        return num_cars;
    }

    @Column(name = "name")
    private String name;
    @Column(name = "num_cars")
    private int num_cars;

    @Override
    public String toString() {
        return  "\n" +
                "name='" + name + '\'' +
                ", num_cars=" + num_cars;
    }
}
