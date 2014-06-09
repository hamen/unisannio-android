package org.dronix.android.unisannio.models;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(prefix = "m")
public class News {

    private String mId;

    private String mBody;

    private String mDate;

    public News(String date, String body, String id) {
        mDate = date;
        mBody = body;
        mId = id;
    }
}
