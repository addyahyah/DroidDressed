package edu.rosehulman.harrislb.droiddressed;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
/**
 * Created by harrislb on 1/25/2016.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder>{

    private List<Article> mArticles;
    private ArticleListFragment.ArticleCallback mCallback;
    private static final String PICS_PATH = "https://harrsilb-droiddressed.firebaseio.com/articlePics";
    private Firebase mFirebase;
    private Context context;
    private String oldCategory = "";
    private String oldURL = "";

    public ArticleAdapter(ArticleListFragment.ArticleCallback callback, Context context) {

        mCallback = callback;
        mArticles = new ArrayList<>();
        this.context = context;
        Firebase.setAndroidContext(context);
        mFirebase = new Firebase(PICS_PATH);
        mFirebase.addChildEventListener(new PicsChildEventListener());

    }

    class PicsChildEventListener implements ChildEventListener {

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Article pic = dataSnapshot.getValue(Article.class);
            pic.setKey(dataSnapshot.getKey());
            mArticles.add(0, pic);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            String key = dataSnapshot.getKey();
            Article newOutfitPic = dataSnapshot.getValue(Article.class);
            for(Article op : mArticles) {
                if (op.getKey().equals(key)) {
                    op.setValues(newOutfitPic);
                    break;
                }
            }
            notifyDataSetChanged();
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            String key = dataSnapshot.getKey();
            for (Article op : mArticles) {
                if (key.equals(op.getKey())) {
                    mArticles.remove(op);
                    notifyDataSetChanged();
                    break;
                }
            }
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_row_view, parent, false);
        return new ViewHolder(view);
    }




    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Article article = mArticles.get(position);
        holder.mCategoryTextView.setText(article.getCategory());
        //holder.mURLTextView.setText(outfitPic.getUrl());

        new GetImageTask(holder.mURLImageView).execute(mArticles.get(position).getUrl());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onArticleSelected(mArticles.get(position));
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showEditDialog(mArticles.get(position));
                return true;
            }
        });

    }

    public void remove(Article article) {
        mFirebase.child(article.getKey()).removeValue();
    }

    @Override
    public int getItemCount(){
        return mArticles.size();
    }

    public void add(Article article) {
        mFirebase.push().setValue(article);

    }

    public void update(Article article, String newCategory, String newURL){
        article.setCategory(newCategory);
        article.setUrl(newURL);
        mFirebase.child(article.getKey()).setValue(article);
    }

    private void showEditDialog(final Article article) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(article == null ? R.string.dialog_add_title : R.string.dialog_edit_title));
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit, null, false);
        builder.setView(view);
        final EditText categoryEditText = (EditText) view.findViewById(R.id.dialog_edit_caption_text);
        final EditText urlEditText = (EditText) view.findViewById(R.id.dialog_edit_url_text);
        // pre-populate
        categoryEditText.setText(article.getCategory());
        urlEditText.setText(article.getUrl());
        oldCategory = categoryEditText.getText().toString();
        oldURL = urlEditText.getText().toString();

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // empty
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // empty
            }

            @Override
            public void afterTextChanged(Editable s) {
                String caption = categoryEditText.getText().toString();
                String url = urlEditText.getText().toString();
                //if no url is entered, get a random one for testing
                if(url.equals("")){
                    url = Util.randomArticleUrl();
                }
                update(article, caption, url);
            }
        };

        categoryEditText.addTextChangedListener(textWatcher);
        urlEditText.addTextChangedListener(textWatcher);


        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (article == null) {
//                            String caption = captionEditText.getText().toString();
//                            String url = urlEditText.getText().toString();
//                            System.out.println("url is: " + url);
//                            if(url.equals("")){
//                                url = Util.randomImageUrl();
//                            }
//                            update(outfitPic, caption, url);
                    //notifyDataSetChanged();
                }
            }
        });
        builder.setNegativeButton(context.getString(R.string.delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                remove(article);

            }
        });
        builder.setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                update(article, oldCategory, oldURL);
            }
        });

        AlertDialog alertDialog = builder.create();
        //used to say getSupportFragmentManager(), "add");
        alertDialog.show();


    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mCategoryTextView;
        private TextView mURLTextView;
        private ImageView mURLImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    mCallback.onArticleSelected(mArticles.get(getAdapterPosition()));

                }
            });
            mCategoryTextView = (TextView) itemView.findViewById(R.id.category_text_article);
            //mURLTextView = (TextView) itemView.findViewById(R.id.url_text);
             mURLImageView = (ImageView) itemView.findViewById(R.id.url_image_article);
        }
    }

}
