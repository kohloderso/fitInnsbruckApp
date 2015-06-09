package models;

import play.Logger;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;
import play.db.ebean.Model;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Christina on 18.01.2015.
 */
@Entity
public class Facility extends Model {
    @Id
    public int objectid;
    @Constraints.Required(message = "Darf nicht leer sein")
    public String name;
    @Constraints.Required(message = "Darf nicht leer sein")
    public String address;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name="typeID")
    public FacilityType facilityType;
    public boolean roof;

    @ManyToMany(cascade=CascadeType.ALL)
    public List<SportType> possibleSport;

    //TODO find regex that represents coordinates
    public String lat;
    public String lon;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="hoursID")
    public OpeningHours openingHours;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="priceID")
    public Pricing prices;


    public Map<String, List<ValidationError>> validate() {
        Map<String, List<ValidationError>> errors = new HashMap<String, List<ValidationError>>();
        List<ValidationError> list = new ArrayList<ValidationError>();
        if (find.where().eq("name", name).ne("objectid", objectid).findRowCount() != 0) {
            list.add(new ValidationError("name", "Diese Sportstaette existiert bereits"));
            errors.put("name", list);
        }

        return errors.isEmpty() ? null : errors;
    }

    public static Finder<String, Facility> find = new Finder<String, Facility>(
            String.class, Facility.class
    );

    public boolean isOpen(LocalTime begin, LocalTime end, DayOfWeek dayOfWeek) {
        return this.openingHours.isOpen(begin, end, dayOfWeek);
    }

    public static List<Facility> findFacilitiesForSports(List<SportType> sports) {
        return find.where().in("possibleSport", sports).findList();
    }

    /**
     *
     * @param roof
     * @param sports
     * @param begin
     * @param end
     * @param dayOfWeek
     * @return
     */
    public static List<Facility> findFacilities(Boolean roof, List<SportType> sports, LocalTime begin, LocalTime end, DayOfWeek dayOfWeek) {
        Logger.info("Searching for facility with roof: " + roof + sports.toString() + " begin: " + begin + " end: " + end);
        List<Facility> list;
        if(roof != null) {
            list = find.where().in("possibleSport", sports).eq("roof", roof).findList();
        }
        else {
           list = findFacilitiesForSports(sports);
        }
        List<Facility> tmp = new ArrayList<Facility>();
        for(Facility f: list) {
            if(!f.isOpen(begin, end, dayOfWeek)) {
                Logger.info(f.name + " is not open, putting it at the end of the list");
                list.remove(f);
                tmp.add(f);
            }
        }
        list.addAll(tmp);
        return list;
    }

    public String toString() {
        String s = "ID: " + objectid + "\n" + name + "\n" + address + "\n" + facilityType + "\n" + "roofed: " + roof + "\n" + possibleSport + "\n" + lat + "\t" + lon + "\n" + openingHours.toString() + "\n" + prices.toString();
        return s;
    }
}
