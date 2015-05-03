package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Kathi on 19.04.2015.
 */
@Entity
public class Pricing extends Model {

    @Id
    public int priceID;
    public double adult;
    public double child;
    public double youth;
    public double student;


    /*
    here we could make a function to calculate for example how much you have to pay for 3 adults and 2 children
    public int howMuch(){
    }
    */

    public String toString() {
        String s = "ID: " + priceID + "\n" + "adults: " + adult + "\n" + "children: " + child + "\n" + "youth: " + youth + "\n" + "students: " + student;
        return s;
    }
}
