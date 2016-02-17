package edu.rosehulman.harrislb.droiddressed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import edu.rosehulman.harrislb.droiddressed.ImgurStorage.ImgurActivity;

/**
 * Created by harrislb on 1/25/2016.
 */
public class OutfitFromFragment extends Fragment {

    private Button photosButton;
    private Button articlesButton;
    private OutfitFromCallback mCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.fragment_closet, container, false);
        photosButton = (Button) view.findViewById(R.id.outfit_button);
        articlesButton = (Button) view.findViewById(R.id.articles_button);

        photosButton.setText("From Photos");
        articlesButton.setText("From Articles");
        photosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mCallback.onOutfitButtonSelected();
                    mCallback.onPhotosButtonSelected();
            }
        });

        articlesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                CategoryListFragment fragment = new CategoryListFragment();
                ft.replace(R.id.fragment_container, fragment);
                ft.addToBackStack("article categories");
                ft.commit();

            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OutfitFromCallback) {
            mCallback = (OutfitFromCallback) context;
        } else{
            throw new RuntimeException(context.toString() + " must implement Callback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    public interface OutfitFromCallback{
        void onPhotosButtonSelected();
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//
//        outfitButton = (Button) findViewById(R.id.outfit_button);
//        articlesButton = (Button) findViewById(R.id.articles_button);
//
//    }
}
