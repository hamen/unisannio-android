package org.dronix.android.unisannio.parsers;

import org.dronix.android.unisannio.interfaces.IParser;
import org.dronix.android.unisannio.models.Article;
import org.dronix.android.unisannio.models.News;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ScienzeParser implements IParser {

    @Override
    public <T> List<T> parse(Document document) {
        Elements newsItems = document.select("li.latestnewsfl_green");

        List<T> newsList = new ArrayList<>();
        for (int i = 0; i < newsItems.size(); i++) {
            String date = null;
            Element dateElement = newsItems.get(i).select("span").first();
            if (dateElement != null) {
                date = dateElement.text().trim();
            }

            String title = null;
            Element bodyElement = newsItems.get(i).select("a").first();
            String link = null;
            if (bodyElement != null) {
                title = bodyElement.text();
                link = bodyElement.attr("href");
            }

            if (date != null && title != null) {
                newsList.add((T) new Article(title, link, null, date, null));
            }
        }

        newsItems = document.select("li.latestnewsfl_orange");
        for (int i = 0; i < newsItems.size(); i++) {
            String date = null;
            Element dateElement = newsItems.get(i).select("span").first();
            if (dateElement != null) {
                date = dateElement.text().trim();
            }

            String title = null;
            Element bodyElement = newsItems.get(i).select("a").first();
            String link = null;
            if (bodyElement != null) {
                title = bodyElement.text();
                link = bodyElement.attr("href");
            }

            if (date != null && title != null) {
                newsList.add((T) new Article(title, link, null, date, null));
            }
        }
        return (List<T>) newsList;
    }
}
