package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by Christina on 18.01.2015.
 */
@Entity
public class User extends Model {
    @Id
    public String id;
    public String username;
    public String password;
    public Date birthday;
    public int height;
    public int weight;
}
