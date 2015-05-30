package controllers;

import be.objectify.deadbolt.java.actions.SubjectPresent;
import models.*;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.data.validation.ValidationError;
import play.db.ebean.Model;
import play.mvc.*;
import play.twirl.api.Html;
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
            userForm.reject("Es gab Probleme beim Registrieren");
            return badRequest(register.render(userForm));
        }
        Athlete athlete = userForm.get();
        athlete.role = SecurityRole.findByName("normalUser");
        athlete.save();
        return redirect(routes.Application.index());
    }

    @SubjectPresent
    public static Result showQueryForm() {
        return ok(queryView.render());
    }

    @SubjectPresent
    public static Result askQuery() {
        DynamicForm requestData = form().bindFromRequest();
        if (requestData.hasErrors()) {
            Logger.info("error with query");
            return badRequest(queryView.render());
        }
        LocalTime begin = LocalTime.parse(requestData.get("begin"));
        LocalTime end = LocalTime.parse(requestData.get("end"));

        List<SportType> sports = new ArrayList<SportType>();
        Map<String, String[]> map = request().body().asFormUrlEncoded();
        String[] checkedSport = map.get("sportlist"); // get selected sports
        for(String sportID: checkedSport) {
            sports.add(SportType.find.byId(sportID));
        }

        Boolean roofed;
        if(requestData.get("inout").equals("INDOOR")) {
            roofed = true;
        } else if(requestData.get("inout").equals("INDOOR")) {
            roofed = false;
        } else {
            roofed = null;
        }

        List<Facility> facilities = Facility.findFacilities(roofed, sports, begin, end);
        scala.collection.immutable.List<Facility> ls = JavaConverters.asScalaBufferConverter(facilities).asScala().toList();
        return ok(allFacilities.render(ls));
    }

    @SubjectPresent
    public static Result getFacilities() {
        List<Facility> facilities = new Model.Finder(String.class, Facility.class).all();
        scala.collection.immutable.List<Facility> ls = JavaConverters.asScalaBufferConverter(facilities).asScala().toList();
        return ok(allFacilities.render(ls));
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

    @SubjectPresent
    public static Result showFacility(Long facilityID) {
        Facility f = Facility.find.where().eq("objectid", facilityID).findUnique();
        return ok(facility.render(f));
    }
}
