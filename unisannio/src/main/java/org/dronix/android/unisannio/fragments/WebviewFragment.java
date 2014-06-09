package org.dronix.android.unisannio.fragments;


import org.dronix.android.unisannio.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebviewFragment extends Fragment {


    public WebviewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_webview, container, false);

        Bundle bundle = getArguments();
        WebView webView = (WebView) rootView.findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);

        webView.loadUrl(bundle.getString("URL"));

        return rootView;
    }

    public static WebviewFragment newInstance(String url) {
        Bundle bundle = new Bundle();
        bundle.putString("URL", url);

        WebviewFragment fragment = new WebviewFragment();
        fragment.setArguments(bundle);

        return fragment;
    }
}
