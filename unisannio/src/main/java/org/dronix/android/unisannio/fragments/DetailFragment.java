package org.dronix.android.unisannio.fragments;

import org.dronix.android.unisannio.R;
import org.dronix.android.unisannio.interfaces.IParser;
import org.dronix.android.unisannio.models.Article;
import org.dronix.android.unisannio.parsers.Parsers;
import org.dronix.android.unisannio.providers.ParserProvider;
import org.dronix.android.unisannio.retrievers.NewsRetriever;
import org.dronix.android.unisannio.retrievers.ScienzeRetriever;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observer;

public class DetailFragment extends Fragment {

    @InjectView(R.id.webView)
    WebView mWebView;

    private String mUrl;

    public static DetailFragment newInstance(String url, Parsers parser) {

        Bundle bundle = new Bundle();
        bundle.putString("URL", url);
        bundle.putSerializable("PARSER", parser);

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
