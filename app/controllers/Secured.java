package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Http.*;

import models.*;
import views.html.login2;

import static play.data.Form.form;

public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("username");
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        return unauthorized(login2.render(form(Login.class)));
    }
}