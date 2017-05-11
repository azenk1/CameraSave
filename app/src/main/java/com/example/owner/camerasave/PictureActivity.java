package com.example.owner.camerasave;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class PictureActivity extends AppCompatActivity {


    Place place;
    public static final int PLACE_PICKER_REQUEST = 1001;
    EditText locationEditText;
    EditText pictureNameEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        locationEditText = (EditText) findViewById(R.id.locationEditText);
        pictureNameEditText = (EditText)findViewById(R.id.pictureNameET);



    }

    public void locationOnClick(View view)
    {
        PlacePicker.IntentBuilder locationBuilder = new PlacePicker.IntentBuilder();
        try{
            startActivityForResult(locationBuilder.build(PictureActivity.this), PLACE_PICKER_REQUEST);
        }catch(Exception e){
            Log.e("com.zenk", "Error with location service" + e.toString());
            place = null;
        }
    }
    public void saveDetailsOnClick(View view)
    {
        Picture picture = new Picture(locationEditText.getText().toString(),
                pictureNameEditText.getText().toString());

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                place = PlacePicker.getPlace(this, data);
                locationEditText.setText(place.getLatLng().toString());

            }
        }
    }
}
