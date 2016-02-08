package edu.rosehulman.harrislb.droiddressed;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by harrislb on 2/1/2016.
 */
public class Article implements Comparable<Article> {
    public static final String CATEGORY_KEY = "categoryKey";

    private String url;
    private String categoryKey;

    @JsonIgnore
    private String key;

    public Article(){}

    public Article(String categoryKey, String url) {
        this.url = url;
        this.categoryKey = categoryKey;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public void setKey(String key){
        this.key = key;
    }

    public void setCategoryKey(String cat){
        this.categoryKey = cat;
    }

    public String getKey(){
        return this.key;
    }
    public String getCategoryKey(){
        return this.categoryKey;
    }

    public String getUrl(){
        return this.url;
    }

    public void setValues(Article newArticle){
        this.categoryKey = newArticle.categoryKey;
        this.url = newArticle.url;
    }

    //TODO is this the best thing to compare??
    @Override
    public int compareTo(Article another) {
        return key.compareTo(another.key);
    }
}
