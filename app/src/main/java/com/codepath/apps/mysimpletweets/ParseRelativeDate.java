package com.codepath.apps.mysimpletweets;


import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ParseRelativeDate {

    public String getRelativeTimeAgo(String rawJsonDate) {
        String relativeDate = "";
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        if (rawJsonDate == null) {
            return relativeDate = "1 second ago";
        } else {
            try {
                long dateMillis = sf.parse(rawJsonDate).getTime();
                relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                        System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        return relativeDate;
    }
}
