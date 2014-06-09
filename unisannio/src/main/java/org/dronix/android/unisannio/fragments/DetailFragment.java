package org.dronix.android.unisannio.fragments;

import org.dronix.android.unisannio.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DetailFragment extends Fragment {

    @InjectView(R.id.webView)
    WebView mWebView;

    private String mUrl;

    public static DetailFragment newInstance(String url) {

        Bundle bundle = new Bundle();
        bundle.putString("URL", url);

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.inject(this, rootView);

        Bundle bundle = getArguments();
        mUrl = bundle.getString("URL");

        return rootView;
    }
}
