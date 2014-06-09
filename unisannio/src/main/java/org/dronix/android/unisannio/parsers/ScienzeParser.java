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
    public List<Article> parse(Document document) {
        Elements newsItems = document.select("li.latestnewsfl_green");

        List<Article> newsList = new ArrayList<>();
        for (Element newsItem : newsItems) {
            String date = null;
            Element dateElement = newsItem.select("span").first();
            if (dateElement != null) {
                date = dateElement.text().trim();
            }

            String title = null;
            Element bodyElement = newsItem.select("a").first();
            String link = null;
            if (bodyElement != null) {
                title = bodyElement.text();
                link = bodyElement.attr("href");
            }

            if (date != null && title != null) {
                newsList.add(new Article(title, link, null, date, null));
            }
        }

        newsItems = document.select("li.latestnewsfl_orange");
        for (Element newsItem : newsItems) {
            String date = null;
            Element dateElement = newsItem.select("span").first();
            if (dateElement != null) {
                date = dateElement.text().trim();
            }

            String title = null;
            Element bodyElement = newsItem.select("a").first();
            String link = null;
            if (bodyElement != null) {
                title = bodyElement.text();
                link = bodyElement.attr("href");
            }

            if (date != null && title != null) {
                newsList.add(new Article(title, link, null, date, null));
            }
        }
        return newsList;
    }
}
