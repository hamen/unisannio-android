package org.dronix.android.unisannio.ingegneria;

import com.viewpagerindicator.TitlePageIndicator;

import org.dronix.android.unisannio.MainActivity;
import org.dronix.android.unisannio.R;
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

public class IngegneriaLandingFragment extends Fragment {

    @Inject
    NavigationDrawerFragment mNavigationDrawerFragment;

    private ViewPager mPager;

    private FragmentPagerAdapter mAdapter;

    public static IngegneriaLandingFragment newInstance() {
        return new IngegneriaLandingFragment();
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
        mAdapter = new IngegneriaFragmentAdapter(getChildFragmentManager());
        mPager.setAdapter(mAdapter);

        TitlePageIndicator titleIndicator = (TitlePageIndicator) rootView.findViewById(R.id.titles);
        titleIndicator.setViewPager(mPager);
        titleIndicator.setCurrentItem(1);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(2);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (mNavigationDrawerFragment != null && !mNavigationDrawerFragment.isDrawerOpen()) {
            inflater.inflate(R.menu.ingegneria, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_website) {
            Uri uri = Uri.parse(URLS.INGEGNERIA);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_contact) {
            String recepientEmail = "webmaster.ingegneria@unisannio.it";
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:" + recepientEmail));
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}