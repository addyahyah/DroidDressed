package edu.rosehulman.harrislb.droiddressed;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * Created by harrislb on 1/25/2016.
 */
public class OutfitPic implements Parcelable {

    private String category;
    private String url;

    @JsonIgnore
    private String key;

    public OutfitPic(){}

    public OutfitPic(String category, String url){
        this.category = category;
        this.url = url;
    }

    protected OutfitPic(Parcel in) {
        category = in.readString();
        url = in.readString();
        key = in.readString();
    }

    public static final Creator<OutfitPic> CREATOR = new Creator<OutfitPic>() {
        @Override
        public OutfitPic createFromParcel(Parcel in) {
            return new OutfitPic(in);
        }

        @Override
        public OutfitPic[] newArray(int size) {
            return new OutfitPic[size];
        }
    };

    public String getKey() {
        return key;
    }
    public void setKey(String key){ this.key = key;}

    public String getCategory() {return this.category;}

    public void setCategory(String c) { this.category=c;}

    public String getUrl(){return this.url;}

    public void setUrl(String u){ this.url = u;}

    public void setValues(OutfitPic newOutfitpic){
        this.category = newOutfitpic.category;
        this.url = newOutfitpic.url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.category);
        dest.writeString(this.url);
    }
}
