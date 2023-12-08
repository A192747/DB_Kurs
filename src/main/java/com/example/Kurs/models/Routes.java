package com.example.Kurs.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "routes")
public class Routes {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "route")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Journal> journal = new ArrayList<>();

    public List<Journal> getJournal() {
        return journal;
    }

    public void setJournal(List<Journal> journal) {
        this.journal = journal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Routes() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Routes(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Routes{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
