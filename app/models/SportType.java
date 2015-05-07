package models;

import play.db.ebean.Model;
import scala.collection.JavaConverters;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

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

    public static scala.collection.immutable.List<SportType> allAsScalaList() {
        List<SportType> sports = SportType.find.all();
        scala.collection.immutable.List<SportType> ls = JavaConverters.asScalaBufferConverter(sports).asScala().toList();
        return ls;
    }

}
