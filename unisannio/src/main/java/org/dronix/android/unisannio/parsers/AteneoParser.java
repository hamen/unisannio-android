package org.dronix.android.unisannio.parsers;

import org.dronix.android.unisannio.interfaces.IParser;
import org.dronix.android.unisannio.models.News;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class AteneoParser implements IParser {

    @Override
    public List<News> parse(Document doc) {
        List<News> newsList = new ArrayList<News>();

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
        return newsList;
    }
}
