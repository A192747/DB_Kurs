package com.example.Kurs.models;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Entity
@Table(name = "journal")
public class Journal {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "time_out")
    private Timestamp time_out;
    @Column(name = "time_in")
    private Timestamp time_in;
    @ManyToOne
    @JoinColumn(name = "auto_id", referencedColumnName = "id")
    private Auto auto;
    @ManyToOne
    @JoinColumn(name = "route_id", referencedColumnName = "id")
    private Routes route;

    public Journal() {
    }


    public Journal(Timestamp time_out, Timestamp time_in, Auto auto, Routes route) {
        this.time_out = time_out;
        this.time_in = time_in;
        this.auto = auto;
        this.route = route;
    }

    @Override
    public String toString() {
        return "Journal{" +
                "id=" + id +
                ", time_out=" + time_out +
                ", time_in=" + time_in +
                ", auto=" + auto +
                ", route=" + route +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Timestamp getTime_out() {
        if (time_out == null)
            return null;
        return time_out;
    }

    public void setTime_out(Timestamp time_out) {
        this.time_out = time_out;
    }

    public Timestamp getTime_in() {
        if (time_in == null)
            return null;
        return time_in;
    }

    public void setTime_in(Timestamp time_in) {
        this.time_in = time_in;
    }

    public Auto getAuto() {
        return auto;
    }

    public Integer getAuto_id() {
        if (auto == null)
            return null;
        return auto.getId();
    }


    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public Routes getRoute() {
        return route;
    }

    public void setRoute(Routes route) {
        this.route = route;
    }
    public Integer getRoute_id(){
        if (route == null)
            return null;
        return route.getId();
    }

    public Journal(int id, Timestamp time_out, Timestamp time_in, Auto auto, Routes route) {
        this.id = id;
        this.time_out = time_out;
        this.time_in = time_in;
        this.auto = auto;
        this.route = route;
    }
}
