package models;

import play.data.validation.Constraints;
import play.data.validation.ValidationError;
import play.db.ebean.Model;

import javax.persistence.*;
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

    @OneToOne(cascade=CascadeType.ALL)
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
        if (find.where().eq("name", name).findUnique() != null) {
            list.add(new ValidationError("name", "Diese Sportstaette existiert bereits"));
            errors.put("name", list);
        }

        return errors.isEmpty() ? null : errors;
    }

    public static Finder<String, Facility> find = new Finder<String, Facility>(
            String.class, Facility.class
    );

    public static List<Facility> findFacilitesForSports(List<SportType> sports) {
        return find.where().in("possibleSport", sports).findList();
    }

    public static List<Facility> findFacilities(Boolean roof, List<SportType> sports, LocalTime begin, LocalTime end) {

        if(roof != null) {
            return find.where().in("possibleSport", sports).eq("roof", roof).findList();
        }
        else {
           return findFacilitesForSports(sports);
        }
        //find.where().in("possibleSport", sports).eq("roof", roof).between("openingHours)
    }

    public String toString() {
        String s = "ID: " + objectid + "\n" + name + "\n" + address + "\n" + facilityType + "\n" + "roofed: " + roof + "\n" + possibleSport + "\n" + lat + "\t" + lon + "\n" + openingHours.toString() + "\n" + prices.toString();
        return s;
    }
}
