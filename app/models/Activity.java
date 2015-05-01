package models;

import play.data.format.Formats;
import play.db.ebean.Model;

import java.time.LocalDateTime;
import java.util.*;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Tammy on 18.04.2015.
 */
@Entity
public class Activity extends Model {

    @Id
    public int id;
    public Date beginOfActivity;
    public Date endOfActivity;


    public Weather weather;

    public SportType sport;

    public Facility place;

    public int calories;

    public static Model.Finder<String, Activity> find = new Model.Finder<String, Activity>(
            String.class, Activity.class
    );


    public static Activity findActivity(String sport) {


        return find.where().eq("sport", sport).findUnique();
    }


}
