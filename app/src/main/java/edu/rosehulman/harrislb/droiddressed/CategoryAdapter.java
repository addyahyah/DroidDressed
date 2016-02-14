package edu.rosehulman.harrislb.droiddressed;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final CategoryListFragment mCategoryListFragment;

    private final CategoryListFragment.OnCategorySelectedListener mCategorySelectedListener;
    private String mUid;
    private Firebase mOwnerRef;
    private Firebase mCategoriesRef;
    private ArrayList<Category> mCategories = new ArrayList<>();
    private Button mPreviewOutfitButton;

    public CategoryAdapter(CategoryListFragment categoryListFragment, CategoryListFragment.OnCategorySelectedListener listener, Button previewButton) {
        Log.d(Constants.TAG, "CategoryAdapter adding OwnerValueListener");
        mPreviewOutfitButton = previewButton;
        mCategoryListFragment = categoryListFragment;
        mCategorySelectedListener = listener;

        mUid = SharedPreferencesUtils.getCurrentUser(categoryListFragment.getContext());
        Log.d(Constants.TAG, "Current user: " + mUid);

        assert (!mUid.isEmpty()); // Consider: use if (BuildConfig.DEBUG)

        mCategoriesRef = new Firebase(Constants.CATEGORY_PATH);
        // Deep query. Find the categorys owned by me
        Query query = mCategoriesRef.orderByChild("owners/" + mUid).equalTo(true);
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
        holder.mCategoryNameTextView.setText(mCategories.get(position).getCategoryName());
    }

    public void firebasePush(String categoryName) {
        // Create a new auto-ID for a category in the categorys path
        Firebase ref = mCategoriesRef.push();
        // Add the category to the categorys path
        ref.setValue(new Category(categoryName, mUid));

        // Add the category to the owners path
        Map<String, Object> map = new HashMap<>();
        map.put(ref.getKey(), true);
        // See https://www.firebase.com/docs/android/guide/saving-data.html for this method.
        mOwnerRef.child(Owner.CATEGORIES).updateChildren(map);
    }

    public void firebaseEdit(Category category, String newCategoryName) {
        // Since there is only 1 editable field, we set it directly by tunneling down the path 1 more level.
        Firebase categoryNameRef = new Firebase(Constants.CATEGORY_PATH + "/" + category.getKey() + "/" + Category.CATEGORY_NAME);
        categoryNameRef.setValue(newCategoryName);
    }

    // Where is firebaseRemove? It is a Utils method since removing a category cascades to every table in the Firebase.


    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    class CategorysChildEventListener implements ChildEventListener {
        // While we don't push up deletes, we need to listen for other owners deleting our category.

        private void add(DataSnapshot dataSnapshot) {
            Category category = dataSnapshot.getValue(Category.class);
            category.setKey(dataSnapshot.getKey());
            mCategories.add(category);
            Collections.sort(mCategories);
        }

        private int remove(String key) {
            for (Category category : mCategories) {
                if (category.getKey().equals(key)) {
                    int foundPos = mCategories.indexOf(category);
                    mCategories.remove(category);
                    return foundPos;
                }
            }
            return -1;
        }


        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Log.d(Constants.TAG, "My category: " + dataSnapshot);
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
            SharedPreferencesUtils.setCurrentCategoryKey(mCategoryListFragment.getContext(), mCategories.get(getAdapterPosition()).getKey());
            Category category = mCategories.get(getAdapterPosition());
            mCategorySelectedListener.onCategorySelected(category);

        }

        @Override
        public boolean onLongClick(View v) {
            Category category = mCategories.get(getAdapterPosition());
            mCategoryListFragment.showCategoryDialog(category);
            return true;
        }
    }

}
