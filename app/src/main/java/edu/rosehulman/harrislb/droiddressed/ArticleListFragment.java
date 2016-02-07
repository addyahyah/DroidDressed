package edu.rosehulman.harrislb.droiddressed;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by harrislb on 2/1/2016.
 */
public class ArticleListFragment extends Fragment {

    private ArticleCallback mCallback;
    private ArticleAdapter mAdapter;

    private static final String ARG_CURRENT_CATEGORY_KEY = "CURRENT_CATEGORY_KEY";
    private String mCurrentCategoryKey;

    public ArticleListFragment(){}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            mCurrentCategoryKey = getArguments().getString(ARG_CURRENT_CATEGORY_KEY);

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // mAdapter = new edu.rosehulman.harrislb.weatherpics.WeatherpicAdapter(this, this);
        CoordinatorLayout cl = (CoordinatorLayout) inflater.inflate(R.layout.activity_closet, container, false);

        // RecyclerView view = (RecyclerView) inflater.inflate(R.layout.list_fragment, container, false);
        RecyclerView view = (RecyclerView) cl.findViewById(R.id.rec_view);
        view.setLayoutManager(new LinearLayoutManager(getContext()));

//        Toolbar toolbar = (Toolbar) cl.findViewById(R.id.toolbar);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        view.setHasFixedSize(true);
        FloatingActionButton fab = (FloatingActionButton) cl.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddEditDialog(null);
            }
        });

        Button mPreviewOutfitButton = (Button) cl.findViewById(R.id.preview_button);
        mPreviewOutfitButton.setVisibility(View.INVISIBLE);

        mAdapter = new ArticleAdapter(mCallback, getContext(), mPreviewOutfitButton, mCurrentCategoryKey);
        view.setAdapter(mAdapter);



        return cl;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ArticleCallback) {
            mCallback = (ArticleCallback) context;
        } else{
            throw new RuntimeException(context.toString() + " must implement Callback");
        }
    }

    public static ArticleListFragment newInstance(String currentCategoryKey){
        ArticleListFragment fragment = new ArticleListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CURRENT_CATEGORY_KEY, currentCategoryKey);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }




    public interface ArticleCallback {
        void onArticleSelected(Article article);

    }

    private void showAddEditDialog(final Article article) {
        DialogFragment df = new DialogFragment() {
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getString(article == null ? R.string.dialog_add_title : R.string.dialog_edit_title));
                View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add, null, false);
                builder.setView(view);
                final EditText captionEditText = (EditText) view.findViewById(R.id.dialog_add_caption_text);
                final EditText urlEditText = (EditText) view.findViewById(R.id.dialog_add_url_text);
                if (article != null) {
                    // pre-populate
                    captionEditText.setText(article.getCategory());
                    urlEditText.setText(article.getUrl());

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
                            String caption = captionEditText.getText().toString();
                            String url = urlEditText.getText().toString();
                            //if no url is entered, get a random one for testing
                            System.out.println("url is: " + url);
                            if(url == ""){
                                url = Util.randomArticleUrl();
                            }
                            mAdapter.update(article, caption, url);
                        }
                    };

                    captionEditText.addTextChangedListener(textWatcher);
                    urlEditText.addTextChangedListener(textWatcher);
                }

                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (article == null) {
                            String caption = captionEditText.getText().toString();
                            String url = urlEditText.getText().toString();
                            System.out.println("url is: " + url);
                            if(url.equals("")){
                                url = Util.randomArticleUrl();
                            }
                            mAdapter.add(new Article(caption, url));
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, null);

                return builder.create();
            }
        };
        //used to say getSupportFragmentManager(), "add");
        df.show(getFragmentManager(), "add");
    }

    public interface OnArticleSelectedListener {
        void onArticleSelected(Article article);
    }
}
