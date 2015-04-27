import models.Activity;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import org.junit.Test;
import play.mvc.Result;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.GET;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.routeAndCall;



import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import play.libs.F;
import static play.libs.F.Promise;
import play.api.libs.ws.WS;
import play.test.TestBrowser;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.callAction;
import static play.test.Helpers.charset;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;
import static play.test.Helpers.status;
import static play.test.Helpers.testServer;

public class JUnit
{

    /*

    Test Routes

     */

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

    /*

    Test Controllers

     */

    @Test
    public void callIndex() {
        Result result = callAction(controllers.routes.ref.Application.index());
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/html");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(contentAsString(result)).contains("Fit in Innsbruck");
    }

/*
   @Test
    public void testIndexWithTestServerRunnable() {
        running(testServer(3333), new Runnable() {
            @Override
            public void run() {
                assertThat(

                        WS.url("http://localhost:9000").get().get().getStatus();
                ).isEqualTo(OK);
            }
        });
    }
*/
  /*  public void runInBrowser() {
        running(testServer(9000), HtmlUnitDriver.class, new F.Callback() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:9000");
                assertThat(browser.$("body").getTexts().get(0)).isEqualTo("Fit in Innsbruck");
            }
        });
    } */

}
