package edu.rosehulman.harrislb.droiddressed;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

/**
 * Created by harrislb on 1/25/2016.
 */

public class ComplexOutfitAdapter extends RecyclerView.Adapter<ComplexOutfitAdapter.ViewHolder> {

    private String mCatID;
    private Firebase mComplexOutfitCategoryRef;
    private Firebase mComplexOutfitRef;
    private ArrayList<ComplexOutfit> mComplexOutfits = new ArrayList<>();
    private ComplexOutfitListFragment mOutfitListFragment;

    public ComplexOutfitAdapter(ComplexOutfitListFragment outfitListFragment) {
        Log.d(Constants.TAG, "ComplexOutfitAdapter adding CategoryValueListener");
        mOutfitListFragment = outfitListFragment;

        mCatID = SharedPreferencesUtils.getCurrentOutfitCategoryKey(outfitListFragment.getContext());
        Log.d(Constants.TAG, "Current outfitCategory: " + mCatID);

        assert (!mCatID.isEmpty()); // Consider: use if (BuildConfig.DEBUG)

        mComplexOutfitRef = new Firebase(Constants.OUTFITS_PATH);
        // Deep query. Find the categorys owned by me
        Query query = mComplexOutfitRef.orderByChild("complexOutfitCategories/" + mCatID).equalTo(true);
        query.addChildEventListener(new OutfitChildEventListener());

        // This is so that a new category can be pushed to the onwers path as well.
        mComplexOutfitCategoryRef = new Firebase(Constants.OUTFIT_CATEGORY_PATH + "/" + mCatID);


        //added for displaying complex
        // query.addChildEventListener(new ComplexOutfitChildEventListener());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_row_view, parent, false);
        //mCategorySelectedListener.isPreviewButtonVisibleCategoryFragment();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

//            for(int i = 0; i<mComplexOutfits.getArticles().size();i++){
//            ImageView imageView = new ImageView(mOutfitListFragment.getActivity());
//            new GetImageTask(imageView).execute(mComplexOutfits.getArticles().get(i).getURL());
//
//        }
        

    }

    public void firebasePush(String outfitURL) {
        System.out.println("firebase push called");
        System.out.println("current outfit category is: " + SharedPreferencesUtils.getCurrentOutfitCategoryKey(mOutfitListFragment.getContext()));
        System.out.println("mCATID is: " + mCatID);
        // Create a new auto-ID for an article in the articles path
        Firebase ref = mComplexOutfitRef.push();
        // Add the category to the categorys path
        //ref.setValue(new Article(articleURL, mUid));
        ref.setValue(new Outfit(outfitURL, mCatID));
        // Add the category to the owners path
        Map<String, Object> map = new HashMap<>();
        map.put(ref.getKey(), true);
        // See https://www.firebase.com/docs/android/guide/saving-data.html for this method.
        mComplexOutfitCategoryRef.child(OutfitCategory.OUTFITS).updateChildren(map);

    }

    public void firebaseEdit(Outfit outfit, String newOutfitName) {
        // Since there is only 1 editable field, we set it directly by tunneling down the path 1 more level.
        Firebase outfitNameRef = new Firebase(Constants.OUTFITS_PATH + "/" + outfit.getKey() + "/" + Outfit.OUTFIT_NAME);
        outfitNameRef.setValue(newOutfitName);
    }

    // Where is firebaseRemove? It is a Utils method since removing a category cascades to every table in the Firebase.


    @Override
    public int getItemCount() {
        return mComplexOutfits.size() + mComplexOutfits.size();
    }

    class OutfitChildEventListener implements ChildEventListener {
        // While we don't push up deletes, we need to listen for other owners deleting our category.

        private void add(DataSnapshot dataSnapshot) {
            ComplexOutfit outfit = dataSnapshot.getValue(ComplexOutfit.class);
            outfit.setKey(dataSnapshot.getKey());
            mComplexOutfits.add(outfit);

        }

        private int remove(String key) {
            for (ComplexOutfit outfit : mComplexOutfits) {
                if (outfit.getKey().equals(key)) {
                    int foundPos = mComplexOutfits.indexOf(outfit);
                    mComplexOutfits.remove(outfit);
                    return foundPos;
                }
            }
            return -1;
        }


        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Log.d(Constants.TAG, "My outfit: " + dataSnapshot);
            add(dataSnapshot);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            remove(dataSnapshot.getKey());
            add(dataSnapshot);
            notifyDataSetChanged();
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            int position = remove(dataSnapshot.getKey());
            if (position >= 0) {
                notifyItemRemoved(position);
            }
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            // empty
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {
            Log.e("TAG", "onCancelled. Error: " + firebaseError.getMessage());

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private ImageView mOutfitImageView;
        private ImageView mOutfitImageView2;

        public ViewHolder(View itemView) {
            super(itemView);
            mOutfitImageView = (ImageView) itemView.findViewById(R.id.url_image_article);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            SharedPreferencesUtils.setCurrentOutfitKey(mOutfitListFragment.getContext(), mComplexOutfits.get(getAdapterPosition()).getKey());
            ComplexOutfit outfit = mComplexOutfits.get(getAdapterPosition());


        }

        @Override
        public boolean onLongClick(View v) {
//            Article article = mComplexOutfits.get(getAdapterPosition());
//            mArticleListFragment.showCategoryDialog(category);
            return true;
        }
    }




}

