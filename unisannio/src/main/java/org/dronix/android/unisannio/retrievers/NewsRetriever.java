package org.dronix.android.unisannio.retrievers;

import org.dronix.android.unisannio.interfaces.IParser;
import org.dronix.android.unisannio.models.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NewsRetriever {

    public static Observable<List<News>> getNewsList(final String url, final IParser parser) {
        return Observable
                .create(new Observable.OnSubscribe<List<News>>() {
                    @Override
                    public void call(Subscriber<? super List<News>> subscriber) {
                        subscriber.onNext(get(url, parser));
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private static List<News> get(String url, IParser parser) {
        List<News> newsList;
        try {
            Document doc = Jsoup.connect(url).timeout(10 * 1000).get();
            newsList = parser.parse(doc);
        } catch (Exception e) {
            return new ArrayList<News>();
        }
        return newsList;
    }
}
