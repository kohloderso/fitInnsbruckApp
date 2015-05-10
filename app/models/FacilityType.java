package models;

import play.db.ebean.Model;
import scala.collection.JavaConverters;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

/**
 * Created by Christina on 09.05.2015.
 */
@Entity
public class FacilityType extends Model {
    @Id
    public int typeID;
    public String description;


    public String toString() {
        return description;
    }

    public static Model.Finder<String, FacilityType> find = new Model.Finder<String, FacilityType>(
            String.class, FacilityType.class
    );

    public static scala.collection.immutable.List<FacilityType> allAsScalaList() {
        List<FacilityType> sports = FacilityType.find.all();
        scala.collection.immutable.List<FacilityType> ls = JavaConverters.asScalaBufferConverter(sports).asScala().toList();
        return ls;
    }

}
