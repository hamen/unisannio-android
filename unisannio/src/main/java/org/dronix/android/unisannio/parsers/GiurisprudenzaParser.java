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
public class GiurisprudenzaParser {

    @Inject
    DetailedAndroidLogger mLogger;

    public List<Article> parseRSS(Document document) {
        Elements elements = document.select("item");
        List<Article> articles = new ArrayList<>();
        for (Element e : elements) {
            String title = e.select("title").first().text();

            // Clean up pointless prefix
            title = title.replace("[Giurisprudenza] ", "");

            String description = e.select("description").first().text();
            description = StringEscapeUtils.unescapeHtml4(description);

            articles.add(new Article(title, "", description, "", ""));
        }
        return articles;
    }
}
