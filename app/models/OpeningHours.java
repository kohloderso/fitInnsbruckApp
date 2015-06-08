package models;


import play.db.ebean.Model;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kathi on 19.04.2015.
 */
@Entity
public class OpeningHours extends Model {

    @Id
    public int hoursID;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="mondayID")
    public OpenHoursDay monday;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="tuesdayID")
    public OpenHoursDay tuesday;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="wednesdayID")
    public OpenHoursDay wednesday;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="thursdayID")
    public OpenHoursDay thursday;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="fridayID")
    public OpenHoursDay friday;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="saturdayID")
    public OpenHoursDay saturday;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="sundayID")
    public OpenHoursDay sunday;

    /*calculates if a specific facility is open or not*/
    public boolean isOpen(LocalTime begin, LocalTime end, DayOfWeek weekday){
        switch(weekday) {
            case MONDAY:
                return monday.isOpen(begin, end);
            case TUESDAY:
                return tuesday.isOpen(begin, end);
            case WEDNESDAY:
                return wednesday.isOpen(begin, end);
            case THURSDAY:
                return thursday.isOpen(begin, end);
            case FRIDAY:
                return friday.isOpen(begin, end);
            case SATURDAY:
                return saturday.isOpen(begin, end);
            case SUNDAY:
                return sunday.isOpen(begin, end);
            default:
                return false;
        }
    }

    public String toString() {
        String s = "monday: " + monday.toString();
        s = s + "\ntuesday: " + tuesday.toString();
        s = s + "\nwednesday: " + wednesday.toString();
        s = s + "\nthursday: " + thursday.toString();
        s = s + "\nfriday: " + friday.toString();
        s = s + "\nsaturday: " + saturday.toString();
        s = s + "\nsunday: " + sunday.toString();
        return s;
    }
}
