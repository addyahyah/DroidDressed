package edu.rosehulman.harrislb.droiddressed;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by harrislb on 2/6/2016.
 */
public class ComplexOutfitCategoryAdapter extends RecyclerView.Adapter<ComplexOutfitCategoryAdapter.ViewHolder> {

    private final ComplexOutfitCategoryListFragment mOutfitCategoryListFragment;

    private final ComplexOutfitCategoryListFragment.OnOutfitCategorySelectedListener mOutfitCategorySelectedListener;
    private String mUid;
    private Firebase mOwnerRef;
    private Firebase mOutfitCategoriesRef;
    private ArrayList<OutfitCategory> mOutfitCategories = new ArrayList<>();

    public ComplexOutfitCategoryAdapter(ComplexOutfitCategoryListFragment outfitCategoryListFragment, ComplexOutfitCategoryListFragment.OnOutfitCategorySelectedListener listener) {
        Log.d(Constants.TAG, "OutfitCategoryAdapter adding OwnerValueListener");
        mOutfitCategoryListFragment = outfitCategoryListFragment;
        mOutfitCategorySelectedListener = listener;

        mUid = SharedPreferencesUtils.getCurrentUser(outfitCategoryListFragment.getContext());
        Log.d(Constants.TAG, "Current user: " + mUid);

        assert (!mUid.isEmpty()); // Consider: use if (BuildConfig.DEBUG)

        mOutfitCategoriesRef = new Firebase(Constants.COMPLEX_OUTFIT_CATEGORY_PATH);
        // Deep query. Find the categorys owned by me
        Query query = mOutfitCategoriesRef.orderByChild("owners/" + mUid).equalTo(true);
        query.addChildEventListener(new CategorysChildEventListener());

        // This is so that a new category can be pushed to the onwers path as well.
        mOwnerRef = new Firebase(Constants.OWNERS_PATH + "/" + mUid);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_category, parent, false);
        //mCategorySelectedListener.isPreviewButtonVisibleCategoryFragment();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mCategoryNameTextView.setText(mOutfitCategories.get(position).getCategoryName());
    }

    public void firebasePush(String categoryName) {
        // Create a new auto-ID for a category in the categorys path
        Firebase ref = mOutfitCategoriesRef.push();
        // Add the category to the categorys path
        ref.setValue(new OutfitCategory(categoryName, mUid));

        // Add the category to the owners path
        Map<String, Object> map = new HashMap<>();
        map.put(ref.getKey(), true);
        // See https://www.firebase.com/docs/android/guide/saving-data.html for this method.
        mOwnerRef.child(Owner.OUTFIT_CATEGORIES).updateChildren(map);
    }

    public void firebaseEdit(OutfitCategory category, String newOutfitCategoryName) {
        // Since there is only 1 editable field, we set it directly by tunneling down the path 1 more level.
        Firebase outfitCategoryNameRef = new Firebase(Constants.OUTFIT_CATEGORY_PATH + "/" + category.getKey() + "/" + OutfitCategory.CATEGORY_NAME);
        outfitCategoryNameRef.setValue(newOutfitCategoryName);
    }

    // Where is firebaseRemove? It is a Utils method since removing a category cascades to every table in the Firebase.


    @Override
    public int getItemCount() {
        return mOutfitCategories.size();
    }

    class CategorysChildEventListener implements ChildEventListener {
        // While we don't push up deletes, we need to listen for other owners deleting our category.

        private void add(DataSnapshot dataSnapshot) {
            OutfitCategory category = dataSnapshot.getValue(OutfitCategory.class);
            category.setKey(dataSnapshot.getKey());
            mOutfitCategories.add(category);
            Collections.sort(mOutfitCategories);
        }

        private int remove(String key) {
            for (OutfitCategory category : mOutfitCategories) {
                if (category.getKey().equals(key)) {
                    int foundPos = mOutfitCategories.indexOf(category);
                    mOutfitCategories.remove(category);
                    return foundPos;
                }
            }
            return -1;
        }


        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Log.d(Constants.TAG, "My outfitCategory: " + dataSnapshot);
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
        private TextView mCategoryNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mCategoryNameTextView = (TextView) itemView.findViewById(R.id.text_view);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            SharedPreferencesUtils.setCurrentOutfitCategoryKey(mOutfitCategoryListFragment.getContext(), mOutfitCategories.get(getAdapterPosition()).getKey());
            OutfitCategory category = mOutfitCategories.get(getAdapterPosition());
            FragmentTransaction ft = mOutfitCategoryListFragment.getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container,new ComplexOutfitListFragment());
            ft.addToBackStack("outfitListFragment");
            ft.commit();

        }

        @Override
        public boolean onLongClick(View v) {
            OutfitCategory category = mOutfitCategories.get(getAdapterPosition());
            mOutfitCategoryListFragment.showCategoryDialog(category);
            return true;
        }
    }

}
