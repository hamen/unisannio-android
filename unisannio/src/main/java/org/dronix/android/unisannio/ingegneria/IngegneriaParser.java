package org.dronix.android.unisannio.ingegneria;

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
public class IngegneriaParser {

    @Inject
    DetailedAndroidLogger mLogger;

    public List<Article> parseRSS(Document document) {
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

    public List<Article> parsePHP(Document document) {
        Elements elements = document.select(".entry-content > fieldset");
        List<Article> articles = new ArrayList<>();
        for (Element e : elements) {
            String title = e.select("legend").first().text();

            String description = e.select(".avvisi_corpo").first().html();
            description = StringEscapeUtils.unescapeHtml4(description);

            String pubDate = e.select(".avvisi_data").first().text();

            articles.add(new Article(title, "", description, pubDate, ""));
        }
        return articles;
    }
}
