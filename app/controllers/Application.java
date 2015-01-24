package controllers;

import models.*;
import play.db.ebean.Model;
import play.mvc.*;
import views.html.*;

import java.util.List;

import static play.data.Form.form;
import static play.libs.Json.toJson;

/**
 * Created by Christina on 18.01.2015.
 */
public class Application extends Controller{

    public static Result index() {
        return ok(register.render());
    }

    public static Result addUser() {
        User user = form(User.class).bindFromRequest().get();
        user.save();
        return redirect(routes.Application.showQueryForm());
    }

    public static Result showQueryForm() {
        return ok(queryView.render());
    }

    public static Result askQuery() {
        Query query = form(Query.class).bindFromRequest().get();
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

    public static Result login() {
        return ok(login2.render(form(Login.class)));
    }


}
