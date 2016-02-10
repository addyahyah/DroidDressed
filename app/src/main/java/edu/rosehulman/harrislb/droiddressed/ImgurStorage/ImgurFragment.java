package edu.rosehulman.harrislb.droiddressed.ImgurStorage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.io.File;

import edu.rosehulman.harrislb.droiddressed.GetImageTask;
import edu.rosehulman.harrislb.droiddressed.R;

/**
 * Created by harrislb on 2/9/2016.
 */
public class ImgurFragment extends Fragment{
    private ImageView uploadImage;
    private EditText uploadTitle;
    private EditText uploadDesc;
    private Toolbar toolbar;


    private Upload upload;
    private File chosenFile;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_imgur_select, container, false);
        uploadImage = (ImageView) view.findViewById(R.id.imgur_imageview);
        uploadTitle = (EditText) view.findViewById(R.id.editText_upload_title);
        uploadDesc = (EditText) view.findViewById(R.id.editText_upload_desc);


        return view;
    }
}



