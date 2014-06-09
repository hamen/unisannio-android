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

    public static WebviewFragment newInstance(String url) {
        Bundle bundle = new Bundle();
        bundle.putString("URL", url);

        WebviewFragment fragment = new WebviewFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_webview, container, false);

        Bundle bundle = getArguments();
        WebView webView = (WebView) rootView.findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUserAgentString(
                "Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3");

        webView.loadUrl(bundle.getString("URL"));

        return rootView;
    }
}
