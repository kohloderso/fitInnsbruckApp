package controllers;

import models.*;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.db.ebean.Model;
import play.mvc.*;
import views.html.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

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
        return ok(register.render(form(User.class)));
    }

    public static Result addUser() {
        Form<User> userForm = form(User.class).bindFromRequest();
        if (userForm.hasErrors()) {
            Logger.info("error while registrating");
            userForm.reject("a problem occurred with your registration");
            return badRequest(register.render(userForm));
        }
        User user = userForm.get();
        user.save();
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
        User currentUser = User.findUser(session().get("username"));
        Logger.info(currentUser.name);
        int calories = sport.computeCalories(currentUser.weight, currentUser.height, currentUser.getAge(), duration);
        Logger.info("calories " + calories);
        //find facilities
        List<Facility> facilities = Facility.findFacilitesForSport(sport);
        System.out.println(facilities);
        scala.collection.immutable.List<Facility> ls = JavaConverters.asScalaBufferConverter(facilities).asScala().toList();


        return ok(result.render(ls, calories));
    }


    public static Result getUsers() {
        List<User> users = new Model.Finder(String.class, User.class).all();
        return ok(toJson(users));
    }

    public static Result getFacilities() {
        List<Facility> facilities = new Model.Finder(String.class, Facility.class).all();
        return ok(toJson(facilities));
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
                    routes.Application.index()
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


}
