package models;

import com.avaje.ebean.Ebean;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import play.Logger;
import play.test.FakeApplication;
import play.test.Helpers;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class BaseModelTest {
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
  public void testIsOpen() throws Exception{

    //Test IsOpen function -> OpenPeriod
    Logger.info("before OpenPeriod");
    OpenPeriod test1_1 = new OpenPeriod();
    test1_1.begin= "09:00";
    test1_1.end= "12:00";
    LocalTime begin= LocalTime.parse("12:00");
    LocalTime end = LocalTime.parse("14:00");
    Logger.info("before assert");
    assertFalse("test", test1_1.isOpen(begin, end));

    LocalTime begin1 =LocalTime.parse("10:00");
    LocalTime end1 = LocalTime.parse("12:00");
    assertTrue("test1", test1_1.isOpen(begin1, end1));

//    //Test isOpen function -> OpenHoursDay
//    OpenHoursDay test2_1= new OpenHoursDay();
//    test2_1.openPeriods = new ArrayList<>();
//    OpenPeriod op= new OpenPeriod();
//    op.begin="08:00";
//    op.end="10:00";
//    OpenPeriod asd= new OpenPeriod();
//    asd.begin= "12:00";
//    asd.end= "15:00";
//    test2_1.openPeriods.add(op);
//    test2_1.openPeriods.add(asd);
//    assertFalse("test2", test2_1.isOpen(begin, end));
//
//    LocalTime begin2 =LocalTime.parse("08:00");
//    LocalTime end2 = LocalTime.parse("09:00");
//    assertTrue("test3", test2_1.isOpen(begin2, end2));
//
//
//    //Test isOpen function ->OpeningHours
//    OpeningHours test3_1 = new OpeningHours();
//    test3_1.monday= test2_1;
//    OpenPeriod h= new OpenPeriod();
//    h.begin="07:15";
//    h.end="15:15";
//    assertTrue("test4",test3_1.isOpen(begin,end, DayOfWeek.MONDAY));

  }


}
