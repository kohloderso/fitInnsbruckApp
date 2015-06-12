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
 * Created by Christina on 18.01.2015.
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


    public Map<String, List<ValidationError>> validate() {
        Map<String, List<ValidationError>> errors = new HashMap<String, List<ValidationError>>();
        List<ValidationError> list = new ArrayList<ValidationError>();
        if (find.where().eq("name", name).ne("id", id).findRowCount() != 0) {
            list.add(new ValidationError("name", "Dieser Name existiert bereits"));
            errors.put("name", list);
        }

        return errors.isEmpty() ? null : errors;
    }

    public int getAge() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(birthday);
        int birthyear = cal.get(Calendar.YEAR);
        cal = Calendar.getInstance();
        int age = cal.get(Calendar.YEAR) - birthyear;
        System.out.println("age: " + age);
        return age;
    }

    public static Finder<String, Athlete> find = new Finder<String, Athlete>(
            String.class, Athlete.class
    );



    public static Athlete findUser(String name) {
        return find.where().eq("name", name).findUnique();
    }

    public static Athlete authenticate(String name, String password) {
        return find.where().eq("name", name)
                .eq("password", password).findUnique();
    }

    @Override
    public List<? extends Role> getRoles() {
        List<SecurityRole> roles = new ArrayList<SecurityRole>();
        roles.add(role);
        return roles;
    }

    @Override
    public List<? extends Permission> getPermissions() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return name;
    }
}
