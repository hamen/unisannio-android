package org.dronix.android.unisannio.retrievers;

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

    public static Observable<List<News>> getNewsList(final String url) {
        return Observable
                .create(new Observable.OnSubscribe<List<News>>() {
                    @Override
                    public void call(Subscriber<? super List<News>> subscriber) {
                        subscriber.onNext(get(url));
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private static List<News> get(String url) {
        List<News> newsList = new ArrayList<News>();

        try {
            Document doc = Jsoup.connect(url).timeout(10000).get();
            Elements newsItems = doc.select("div.meta > table > tbody > tr");

            for (int i = 2; i < newsItems.size(); i++) {
                String date = null;
                Element dateElement = newsItems.get(i).select("p").first();
                if (dateElement != null) {
                    date = dateElement.text();
                }

                String body = null;
                Element bodyElement = newsItems.get(i).select("a").first();
                String id = "";
                if (bodyElement != null) {
                    body = bodyElement.text();

                    String href = bodyElement.attr("href");
                    id = href.substring(href.indexOf("=") + 1);
                }

                if (date != null && body != null) {
                    newsList.add(new News(date, body, id));
                }
            }
        } catch (Exception e) {
            return new ArrayList<News>();
        }
        return newsList;
    }
}
