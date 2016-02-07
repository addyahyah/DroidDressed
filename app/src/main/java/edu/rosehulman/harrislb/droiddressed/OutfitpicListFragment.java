package edu.rosehulman.harrislb.droiddressed;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by harrislb on 1/25/2016.
 */
public class OutfitpicListFragment extends Fragment {

    private Callback mCallback;
    private OutfitpicAdapter mAdapter;

    public OutfitpicListFragment(){}


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

        mAdapter = new OutfitpicAdapter(mCallback, getContext());
        view.setAdapter(mAdapter);
        return cl;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Callback) {
            mCallback = (Callback) context;
        } else{
            throw new RuntimeException(context.toString() + " must implement Callback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }




    public interface Callback {
        void onOutfitpicSelected(OutfitPic weatherPic);

    }

    private void showAddEditDialog(final OutfitPic weatherPic) {
        DialogFragment df = new DialogFragment() {
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getString(weatherPic == null ? R.string.dialog_add_title : R.string.dialog_edit_title));
                View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add, null, false);
                builder.setView(view);
                final EditText captionEditText = (EditText) view.findViewById(R.id.dialog_add_caption_text);
                final EditText urlEditText = (EditText) view.findViewById(R.id.dialog_add_url_text);
                if (weatherPic != null) {
                    // pre-populate
                    captionEditText.setText(weatherPic.getCategory());
                    urlEditText.setText(weatherPic.getUrl());

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
                                url = Util.randomImageUrl();
                            }
                            mAdapter.update(weatherPic, caption, url);
                        }
                    };

                    captionEditText.addTextChangedListener(textWatcher);
                    urlEditText.addTextChangedListener(textWatcher);
                }

                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (weatherPic == null) {
                            String caption = captionEditText.getText().toString();
                            String url = urlEditText.getText().toString();
                            System.out.println("url is: " + url);
                            if(url.equals("")){
                                url = Util.randomImageUrl();
                            }
                            mAdapter.add(new OutfitPic(caption, url));
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
}
