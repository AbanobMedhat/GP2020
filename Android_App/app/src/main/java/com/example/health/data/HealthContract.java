package com.example.health.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class HealthContract {
    public static final String CONTENT_AUTHORITY = "com.example.health.data";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_Health = "care";

    /* Inner class that defines the table contents of the weather table */
    public static final class HealthEntry implements BaseColumns {

        /* The base CONTENT_URI used to query the Weather table from the content provider */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_Health)
                .build();

        /* Used internally as the name of our weather table. */
        public static final String TABLE_NAME = "health";
        public static final String NAME="name";
        public static final String AGE="age";
        public static final String SENSOR="sensor";
        public static final String FORM="form";

        public static Uri buildWeatherUriWithDate(long date) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Long.toString(date))
                    .build();
        }
    }
}
