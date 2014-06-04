package org.dronix.android.unisannio.adapters;

import org.dronix.android.unisannio.fragments.AteneoAvvisiFragment;
import org.dronix.android.unisannio.fragments.WebviewFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class AteneoFragmentAdapter extends FragmentPagerAdapter {

    protected static final String[] CONTENT = new String[]{
            "Sito WEB", "Avvisi", "Mappa"
    };

    private int mCount = CONTENT.length;

    public AteneoFragmentAdapter(FragmentManager fm) {
        super(fm);
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
                retval = new Fragment();
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