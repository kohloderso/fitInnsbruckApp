
import models.Athlete;
import models.SportType;
import play.Application;
import play.GlobalSettings;
import play.data.format.Formatters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Locale;


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


        if (SportType.find.findRowCount() == 0) {
            SportType klettern = new SportType();
            klettern.description = "Klettern";
            klettern.save();

            SportType schwimmen = new SportType();
            schwimmen.description = "Schwimmen";
            schwimmen.save();
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
            u.save();
        }

    }

}

