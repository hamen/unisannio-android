package org.dronix.android.unisannio.adapters;

import org.dronix.android.unisannio.fragments.SeaAvvisiFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SEAFragmentAdapter extends FragmentPagerAdapter {

    protected static final String[] CONTENT = new String[]{
            "Avvisi"
    };

    private int mCount = CONTENT.length;

    public SEAFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment retval = null;
        switch (position) {
            case 0:
                retval = new SeaAvvisiFragment();
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
        return SEAFragmentAdapter.CONTENT[position % CONTENT.length];
    }
}