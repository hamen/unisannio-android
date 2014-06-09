package org.dronix.android.unisannio.parsers;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ScienzeDetailParser {

    public String parse(Document document) {
        Element div = document.select("div.rt-article-inner").first();
        String description = null;

        if (div == null) {
            description = document.select("div.rt-article").first().html();
        } else {
            div.select("table.pagenav").first().remove();
            div.select("p.rt-article-cat").first().remove();
            description = div.html();
        }
        return description;
    }
}
