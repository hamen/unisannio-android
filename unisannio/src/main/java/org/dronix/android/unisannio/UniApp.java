package org.dronix.android.unisannio;

import com.alterego.advancedandroidlogger.implementations.AndroidLogger;
import com.alterego.advancedandroidlogger.implementations.DetailedAndroidLogger;
import com.alterego.advancedandroidlogger.implementations.NullAndroidLogger;
import com.alterego.advancedandroidlogger.interfaces.IAndroidLogger;

import android.app.Application;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(prefix = "m")
public class UniApp extends Application {

    @Getter
    @Setter
    private static MainActivity mActivity;

    private ObjectGraph applicationGraph;

    @Getter
    private DetailedAndroidLogger mLogger;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationGraph = ObjectGraph.create(getModules().toArray());
        applicationGraph.injectStatics();

        mLogger = new DetailedAndroidLogger("UNISANNIO", IAndroidLogger.LoggingLevel.VERBOSE);
        mLogger.debug("Logger has been created");
    }

    /**
     * A list of modules to use for the application graph. Subclasses can override this method to provide additional modules provided they call {@code
     * super.getModules()}.
     */
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new AndroidModule(this));
    }

    ObjectGraph getApplicationGraph() {
        return applicationGraph;
    }
}
