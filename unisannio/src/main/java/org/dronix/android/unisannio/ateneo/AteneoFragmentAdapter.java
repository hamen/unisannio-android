package org.dronix.android.unisannio.ateneo;

import org.dronix.android.unisannio.fragments.BlankFragment;
import org.dronix.android.unisannio.fragments.MapFragment;
import org.dronix.android.unisannio.settings.UnisannioGeoData;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class AteneoFragmentAdapter extends FragmentPagerAdapter {

    protected static final String[] CONTENT = new String[]{
            "Avvisi", "Mappa"
    };

    private final FragmentManager mFragmentManager;

    private int mCount = CONTENT.length;

    public AteneoFragmentAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment retval = null;
        switch (position) {
            case 0:
                retval = new AteneoAvvisiFragment();
                break;
            case 1:
                int currentapiVersion = android.os.Build.VERSION.SDK_INT;
                if (currentapiVersion >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    retval = MapFragment.newInstance(UnisannioGeoData.ATENEO());
                } else {
                    retval = new BlankFragment();
                }
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