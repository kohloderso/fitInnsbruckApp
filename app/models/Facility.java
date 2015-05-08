package models;

import com.avaje.ebean.Expr;
import com.avaje.ebean.Expression;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Christina on 18.01.2015.
 */
@Entity
public class Facility extends Model {
    @Id
    public int objectid;
    public String name;
    public String address;
    public String facilityType;
    public boolean roof;

    @ManyToMany(cascade=CascadeType.ALL)
    public List<SportType> possibleSport;

    public String lat;
    public String lon;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="hoursID")
    public OpeningHours openingHours;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="priceID")
    public Pricing prices;

    public static Finder<String, Facility> find = new Finder<String, Facility>(
            String.class, Facility.class
    );

    public static List<Facility> findFacilitesForSport(SportType sport) {
        // TODO
        return null;
    }

    public String toString() {
        String s = "ID: " + objectid + "\n" + name + "\n" + address + "\n" + facilityType + "\n" + "roofed: " + roof + "\n" + possibleSport + "\n" + lat + "\t" + lon + "\n" + openingHours.toString() + "\n" + prices.toString();
        return s;
    }
}
