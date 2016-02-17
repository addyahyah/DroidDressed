package edu.rosehulman.harrislb.droiddressed;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by harrislb on 2/7/2016.
 */
public class PreviewOutfitFragment extends Fragment {
    //private ArticleCallback mCallback;
    //private ArticleAdapter mAdapter;

    //private static final ArrayList<Article> ARG_PREVIEW_ARTICLES_KEY = "CURRENT_CATEGORY_KEY";
    //private String mCurrentCategoryKey;
    private ArrayList<Article> previewArticles;
    private ArrayList<String> previewArticleIDs;
    private ComplexOutfit newOutfit;

    public PreviewOutfitFragment(){}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
           // mCurrentCategoryKey = getArguments().getString(ARG_CURRENT_CATEGORY_KEY);
        }
        newOutfit = new ComplexOutfit();
        previewArticles = ((MainActivity) getActivity()).getPreviewArticles();
        previewArticleIDs = ((MainActivity) getActivity()).getPreviewArticleIDs();
        System.out.println("size of prev articles is: " + previewArticles.size());

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        CoordinatorLayout cl = (CoordinatorLayout) inflater.inflate(R.layout.activity_closet, container, false);
//
//        RecyclerView view = (RecyclerView) cl.findViewById(R.id.rec_view);
//        view.setLayoutManager(new LinearLayoutManager(getContext()));

//        Toolbar toolbar = (Toolbar) cl.findViewById(R.id.toolbar);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

//        view.setHasFixedSize(true);

        LinearLayout cl = (LinearLayout) inflater.inflate(R.layout.fragment_preview_outfit, container, false);
        FloatingActionButton fab = (FloatingActionButton) cl.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddOutfitDialog();
            }
        });



        for(int i = 0; i<previewArticles.size();i++){
            newOutfit.addArticle(previewArticles.get(i));

            ImageView articlePic = new ImageView(this.getActivity());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(300, 400);
            layoutParams.gravity= Gravity.CENTER;
            articlePic.setLayoutParams(layoutParams);
            articlePic.setMaxHeight(40);
            articlePic.setMaxWidth(40);

            new GetImageTask(articlePic).execute(previewArticles.get(i).getURL());
            cl.addView(articlePic);
        }



        return cl;
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if(context instanceof ArticleCallback) {
//            mCallback = (ArticleCallback) context;
//        } else{
//            throw new RuntimeException(context.toString() + " must implement Callback");
//        }
//    }

    public static PreviewOutfitFragment newInstance(){
        System.out.println("ArticleListFragment's newInstance is calledd");
        PreviewOutfitFragment fragment = new PreviewOutfitFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_CURRENT_CATEGORY_KEY, currentCategoryKey);
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mCallback = null;
//    }





    private void showAddOutfitDialog() {
        DialogFragment df = new DialogFragment() {
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getString(R.string.fragment_preview_add_outfit_question));
                View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_save_complex_outfit, null, false);
                builder.setView(view);

                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Firebase mArticleRef = new Firebase(Constants.COMPLEX_OUTFITS_PATH);

                        Firebase ref = mArticleRef.push();
                        // Add the category to the categorys path
                        //ref.setValue(new Article(articleURL, mUid));
                        ref.setValue(new ComplexOutfit(SharedPreferencesUtils.getCurrentOutfitCategoryKey(getActivity()),previewArticleIDs));
                        System.out.println("****HERE " + SharedPreferencesUtils.getCurrentOutfitCategoryKey(getActivity()));
                        // Add the category to the owners path
                        Map<String, Object> map = new HashMap<>();
                        map.put(ref.getKey(), true);
                        // See https://www.firebase.com/docs/android/guide/saving-data.html for this method.
                        Firebase mOutfitCategoryRef = new Firebase(Constants.OUTFIT_CATEGORY_PATH);
                        mOutfitCategoryRef.child(OutfitCategory.COMPLEX_OUTFITS).updateChildren(map);

                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((MainActivity) getActivity()).resetPreviewArticles();
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment_container, new ClosetFragment());
                        ft.addToBackStack("preview_fragment");
                        ft.commit();
                    }
                });

                return builder.create();
            }
        };
        //used to say getSupportFragmentManager(), "add");
        df.show(getFragmentManager(), "add");
    }

}
