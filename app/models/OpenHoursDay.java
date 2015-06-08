package models;

import play.db.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalTime;
import java.util.List;

/**
 * Created by Christina on 01.05.2015.
 */
@Entity
public class OpenHoursDay extends Model {

    @Id
    public int dayID;
    @OneToMany(cascade = CascadeType.ALL)
    public List<OpenPeriod> openPeriods;

    public boolean isOpen(LocalTime begin, LocalTime end) {
        for(OpenPeriod op: openPeriods) {
            if(!op.isOpen(begin, end)) return false;
        }
        return true;
    }

    public String toString() {
        String s = "";
        for(OpenPeriod openPeriod : openPeriods) {
            s = s + openPeriod.toString() + ", ";
        }
        return s.substring(0,s.length()-2);
    }
}
