package org.dronix.android.unisannio;

import android.app.Application;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(prefix = "m")
public class UnisannioApplication extends Application {

    @Getter
    @Setter
    public static MainActivity mActivity;
}
