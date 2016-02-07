package edu.rosehulman.harrislb.droiddressed;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by harrislb on 1/25/2016.
 */
public class ClosetFragment extends Fragment {

    private Button outfitButton;
    private Button articlesButton;
    private ClosetCallback mCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.fragment_closet, container, false);
        outfitButton = (Button) view.findViewById(R.id.outfit_button);
        articlesButton = (Button) view.findViewById(R.id.articles_button);

        outfitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onOutfitButtonSelected();
            }
        });

        articlesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onArticlesButtonSelected();
            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ClosetCallback) {
            mCallback = (ClosetCallback) context;
        } else{
            throw new RuntimeException(context.toString() + " must implement Callback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    public interface ClosetCallback{
        void onOutfitButtonSelected();
        void onArticlesButtonSelected();
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
