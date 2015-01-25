package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

/**
 * Created by Christina on 18.01.2015.
 */
@Entity
public class Facility extends Model{
    @Id
    public int objectID;
    public String name;
    public String address;
    public String type;
    public String description;
    public String lat;
    public String lon;

    public static Finder<String,Facility> find = new Finder<String,Facility>(
            String.class, Facility.class
    );

    public static List<Facility> findFacilitesByGroup(String group) {
        return find.where().eq("description", group).findList();
    }
}
