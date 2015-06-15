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
 * This class represents a facility were you can do some kind of sport
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


    /**
     * Check if every new facility has a unique name. If the name already exists, add an error to the error map of the
     * corresponding form.
     * @return either null, if there were no errors or a Map with the errors
     */
    public Map<String, List<ValidationError>> validate() {
        Map<String, List<ValidationError>> errors = new HashMap<String, List<ValidationError>>();
        List<ValidationError> list = new ArrayList<ValidationError>();
        if (find.where().eq("name", name).ne("objectid", objectid).findRowCount() != 0) {
            list.add(new ValidationError("name", "Diese Sportstaette existiert bereits"));
            errors.put("name", list);
        }

        return errors.isEmpty() ? null : errors;
    }

    /**
     * check if this facility is open at the specified time and day of the week
     * @param begin
     * @param end
     * @param dayOfWeek
     * @return true if it is open, false otherwise
     */
    public boolean isOpen(LocalTime begin, LocalTime end, DayOfWeek dayOfWeek) {
        return this.openingHours.isOpen(begin, end, dayOfWeek);
    }

    public String toString() {
        String s = "ID: " + objectid + "\n" + name + "\n" + address + "\n" + facilityType + "\n" + "roofed: " + roof + "\n" + possibleSport + "\n" + lat + "\t" + lon + "\n" + openingHours.toString() + "\n" + prices.toString();
        return s;
    }

    public static Finder<String, Facility> find = new Finder<String, Facility>(
            String.class, Facility.class
    );

    /**
     * Find all facilities were you can do one of the sports in the specified list.
     * @param sports
     * @return list of all facilities were you can do one or more of the sports
     */
    public static List<Facility> findFacilitiesForSports(List<SportType> sports) {
        if(sports.isEmpty()) return find.all();
        return find.where().in("possibleSport", sports).findList();
    }

    /**
     * Find all facilities were you can do one or more of the sports and that have a roof if roof is true, no roof if
     * roof is false or where the roof isn't taken into account if roof is null
     * @param roof
     * @param sports
     * @return list of facilities with the specified properties
     */
    public static List<Facility> findFacilities(Boolean roof, List<SportType> sports) {
        Logger.info("Searching for facility with roof: " + roof + sports.toString());
        List<Facility> list;
        if (roof != null) {
            if(sports.isEmpty()) {
                list = find.where().eq("roof", roof).findList();
            } else {
                list = find.where().in("possibleSport", sports).eq("roof", roof).findList();
            }
        } else {
            list = findFacilitiesForSports(sports);
        }
        return list;
    }

    /**
     * Find all facilities were you can do one or more of the sports and that have a roof if roof is true, no roof if
     * roof is false or where the roof isn't taken into account if roof is null. Also use the specified times, to only
     * return facilities that are open between begin and end.
     * @param roof
     * @param sports
     * @param begin
     * @param end
     * @param dayOfWeek
     * @return list of facilities with the specified properties
     */
    public static List<Facility> findFacilities(Boolean roof, List<SportType> sports, LocalTime begin, LocalTime end, DayOfWeek dayOfWeek) {
        Logger.info("Searching for facility with roof: " + roof + sports.toString() + " begin: " + begin + " end: " + end);
        List<Facility> tmp = findFacilities(roof, sports);
        List<Facility> list = new ArrayList<Facility>();
        for(Facility f: tmp) {
            if (f.isOpen(begin, end, dayOfWeek)) {
                list.add(f);
            }
        }
        /*for(Facility f: list) {
            if(!f.isOpen(begin, end, dayOfWeek)) {
                Logger.info(f.name + " is not open, putting it at the end of the list");
                //list.remove(f);
                tmp.add(f);
            } else {
                tmp.add(0, f);
            }
        }
        //list.addAll(tmp);*/
        return list;
    }


}
