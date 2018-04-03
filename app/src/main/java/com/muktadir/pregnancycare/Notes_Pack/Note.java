package com.muktadir.pregnancycare.Notes_Pack;

import android.content.Context;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * This class represents a Note object
 * Created by saeid on 5/8/2016.
 */
public class Note implements Serializable {

    private long mDateTime; //creation time of the note
    private String mTitle; //title of the note
    private String mContent; //content of the note
    public Note(long dateInMillis, String title, String content) {
        mDateTime = dateInMillis;
        mTitle = title;
        mContent = content;

    }

    public void setDateTime(long dateTime) {
        mDateTime = dateTime;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public long getDateTime() {
        return mDateTime;
    }

    /**
     * Get date time as a formatted string
     * @param context The context is used to convert the string to user set locale
     * @return String containing the date and time of the creation of the note
     */
    public String getDateTimeFormatted(Context context) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"
                , context.getResources().getConfiguration().locale);
        formatter.setTimeZone(TimeZone.getDefault());
        return formatter.format(new Date(mDateTime));
    }

    public String getTitle() {
        return mTitle;
    }

    public String getContent() {
        return mContent;
    }
}
