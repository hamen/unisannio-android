package org.dronix.android.unisannio.retrievers;

import com.alterego.advancedandroidlogger.implementations.DetailedAndroidLogger;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.dronix.android.unisannio.models.Article;
import org.dronix.android.unisannio.parsers.GiurisprudenzaParser;
import org.dronix.android.unisannio.parsers.IngegneriaParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

@Singleton
public class GiurisprudenzaRetriever {

    @Inject
    GiurisprudenzaParser mParser;

    @Inject
    DetailedAndroidLogger mLogger;

    public Observable<List<Article>> getArticles(final String url) {
        return getRSS(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<List<Article>> getRSS(final String url) {
        return Observable
                .create(new Observable.OnSubscribe<List<Article>>() {
                    @Override
                    public void call(Subscriber<? super List<Article>> subscriber) {
                        List<Article> newsList = new ArrayList<Article>();
                        try {
                            String html;
                            URL remote = new URL(url);
                            BufferedReader reader = new BufferedReader(new InputStreamReader(callWebErvice(url), "UTF-8"));

                            StringBuilder str = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                str.append(line);
                            }
                            reader.close();
                            html = str.toString();

                            Document doc = Jsoup.parse(html, "", Parser.xmlParser());

                            newsList = mParser.parseRSS(doc);
                            subscriber.onNext(newsList);
                            subscriber.onCompleted();
                        } catch (Exception e) {
                            mLogger.error(e.getMessage());
                            subscriber.onError(e);
                        }
                    }
                });
    }

    private InputStream callWebErvice(String serviceURL) {
        // http get client
        HttpClient client = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet();

        try {
            // construct a URI object
            getRequest.setURI(new URI(serviceURL));
        } catch (URISyntaxException e) {
            mLogger.error("URISyntaxException", e.toString());
        }

        // buffer reader to read the response
        BufferedReader in = null;
        // the service response
        HttpResponse response = null;
        try {
            // execute the request
            response = client.execute(getRequest);
        } catch (ClientProtocolException e) {
            mLogger.error("ClientProtocolException", e.toString());
        } catch (IOException e) {
            mLogger.error("IO exception", e.toString());
        }
        if (response != null) {
            try {
                return response.getEntity().getContent();
            } catch (IOException e) {
                mLogger.error(e.getMessage());
            }
        } else {
            return null;
        }
        return null;
    }
}
