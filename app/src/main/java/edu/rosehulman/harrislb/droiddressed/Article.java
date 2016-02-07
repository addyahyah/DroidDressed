package edu.rosehulman.harrislb.droiddressed;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by harrislb on 2/1/2016.
 */
public class Article {
    public static final String CATEGORY_KEY = "categoryKey";

    private String url;
    private String category;

    @JsonIgnore
    private String key;

    public Article(){}

    public Article(String category, String url) {
        this.url = url;
        this.category = category;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public void setKey(String key){
        this.key = key;
    }

    public void setCategory(String cat){
        this.category = cat;
    }

    public String getKey(){
        return this.key;
    }
    public String getCategory(){
        return this.category;
    }

    public String getUrl(){
        return this.url;
    }

    public void setValues(Article newArticle){
        this.category = newArticle.category;
        this.url = newArticle.url;
    }
}
