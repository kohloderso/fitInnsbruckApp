
package models;



import junit.framework.TestCase;
import org.junit.Test;

import org.junit.Assert;

//import play.test.WithApplication;

import java.util.ArrayList;

import static org.junit.Assert.assertNotEquals;

//import play.test.Helpers;

public class AthleteTest extends TestCase/*extends WithApplication*/ {


    //ADMIN abfrage


/*funktioniert leider noch nicht*/

    /*@Test
    public void testFindUser() throws Exception {
        Helpers.running(Helpers.fakeApplication(Helpers.inMemoryDatabase()), () -> {
            //toller Test
        });


    }
    */
    @Test
    public void testOpeningHours() throws Exception {
        OpeningHours oha = new OpeningHours();
        OpenPeriod op= new OpenPeriod();
        op.begin="20:10";
        op.end="20:10";
        op.opID=0;

        OpenHoursDay ohd= new OpenHoursDay();
        oha.monday= ohd;
        oha.monday.openPeriods= new ArrayList<>();
        oha.monday.openPeriods.add(op);


        /*Should not be the same*/
        assertNotEquals(op.begin, op.end);
        Assert.assertNotNull(oha.monday.openPeriods);

    }

    @Test
    public void testGetAge() throws Exception {
        fail("Not yet implemented");
    }

}