package org.dronix.android.unisannio;

import com.alterego.advancedandroidlogger.implementations.DetailedAndroidLogger;

import org.dronix.android.unisannio.ingegneria.IngegneriaParser;
import org.dronix.android.unisannio.parsers.SeaParser;
import org.dronix.android.unisannio.ingegneria.IngegneriaRetriever;
import org.dronix.android.unisannio.retrievers.SeaRetriever;

import android.content.Context;
import android.location.LocationManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.LOCATION_SERVICE;

@Module(
        injects = {
                IngegneriaParser.class,
                IngegneriaRetriever.class,
                SeaParser.class,
                SeaRetriever.class
        },
        library = true
)
public class AndroidModule {

    private final UniApp application;

    public AndroidModule(UniApp application) {
        this.application = application;
    }

    @Provides
    @Singleton
    @ForApplication
    Context provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    LocationManager provideLocationManager() {
        return (LocationManager) application.getSystemService(LOCATION_SERVICE);
    }

    @Provides
    @Singleton
    DetailedAndroidLogger provideLogger() {
        return application.getLogger();
    }
}