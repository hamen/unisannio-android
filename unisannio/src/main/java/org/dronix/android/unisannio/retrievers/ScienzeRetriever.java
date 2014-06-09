package org.dronix.android.unisannio.retrievers;

import org.dronix.android.unisannio.models.Article;
import org.dronix.android.unisannio.parsers.ScienzeParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ScienzeRetriever {

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
            Document doc = Jsoup.connect(url).timeout(10 * 1000).get();
            newsList = new ScienzeParser().parse(doc);
        } catch (Exception e) {
            return new ArrayList<>();
        }
        return newsList;
    }
}
