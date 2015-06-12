package models;

import play.db.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalTime;
import java.util.List;

/**
 * Class that represents the opening hours of a facility on one single day.
 * Opening hours are subdivided in OpenPeriods so that you can have the facility open from e.g. 8-12 and again from 14-18.
 * One day consists of a list of OpenPeriods.
 */
@Entity
public class OpenHoursDay extends Model {

    @Id
    public int dayID;
    @OneToMany(cascade = CascadeType.ALL)
    public List<OpenPeriod> openPeriods;

    /**
     *
     * @param begin
     * @param end
     * @return true if begin and end lie in one of the OpenPeriods
     */
    public boolean isOpen(LocalTime begin, LocalTime end) {
        for(OpenPeriod op: openPeriods) {
            if(op.isOpen(begin, end)) return true;
        }
        return false;
    }

    public String toString() {
        String s = "";
        for(OpenPeriod openPeriod : openPeriods) {
            s = s + openPeriod.toString() + ", ";
        }
        return s.substring(0,s.length()-2);
    }
}
