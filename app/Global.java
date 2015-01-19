import com.avaje.ebean.Ebean;
import models.Facility;
import play.Application;
import play.GlobalSettings;
import play.api.Play;
import play.libs.Yaml;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Christina on 19.01.2015.
 */

public class Global extends GlobalSettings {
    @Override
    public void onStart(Application app) {
        InputStream facilityStream = ClassLoader.getSystemResourceAsStream("resources/sportstaetten.csv");

    }
}

