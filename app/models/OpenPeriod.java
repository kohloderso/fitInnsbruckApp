package models;


import play.db.ebean.Model;

import javax.persistence.*;
import java.time.LocalTime;

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

    /**
     * @param startQ
     * @param endQ
     * @return true if the startQ and endQ times are between the times of this OpenPeriod
     */
    public boolean isOpen(LocalTime startQ, LocalTime endQ) {
        if(begin.isEmpty() || end.isEmpty()) return false;
        LocalTime openPeriodBegin = LocalTime.parse(begin);
        LocalTime openPeriodEnd = LocalTime.parse(end);
        return (startQ.isAfter(openPeriodBegin) || startQ.equals(openPeriodBegin)) && (endQ.isBefore(openPeriodEnd) || endQ.equals(openPeriodEnd));
    }

    public static Finder<String, OpenPeriod> find = new Finder<String, OpenPeriod>(
            String.class, OpenPeriod.class
    );

    public String toString() {
            return begin.toString() + " - " + end.toString();
    }

}