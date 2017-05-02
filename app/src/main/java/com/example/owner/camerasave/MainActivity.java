package com.example.owner.camerasave;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    Button takePictureButton;
    public static final int CAMERA_REQUEST = 10;
    public static int pictureNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        takePictureButton = (Button) findViewById(R.id.takePictureButton);
    }

    public void pictureOnClick(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureName = getPictureName();
        File imageFile = new File(pictureDirectory, pictureName);
        Uri pictureUri = FileProvider.getUriForFile(getApplicationContext(), "com.example.owner.camerasave", imageFile);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }


    public String getPictureName() {

        pictureNumber++;
        String pictureName = Integer.toString(pictureNumber);

        return pictureName;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Did the user choose OK?  If so, the code inside these curly braces will execute.
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                // we are hearing back from the camera.
                //  Bitmap cameraImage = (Bitmap) data.getExtras().get("data");
                // at this point, we have the image from the camera.
                //imgSpecimenPhoto.setImageBitmap(cameraImage);
            }
        }
    }
}

