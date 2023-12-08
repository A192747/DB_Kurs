package com.example.Kurs.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "auto")
public class Auto {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "num")
    private String num;
    @Column(name = "color")
    private String color;

    @Column(name = "mark")
    private String mark;
    @ManyToOne
    @JoinColumn(name = "personnel_id", referencedColumnName = "id")
    private AutoPersonnel person;

    @OneToMany(mappedBy = "auto")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Journal> journal = new ArrayList<>();

    public List<Journal> getJournal() {
        return journal;
    }

    public String getMark() {
        return mark;
    }

    public Integer getPersonnel_id(){
        return person.getId();
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public void setJournal(List<Journal> journal) {
        this.journal = journal;
    }
    public Auto() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public AutoPersonnel getPerson() {
        return person;
    }

    public void setPerson(AutoPersonnel person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Auto{" +
                "id=" + id +
                ", num='" + num + '\'' +
                ", color='" + color + '\'' +
                ", mark='" + mark + '\'' +
                ", person=" + person.getId() +
                '}';
    }

    public Auto(int id, String num, String color, String mark, AutoPersonnel person, List<Journal> journal) {
        this.id = id;
        this.num = num;
        this.color = color;
        this.mark = mark;
        this.person = person;
        this.journal = journal;
    }

    public Auto(String num, String color, String mark, AutoPersonnel person) {
        this.num = num;
        this.color = color;
        this.mark = mark;
        this.person = person;
    }

}
