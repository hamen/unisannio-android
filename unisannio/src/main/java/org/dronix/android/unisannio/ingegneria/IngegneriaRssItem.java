package org.dronix.android.unisannio.ingegneria;

import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(prefix = "m")
public class IngegneriaRssItem {

    private String mTitle;

    private String mLink;

    private String mDescription;

    private Date mPubDate;

    private String mUrl;

    public IngegneriaRssItem(String title, String link, String description, Date pubDate, String source) {
        mTitle = title;
        mLink = link;
        mDescription = description;
        mPubDate = pubDate;
        mUrl = source;
    }
}
