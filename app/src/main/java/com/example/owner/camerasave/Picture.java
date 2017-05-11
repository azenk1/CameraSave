package com.example.owner.camerasave;

/**
 * Created by Owner on 5/10/2017.
 */

public class Picture
{
    private String location;
    private String name;

    Picture()
    {

    }

    Picture(String location, String name)
    {
        this.location = location;
        this.name = name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getName()
    {
        return name;
    }

    public String getLocation()
    {
        return location;
    }
}
