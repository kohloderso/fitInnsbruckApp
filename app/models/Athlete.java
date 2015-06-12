package models;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;


import be.objectify.deadbolt.core.models.Permission;
import be.objectify.deadbolt.core.models.Role;
import be.objectify.deadbolt.core.models.Subject;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;
import play.db.ebean.Model;


/**
 * This class reprents a user of our app
 */
@Entity
public class Athlete extends Model implements Subject {
    @Id
    public String id;
    @Constraints.Required(message = "Darf nicht leer sein")
    public String name;
    @Constraints.Required(message = "Darf nicht leer sein")
    @Constraints.MinLength(value = 5, message = "Passwort muss aus mindestens 5 Zeichen bestehen")
    public String password;

    @Formats.DateTime(pattern = "dd/MM/yyyy")
    @Past(message="muss in der Vergangenheit liegen ;-)")
    public Date birthday;
   //@NotNull
    public Integer height;
   //@NotNull
    public Integer weight;

    @OneToMany(cascade = CascadeType.ALL)   //This hopefully doesn't delete every facility and SportType connected with it --> TODO test it
    public List<Activity> pastActivities;

    @ManyToOne(cascade = CascadeType.PERSIST)
    public SecurityRole role;


    /**
     * Check if every new user has a unique name. If the name already exists, add an error to the error map of the
     * corresponding form.
     * @return either null, if there were no errors or a Map with the errors
     */
    public Map<String, List<ValidationError>> validate() {
        Map<String, List<ValidationError>> errors = new HashMap<String, List<ValidationError>>();
        List<ValidationError> list = new ArrayList<ValidationError>();
        if (find.where().eq("name", name).ne("id", id).findRowCount() != 0) {
            list.add(new ValidationError("name", "Dieser Name existiert bereits"));
            errors.put("name", list);
        }

        return errors.isEmpty() ? null : errors;
    }

    /**
     * calculate the age of this athlete
     * @return
     */
    public int getAge() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(birthday);
        int birthyear = cal.get(Calendar.YEAR);
        cal = Calendar.getInstance();
        int age = cal.get(Calendar.YEAR) - birthyear;
        System.out.println("age: " + age);
        return age;
    }


    @Override
    public List<? extends Role> getRoles() {
        List<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(role);
        return roles;
    }

    /**
     * we have to override this method for deadbolt, but don't use it
     * @return
     */
    @Override
    public List<? extends Permission> getPermissions() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return name;
    }

    public static Finder<String, Athlete> find = new Finder<String, Athlete>(
            String.class, Athlete.class
    );

    /**
     * find an athlete by name
     * @param name
     * @return athlete with the specified name
     */
    public static Athlete findUser(String name) {
        return find.where().eq("name", name).findUnique();
    }

    /**
     * check if the combination of name and password is valid
     * @param name
     * @param password
     * @return null if the combination was wrong, the athlete if it was right
     */
    public static Athlete authenticate(String name, String password) {
        return find.where().eq("name", name)
                .eq("password", password).findUnique();
    }


}
