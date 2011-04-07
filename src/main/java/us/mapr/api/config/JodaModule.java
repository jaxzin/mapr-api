package us.mapr.api.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class JodaModule extends AbstractModule {
    @Override
    protected void configure() {
    }

    @Provides @Facebook
    public DateTimeFormatter providesFacebookDateTimeFormatter() {
        return DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");
    }

//    @Provides @Standard
//    public DateTimeFormatter providesStandardDateTimeFormatter() {
//        return ISODateTimeFormat.dateTime();
//    }
}
