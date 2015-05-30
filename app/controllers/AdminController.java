package controllers;

import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import be.objectify.deadbolt.java.actions.Unrestricted;
import models.Athlete;
import models.Facility;
import models.FacilityType;
import models.SportType;
import play.Logger;
import play.data.Form;
import play.db.ebean.Model;
import play.mvc.Controller;
import play.mvc.Result;
import scala.collection.JavaConverters;
import views.html.addFacility;
import views.html.editFacility;

import java.util.List;
import java.util.Map;

import static play.data.Form.form;
import static play.libs.Json.toJson;

/**
 * Created by Christina on 30.05.2015.
 */
@Restrict({@Group("admin")})
public class AdminController extends Controller {

    @Unrestricted
    public static Result getUsers() {
        List<Athlete> athletes = new Model.Finder(String.class, Athlete.class).all();
        return ok(toJson(athletes));
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
        facility.facilityType = FacilityType.find.byId(new Integer(facility.facilityType.typeID).toString());
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

    public static Result deleteFacility(Long facilityID) {
        Facility f = Facility.find.byId(facilityID.toString());
        f.delete();
        return Application.getFacilities();
    }


}
