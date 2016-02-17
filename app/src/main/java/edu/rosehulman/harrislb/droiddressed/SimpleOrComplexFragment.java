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
public class SimpleOrComplexFragment extends Fragment {

    private Button simpleButton;
    private Button complexButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.fragment_closet, container, false);
        simpleButton = (Button) view.findViewById(R.id.outfit_button);
        complexButton = (Button) view.findViewById(R.id.articles_button);

        simpleButton.setText("Simple Outfits");
        complexButton.setText("Complex Outfits");
        simpleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                OutfitCategoryListFragment fragment = new OutfitCategoryListFragment();
                ft.replace(R.id.fragment_container, fragment);
                ft.addToBackStack("simple outfit list fragment");
                ft.commit();

            }
        });

        complexButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ComplexOutfitCategoryListFragment fragment = new ComplexOutfitCategoryListFragment();
                ft.replace(R.id.fragment_container, fragment);
                ft.addToBackStack("complex outfit list fragment");
                ft.commit();

            }
        });


        return view;
    }


}