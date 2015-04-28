package controllers;

import models.*;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.db.ebean.Model;
import play.mvc.*;
import views.html.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import scala.collection.JavaConverters;


import static play.data.Form.form;
import static play.libs.Json.toJson;

/**
 * Created by Christina on 18.01.2015.
 */
public class Application extends Controller {

    public static Result index() {
        return ok(index.render());
    }

    public static Result register() {
        return ok(register.render(form(Athlete.class)));
    }

    public static Result addUser() {
        Form<Athlete> userForm = form(Athlete.class).bindFromRequest();
        if (userForm.hasErrors()) {
            Logger.info("error while registrating");
            userForm.reject("a problem occurred with your registration");
            return badRequest(register.render(userForm));
        }
        Athlete athlete = userForm.get();
        athlete.save();
        return redirect(routes.Application.index());
    }

    @Security.Authenticated(Secured.class)
    public static Result showQueryForm() {
        return ok(queryView.render());
    }

    public static Result askQuery() {
        DynamicForm requestData = form().bindFromRequest();
        if (requestData.hasErrors()) {
            Logger.info("error with query");
            return badRequest(queryView.render());
        }
        Sport sport = Sport.valueOf(requestData.get("preferredSport"));
        LocalTime start = LocalTime.parse(requestData.get("start"));
        LocalTime end = LocalTime.parse(requestData.get("end"));

        //compute Calories
        int duration = end.getHour() * 60 + end.getMinute() - (start.getHour() * 60 + start.getMinute());
        Logger.info("duration " + duration);
        Athlete currentAthlete = Athlete.findUser(session().get("username"));
        Logger.info(currentAthlete.name);
        int calories = sport.computeCalories(currentAthlete.weight, currentAthlete.height, currentAthlete.getAge(), duration);
        Logger.info("calories " + calories);
        //find facilities
        List<Facility> facilities = Facility.findFacilitesForSport(sport);
        System.out.println(facilities);
        scala.collection.immutable.List<Facility> ls = JavaConverters.asScalaBufferConverter(facilities).asScala().toList();


        return ok(result.render(ls, calories));
    }


    public static Result getUsers() {
        List<Athlete> athletes = new Model.Finder(String.class, Athlete.class).all();
        return ok(toJson(athletes));
    }

    public static Result getFacilities() {

        List<Facility> facilities = new Model.Finder(String.class, Facility.class).all();
        scala.collection.immutable.List<Facility> ls = JavaConverters.asScalaBufferConverter(facilities).asScala().toList();
        return ok(allFacilities.render(ls));
    }

    public static Result getMaptest() {
        return ok(map.render("47.269212", "11.404102"));
    }

    public static Result login() {
        return ok(login2.render(form(Login.class)));
    }

    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            Logger.info("error logging on");
            return badRequest(login2.render(loginForm));
        } else {
            Logger.info("Login successful");
            session().clear();
            session("username", loginForm.get().username);
            return redirect(
                    routes.Application.showQueryForm()
            );
        }
    }

    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
                routes.Application.login()
        );
    }

    @Security.Authenticated(Secured.class)
    public static Result showFacility(Long facilityID) {
        Facility f = Facility.find.where().eq("objectid", facilityID).findUnique();
        return ok(facility.render(f));
    }

    public static Result addNewFacility() {
        return ok(editFacility.render(form(Facility.class)));
    }

    public static Result addFacility() {
        Form<Facility> facilityForm = form(Facility.class).bindFromRequest();
        if (facilityForm.hasErrors()) {
            Logger.info("error while registrating");
            facilityForm.reject("a problem occurred with your registration");
            return badRequest(editFacility.render(facilityForm));
        }
        Facility facility = facilityForm.get();

        // get request value from submitted form
        Map<String, String[]> map = request().body().asFormUrlEncoded();

        // monday
        String[] openMonday = map.get("openingHours.monday[]");
        facility.openingHours.monday = new ArrayList<OpenPeriod>();
        for(int i = 0; i < openMonday.length; i=i+2) {
            if(openMonday[i] != null && openMonday[i+1] != null) {
                OpenPeriod op = new OpenPeriod(LocalTime.parse(openMonday[i]), LocalTime.parse(openMonday[i + 1]));
                facility.openingHours.monday.add(op);
            }
        }
        System.out.println(facility.toString());
        //facility.save();
        return redirect(routes.Application.index());
    }



}
