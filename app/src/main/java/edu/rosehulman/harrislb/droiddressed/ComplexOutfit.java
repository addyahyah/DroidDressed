package edu.rosehulman.harrislb.droiddressed;

import java.util.ArrayList;

/**
 * Created by harrislb on 2/1/2016.
 */
public class ComplexOutfit {

    String category;
    ArrayList<Article> articles = new ArrayList<>();

    public ComplexOutfit(){}

    public ComplexOutfit(String category, ArrayList<Article> articles){
        this.category = category;
        this.articles = articles;
    }

    public void setCategory(String cat){
        this.category = cat;
    }

    public String getCategory(){
        return this.category;
    }

    public ArrayList<Article> getArticles(){
        return articles;
    }

    public void addArticle(Article a){
        this.articles.add(a);
    }


}
