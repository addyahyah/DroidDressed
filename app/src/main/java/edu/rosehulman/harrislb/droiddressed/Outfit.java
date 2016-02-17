package edu.rosehulman.harrislb.droiddressed;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by harrislb on 2/1/2016.
 */

public class Outfit implements Comparable<Outfit>{

    public static final String OUTFIT_NAME = "outfit_name";
    public static final String OUTFIT_CATEGORIES = "outfit_categories";

    @JsonIgnore
    private String key;
    private String url;
    private Map<String, Boolean> outfitCategories;

    public Outfit() {

    }

    public Outfit(String url, String uid){
        this.url = url;
        outfitCategories = new HashMap<>();
        outfitCategories.put(uid, true);
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

    public Map<String, Boolean> getOutfitCategories(){
        return this.outfitCategories;
    }


    public int compareTo(Outfit another) {
        return url.compareTo(another.url);
    }

}


