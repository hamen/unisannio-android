package org.dronix.android.unisannio.adapters;

import com.google.android.gms.maps.GoogleMap;

import org.dronix.android.unisannio.fragments.GiurisprudenzaAvvisiFragment;
import org.dronix.android.unisannio.settings.URLS;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class GiurisprudenzaFragmentAdapter extends FragmentPagerAdapter {

    protected static final String[] CONTENT = new String[]{
            "Comunicazioni" , "Avvisi"
    };

    private final FragmentManager mFragmentManager;

    private int mCount = CONTENT.length;

    private GoogleMap mMap;

    public GiurisprudenzaFragmentAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment retval = null;
        switch (position) {
            case 0:
                retval = GiurisprudenzaAvvisiFragment.newInstance(URLS.GIURISPRUDENZA_COMUNICAZIONI);
                break;
            case 1:
                retval = GiurisprudenzaAvvisiFragment.newInstance(URLS.GIURISPRUDENZA_AVVISI);
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
        return GiurisprudenzaFragmentAdapter.CONTENT[position % CONTENT.length];
    }
}