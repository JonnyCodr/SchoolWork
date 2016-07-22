package com.example.spotr;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

//comment
public class SinglePhotoViewActivity extends ActionBarActivity {

    private static final String LOG_TAG = "Simple Camera App";
    private static int TAKE_PICTURE = 1001;
    private ImageView imageView;
    private Uri imageUri;

    private TextView textView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_photo_view);
        takePhoto();
    }

    public void takePhoto(){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo = new File(Environment
                .getExternalStoragePublicDirectory(Environment
                        //this will continually overwrite the same file
                        //need to implement a date/ time string
                        //to save as file name.
                        .DIRECTORY_PICTURES), "pic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 1001:
                if(resultCode == Activity.RESULT_OK) {
                    getContentResolver().notifyChange(imageUri, null);
                    imageView = (ImageView )findViewById(R.id.ivCameraImageView);
                    ContentResolver contentResolver = getContentResolver();
                    Bitmap bitmap;
                    try {
                        bitmap = android.provider.MediaStore.Images.Media.getBitmap(contentResolver, imageUri);
                        imageView.setImageBitmap(bitmap);
                    } catch(Exception e) {
                        Toast.makeText(SinglePhotoViewActivity.this, "failed to load", Toast.LENGTH_LONG).show();
                        Log.e(LOG_TAG, e.toString());
                    }
                }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.single_photo_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
