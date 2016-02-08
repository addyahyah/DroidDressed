package edu.rosehulman.harrislb.droiddressed;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by harrislb on 2/6/2016.
 */
public class CategoryListFragment extends Fragment {
    private CategoryAdapter mAdapter;

    private OnCategorySelectedListener mListener;
    private Toolbar mToolbar;

    public CategoryListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Context context = getContext();

       // View rootView = inflater.inflate(R.layout.fragment_category_list, container, false);

//        FloatingActionButton fab = ((GradeRecorderActivity) context).getFab();
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showCategoryDialog(null);
//            }
//        });
//        fab.setVisibility(View.VISIBLE);
        CoordinatorLayout cl = (CoordinatorLayout) inflater.inflate(R.layout.activity_closet, container, false);
        RecyclerView view = (RecyclerView) cl.findViewById(R.id.rec_view);
        view.setLayoutManager(new LinearLayoutManager(getContext()));
        FloatingActionButton fab = (FloatingActionButton) cl.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCategoryDialog(null);
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

        Button mPreviewOutfitButton = (Button) cl.findViewById(R.id.preview_button);
       // mPreviewOutfitButton.setVisibility(View.INVISIBLE);
        mAdapter = new CategoryAdapter(this, mListener, mPreviewOutfitButton);
        view.setAdapter(mAdapter);

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
    public void showCategoryDialog(final Category category) {
        DialogFragment df = new DialogFragment() {
            @NonNull
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getString(category == null ? R.string.dialog_category_add_title : R.string.dialog_category_edit_title));

                View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_category, null);
                builder.setView(view);
                final EditText categoryNameEditText = (EditText) view.findViewById(R.id.dialog_add_data);
                categoryNameEditText.setHint(R.string.dialog_category_hint);
                if (category != null) {
                    categoryNameEditText.setText(category.getCategoryName());
                }
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String categoryName = categoryNameEditText.getText().toString();
                        if (category == null) {
                            mAdapter.firebasePush(categoryName);
                        } else {
                            mAdapter.firebaseEdit(category, categoryName);
                        }
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, null);
                if (category != null) {
                    builder.setNeutralButton(R.string.remove, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showDeleteConfirmationDialog(category);
                        }
                    });
                }
                return builder.create();
            }
        };
        df.show(getActivity().getSupportFragmentManager(), "addedit");
    }

    private void showDeleteConfirmationDialog(final Category category) {
        DialogFragment df = new DialogFragment() {
            @Override
            @NonNull
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getString(R.string.remove_question_format, category.getCategoryName()));
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Util.removeCategory(getActivity(), category);
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
        if (context instanceof OnCategorySelectedListener) {
            mListener = (OnCategorySelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCategorySelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnCategorySelectedListener {
        void onCategorySelected(Category selectedCategory);
        boolean isPreviewButtonVisibleCategoryFragment();
    }
}
