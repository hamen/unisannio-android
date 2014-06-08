package org.dronix.android.unisannio.fragments;

import com.viewpagerindicator.TitlePageIndicator;

import org.dronix.android.unisannio.MainActivity;
import org.dronix.android.unisannio.R;
import org.dronix.android.unisannio.adapters.AteneoFragmentAdapter;
import org.dronix.android.unisannio.adapters.ScienzeFragmentAdapter;
import org.dronix.android.unisannio.settings.Adapters;
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

public class LandingFragment extends Fragment {

    @Inject
    NavigationDrawerFragment mNavigationDrawerFragment;

    private ViewPager mPager;

    private FragmentPagerAdapter mAdapter;

    private int mMenuLayout;

    private String mUrl;

    public static LandingFragment newInstance(Adapters adapterId, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("ADAPTER", adapterId);
        bundle.putInt("SECTION", position);

        LandingFragment fragment = new LandingFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        Adapters adapterId = (Adapters) bundle.getSerializable("ADAPTER");

        switch (adapterId) {
            case ATENEO:
                mMenuLayout = R.menu.ateneo;
                mUrl = URLS.ATENEO;
                mAdapter = new AteneoFragmentAdapter(getChildFragmentManager());
                break;
            case SCIENZE:
                mMenuLayout = R.menu.scienze;
                mUrl = URLS.SCIENZE;
                mAdapter = new ScienzeFragmentAdapter(getChildFragmentManager());
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_landing, container, false);

        setHasOptionsMenu(true);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) rootView.findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        //Bind the title indicator to the adapter
        TitlePageIndicator titleIndicator = (TitlePageIndicator) rootView.findViewById(R.id.titles);
        titleIndicator.setViewPager(mPager);
        titleIndicator.setCurrentItem(0);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) getActivity()).inject(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((MainActivity) activity).onSectionAttached(getArguments().getInt("SECTION"));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            inflater.inflate(mMenuLayout, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_website) {
            Uri uri = Uri.parse(mUrl);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_contact) {
            String recepientEmail = "presidio.didattico.scienze@unisannio.it";
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:" + recepientEmail));
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}