package security;

import be.objectify.deadbolt.core.models.Subject;
import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import models.Athlete;
import play.Logger;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;
import views.html.restrictedView;

import static play.data.Form.form;
import static play.mvc.Results.forbidden;
import static play.mvc.Results.ok;

/**
 * Created by Christina on 30.05.2015.
 */
public class MyDeadboltHandler implements DeadboltHandler {


    @Override
    public F.Promise<Result> beforeAuthCheck(Http.Context context) {
        Logger.info("called beforeAuthCheck");
        return F.Promise.pure(null);
    }

    @Override
    public Subject getSubject(Http.Context context) {
        String name = context.session().get("username");
        return  Athlete.findUser(name);
    }

    @Override
    public F.Promise<Result> onAuthFailure(Http.Context context, String content) {
        // you can return any result from here - forbidden, etc
        return F.Promise.promise(() -> forbidden(restrictedView.render()));
    }

    @Override
    public DynamicResourceHandler getDynamicResourceHandler(Http.Context context) {
        return null;
    }
}
