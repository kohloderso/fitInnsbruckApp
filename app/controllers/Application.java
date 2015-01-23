package controllers;

import com.feth.play.module.pa.PlayAuthenticate;
import models.*;
import play.data.Form;
import play.db.ebean.Model;
import play.mvc.*;
import views.html.*;

import java.util.List;

import static play.libs.Json.toJson;

/**
 * Created by Christina on 18.01.2015.
 */
public class Application extends Controller{

    public static final String FLASH_MESSAGE_KEY = "message";
    public static final String FLASH_ERROR_KEY = "error";

    public static Result index() {
        return ok(index.render());
    }

    public static Result addUser() {
        User user = Form.form(User.class).bindFromRequest().get();
        user.save();
        return redirect(routes.Application.showQueryForm());
    }

    public static Result showQueryForm() {
        return ok(queryView.render());
    }

    public static Result askQuery() {
        Query query = Form.form(Query.class).bindFromRequest().get();
        return redirect(routes.Application.showQueryForm());
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
        return ok(mapTest.render());
    }

    public static Result oAuthDenied(final String providerKey) {
        com.feth.play.module.pa.controllers.Authenticate.noCache(response());
        flash(FLASH_ERROR_KEY,
                "You need to accept the OAuth connection in order to use this website!");
        return redirect(routes.Application.index());
    }

    public static User getLocalUser(final Http.Session session) {
        final User localUser = User.findByAuthUserIdentity(PlayAuthenticate
                .getUser(session));
        return localUser;
    }

}
