
import be.objectify.deadbolt.core.models.Role;
import models.*;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.data.Form;
import play.data.format.Formatters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;

import static play.data.Form.form;


/**
 * Created by Christina on 19.01.2015.
 */

public class Global extends GlobalSettings {
    @Override
    public void onStart(Application app) {

        Formatters.register(LocalTime.class, new
                Formatters.SimpleFormatter<LocalTime>() {

                    //private Pattern timePattern = Pattern.compile("([012]?\\d)[\\s:._-]+([012345]?\\d)");

                    @Override
                    public LocalTime parse(String input, Locale l) throws ParseException
                    {
                        return LocalTime.parse(input);
                    }

                    @Override
                    public String print(LocalTime localTime, Locale l) {
                        return localTime.toString();
                    }
                });


        if (FacilityType.find.findRowCount() == 0) {
            FacilityType schwimmbad = new FacilityType();
            schwimmbad.description = "Schwimmbad";
            schwimmbad.save();

            FacilityType kletterhalle = new FacilityType();
            kletterhalle.description = "Kletterhalle";
            kletterhalle.save();

            FacilityType sportplatz = new FacilityType();
            sportplatz.description = "Sportplatz";
            sportplatz.save();
        }
        if (SportType.find.findRowCount() == 0) {
            SportType klettern = new SportType();
            klettern.description = "Klettern";
            klettern.save();

            SportType schwimmen = new SportType();
            schwimmen.description = "Schwimmen";
            schwimmen.save();
        }
        if (Facility.find.findRowCount() == 0) {
            Facility hoettingerAu = new Facility();
            hoettingerAu.name = "Sport-Oase Hoettinger Au";
            hoettingerAu.address = "Fuerstenweg 12";
            hoettingerAu.facilityType = FacilityType.find.where().eq("description", "Schwimmbad").findUnique();
            hoettingerAu.roof = true;
            hoettingerAu.possibleSport = new ArrayList<>();
            hoettingerAu.possibleSport.add(SportType.find.where().eq("description", "Schwimmen").findUnique());

            OpeningHours oh = new OpeningHours();
            OpenHoursDay daymomidosa = new OpenHoursDay();
            OpenPeriod momidosa = new OpenPeriod();
            momidosa.begin = "09:00";
            momidosa.end = "22:00";
            daymomidosa.openPeriods = new ArrayList<OpenPeriod>();
            daymomidosa.openPeriods.add(momidosa);
            oh.monday = daymomidosa;
            oh.wednesday = daymomidosa;
            oh.thursday = daymomidosa;
            oh.saturday = daymomidosa;

            OpenHoursDay daydi = new OpenHoursDay();
            daydi.openPeriods = new ArrayList<OpenPeriod>();
            OpenPeriod di = new OpenPeriod();
            di.begin ="";
            di.end = "";
            daydi.openPeriods.add(di);
            oh.tuesday = daydi;

            OpenHoursDay dayfr = new OpenHoursDay();
            dayfr.openPeriods = new ArrayList<OpenPeriod>();
            OpenPeriod fr = new OpenPeriod();
            fr.begin = "08:00";
            fr.end = "22:00";
            dayfr.openPeriods.add(fr);
            oh.friday = dayfr;

            OpenHoursDay dayso = new OpenHoursDay();
            dayso.openPeriods = new ArrayList<>();
            OpenPeriod so = new OpenPeriod();
            so.begin = "10:00";
            so.end = "22:00";
            dayso.openPeriods.add(so);
            oh.sunday = dayso;

            hoettingerAu.openingHours = oh;

            hoettingerAu.lat = "47.2651444";
            hoettingerAu.lon = "11.3806308";

            Pricing prices = new Pricing();
            prices.child = 2.60;
            prices.adult = 6.50;
            prices.student = 6.50;
            hoettingerAu.prices = prices;

            hoettingerAu.save();

            Facility test = Facility.find.where().eq("name", "Sport-Oase Hoettinger Au").findUnique();
            Logger.info("aus Datenbank: " + test.name);
        }
        if (SecurityRole.find.findRowCount() == 0) {
            SecurityRole admin = new SecurityRole();
            admin.name = "admin";
            admin.save();

            SecurityRole normal = new SecurityRole();
            normal.name = "normalUser";
            normal.save();
        }
        if (Athlete.find.findRowCount() == 0) {
            Athlete u = new Athlete();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                u.birthday = dateFormat.parse("11/10/1994");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            u.name = "christina";
            u.height = 170;
            u.weight = 60;
            u.password = "secret";
            u.role = SecurityRole.findByName("admin");
            u.save();
        }



    }

}

