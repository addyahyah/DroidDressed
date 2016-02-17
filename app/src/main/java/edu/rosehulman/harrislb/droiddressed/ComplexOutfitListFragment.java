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

public class ComplexOutfitListFragment extends Fragment implements ImgurActivity.OnURLComputedListener{
    private ComplexOutfitAdapter mAdapter;

    Context context;

    public ComplexOutfitListFragment() {
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

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                //ft.replace(R.id.fragment_container, new OutfitFromFragment());
                ft.replace(R.id.fragment_container, new ArticleListFragment());

                ft.addToBackStack("outfit_from_fragment");
                ft.commit();

            }
        });
        fab.setVisibility(View.VISIBLE);


        mAdapter = new ComplexOutfitAdapter(this);

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
    public void showArticleDialog(final Outfit outfit) {
        DialogFragment df = new DialogFragment() {
            @NonNull
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getString(outfit == null ? R.string.dialog_add_title : R.string.dialog_edit_title));

                View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add, null);
                builder.setView(view);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton(android.R.string.cancel, null);
                if (outfit != null) {

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
    public void OnURLComputed(String url) {
        mAdapter.firebasePush(url);

    }


    public static OutfitListFragment newInstance(String currentOutfitCategoryKey){
        System.out.println("ComplexOutfitListFragment's newInstance is calledd");
        OutfitListFragment fragment = new OutfitListFragment();
        return fragment;
    }



}
