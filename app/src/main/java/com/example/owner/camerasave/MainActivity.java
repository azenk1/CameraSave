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
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class: MainActivity
 * Description: Handles user authentication and allows user to utilize camera via Intent. User may
 * use this feature with or without logging in. Image saved to phone storage.
 */
public class MainActivity extends AppCompatActivity {


    //Finals. Request code for intents as well as tag for firebase.
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String TAG = "Firebase";
    private static final int CIS3334_REQUEST_CODE = 1001;

    //Declarations for Firebase authorization.
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //Widget Declarations
    Button takePictureButton;
    EditText emailET;
    EditText passwordET;

    //Declarations for photo storage
    String currentPhotoPath;
    Uri photoURI;


    /**
     * onCreate
     * Instantiates above declarations and initializes activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        takePictureButton = (Button) findViewById(R.id.takePictureButton);
        emailET = (EditText) findViewById(R.id.emailET);
        passwordET = (EditText) findViewById(R.id.passwordET);

        mAuth = FirebaseAuth.getInstance()  ;

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

            }
        };


    }

    /**
     * Method: pictureOnClick
     * Calls intent to open camera. Utilizes FileProvider to store image on phone storage
     * Calls createImageFile() to create name for image.
     * @param view
     */
    public void pictureOnClick(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.owner.camerasave",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

            }
        }
    }

    /**
     * Class signInWithEmailAndPassword method when user clicks sign in button
     * @param view
     */
    public void signInOnClick(View view) {
        signInWithEmailAndPassword(emailET.getText().toString(),
                passwordET.getText().toString());

    }

    /**
     * Calls createAccount method when user clicks create account button.
     * @param view
     */
    public void createUserOnClick(View view) {
        createAccount(emailET.getText().toString(),
                passwordET.getText().toString());
    }

    /**
     * Calls intent to open PictureActivity. This allows user to enter details about pictures.
     * @param view
     */
    public void detailsOnClick(View view) {
        Intent secondActivityIntent = new Intent(this, PictureActivity.class);
        startActivity(secondActivityIntent);
    }

    //Creates file name for picture and also determines path for file storage.
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    /**
     * Creates user account with supplied email and password params.
     * @param email
     * @param password
     */
    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {

                            /** Toast replaced by update to textViewStatus displaying status of authentication.
                             Toast.makeText(MainActivity.this, "Authentication failed.",
                             Toast.LENGTH_SHORT).show();
                             */
                            Toast.makeText(MainActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }

                        //If user is created successful display success status.
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, R.string.auth,
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    /**
     * Signs user in with provided email and password params
     * @param email
     * @param password
     */
    private void signInWithEmailAndPassword(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful())
                        {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(MainActivity.this, R.string.auth,
                                    Toast.LENGTH_SHORT).show();
                        }
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful())
                        {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(MainActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}

