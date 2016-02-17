package edu.rosehulman.harrislb.droiddressed;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Map;

/**
 * Created by harrislb on 2/6/2016.
 */
public class Owner implements Comparable<Owner> {


    public static final String USERNAME = "username";
    public static final String CATEGORIES = "categories";
    public static final String OUTFIT_CATEGORIES = "outfitCategories";

    @JsonIgnore
    private String key;

    private String username;
    private Map<String, Boolean> categories;
    private Map<String, Boolean> outfitCategories;

    // Required by Firebase when deserializing json
    public Owner() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, Boolean> getCategories() {
        return categories;
    }

    public Map<String, Boolean> getOutfitCategories() { return outfitCategories;}

    public void setCategories(Map<String, Boolean> categories) {
        this.categories = categories;
    }

    public void setOutfitCategores(Map<String, Boolean> outfitCategories) {this.outfitCategories = categories;}

    @Override
    public String toString() {
        return username;
    }

    public boolean containsCategory(String categoryKey) {
        return categories != null && categories.containsKey(categoryKey);
    }

    public boolean containsOutfitCategory(String outfitCategoryKey) {
        return outfitCategories != null && outfitCategories.containsKey(outfitCategoryKey);
    }

    @Override
    public int compareTo(Owner another) {
        return username.compareTo(another.username);
    }

}
