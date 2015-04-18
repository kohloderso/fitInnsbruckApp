package models;

import play.data.format.Formats;

import java.time.LocalDateTime;
import java.util.*;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Tammy on 18.04.2015.
 */
@Entity
public class Activity {

    @Id
    @Formats.DateTime(pattern = "dd/MM/yyyy hh:mm")
    public LocalDateTime from;
    @Id
    @Formats.DateTime(pattern = "dd/MM/yyyy hh:mm")
    public LocalDateTime to;


    public Weather weather;

    public Sport sport;

    public Facility place;

    public int calories;





}
