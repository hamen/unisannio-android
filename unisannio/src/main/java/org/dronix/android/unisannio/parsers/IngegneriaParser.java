package org.dronix.android.unisannio.parsers;

import org.apache.commons.lang3.StringEscapeUtils;
import org.dronix.android.unisannio.models.Article;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class IngegneriaParser {

    public List<Article> parse(Document document) {
        Elements elements = document.select("item");
        List<Article> articles = new ArrayList<>();
        for (Element e : elements) {
            String title = e.select("title").first().text();

            String description = e.select("description").first().text();
            description = StringEscapeUtils.unescapeHtml4(description);

            String link = e.select("link").first().text();

            String pubDate = e.select("pubDate").first().text();

            articles.add(new Article(title, link, description, pubDate, ""));
        }
        return articles;
    }
}
