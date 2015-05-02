package models;


import play.db.ebean.Model;

import javax.persistence.*;

@Entity
public class OpenPeriod extends Model {
    @Id
    public int opID;
    @Column(name="period_begin")
    public String begin;
    @Column(name="period_end")
    public String end;
    @ManyToOne
    public OpeningHours openingHours;



    public String toString() {
            return begin.toString() + " - " + end.toString();
    }

}