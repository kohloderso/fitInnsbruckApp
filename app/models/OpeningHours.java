package models;

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


    }
    /*table is not ready for loading the opening times */
    /*calculates if a specific facility is open or not*/
    public boolean isOpen(LocalTime time){

    }
}
