package models;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kathi on 19.04.2015.
 */
@Entity
public class OpeningHours {

    @Id
    public int id;
    public List<OpenPeriod> monday;
    public List<OpenPeriod> tuesday;
    public List<OpenPeriod> wednesday;
    public List<OpenPeriod> thursday;
    public List<OpenPeriod> friday;
    public List<OpenPeriod> saturday;
    public List<OpenPeriod> sunday;
   // public List<OpenPeriod> holidays;


    /*table is not ready for loading the opening times */
    /*calculates if a specific facility is open or not*/
    public boolean isOpen(LocalTime time){

        return false;
    }

    public String toString() {
        String s = "monday: ";
        for(OpenPeriod openPeriod : monday) {
            s = s + openPeriod.toString() + ", ";
        }
        s = s + "\ntuesday: ";
        for(OpenPeriod openPeriod : tuesday) {
            s = s + openPeriod.toString() + ", ";
        }
        s = s + "\nwednesday: ";
        for(OpenPeriod openPeriod : wednesday) {
            s = s + openPeriod.toString() + ", ";
        }
        s = s + "\nthursday: ";
        for(OpenPeriod openPeriod : thursday) {
            s = s + openPeriod.toString() + ", ";
        }
        s = s + "\nfriday: ";
        for(OpenPeriod openPeriod : friday) {
            s = s + openPeriod.toString() + ", ";
        }
        s = s + "\nsaturday: ";
        for(OpenPeriod openPeriod : saturday) {
            s = s + openPeriod.toString() + ", ";
        }
        s = s + "\nsunday: ";
        for(OpenPeriod openPeriod : sunday) {
            s = s + openPeriod.toString() + ", ";
        }
        return s;
    }
}
