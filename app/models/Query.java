package models;

import play.db.ebean.Model;
import play.libs.Time;

/**
 * Created by Christina on 18.01.2015.
 */
public class Query extends Model{
    public Time start;
    public Time end;
    public Sport preferredSport;
}
