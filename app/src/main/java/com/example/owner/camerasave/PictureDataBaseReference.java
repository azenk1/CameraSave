package com.example.owner.camerasave;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Owner on 5/10/2017.
 */

public class PictureDataBaseReference
{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference pictureRef = database.getReference("message");

}
