package models;

import play.db.ebean.Model;

import javax.persistence.Id;

/**
 * Created by Christina on 18.01.2015.
 */
public class Facility extends Model{
    @Id
    public int objectID;
    public String name;
    public String address;
    public int lat;
    public int lon;
}
