package edu.rosehulman.harrislb.droiddressed;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by harrislb on 2/1/2016.
 */

public class Article implements Comparable<Article>{

    public static final String ARTICLE_NAME = "article_name";
    public static final String CATEGORIES = "categories";

    @JsonIgnore
    private String key;
    private String url;
    private Map<String, Boolean> categories;
    private Map<String, Boolean> outfits;

    public Article() {

    }

    public Article(String url, String uid){
        this.url = url;
        categories = new HashMap<>();
        categories.put(uid, true);
    }


    public void setURL(String url){
        this.url = url;
    }

    public String getURL(){
        return this.url;
    }


    public void setKey(String key){
        this.key = key;
    }

    public String getKey(){
        return this.key;
    }

    public Map<String, Boolean> getCategories(){
        return this.categories;
    }

    public Map<String, Boolean> getOutfits() { return this.outfits;}

    public int compareTo(Article another) {
        return url.compareTo(another.url);
    }

}



//public class Article implements Comparable<Article> {
//    public static final String CATEGORY_KEY = "categoryKey";
//
//    private String url;
//    private String categoryKey;
//
//    private Map<String, Boolean> categories;
//
//
//
//    @JsonIgnore
//    private String key;
//
//    public Article(){}
//
//    public Article(String categoryKey, String url) {
//        this.url = url;
//        this.categoryKey = categoryKey;
//    }
//
//    public void setUrl(String url){
//        this.url = url;
//    }
//
//    public void setKey(String key){
//        this.key = key;
//    }
//
//    public void setCategoryKey(String cat){
//        this.categoryKey = cat;
//    }
//
//    public String getKey(){
//        return this.key;
//    }
//    public String getCategoryKey(){
//        return this.categoryKey;
//    }
//
//    public String getUrl(){
//        return this.url;
//    }
//
//    public void setValues(Article newArticle){
//        this.categoryKey = newArticle.categoryKey;
//        this.url = newArticle.url;
//    }
//
//    //TODO is this the best thing to compare??
//    @Override
//    public int compareTo(Article another) {
//        return key.compareTo(another.key);
//    }
//}
