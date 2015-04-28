
import au.com.bytecode.opencsv.*;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import models.Facility;
import models.Athlete;
import play.Application;
import play.GlobalSettings;
import play.data.format.Formatters;
import play.db.ebean.Model;

import java.io.FileReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

        if (new Model.Finder(String.class, Facility.class).findRowCount() == 0) {
            try {
                String csvFilename = app.path() + "/conf/resources/sportstaetten.csv";
                CSVReader csvReader = new CSVReader(new FileReader(csvFilename), ';', '\"', 1);
                ColumnPositionMappingStrategy strat = new ColumnPositionMappingStrategy();
                strat.setType(Facility.class);
                String[] columns = new String[]{"objectid", "name", "address", "type", "description", "lon", "lat"}; // the fields to bind do in your JavaBean
                strat.setColumnMapping(columns);

                CsvToBean csv = new CsvToBean();

                List list = csv.parse(strat, csvReader);
                for (Object object : list) {
                    Facility facility = (Facility) object;
                    System.out.println(facility.name);
                    facility.save();
                }
                csvReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (Athlete.find.findRowCount() == 0) {
            Athlete u = new Athlete();
            u.id = "100";
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

