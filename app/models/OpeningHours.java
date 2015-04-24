package models;

import jdk.nashorn.internal.objects.NativeArray;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

/**
 * Created by Kathi on 19.04.2015.
 */
@Entity
public class OpeningHours {

    @Id
    public int id;
    List<OpenPeriod> monday;
    List<OpenPeriod> tuesday;
    List<OpenPeriod> wednesday;
    List<OpenPeriod> thursday;
    List<OpenPeriod> friday;
    List<OpenPeriod> saturday;
    List<OpenPeriod> sunday;
    List<OpenPeriod> holidays;


    public class OpenPeriod{
        LocalTime begin;
        LocalTime end;

        public String toString() {
            return begin.toString() + " - " + end.toString();
        }

    }
    /*table is not ready for loading the opening times */
    /*calculates if a specific facility is open or not*/
    public boolean isOpen(LocalTime time){

        return false;
    }

    public String toString() {
        String s = "monday: ";
        for(OpenPeriod openPeriod : monday) {
            s = s + openPeriod.toString();
        }
        s = s + "\ntuesday: ";
        for(OpenPeriod openPeriod : tuesday) {
            s = s + openPeriod.toString();
        }
        s = s + "\nwednesday: ";
        for(OpenPeriod openPeriod : wednesday) {
            s = s + openPeriod.toString();
        }
        s = s + "\nthursday: ";
        for(OpenPeriod openPeriod : thursday) {
            s = s + openPeriod.toString();
        }
        s = s + "\nfriday: ";
        for(OpenPeriod openPeriod : friday) {
            s = s + openPeriod.toString();
        }
        s = s + "\nsaturday: ";
        for(OpenPeriod openPeriod : saturday) {
            s = s + openPeriod.toString();
        }
        s = s + "\nsunday: ";
        for(OpenPeriod openPeriod : sunday) {
            s = s + openPeriod.toString();
        }
        return s;
    }
}
