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
public class User extends Model {
    @Id
    public String id;

    public String name;
    public String password;
    @Formats.DateTime(pattern="dd/MM/yyyy")
    public Date birthday;
    public int height;
    public int weight;


    public static Finder<String,User> find = new Finder<String,User>(
            String.class, User.class
    );


    public static User authenticate(String name, String password) {
        return find.where().eq("name", name)
                .eq("password", password).findUnique();
    }
}
