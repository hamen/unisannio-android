package org.dronix.android.unisannio.parsers;

import org.dronix.android.unisannio.interfaces.IParser;
import org.dronix.android.unisannio.models.Article;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ScienzeDetailParser implements IParser {

    @Override
    public List<String> parse(Document document) {

        List<String> body = new ArrayList<>();
        Element div = document.select("div.rt-article-inner").first();
        String description = null;

        if (div == null) {
            description = document.select("div.rt-article").first().html();
        } else {
            div.select("table.pagenav").first().remove();
            div.select("p.rt-article-cat").first().remove();
            description = div.html();
        }
        body.add(description);
        return body;
    }
}
