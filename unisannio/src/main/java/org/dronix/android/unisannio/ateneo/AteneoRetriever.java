package org.dronix.android.unisannio.ateneo;

import org.dronix.android.unisannio.models.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AteneoRetriever {

    public static Observable<List<News>> getNewsList(final String url) {
        return Observable
                .create(new Observable.OnSubscribe<List<News>>() {
                    @Override
                    public void call(Subscriber<? super List<News>> subscriber) {
                        List<News> list = get(url);
                        subscriber.onNext(list);
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private static List<News> get(String url) {
        List<News> newsList;
        try {
            Document doc = Jsoup.connect(url).timeout(10 * 1000).get();
            newsList = new AteneoParser().parse(doc);
        } catch (Exception e) {
            return new ArrayList<News>();
        }
        return newsList;
    }
}
