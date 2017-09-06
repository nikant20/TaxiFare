package me.nikantchaudhary.taxifare;

import android.net.Uri;


public class Utils {

    // Symbolic Constants

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "rate.db";

    public static final String TAB_NAME = "DayRate";
    public static final String BASE_RATE = "BASE_RATE";
    public static final String FARE_RATE = "FARE_RATE";
    public static final String TIME_RATE = "TIME_RATE";
    public static final String CREATE_TAB_QUERY = "create table DayRate(" +
            "BASE_RATE text," +
            "FARE_RATE text," +
            "TIME_RATE text," +
            ")";

    public static final Uri USER_URI = Uri.parse("content://me.nikantchaudhary.taxifare.usercontentprovider/"+TAB_NAME);

    public static final String KEY_USER = "keyUser";
}
