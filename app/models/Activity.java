package models;

import play.data.format.Formats;
import play.db.ebean.Model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import javax.persistence.*;


@Entity
public class Activity extends Model {

    @Id
    public int activityID;
    public String beginOfActivity;
    public String endOfActivity;
    @Formats.DateTime(pattern = "dd/MM/yyyy")
    public Date day;


    //public Weather weather;

    @ManyToOne(cascade= CascadeType.PERSIST)
    @JoinColumn(name="sportID")
    public SportType sport;

    @ManyToOne(cascade=CascadeType.PERSIST)
    public Facility place;

    public double calories;

    public double computeCalories(){
        calories = 0;
        //TODO
        return calories;
    }

    public Duration duration() {
        LocalTime start = LocalTime.parse(beginOfActivity);
        LocalTime end = LocalTime.parse(endOfActivity);
        Duration duration = Duration.between(start, end);
        return duration;
    }

    public String toString() {
        return place.name + " " + sport.description + " Duration: " + duration().toString() + " " + beginOfActivity + "-" + endOfActivity + " am " + day.toString();
    }

    public static Model.Finder<String, Activity> find = new Model.Finder<String, Activity>(
            String.class, Activity.class
    );


    public static Activity findActivity(String sport) {


        return find.where().eq("sport", sport).findUnique();
    }


}
