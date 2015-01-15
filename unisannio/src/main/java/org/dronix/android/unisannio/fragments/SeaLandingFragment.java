package org.dronix.android.unisannio.fragments;

import com.viewpagerindicator.TitlePageIndicator;

import org.dronix.android.unisannio.MainActivity;
import org.dronix.android.unisannio.R;
import org.dronix.android.unisannio.adapters.SEAFragmentAdapter;
import org.dronix.android.unisannio.nagivation_drawer.NavigationDrawerFragment;
import org.dronix.android.unisannio.settings.URLS;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

public class SeaLandingFragment extends Fragment {

    @Inject
    NavigationDrawerFragment mNavigationDrawerFragment;

    private ViewPager mPager;

    private FragmentPagerAdapter mAdapter;

    public static SeaLandingFragment newInstance() {
        return new SeaLandingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_landing, container, false);

        setHasOptionsMenu(true);

        mPager = (ViewPager) rootView.findViewById(R.id.pager);
        mAdapter = new SEAFragmentAdapter(getChildFragmentManager());
        mPager.setAdapter(mAdapter);

        TitlePageIndicator titleIndicator = (TitlePageIndicator) rootView.findViewById(R.id.titles);
        titleIndicator.setViewPager(mPager);
        titleIndicator.setCurrentItem(1);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(4);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (mNavigationDrawerFragment != null && !mNavigationDrawerFragment.isDrawerOpen()) {
            inflater.inflate(R.menu.sea, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_website) {
            Uri uri = Uri.parse(URLS.SEA);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}