package com.example.owner.camerasave;

/**
 * Author: Al Zenk
 * Date: 05/11/2017
 * Course: CIS3334
 * Instructor: Tom Gibbons
 * Description: Final project for mobile Apps. Shows use of Firebase authentication and database.
 * Features Intents, location services, toasts, multiple activities.
 */

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Owner on 5/10/2017.
 */

/**
 * Class: Picture
 * Description: Handles data related to Picture objects and also uploads this data to Firebase.
 * Seperates the data from the users end.
 */
public class Picture
{

    //Declarations
    private String location;
    private String name;

    //Firebase instance retrieved and reference to location set.
    private final FirebaseDatabase pictureDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference pictureRef = pictureDatabase.getReference("server/picture-album/cameraSave");

    //HashMap created to store picture data
    private Map<String, Picture> picturesMap = new HashMap<String, Picture>();

    //Tag used to help generate unique names for each picture for tagging in database.
    private final String PICTURE_TAG = "picture";

    /**
     * Constructor for picture object. Receives location and name parameters.
     * @param location
     * @param name
     */
    public Picture(String location, String name)
    {
        this.location = location;
        this.name = name;
    }

    /**
     * Setter for Picture object name
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Setter for Picture object location
     * @param location
     */
    public void setLocation(String location)
    {
        this.location = location;
    }

    /**
     * Returns name associated with instance of Picture object.
     * @return
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns location associated with instance of Picture object.
     * @return
     */
    public String getLocation()
    {
        return location;
    }


    /**
     * Utilizes the picturesMap HashMap to store data on Firebase.
     * @param location
     * @param name
     */
    public void pushPicture(String location, String name)
    {
        //User child.setValue to prevent overwrites.
        pictureRef.child(name + PICTURE_TAG).setValue(new Picture(location, name));
    }
}
