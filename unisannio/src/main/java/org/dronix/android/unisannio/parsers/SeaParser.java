package org.dronix.android.unisannio.parsers;

import com.alterego.advancedandroidlogger.implementations.DetailedAndroidLogger;

import org.apache.commons.lang3.StringEscapeUtils;
import org.dronix.android.unisannio.models.Article;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SeaParser {
    @Inject
    DetailedAndroidLogger mLogger;

    public List<Article> parsePHP(Document document) {
        Elements elements = document.select("#jazin-hlfirst > div.jazin-contentwrap > div.jazin-content");
        List<Article> articles = new ArrayList<>();
        for (Element e : elements) {
            Element a = e.select("h4 > a").first();

            String title = a.attr("title");
            String link = a.attr("href");

            String description = e.text();
            description = StringEscapeUtils.unescapeHtml4(description).replace(title + " ", "");

            articles.add(new Article(title, link, description, "", ""));
        }
        return articles;
    }

    public String parseDetailPage(Document doc) {
        Elements elements = doc.select("#ja-current-content");
        return elements.select(".article-content").first().html();
    }
}
