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
 * This class contains all functions, that only admins are allowed to call.
 * This includes changing anything about the facilities and viewing the registered users.
 */
@Restrict({@Group("admin")})
public class AdminController extends Controller {

    /**
     * List all registered users and all their properties.
     *
     */
    public static Result getUsers() {
        List<Athlete> athletes = new Model.Finder(String.class, Athlete.class).all();
        return ok(toJson(athletes));
    }

    /**
     * Creates a form for the facility and renders the appropriate view, to allow the user to fill the form.
     */
    public static Result makeFacilityForm() {
        return ok(addFacility.render(form(Facility.class)));
    }

    /**
     * If there was no error: reads data from the filled form, creates a new facility instance and saves it.
     * After successful saving the user is redirected to the list of all facilities.
     * If there was an error in the form, the form is rendered again, with the incorrect fields highlighted.
     */
    public static Result addFacility() {
        Form<Facility> facilityForm = form(Facility.class).bindFromRequest();

        if (facilityForm.hasErrors()) {
            Logger.info("error while binding facility form");
            facilityForm.reject("a problem occurred");
            return badRequest(addFacility.render(facilityForm));
        }
        Facility facility = facilityForm.get();
        Map<String, String[]> map = request().body().asFormUrlEncoded();
        String[] checkedSport = map.get("sportlist"); // get selected sports
        for(String sportID: checkedSport) {
            facility.possibleSport.add(SportType.find.byId(sportID));
        }
        facility.facilityType = FacilityType.find.byId(map.get("facilityTypeID")[0]);
        System.out.println(facility.toString());
        facility.save();
        Logger.info("saved");
        return redirect(routes.Application.getFacilities());
    }

    /**
     * Renders the same form as with makeFacilityForm but with the fields preset to the values of the facility you want to edit.
     * @param facilityID
     */
    public static Result editFacility(Long facilityID) {
        Facility f = Facility.find.byId(facilityID.toString());
        Form facilityForm = Form.form(Facility.class).fill(f);
        scala.collection.immutable.List<SportType> ls = JavaConverters.asScalaBufferConverter(f.possibleSport).asScala().toList();
        return ok(editFacility.render(facilityForm,ls) );
    }

    /**
     * Reads the data from the facilityForm and if there were no changes updates the values for the facility with the specified ID.
     * After the successful update the user is redirected to detailed view of the edited facility.
     * If there were errors, the form is rendered again with the incorrect fields highlighted.
     * @param facilityID
     */
    public static Result updateFacility(Long facilityID) {
        Form<Facility> facilityForm = form(Facility.class).bindFromRequest();

        if (facilityForm.hasErrors()) {
            Logger.info("error while binding facility form");
            facilityForm.reject("a problem occurred");
            Facility f = Facility.find.byId(facilityID.toString());
            scala.collection.immutable.List<SportType> ls = JavaConverters.asScalaBufferConverter(f.possibleSport).asScala().toList();
            return badRequest(editFacility.render(facilityForm, ls));
        }
        Facility updatedFacility = facilityForm.get();
        Map<String, String[]> map = request().body().asFormUrlEncoded();
        String[] checkedSport = map.get("sportlist"); // get selected sports
        for(String sportID: checkedSport) {
            updatedFacility.possibleSport.add(SportType.find.byId(sportID));
        }
        updatedFacility.facilityType = FacilityType.find.byId(map.get("facilityTypeID")[0]);
        updatedFacility.objectid = facilityID.intValue();
        System.out.println(updatedFacility.toString());
        updatedFacility.update();
        Logger.info("updated");
        return redirect(routes.Application.showFacility(updatedFacility.objectid));
    }

    /**
     * Deletes the facility with this ID.
     * After deletion the user is redirected to the list of all facilities.
     * @param facilityIDz
     */
    public static Result deleteFacility(Long facilityID) {
        Facility f = Facility.find.byId(facilityID.toString());
        f.delete();
        return redirect(routes.Application.getFacilities());
    }


}
