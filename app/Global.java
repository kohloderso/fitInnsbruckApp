
import au.com.bytecode.opencsv.*;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import models.Facility;
import play.Application;
import play.GlobalSettings;

import java.io.FileReader;
import java.util.List;

/**
 * Created by Christina on 19.01.2015.
 */

public class Global extends GlobalSettings {
    @Override
    public void onStart(Application app) {
        try {
            String csvFilename = app.path() + "\\conf\\resources\\sportstaetten.csv";
            CSVReader csvReader = new CSVReader(new FileReader(csvFilename), ';', '\"', 1);
            ColumnPositionMappingStrategy strat = new ColumnPositionMappingStrategy();
            strat.setType(Facility.class);
            String[] columns = new String[] {"objectID", "name", "address"}; // the fields to bind do in your JavaBean
            strat.setColumnMapping(columns);

            CsvToBean csv = new CsvToBean();

            List list = csv.parse(strat, csvReader);
            for (Object object : list) {
                Facility facility = (Facility) object;
                System.out.println(facility.name);
                facility.save();
            }
            csvReader.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}

