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
 * Class for the type of a facility. E.g. "Kletterhalle", "Schwimmbad"
 */
@Entity
public class FacilityType extends Model {
    @Id
    public int typeID;
    public String description;


    public Map<String, List<ValidationError>> validate() {
        Map<String, List<ValidationError>> errors = new HashMap<String, List<ValidationError>>();
        List<ValidationError> list = new ArrayList<ValidationError>();
        if (find.where().eq("description", description).findRowCount() != 0) {
            list.add(new ValidationError("description", "Dieser Typ existiert bereits"));
            errors.put("description", list);
        }

        return errors.isEmpty() ? null : errors;
    }

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
