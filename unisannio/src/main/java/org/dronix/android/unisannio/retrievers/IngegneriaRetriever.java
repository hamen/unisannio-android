package org.dronix.android.unisannio.retrievers;

import com.alterego.advancedandroidlogger.implementations.DetailedAndroidLogger;
import com.alterego.advancedandroidlogger.interfaces.IAndroidLogger;

import org.dronix.android.unisannio.MainActivity;
import org.dronix.android.unisannio.UniApp;
import org.dronix.android.unisannio.models.Article;
import org.dronix.android.unisannio.parsers.IngegneriaParser;
import org.dronix.android.unisannio.settings.URLS;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

@Singleton
public class IngegneriaRetriever {

    @Inject
    IngegneriaParser mParser;

    @Inject
    DetailedAndroidLogger mLogger;

    public Observable<List<Article>> getArticles(final String url, final String phpUrl) {
        return Observable
                .zip(getRSS(url), getPHP(phpUrl), new Func2<List<Article>, List<Article>, List<Article>>() {
                    @Override
                    public List<Article> call(List<Article> rssArticles, List<Article> phpArticles) {
                        List<Article> articles = new ArrayList<>();
                        for (Article rssArticle : rssArticles) {
                            for (Article phpArticle : phpArticles) {
                                if (phpArticle.getTitle().equals(rssArticle.getTitle())) {
                                    phpArticle.setLink(rssArticle.getLink());
                                    articles.add(phpArticle);
                                }
                            }
                        }
                        return articles;
                    }
                })
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
                            BufferedReader reader = new BufferedReader(new InputStreamReader(remote.openStream(), "ISO-8859-1"));

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
                            subscriber.onError(e);
                        }
                    }
                });
    }

    private Observable<List<Article>> getPHP(final String url) {
        return Observable
                .create(new Observable.OnSubscribe<List<Article>>() {
                    @Override
                    public void call(Subscriber<? super List<Article>> subscriber) {
                        List<Article> newsList = new ArrayList<Article>();

                        Document doc = null;
                        try {
                            doc = Jsoup.parse(new URL(url).openStream(), "ISO-8859-1", url);
                        } catch (IOException e) {
                            subscriber.onError(e);
                        }
                        newsList = mParser.parsePHP(doc);
                        subscriber.onNext(newsList);
                        subscriber.onCompleted();
                    }
                });
    }

    public Observable<String> getEventi(final String url) {
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
