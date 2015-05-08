package controllers;

import models.*;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.db.ebean.Model;
import play.mvc.*;
import play.twirl.api.Html;
import views.html.*;

import java.time.LocalTime;
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
        // TODO

        return ok(main.render("not yet implemented", Html.apply("this content will be available soon")));
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
        List<SportType> sports = SportType.find.all();
        scala.collection.immutable.List<SportType> ls = JavaConverters.asScalaBufferConverter(sports).asScala().toList();
        return ok(addFacility.render(form(Facility.class), ls));
    }

    public static Result addFacility() {
        Form<Facility> facilityForm = form(Facility.class).bindFromRequest();

        if (facilityForm.hasErrors()) {
            Logger.info("error while binding facility form");
            facilityForm.reject("a problem occurred");
            List<SportType> sports = SportType.find.all();
            scala.collection.immutable.List<SportType> ls = JavaConverters.asScalaBufferConverter(sports).asScala().toList();
            return badRequest(addFacility.render(facilityForm, ls));
        }
        Facility facility = facilityForm.get();
        Map<String, String[]> map = request().body().asFormUrlEncoded();
        String[] checkedSport = map.get("sportlist"); // get selected sports
        for(String sportID: checkedSport) {
            facility.possibleSport.add(SportType.find.byId(sportID));
        }
        System.out.println(facility.toString());
        facility.save();
        Logger.info("saved");
        return redirect(routes.Application.index());
    }

    public static Result editFacility(Long facilityID) {
        List<SportType> sports = SportType.find.all();
        scala.collection.immutable.List<SportType> ls = JavaConverters.asScalaBufferConverter(sports).asScala().toList();
        Facility f = Facility.find.byId(facilityID.toString());
        Form facilityForm = Form.form(Facility.class).fill(f);
        return ok(editFacility.render(facilityForm, ls));
    }

    public static Result updateFacility(Long facilityID) {
        Form<Facility> facilityForm = form(Facility.class).bindFromRequest();

        if (facilityForm.hasErrors()) {
            Logger.info("error while binding facility form");
            facilityForm.reject("a problem occurred");
            List<SportType> sports = SportType.find.all();
            scala.collection.immutable.List<SportType> ls = JavaConverters.asScalaBufferConverter(sports).asScala().toList();
            return badRequest(editFacility.render(facilityForm, ls));
        }
        Facility updatedFacility = facilityForm.get();
        Map<String, String[]> map = request().body().asFormUrlEncoded();
        String[] checkedSport = map.get("sportlist"); // get selected sports
        for(String sportID: checkedSport) {
            updatedFacility.possibleSport.add(SportType.find.byId(sportID));
        }

        Facility f = Facility.find.byId(facilityID.toString());
        updatedFacility.objectid = facilityID.intValue();
        System.out.println(updatedFacility.toString());
        updatedFacility.update();
        Logger.info("updated");
        return redirect(routes.Application.showFacility(updatedFacility.objectid));
    }

}
