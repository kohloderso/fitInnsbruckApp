package models;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.format.Formats;
import play.db.ebean.Model;


/**
 * Created by Christina on 18.01.2015.
 */
@Entity
public class Athlete extends Model {
    @Id
    public String id;
    public String name;
    public String password;
    @Formats.DateTime(pattern = "dd/MM/yyyy")
    public Date birthday;
    public int height;
    public int weight;


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

}
