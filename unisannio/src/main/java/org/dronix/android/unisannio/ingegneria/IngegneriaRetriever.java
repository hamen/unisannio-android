package org.dronix.android.unisannio.ingegneria;

import com.alterego.advancedandroidlogger.implementations.DetailedAndroidLogger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

@Singleton
public class IngegneriaRetriever {

    @Inject
    IngegneriaParser mParser;

    @Inject
    DetailedAndroidLogger mLogger;

    public Observable<Elements> retrieveRssRawElements() {

        return Observable
                .create(new Observable.OnSubscribe<Elements>() {
                    @Override
                    public void call(Subscriber<? super Elements> subscriber) {
                        Document doc = null;
                        try {
                            doc = Jsoup.parse(new URL(IngegneriaSettings.AVVISI_URL).openStream(), "ISO-8859-1", IngegneriaSettings.AVVISI_URL);
                        } catch (IOException e) {
                            subscriber.onError(e);
                        }
                        if (doc != null) {
                            Elements items = doc.select("item");
                            subscriber.onNext(items);
                            subscriber.onCompleted();
                        }
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    public Observable<List<IngegneriaRssItem>> getRssItems(final Elements elements) {
        final List<IngegneriaRssItem> items = new ArrayList<>();
        return Observable
                .create(new Observable.OnSubscribe<List<IngegneriaRssItem>>() {
                    @Override
                    public void call(Subscriber<? super List<IngegneriaRssItem>> subscriber) {
                        for (Element element : elements) {
                            String pattern = "EEE, dd MMM yyyy HH:mm:ss Z";
                            SimpleDateFormat format = new SimpleDateFormat(pattern);
                            Date pubDate = null;
                            try {
                                pubDate = format.parse(element.select("pubDate").text());
                            } catch (ParseException e) {
                                subscriber.onError(e);
                            }

                            IngegneriaRssItem item = new IngegneriaRssItem(
                                    element.select("title").text(),
                                    element.select("link").text(),
                                    element.select("description").text(),
                                    pubDate,
                                    element.select("source").text());

                            items.add(item);
                        }

                        subscriber.onNext(items);
                        subscriber.onCompleted();
                    }
                });
    }
}
