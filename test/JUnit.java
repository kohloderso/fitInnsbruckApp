import models.Athlete;
import models.Facility;
import models.SportType;
import org.fest.assertions.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import play.Logger;
import play.api.libs.ws.WS;
import play.libs.F;
import play.mvc.Result;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static play.mvc.Controller.session;
import static play.mvc.Http.Status.UNAUTHORIZED;


import play.test.FakeApplication;
import play.test.FakeRequest;
import play.test.Helpers;
import play.test.TestBrowser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

public class JUnit
{
    public static FakeApplication app;

    @BeforeClass
    public static void startApp() {
        app = Helpers.fakeApplication(Helpers.inMemoryDatabase());
        Helpers.start(app);
    }

    @AfterClass
    public static void stopApp() {
        Helpers.stop(app);
    }

    @Test
    public void Athlete_Test_getAge() throws Exception{
        Athlete athlete1= new Athlete();
        String dateInString = "06/06/1994";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = formatter.parse(dateInString);
            athlete1.birthday=date;
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        //athlete1.getAge()
        assertEquals(21, athlete1.getAge());
    }

    //checks if function findUser is right
    @Test
    public void Athlete_Test_findUser()throws Exception {
        Athlete athlete2= new Athlete();
        Athlete athlete1= new Athlete();
        athlete1.name="emma";
        athlete2.name="jose";
        athlete1.save();
        athlete2.save();
        String id_athlete= athlete2.findUser("jose").id;
        String id_athlete2 = athlete2.id;
        assertEquals(id_athlete, id_athlete2);
    }

    @Test
    public void Athlete_Test_authenticate()throws Exception {
        Athlete athlete3= new Athlete();
        athlete3.name="hello";
        athlete3.password="password";
        athlete3.save();
        String id_same = athlete3.authenticate("hello", "password").id;
        String id_same2 = athlete3.id;
        assertEquals(id_same,id_same2);
    }


/*
    @Test
    public void testAuthentication() {
        // first test what happens, when you call a restricted method and you are not authenticated
        Result result = callAction(
                controllers.routes.ref.Application.index(),
                new FakeRequest(GET, "/facility/1")
        );
        assertThat(status(result)).isEqualTo(UNAUTHORIZED);

        // then what happens, if somone is logged in
        session("username", "somebody");
        result = callAction(
                controllers.routes.ref.Application.index(),
                new FakeRequest(GET, "/facility/1")
        );
        assertThat(status(result)).isEqualTo(OK);
    }
    */
    /*

    Test Routes

     */
/*
    @Test
    public void rootRoute() {
        Result result = routeAndCall(fakeRequest(GET, "/"));
        assertThat(result).isNotNull();
    }

    @Test
    public void badRoute() {
        Result result = routeAndCall(fakeRequest(GET, "/bad"));
        assertThat(result).isNull();
    }
*/
    /*

    Test Controllers

     */
    /*
    @Test
    public void callIndex() {
        Result result = callAction(controllers.routes.ref.Application.index());
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/html");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(contentAsString(result)).contains("Fit in Innsbruck");
    }
*/
    /*
   @Test
    public void testIndexWithTestServerRunnable() {
       running(testServer(3333), new Runnable() {
           @Override
           public void run() {

               assertThat(WS.url("http://localhost:9000").get().get().getStatus()).isEqualTo(OK);
           }
       });
   }
   */
   public void runInBrowser() {
        running(testServer(9000), HtmlUnitDriver.class, new F.Callback() {
            @Override
            public void invoke(Object o) {
                TestBrowser browser = (TestBrowser) o;
                browser.goTo("http://localhost:9000");
                assertThat(browser.$("body").getTexts().get(0)).isEqualTo("Fit in Innsbruck");
            }
        });
    }

}
