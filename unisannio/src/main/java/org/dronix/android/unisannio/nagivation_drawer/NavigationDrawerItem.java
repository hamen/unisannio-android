package org.dronix.android.unisannio.nagivation_drawer;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(prefix = "m")
public class NavigationDrawerItem {

    @Getter
    private int mIcon;

    @Getter
    private String mName;

    public NavigationDrawerItem(int icon, String name) {
        mIcon = icon;
        mName = name;
    }
}