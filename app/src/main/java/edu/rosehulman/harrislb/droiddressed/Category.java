package edu.rosehulman.harrislb.droiddressed;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by harrislb on 2/6/2016.
 */
public class Category implements Comparable<Category>{

    public static final String CATEGORY_NAME = "category_name";
    public static final String OWNERS = "owners";
    public static final String ARTICLES = "articles";

    @JsonIgnore
    private String key;
    private String categoryName;
    private Map<String, Boolean> owners;
    private Map<String, Boolean> articles;

    public Category() {

    }

    public Category(String name, String uid){
        this.categoryName = name;
        owners = new HashMap<>();
        owners.put(uid, true);
    }




    public void setCategoryName(String name){
        this.categoryName = name;
    }



    public String getCategoryName(){
        return this.categoryName;
    }


    public void setKey(String key){
        this.key = key;
    }

    public String getKey(){
        return this.key;
    }

    public Map<String, Boolean> getOwners(){
        return this.owners;
    }

    public Map<String, Boolean> getArticles() {return this.articles;}

    public int compareTo(Category another) {
        return categoryName.compareTo(another.categoryName);
    }

}


