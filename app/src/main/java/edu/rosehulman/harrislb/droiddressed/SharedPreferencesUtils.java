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

    public static String getCurrentCategoryKey(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.PREFS, Activity.MODE_PRIVATE);
        return prefs.getString(Constants.CATEGORY_KEY, "");
    }




    public static void setCurrentCategoryKey(Context context, String categoryKey) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.PREFS, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.CATEGORY_KEY, categoryKey);
        editor.commit();
    }
    public static String getCurrentOutfitCategoryKey(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.PREFS, Activity.MODE_PRIVATE);
        return prefs.getString(Constants.OUTFIT_CATEGORY_KEY, "");
    }



//    public static void setCurrentComplexOutfitCategoryKey(Context context, String outfitCategoryKey) {
//        SharedPreferences prefs = context.getSharedPreferences(Constants.PREFS, Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString(Constants.COMPLEX_OUTFIT_CATEGORY_KEY, outfitCategoryKey);
//        editor.commit();
//    }
    public static void setCurrentOutfitCategoryKey(Context context, String outfitCategoryKey) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.PREFS, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.OUTFIT_CATEGORY_KEY, outfitCategoryKey);
        editor.commit();
    }

    public static void setCurrentArticleKey(Context context, String articleKey) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.PREFS, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.ARTICLE_KEY, articleKey);
        editor.commit();
    }

    public static void setCurrentOutfitKey(Context context, String outfitKey) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.PREFS, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.OUTFIT_KEY, outfitKey);
        editor.commit();
    }

    public static void removeCurrentCourseKey(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.PREFS, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(Constants.CATEGORY_KEY);
        editor.apply();
    }
}