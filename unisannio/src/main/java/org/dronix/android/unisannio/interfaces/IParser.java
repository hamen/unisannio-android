package org.dronix.android.unisannio.interfaces;

import org.dronix.android.unisannio.models.News;
import org.jsoup.nodes.Document;

import java.util.List;

public interface IParser {

    public abstract <T> List<T> parse(Document document);
}
