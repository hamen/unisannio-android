package org.dronix.android.unisannio.retrievers;

import com.alterego.advancedandroidlogger.implementations.DetailedAndroidLogger;

import org.dronix.android.unisannio.models.Article;
import org.dronix.android.unisannio.parsers.IngegneriaParser;
import org.dronix.android.unisannio.parsers.SeaParser;
import org.dronix.android.unisannio.settings.URLS;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
public class SeaRetriever {

    @Inject
    DetailedAndroidLogger mLogger;

    @Inject
    SeaParser mParser;

    public Observable<List<Article>> getArticles(final String url) {
        return Observable
                .create(new Observable.OnSubscribe<List<Article>>() {
                    @Override
                    public void call(Subscriber<? super List<Article>> subscriber) {
                        List<Article> newsList = new ArrayList<Article>();

                        Document doc = null;
                        try {
                            doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
                        } catch (IOException e) {
                            subscriber.onError(e);
                        }
                        newsList = mParser.parsePHP(doc);
                        subscriber.onNext(newsList);
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String> getDescription(final String url) {
        return Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        Document doc = null;
                        try {
                            doc = Jsoup.connect(URLS.SEA + url).timeout(20 * 1000).get();
                        } catch (IOException e) {
                            subscriber.onError(e);
                        }
                        String description = mParser.parseDetailPage(doc);
                        subscriber.onNext(description);
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
