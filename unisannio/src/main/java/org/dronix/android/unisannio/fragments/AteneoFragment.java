package org.dronix.android.unisannio.fragments;

import com.viewpagerindicator.TitlePageIndicator;

import org.dronix.android.unisannio.R;
import org.dronix.android.unisannio.adapters.AteneoFragmentAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AteneoFragment extends Fragment {

    private ViewPager mPager;

    private AteneoFragmentAdapter mAdapter;

    public static AteneoFragment newInstance(String content) {
        AteneoFragment fragment = new AteneoFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_ateneo, container, false);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) rootView.findViewById(R.id.pager);
        mAdapter = new AteneoFragmentAdapter(getActivity().getSupportFragmentManager());
        mPager.setAdapter(mAdapter);

        //Bind the title indicator to the adapter
        TitlePageIndicator titleIndicator = (TitlePageIndicator) rootView.findViewById(R.id.titles);
        titleIndicator.setViewPager(mPager);
        titleIndicator.setCurrentItem(1);

        return rootView;
    }
}