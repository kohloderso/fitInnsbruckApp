package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Christina on 18.01.2015.
 */
@Entity
public class User extends Model {
    @Id
    public String id;
    public String username;
    public String password;
}
