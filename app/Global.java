
import au.com.bytecode.opencsv.*;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import models.Facility;
import play.Application;
import play.GlobalSettings;
import play.db.ebean.Model;

import java.io.FileReader;
import java.util.List;
import play.mvc.Call;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.PlayAuthenticate.Resolver;
import com.feth.play.module.pa.exceptions.AccessDeniedException;
import com.feth.play.module.pa.exceptions.AuthException;

/**
 * Created by Christina on 19.01.2015.
 */

public class Global extends GlobalSettings {
    @Override
    public void onStart(Application app) {

        if(new Model.Finder(String.class, Facility.class).findRowCount() == 0) {
            try {
                String csvFilename = app.path() + "\\conf\\resources\\sportstaetten.csv";
                CSVReader csvReader = new CSVReader(new FileReader(csvFilename), ';', '\"', 1);
                ColumnPositionMappingStrategy strat = new ColumnPositionMappingStrategy();
                strat.setType(Facility.class);
                String[] columns = new String[]{"objectID", "name", "address"}; // the fields to bind do in your JavaBean
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

        PlayAuthenticate.setResolver(new Resolver() {

            @Override
            public Call login() {
                // Your login page
                return routes.Application.index();
            }

            @Override
            public Call afterAuth() {
                // The user will be redirected to this page after authentication
                // if no original URL was saved
                return routes.Application.index();
            }

            @Override
            public Call afterLogout() {
                return routes.Application.index();
            }

            @Override
            public Call auth(final String provider) {
                // You can provide your own authentication implementation,
                // however the default should be sufficient for most cases
                return com.feth.play.module.pa.controllers.routes.Authenticate
                        .authenticate(provider);
            }

            @Override
            public Call onException(final AuthException e) {
                if (e instanceof AccessDeniedException) {
                    return routes.Application
                            .oAuthDenied(((AccessDeniedException) e)
                                    .getProviderKey());
                }

                // more custom problem handling here...

                return super.onException(e);
            }

            @Override
            public Call askLink() {
                // We don't support moderated account linking in this sample.
                // See the play-authenticate-usage project for an example
                return null;
            }

            @Override
            public Call askMerge() {
                // We don't support moderated account merging in this sample.
                // See the play-authenticate-usage project for an example
                return null;
            }
        });
    }
}

