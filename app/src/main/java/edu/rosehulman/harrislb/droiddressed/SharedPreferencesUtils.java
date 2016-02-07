package edu.rosehulman.harrislb.droiddressed;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by harrislb on 2/6/2016.
 */
public class SharedPreferencesUtils {
    public static String getCurrentUser(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.PREFS, Activity.MODE_PRIVATE);
        return prefs.getString(Constants.UID_KEY, "");
    }

    public static void setCurrentUser(Context context, String uid) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.PREFS, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.UID_KEY, uid);
        editor.commit();
    }

    public static void removeCurrentUser(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.PREFS, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(Constants.UID_KEY);
        editor.apply();
    }

    public static String getCurrentCourseKey(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.PREFS, Activity.MODE_PRIVATE);
        return prefs.getString(Constants.CATEGORY_KEY, "");
    }

    public static void setCurrentCategoryKey(Context context, String categoryKey) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.PREFS, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.CATEGORY_KEY, categoryKey);
        editor.commit();
    }

    public static void removeCurrentCourseKey(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.PREFS, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(Constants.CATEGORY_KEY);
        editor.apply();
    }
}