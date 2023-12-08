package com.example.Kurs.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auto_personnel")
public class AutoPersonnel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name")
    private String first_name;
    @Column(name = "last_name")
    private String last_name;
    @Column(name = "parther_name")
    private String parther_name;

    @OneToMany(mappedBy = "person")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Auto> cars = new ArrayList<>();

    public AutoPersonnel() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getParther_name() {
        return parther_name;
    }

    public void setParther_name(String pather_name) {
        this.parther_name = pather_name;
    }

    @Override
    public String toString() {
        return "AutoPersonnel{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", pather_name='" + parther_name + '\'' +
                '}';
    }

    public List<Auto> getCars() {
        return cars;
    }

    public void setCars(List<Auto> cars) {
        this.cars = cars;
    }

    public AutoPersonnel(String first_name, String last_name, String parther_name, List<Auto> cars) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.parther_name = parther_name;
        this.cars = cars;
    }

    public AutoPersonnel(String first_name, String last_name, String parther_name) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.parther_name = parther_name;
    }
}
