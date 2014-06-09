package org.dronix.android.unisannio.adapters;

import org.dronix.android.unisannio.fragments.IngegneriaAvvisiFragment;
import org.dronix.android.unisannio.fragments.IngegneriaEventiFragment;
import org.dronix.android.unisannio.fragments.MapFragment;
import org.dronix.android.unisannio.settings.UnisannioGeoData;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class IngegneriaFragmentAdapter extends FragmentPagerAdapter {

    protected static final String[] CONTENT = new String[]{
            "Eventi", "Avvisi", "Mappa"
    };

    private int mCount = CONTENT.length;

    public IngegneriaFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment retval = null;
        switch (position) {
            case 0:
                retval = new IngegneriaEventiFragment();
                break;
            case 1:
                retval = new IngegneriaAvvisiFragment();
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
        return IngegneriaFragmentAdapter.CONTENT[position % CONTENT.length];
    }
}