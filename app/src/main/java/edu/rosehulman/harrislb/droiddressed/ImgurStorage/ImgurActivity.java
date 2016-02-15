package edu.rosehulman.harrislb.droiddressed.ImgurStorage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.rosehulman.harrislb.droiddressed.ImgurStorage.DocumentHelper;
import edu.rosehulman.harrislb.droiddressed.ImgurStorage.ImageResponse;
import edu.rosehulman.harrislb.droiddressed.ImgurStorage.IntentHelper;
import edu.rosehulman.harrislb.droiddressed.ImgurStorage.Upload;
import edu.rosehulman.harrislb.droiddressed.ImgurStorage.UploadService;
import edu.rosehulman.harrislb.droiddressed.MainActivity;
import edu.rosehulman.harrislb.droiddressed.R;
import edu.rosehulman.harrislb.droiddressed.Util;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ImgurActivity extends AppCompatActivity implements Callback<ImageResponse>{
    public final static String TAG = ImgurActivity.class.getSimpleName();

    private String url;
    private String currentCat;
    /*
      These annotations are for ButterKnife by Jake Wharton
      https://github.com/JakeWharton/butterknife
     */
    @Bind(R.id.imgur_imageview)
    ImageView uploadImage;
    @Bind(R.id.editText_upload_title)
    EditText uploadTitle;
    @Bind(R.id.editText_upload_desc)
    EditText uploadDesc;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private Upload upload; // Upload object containging image and meta data
    private File chosenFile; //chosen file from intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_imgur_select);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        currentCat = intent.getStringExtra("CURRENT_CATEGORY");
        System.out.println("currentCat in imgur activity is "+ currentCat);

        //setSupportActionBar(toolbar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri returnUri;

        if (requestCode != IntentHelper.FILE_PICK) {
            return;
        }

        if (resultCode != RESULT_OK) {
            return;
        }

        returnUri = data.getData();
        String filePath = DocumentHelper.getPath(this, returnUri);
        //Safety check to prevent null pointer exception
        if (filePath == null || filePath.isEmpty()) return;
        chosenFile = new File(filePath);

                /*
                    Picasso is a wonderful image loading tool from square inc.
                    https://github.com/square/picasso
                 */
        Picasso.with(getBaseContext())
                .load(chosenFile)
                .placeholder(R.drawable.ic_photo_library_black)
                .fit()
                .into(uploadImage);

    }


    @OnClick(R.id.imgur_imageview)
    public void onChooseImage() {
        uploadDesc.clearFocus();
        uploadTitle.clearFocus();
        IntentHelper.chooseFileIntent(this);
    }

    private void clearInput() {
        uploadTitle.setText("");
        uploadDesc.clearFocus();
        uploadDesc.setText("");
        uploadTitle.clearFocus();
        uploadImage.setImageResource(R.drawable.ic_photo_library_black);
    }

    @OnClick(R.id.fab)
    public void uploadImage() {
    /*
      Create the @Upload object
     */
        if (chosenFile == null) return;
        createUpload(chosenFile);

    /*
      Start upload
     */
      //  new UploadService(this).Execute(upload, new UiCallback());
        new UploadService(this).Execute(upload, this);


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ImgurActivity.this, MainActivity.class);
        intent.putExtra("Check", "1");
        intent.putExtra("UPLOAD_URL", url);
        startActivity(intent);
    }

    private void createUpload(File image) {
        upload = new Upload();

        upload.image = image;
        upload.title = uploadTitle.getText().toString();
        upload.description = uploadDesc.getText().toString();
    }

    public void setURL(String url){
        this.url = url;
    }

  //  private class UiCallback implements Callback<ImageResponse> {

        @Override
        public void success(ImageResponse imageResponse, Response response) {
            setURL(imageResponse.data.link);

            Intent intent = new Intent(ImgurActivity.this, MainActivity.class);
            intent.putExtra("UPLOAD_URL", url);
            System.out.println("Intent stored");
            intent.putExtra("Check", "1");


            //this.onBackPressed();
            intent.putExtra("Check", "1");
          //  intent.putExtra("UPLOAD_URL", url);

            intent.putExtra("CURRENT_CATEGORY", currentCat);

            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
            finish();


            clearInput();
        }

        @Override
        public void failure(RetrofitError error) {
            //Assume we have no connection, since error is null
            if (error == null) {
                Snackbar.make(findViewById(R.id.rootView), "No internet connection", Snackbar.LENGTH_SHORT).show();
            }
        }
    //}

    public interface OnURLComputedListener {
        void OnURLComputed(String url);
    }
}
