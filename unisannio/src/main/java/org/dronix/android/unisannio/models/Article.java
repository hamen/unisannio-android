package org.dronix.android.unisannio.models;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(prefix = "m")
public class Article {

    private String mTitle;

    private String mLink;

    private String mDescription;

    private String mPubDate;

    private String mAuthor;

    public Article(String title, String link, String description, String pubDate, String author) {
        mTitle = title;
        mLink = link;
        mDescription = description;
        mPubDate = pubDate;
        mAuthor = author;
    }
}
