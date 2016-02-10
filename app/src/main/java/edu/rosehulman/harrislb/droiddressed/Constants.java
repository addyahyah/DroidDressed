package edu.rosehulman.harrislb.droiddressed;

/**
 * Created by harrislb on 1/25/2016.
 */
public class Constants {
    public static final String TAG = "FDD";
    public static final String FIREBASE_URL = "https://harrislb-droiddressed.firebaseio.com/";


    public static final String PREFS = "PREFS";
    public static final String UID_KEY = "UID_KEY";
    public static final String CATEGORY_KEY = "CATEGORY_KEY";
    public static final String CATEGORY_PATH = FIREBASE_URL + "/categories";
    public static final String OWNERS_PATH = FIREBASE_URL + "/owners";
    public static final String ARTICLES_PATH = FIREBASE_URL + "/articles";

    //IMGUR constantts below
        /*
      Logging flag
     */
    public static final boolean LOGGING = false;

    /*
      Your imgur client id. You need this to upload to imgur.

      More here: https://api.imgur.com/
     */
    public static final String MY_IMGUR_CLIENT_ID = "58d730c77c21854";
    public static final String MY_IMGUR_CLIENT_SECRET = "18fccc194234e7272a0542d020e56f6d7d83ec6e";

    /*
      Redirect URL for android.
     */
    public static final String MY_IMGUR_REDIRECT_URL = "http://android";

    /*
      Client Auth
     */
    public static String getClientAuth() {
        return "Client-ID " + MY_IMGUR_CLIENT_ID;
    }


}
