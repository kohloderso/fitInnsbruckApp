package controllers;

import be.objectify.deadbolt.java.actions.SubjectNotPresent;
import be.objectify.deadbolt.java.actions.SubjectPresent;
import models.*;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.db.ebean.Model;
import play.mvc.*;
import security.Login;
import views.html.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import scala.collection.JavaConverters;


import static play.data.Form.form;

/**
 * Provides all the functions a normal user is allowed to call. Some of the are restricted to registered users only.
 */
public class Application extends Controller {

    /**
     * Renders the home view of the app.
     */
    public static Result index() {
        return ok(index.render());
    }

    /**
     * Renders a register form.
     */
    @SubjectNotPresent
    public static Result register() {
        return ok(register.render(form(Athlete.class)));
    }

    /**
     * Reads data from the register form. If there were no errors adds a new user to the database.
     * After successfully saving the user to the database renders the index page.
     * If there were errors renders the form again, with the incorrect fields highlighted.
     */
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
        //TODO automatically login this user
        return redirect(routes.Application.index());
    }

    /**
     * Renders the same form as with register but with the fields preset to the values of the currently logged in athlete.
     */
    @SubjectPresent
    public static Result editUserForm() {
        String name = session().get("username");
        Athlete a = Athlete.findUser(name);
        Form athleteForm = Form.form(Athlete.class).fill(a);
        return ok(editProfile.render(athleteForm));
    }

    /**
     * Reads data from the editform. If there were no errors cahnges are saved to the database.
     * After successfully saving the user to the database renders the index page.
     * If there were errors renders the form again, with the incorrect fields highlighted.
     */
    @SubjectPresent
    public static Result updateUser() {
        Form<Athlete> userForm = form(Athlete.class).bindFromRequest();
        if (userForm.hasErrors()) {
            Logger.info("error while registrating");
            userForm.reject("Es gab Probleme beim bearbeiten");
            return badRequest(editProfile.render(userForm));
        }
        Athlete athlete = userForm.get();
        athlete.role = Athlete.findUser(session().get("username")).role;    // role will always stay the same, the user can't change it
        Logger.info("Athlete: " + athlete.toString());
        athlete.update();
        return redirect(routes.Application.index());
    }

    /**
     * Renders the form that lets you search for sports facilities.
     */
    @SubjectPresent
    public static Result showQueryForm() {
        return ok(queryView.render());
    }

    /**
     * Reads the data from the Query form. If there were no errors tries to find a list of appropriate facilities and displays them as a list.
     * If there were errors in the form it is rendered again with the incorrect fields highlighted.
     */
    @SubjectPresent
    public static Result askQuery() {
        DynamicForm requestData = form().bindFromRequest();
        if (requestData.hasErrors()) {
            Logger.info("error with query");
            return badRequest(queryView.render());
        }
        LocalTime begin = LocalTime.parse(requestData.get("begin"));
        LocalTime end = LocalTime.parse(requestData.get("end"));
        LocalDate date = LocalDate.parse(requestData.get("day"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Logger.info("Datum: " + date.toString());
        date.getDayOfWeek();

        List<SportType> sports = new ArrayList<SportType>();
        Map<String, String[]> map = request().body().asFormUrlEncoded();
        String[] checkedSport = map.get("sportlist"); // get selected sports

        if(checkedSport != null) {
            for(String sportID: checkedSport) {
                sports.add(SportType.find.byId(sportID));
            }
        }

        Boolean roofed;
        if(requestData.get("inout").equals("INDOOR")) {
            roofed = true;
        } else if(requestData.get("inout").equals("OUTDOOR")) {
            roofed = false;
        } else {
            roofed = null;
        }

        List<Facility> facilities;

        if(requestData.get("time") != null) {    //time is not important, can be anything
            facilities = Facility.findFacilities(roofed, sports);
        } else {
            facilities = Facility.findFacilities(roofed, sports, begin, end, date.getDayOfWeek());
        }

        scala.collection.immutable.List<Facility> ls = JavaConverters.asScalaBufferConverter(facilities).asScala().toList();
        return ok(allFacilities.render(ls));
    }

    /**
     * Renders all facilities that exist in the databse as a List.
     */
    @SubjectPresent
    public static Result getFacilities() {
        List<Facility> facilities = new Model.Finder(String.class, Facility.class).all();
        scala.collection.immutable.List<Facility> ls = JavaConverters.asScalaBufferConverter(facilities).asScala().toList();
        return ok(allFacilities.render(ls));
    }

    /**
     * Renders a login form.
     */
    @SubjectNotPresent
    public static Result login() {
        return ok(loginForm.render(form(Login.class)));
    }

    /**
     * Checks if the user provided valid credentials in the form using the validate method in Login.java.
     * If so he is redirected to the index page. If he couldn't be authenticated the form is rendered again, flashing an error message.
     */
    public static Result authenticate() {
        Form<Login> logform = form(Login.class).bindFromRequest();
        if (logform.hasErrors()) {
            Logger.info("error logging on");
            return badRequest(loginForm.render(logform));
        } else {
            Logger.info("Login successful");
            session().clear();
            session("username", logform.get().username);
            return redirect(
                    routes.Application.index()
            );
        }
    }

    /**
     * clears the current user from the session.
     */
    @SubjectPresent
    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
                routes.Application.index()
        );
    }

    /**
     * Shows a detailed view of the facility with the specified ID.
     * @param facilityID
     */
    @SubjectPresent
    public static Result showFacility(Long facilityID) {
        Facility f = Facility.find.where().eq("objectid", facilityID).findUnique();
        return ok(facility.render(f));
    }

    /**
     * shows all activities corresponding to the currently logged in user
     * @return
     */
    @SubjectPresent
    public static Result showActivities() {
        String name = session().get("username");
        Athlete athlete = Athlete.findUser(name);
        scala.collection.immutable.List<Activity> ls = JavaConverters.asScalaBufferConverter(athlete.pastActivities).asScala().toList();
        return ok(allActivities.render(ls));
    }

    /**
     * creates a form for a new activity that takes plaxe at the specified facility
     * @param fID   id of the facility for this activity
     * @return
     */
    @SubjectPresent
    public static Result newActivity(Long fID) {
        Facility f = Facility.find.byId(fID.toString());
        return ok(activityForm.render(form(Activity.class), f));
    }

    /**
     * Reads the filled form for the new activity. If there were no errors, adds the activity to the list of activities for the
     * currently logged in user. If there were errors renders the form again, flashing an error message.
     * @param fID   id of the facility for the new activity
     * @return
     */
    @SubjectPresent
    public static Result addActivity(Long fID) {
        Facility f = Facility.find.byId(fID.toString());
        Form<Activity> activityform = form(Activity.class).bindFromRequest();

        if (activityform.hasErrors()) {
            Logger.info("error with activity form");
            activityform.reject("a problem occurred");
            return badRequest(activityForm.render(activityform, f));
        }
        Activity activity = activityform.get();

        Map<String, String[]> map = request().body().asFormUrlEncoded();
        activity.sport = SportType.find.byId(map.get("sportType")[0]);
        activity.place = Facility.find.byId(map.get("facilityID")[0]);

        Logger.info(activity.toString());
        //activity.save();

        String name = session().get("username");
        Athlete athlete = Athlete.findUser(name);
        athlete.pastActivities.add(activity);
        athlete.save();

        scala.collection.immutable.List<Activity> ls = JavaConverters.asScalaBufferConverter(athlete.pastActivities).asScala().toList();
        return ok(allActivities.render(ls));
    }



}
