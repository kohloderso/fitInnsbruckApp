package controllers;

import models.Query;
import play.data.Form;
import play.mvc.Result;

import static play.mvc.Results.ok;

/**
 * Created by Christina on 23.01.2015.
 */
public class QueryController {

    public static Result getCalorieSport() {
        Query query = Form.form(Query.class).bindFromRequest().get();
        // TODO query.preferredSport.computeCalories(age, time)
        // TODO Wetter abfragen if rainy suche sport.roofedPlace
        //
        return ok();
    }



}
