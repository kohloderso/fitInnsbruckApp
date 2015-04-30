package models;

import com.avaje.ebean.Expr;
import com.avaje.ebean.Expression;
import play.data.validation.Constraints;
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
    public String type;
    //@ManyToMany
    public List<Sport> possibleSport;
    public String lat;
    public String lon;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="opID")
    public OpeningHours openingHours;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="priceID")
    public Pricing prices;

    public static Finder<String, Facility> find = new Finder<String, Facility>(
            String.class, Facility.class
    );

    public static List<Facility> findFacilitesForSport(Sport sport) {
        Expression orEx = Expr.or(Expr.eq("type", sport.getOutsidePlace()), Expr.eq("type", sport.getRoofedPlace()));
        return find.where().add(orEx).findList();
    }

    public String toString() {
        String s = "ID: " + objectid + "\n" + name + "\n" + address + "\n" + type + "\n" + possibleSport + "\n" + lat + "\t" + lon + "\n" + /* openingHours.toString() + "\n" +*/ prices.toString();
        return s;
    }
}
