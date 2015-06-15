package models;

import play.data.validation.ValidationError;
import play.db.ebean.Model;
import scala.collection.JavaConverters;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Christina on 01.05.2015.
 */
@Entity
public class SportType extends Model {
    @Id
    public int sportID;
    public String description;


    public Map<String, List<ValidationError>> validate() {
        Map<String, List<ValidationError>> errors = new HashMap<String, List<ValidationError>>();
        List<ValidationError> list = new ArrayList<ValidationError>();
        if (find.where().eq("description", description).findRowCount() != 0) {
            list.add(new ValidationError("description", "Diese Sportart existiert bereits"));
            errors.put("description", list);
        }

        return errors.isEmpty() ? null : errors;
    }

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
