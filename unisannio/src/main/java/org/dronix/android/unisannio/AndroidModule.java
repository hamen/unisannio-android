package org.dronix.android.unisannio;

import android.content.Context;
import android.location.LocationManager;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

import static android.content.Context.LOCATION_SERVICE;

@Module(library = true)
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
}