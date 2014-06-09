package org.dronix.android.unisannio.retrievers;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.dronix.android.unisannio.models.Article;
import org.dronix.android.unisannio.parsers.IngegneriaParser;
import org.dronix.android.unisannio.parsers.ScienzeParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.xml.sax.InputSource;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class IngegneriaRetriever {

    public static Observable<List<Article>> getArticles(final String url) {
        return Observable
                .create(new Observable.OnSubscribe<List<Article>>() {
                    @Override
                    public void call(Subscriber<? super List<Article>> subscriber) {
                        List<Article> list = get(url);
                        subscriber.onNext(list);
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private static List<Article> get(String url) {
        List<Article> newsList;
        try {
            String html;
            URL remote = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(remote.openStream(), "ISO-8859-1"));

            StringBuilder str = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                str.append(line);
            }
            reader.close();
            html = str.toString();

            Document doc = Jsoup.parse(html, "", Parser.xmlParser());

            newsList = new IngegneriaParser().parse(doc);
        } catch (Exception e) {
            return new ArrayList<>();
        }
        return newsList;
    }

    public static Observable<String> getEventi(final String url) {
        return Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        Document doc = null;
                        Elements div = null;
                        try {
                            doc = Jsoup.connect(url).timeout(20 * 1000).get();
                        } catch (IOException e) {
                            subscriber.onError(e);
                        }

                        if (doc != null) {
                            div = doc.select("#contenuto");
                            if (div.first() != null) {
                                subscriber.onNext(div.first().html());
                                subscriber.onCompleted();
                            }
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
