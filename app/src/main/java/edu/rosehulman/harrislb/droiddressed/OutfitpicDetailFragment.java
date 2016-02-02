package edu.rosehulman.harrislb.droiddressed;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by harrislb on 1/25/2016.
 */
public class OutfitpicDetailFragment extends Fragment {

    private static final String ARG_PIC = "pic";
    private OutfitPic mPic;

    public OutfitpicDetailFragment() {

    }

    public static OutfitpicDetailFragment newInstance(OutfitPic pic) {
        OutfitpicDetailFragment fragment = new OutfitpicDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PIC, pic);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            mPic = getArguments().getParcelable(ARG_PIC);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        TextView captionText = (TextView)view.findViewById(R.id.detail_caption);
        captionText.setText(mPic.getCategory());
        ImageView urlImg = (ImageView)view.findViewById(R.id.detail_img);
        new GetImageTask(urlImg).execute(mPic.getUrl());
        return view;
    }
}


