package controllers;

import models.User;
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

    public static Result index() {
        return ok(register.render());
    }

    public static Result addUser() {
        User user = Form.form(User.class).bindFromRequest().get();
        user.save();
        return redirect(routes.Application.index());
    }

    public static Result getUsers() {
        List<User> users = new Model.Finder(String.class, User.class).all();
        return ok(toJson(users));
    }
}
