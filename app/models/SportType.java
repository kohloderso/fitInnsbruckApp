package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Christina on 01.05.2015.
 */
@Entity
public class SportType extends Model {
    @Id
    public int sportID;
    public String description;


    public String toString() {
        return description;
    }

    public static Finder<String, SportType> find = new Finder<String, SportType>(
            String.class, SportType.class
    );


}
