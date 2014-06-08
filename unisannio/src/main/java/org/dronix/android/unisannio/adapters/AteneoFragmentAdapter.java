package org.dronix.android.unisannio.adapters;

import com.google.android.gms.maps.GoogleMap;

import org.dronix.android.unisannio.fragments.AteneoAvvisiFragment;
import org.dronix.android.unisannio.fragments.MapFragment;
import org.dronix.android.unisannio.fragments.WebviewFragment;
import org.dronix.android.unisannio.settings.UnisannioGeoData;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class AteneoFragmentAdapter extends FragmentPagerAdapter {

    protected static final String[] CONTENT = new String[]{
            "Sito WEB", "Avvisi", "Mappa"
    };

    private final FragmentManager mFragmentManager;

    private int mCount = CONTENT.length;

    private GoogleMap mMap;

    public AteneoFragmentAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment retval = null;
        switch (position) {
            case 0:
                Bundle bundle = new Bundle();
                bundle.putString("URL", "http://www.unisannio.it");

                retval = new WebviewFragment();
                retval.setArguments(bundle);
                break;
            case 1:
                retval = new AteneoAvvisiFragment();
                break;
            case 2:
                retval = MapFragment.newInstance(UnisannioGeoData.INGEGNERIA());
                break;
        }
        return retval;
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return AteneoFragmentAdapter.CONTENT[position % CONTENT.length];
    }
}