package models;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Id;

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
    public Date birthday;
    public int height;
    public int weight;



}
