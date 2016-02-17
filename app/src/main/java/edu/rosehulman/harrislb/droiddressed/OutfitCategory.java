package edu.rosehulman.harrislb.droiddressed;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by harrislb on 2/6/2016.
 */
public class OutfitCategory implements Comparable<OutfitCategory>{

    public static final String CATEGORY_NAME = "category_name";
    public static final String OWNERS = "owners";
    public static final String OUTFITS = "outfits";
    public static final String COMPLEX_OUTFITS = "complexOutfits";

    @JsonIgnore
    private String key;
    private String categoryName;
    private Map<String, Boolean> owners;
    private Map<String, Boolean> outfits;
    private Map<String, Boolean> complexOutfits;

    public OutfitCategory() {

    }

    public OutfitCategory(String name, String uid){
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

    public Map<String, Boolean> getOutfits() {return this.outfits;}

    public Map<String, Boolean> getComplexOutfits() { return this.complexOutfits;}
    public int compareTo(OutfitCategory another) {
        return categoryName.compareTo(another.categoryName);
    }

}


