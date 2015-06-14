import org.junit.Test;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import play.api.libs.ws.WS;
import play.libs.F;
import play.mvc.Result;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Controller.session;
import static play.mvc.Http.Status.UNAUTHORIZED;


import play.test.FakeRequest;
import play.test.TestBrowser;

import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

public class JUnit
{

    /*@Test
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
    }*/
    /*

    Test Routes

     */

    /*@Test
    public void rootRoute() {
        Result result = routeAndCall(fakeRequest(GET, "/"));
        assertThat(result).isNotNull();
    }

    @Test
    public void badRoute() {
        Result result = routeAndCall(fakeRequest(GET, "/bad"));
        assertThat(result).isNull();
    }*/

    /*

    Test Controllers

     */

    /*@Test
    public void callIndex() {
        Result result = callAction(controllers.routes.ref.Application.index());
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/html");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(contentAsString(result)).contains("Fit in Innsbruck");
    }*7


   /*@Test
    public void testIndexWithTestServerRunnable() {
       running(testServer(3333), new Runnable() {
           @Override
           public void run() {

               assertThat(WS.url("http://localhost:9000").get().get().getStatus()).isEqualTo(OK);
           }
       });
   }*/
   /* public void runInBrowser() {
        running(testServer(9000), HtmlUnitDriver.class, new F.Callback() {
            @Override
            public void invoke(Object o) {
                TestBrowser browser = (TestBrowser) o;
                browser.goTo("http://localhost:9000");
                assertThat(browser.$("body").getTexts().get(0)).isEqualTo("Fit in Innsbruck");
            }
        });
    }*/

}
