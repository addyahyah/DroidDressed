package edu.rosehulman.harrislb.droiddressed;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import edu.rosehulman.harrislb.droiddressed.ImgurStorage.ImageResponse;
import edu.rosehulman.harrislb.droiddressed.ImgurStorage.ImgurActivity;

/**
 * Created by harrislb on 2/1/2016.
 */

public class ArticleListFragment extends Fragment implements ImgurActivity.OnURLComputedListener{
    private ArticleAdapter mAdapter;

    private OnArticleSelectedListener mListener;
    private Toolbar mToolbar;
    Context context;

    public ArticleListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         context = getContext();




        CoordinatorLayout cl = (CoordinatorLayout) inflater.inflate(R.layout.activity_closet, container, false);
        RecyclerView view = (RecyclerView) cl.findViewById(R.id.rec_view);
        view.setLayoutManager(new LinearLayoutManager(getContext()));
        FloatingActionButton fab = (FloatingActionButton) cl.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
//                                      generate url method
//                                      showAddEditDialog(null);

                                       //imgur method
                                       Intent intent = new Intent(getActivity(), ImgurActivity.class);
                                       intent.putExtra("CURRENT_CATEGORY", SharedPreferencesUtils.getCurrentCategoryKey(context));
                                       startActivity(intent);
                                   }
                               });
        fab.setVisibility(View.VISIBLE);

//        mToolbar = ((GradeRecorderActivity) context).getToolbar();
//        Utils.getCurrentCategoryNameForToolbar(this);
//
//        mToolbar.setTitle(R.string.fragment_title_category);

//        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.category_recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//        recyclerView.setHasFixedSize(true);
//        registerForContextMenu(recyclerView);

        mAdapter = new ArticleAdapter(this, mListener);


        Button mPreviewOutfitButton = (Button) cl.findViewById(R.id.preview_button);
        mAdapter.setPreviewOutfitButton(mPreviewOutfitButton);
        if(mAdapter.getOnArticleSelectedListener().isPreviewButtonVisible()){
            mPreviewOutfitButton.setVisibility(View.VISIBLE);
        } else{
            mPreviewOutfitButton.setVisibility(View.INVISIBLE);
        }
       // mPreviewOutfitButton.setVisibility(View.INVISIBLE);
        mPreviewOutfitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                System.out.println("preview outfit clicked");
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, PreviewOutfitFragment.newInstance());
                ft.addToBackStack("preview_fragment");
                ft.commit();
            }

        });


        Bundle bundle = this.getArguments();
        if(bundle!=null){
            mAdapter.firebasePush(bundle.getString("UPLOAD_URL"));

        }
        view.setAdapter(mAdapter);

        //clear backstack
       while(getActivity().getFragmentManager().getBackStackEntryCount()!=0){

           getActivity().getFragmentManager().popBackStack();
       }

        return cl;
    }

//    @Override
//    public void setToolbarTitle(String categoryName) {
//        if (categoryName == null || categoryName.isEmpty()) {
//            mToolbar.setTitle(R.string.fragment_title_category);
//        } else {
//            mToolbar.setTitle("Selected " + categoryName);
//        }
//    }


    @SuppressLint("InflateParams")
    public void showArticleDialog(final Article article) {
        DialogFragment df = new DialogFragment() {
            @NonNull
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getString(article == null ? R.string.dialog_add_title : R.string.dialog_edit_title));

                View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add, null);
                builder.setView(view);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton(android.R.string.cancel, null);
                if (article != null) {

                }
                return builder.create();
            }
        };
        df.show(getActivity().getSupportFragmentManager(), "addedit");
    }

    private void showDeleteConfirmationDialog(final Article article) {
        DialogFragment df = new DialogFragment() {
            @Override
            @NonNull
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getString(R.string.remove_question_format));
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Util.removeArticle(getActivity(), article);
                        dismiss();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, null);
                return builder.create();
            }
        };
        df.show(getActivity().getSupportFragmentManager(), "confirm");
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnArticleSelectedListener) {
            mListener = (OnArticleSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void OnURLComputed(String url) {
        mAdapter.firebasePush(url);

    }

    public interface OnArticleSelectedListener {
        boolean onArticleSelected(Article selectedArticle);
        boolean isPreviewButtonVisible();
    }

        public static ArticleListFragment newInstance(String currentCategoryKey){
        System.out.println("ArticleListFragment's newInstance is calledd");
        ArticleListFragment fragment = new ArticleListFragment();
        return fragment;
    }



}


//old articleListFragment
//public class ArticleListFragment extends Fragment {
//
//    private ArticleCallback mCallback;
//    private ArticleAdapter mAdapter;
//
//    private static final String ARG_CURRENT_CATEGORY_KEY = "CURRENT_CATEGORY_KEY";
//    private String mCurrentCategoryKey;
//
//
//    public ArticleListFragment(){}
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        System.out.println("OnCreate called in ArticleListFrag");
//        super.onCreate(savedInstanceState);
//
//
//    }
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        mCurrentCategoryKey = getArguments().getString(ARG_CURRENT_CATEGORY_KEY);
//        System.out.println("Set catKey in onCreateView of articleListFrag and is: " + mCurrentCategoryKey);
//        // mAdapter = new edu.rosehulman.harrislb.weatherpics.WeatherpicAdapter(this, this);
//        CoordinatorLayout cl = (CoordinatorLayout) inflater.inflate(R.layout.activity_closet, container, false);
//
//        // RecyclerView view = (RecyclerView) inflater.inflate(R.layout.list_fragment, container, false);
//        RecyclerView view = (RecyclerView) cl.findViewById(R.id.rec_view);
//        view.setLayoutManager(new LinearLayoutManager(getContext()));
//
////        Toolbar toolbar = (Toolbar) cl.findViewById(R.id.toolbar);
////        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
//
//        view.setHasFixedSize(true);
//        FloatingActionButton fab = (FloatingActionButton) cl.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //generate url method
//                //showAddEditDialog(null);
//
//                //imgur method
//                Intent intent = new Intent(getActivity(), ImgurActivity.class);
//                intent.putExtra("CURRENT_CATEGORY", mCurrentCategoryKey);
//                startActivity(intent);
//            }
//        });
//
//        Button mPreviewOutfitButton = (Button) cl.findViewById(R.id.preview_button);
//        mCallback.isPreviewButtonVisible();
//       // mPreviewOutfitButton.setVisibility(View.INVISIBLE);
//        mPreviewOutfitButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                System.out.println("preview outfit clicked");
//                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.fragment_container, PreviewOutfitFragment.newInstance());
//                ft.addToBackStack("preview_fragment");
//                ft.commit();
//            }
//
//        });
//        mAdapter = new ArticleAdapter(mCallback, getContext(), mPreviewOutfitButton, mCurrentCategoryKey);
//        view.setAdapter(mAdapter);
//
//        //for imgur
//        String url =  getArguments().getString("UPLOAD_URL");
//        System.out.println("LINK IS: " + url);
//        String catFromImgur = getArguments().getString("CURRENT_CATEGORY");
//        if(url!=null && catFromImgur!=null){
//            System.out.println("Adding link" + url + "to adapter!");
//            mAdapter.setMCurrentCategoryKey(catFromImgur);
//            mAdapter.add(url);
//            mAdapter.notifyDataSetChanged();
//        }
//
//
//
//        return cl;
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if(context instanceof ArticleCallback) {
//            mCallback = (ArticleCallback) context;
//        } else{
//            throw new RuntimeException(context.toString() + " must implement Callback");
//        }
//    }
//
//    public static ArticleListFragment newInstance(String currentCategoryKey){
//        System.out.println("ArticleListFragment's newInstance is calledd");
//        ArticleListFragment fragment = new ArticleListFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_CURRENT_CATEGORY_KEY, currentCategoryKey);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mCallback = null;
//    }
//
//
//
//
//    public interface ArticleCallback {
//        boolean onArticleSelected(Article article);
//        boolean isPreviewButtonVisible();
//    }
//
//    private void showAddEditDialog(final Article article) {
//        DialogFragment df = new DialogFragment() {
//            @Override
//            public Dialog onCreateDialog(Bundle savedInstanceState) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setTitle(getString(article == null ? R.string.dialog_add_title : R.string.dialog_edit_title));
//                View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add, null, false);
//                builder.setView(view);
//                final EditText captionEditText = (EditText) view.findViewById(R.id.dialog_add_caption_text);
//                final EditText urlEditText = (EditText) view.findViewById(R.id.dialog_add_url_text);
////                if (article != null) {
////                    // pre-populate
////                    captionEditText.setText(article.getCategoryKey());
////                    urlEditText.setText(article.getUrl());
////
////                    TextWatcher textWatcher = new TextWatcher() {
////                        @Override
////                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////                            // empty
////                        }
////
////                        @Override
////                        public void onTextChanged(CharSequence s, int start, int before, int count) {
////                            // empty
////                        }
////
////                        @Override
////                        public void afterTextChanged(Editable s) {
////                            String caption = captionEditText.getText().toString();
////                            String url = urlEditText.getText().toString();
////                            //if no url is entered, get a random one for testing
////                            System.out.println("url is: " + url);
////                            if(url == ""){
////                                url = Util.randomArticleUrl();
////                            }
////                            mAdapter.update(article, caption, url);
////                        }
////                    };
////
////                    captionEditText.addTextChangedListener(textWatcher);
////                    urlEditText.addTextChangedListener(textWatcher);
////                }
//
//                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (article == null) {
//                            String caption = captionEditText.getText().toString();
//                            String url = urlEditText.getText().toString();
//                            System.out.println("url is: " + url);
//                            if(url.equals("")){
//                                url = Util.randomArticleUrl();
//                            }
//                           // mAdapter.add(new Article(caption, url));
//                            mAdapter.add(url);
//                            mAdapter.notifyDataSetChanged();
//                        }
//                    }
//                });
//                builder.setNegativeButton(android.R.string.cancel, null);
//
//                return builder.create();
//            }
//        };
//        //used to say getSupportFragmentManager(), "add");
//        df.show(getFragmentManager(), "add");
//    }
//
//    public interface OnArticleSelectedListener {
//        void onArticleSelected(Article selectedArticle);
//    }
//
////    public interface OnArticleSelectedListener {
////        boolean onArticleSelected(Article article);
////    }
//}
