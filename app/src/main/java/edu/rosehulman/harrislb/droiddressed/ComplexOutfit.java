package edu.rosehulman.harrislb.droiddressed;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by harrislb on 2/1/2016.
 */

public class ComplexOutfit{

    public static final String OUTFIT_NAME = "outfit_name";
    public static final String OUTFIT_CATEGORIES = "outfit_categories";

    ArrayList<Article> articleArray = new ArrayList<Article>();

    @JsonIgnore
    private String key;
    private Map<String, Boolean> outfitCategories;
    private Map<String, Boolean> articles;

    public ComplexOutfit() {

    }

    public ComplexOutfit(String uid, ArrayList<String> articleIDs){
        outfitCategories = new HashMap<>();
        outfitCategories.put(uid, true);

        articles = new HashMap<>();
        for(int i = 0; i<articleIDs.size();i++){
            articles.put(articleIDs.get(i), true);
        }
    }

    public void addArticle(Article a){
        articleArray.add(a);
        if(articles == null) {
            articles = new HashMap<String, Boolean>();
        }
        articles.put(a.getKey(),true);
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


  //  public int compareTo(ComplexOutfit another) {
//        return articles.compareTo(another.articles);
//    }

}




//package edu.rosehulman.harrislb.droiddressed;
//
//import java.util.ArrayList;
//
///**
// * Created by harrislb on 2/1/2016.
// */
//public class ComplexOutfit {
//
//    String category;
//    ArrayList<Article> articles = new ArrayList<>();
//
//    public ComplexOutfit(){}
//
//    public ComplexOutfit(String category, ArrayList<Article> articles){
//        this.category = category;
//        this.articles = articles;
//    }
//
//    public void setCategory(String cat){
//        this.category = cat;
//    }
//
//    public String getCategory(){
//        return this.category;
//    }
//
//    public ArrayList<Article> getArticles(){
//        return articles;
//    }
//
//    public void addArticle(Article a){
//        this.articles.add(a);
//    }
//
//
//}
