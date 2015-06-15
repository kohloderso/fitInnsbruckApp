package models;

import play.data.format.Formats;
import play.db.ebean.Model;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import javax.persistence.*;

/**
 * This class represents a sports activity.
 */
@Entity
public class Activity extends Model {

    @Id
    public int activityID;
    public String beginOfActivity;
    public String endOfActivity;
    @Formats.DateTime(pattern = "dd/MM/yyyy")
    public Date day;

    @ManyToOne(cascade= CascadeType.PERSIST)
    @JoinColumn(name="sportID")
    public SportType sport;

    @ManyToOne(cascade=CascadeType.PERSIST)
    public Facility place;

    public double calories;

    /**
     * compute the calories, that were burned during this activity
     * @return
     */
    public double computeCalories(){
        calories = 0;
        // TODO
        return calories;
    }

    /**
     * compute the duration of this activity
     * @return
     */
    public Duration duration() {
        LocalTime start = LocalTime.parse(beginOfActivity);
        LocalTime end = LocalTime.parse(endOfActivity);
        Duration duration = Duration.between(start, end);
        return duration;
    }

    public String toString() {
        return place.name + " " + sport.description + " Duration: " + duration().toString() + " " + beginOfActivity + "-" + endOfActivity + " am " + day.toString();
    }

    public String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.format(day);
    }

    public static Model.Finder<String, Activity> find = new Model.Finder<String, Activity>(
            String.class, Activity.class
    );

}
