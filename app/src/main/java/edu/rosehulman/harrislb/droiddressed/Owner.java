package edu.rosehulman.harrislb.droiddressed;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Map;

/**
 * Created by harrislb on 2/6/2016.
 */
public class Owner implements Comparable<Owner> {


    public static final String USERNAME = "username";
    public static final String CATEGORIES = "categories";

    @JsonIgnore
    private String key;

    private String username;
    private Map<String, Boolean> categories;

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

    public void setCategories(Map<String, Boolean> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return username;
    }

    public boolean containsCategory(String categoryKey) {
        return categories != null && categories.containsKey(categoryKey);
    }

    @Override
    public int compareTo(Owner another) {
        return username.compareTo(another.username);
    }

}
