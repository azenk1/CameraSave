package com.example.owner.camerasave;

/**
 * Author: Al Zenk
 * Date: 05/11/2017
 * Course: CIS3334
 * Instructor: Tom Gibbons
 * Description: Final project for mobile Apps. Shows use of Firebase authentication and database.
 * Features Intents, location services, toasts, multiple activities.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

/**
 * Class: PictureActivity
 * Description: Handles user interaction with activity_picture. Allows user to set details about
 * picture and then push said details to Firebase.
 */
public class PictureActivity extends AppCompatActivity {

    //Place declaration used in location services.
    Place place;

    //Final for place picker request
    public static final int PLACE_PICKER_REQUEST = 1001;

    //Declarations for widgets
    EditText locationEditText;
    EditText pictureNameEditText;

    /**
     * onCreate
     * Instantiates above declarations and initializes activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        locationEditText = (EditText) findViewById(R.id.locationEditText);
        pictureNameEditText = (EditText)findViewById(R.id.pictureNameET);
    }

    /**
     * locationOnClick method creates and calls intent for PlacePicker. Try-catch block attempts to
     * start activity
     * @param view
     */
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

    /**
     * Method creates picture object using user input. The instance of picture that has been created
     * is then used to call the pushPicture method of the Picture class. Location and name are passed
     * as params.
     * @param view
     */
    public void saveDetailsOnClick(View view)
    {

        //Create Picture object by passing String params set by user.
        Picture picture = new Picture(locationEditText.getText().toString(),
                pictureNameEditText.getText().toString());

        //call pushPicture method using instance of Picture to push picture to Firebase
        picture.pushPicture(picture.getLocation(),picture.getName());


        //Display Toast to let user know that picture has been saved.
        Toast.makeText(PictureActivity.this, R.string.save_toast,
                Toast.LENGTH_SHORT).show();

    }


    /**
     * If proper result is returned from intent the place picker will store the location in the place
     * variable and the location EditText will show the lattitude and longitude of the location.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                place = PlacePicker.getPlace(this, data);
                locationEditText.setText(place.getLatLng().toString());

                //Toast shown to user to let them know the location has been set.
                Toast.makeText(PictureActivity.this, R.string.location_toast,
                        Toast.LENGTH_SHORT).show();

            }
        }
    }
}
