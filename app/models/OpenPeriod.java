package models;


import play.db.ebean.Model;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
public class OpenPeriod extends Model {
    @Id
    public int opID;
    public String begin;
    public String end;
    @ManyToOne
    public OpeningHours openingHours;



    public String toString() {
            return begin.toString() + " - " + end.toString();
    }

}