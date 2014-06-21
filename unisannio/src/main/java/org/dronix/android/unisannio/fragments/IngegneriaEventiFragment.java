package org.dronix.android.unisannio.fragments;

import org.dronix.android.unisannio.R;
import org.dronix.android.unisannio.retrievers.IngegneriaRetriever;
import org.dronix.android.unisannio.settings.URLS;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observer;

public class IngegneriaEventiFragment extends Fragment {

    @InjectView(R.id.webView)
    WebView mWebView;

    public static IngegneriaEventiFragment newInstance() {
        return new IngegneriaEventiFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_webview, container, false);
        ButterKnife.inject(this, rootView);


        new IngegneriaRetriever()
                .getEventi(URLS.INGEGNERIA_EVENTI)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        mWebView.loadDataWithBaseURL(URLS.INGEGNERIA_EVENTI_BASE_URL, s, "text/html", "UTF-8", null);
                    }
                });

        return rootView;
    }
}
