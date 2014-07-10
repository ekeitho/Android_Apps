package com.ekeitho.habits;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

/*
 * An extension of ParseObject that makes
 * it more convenient to access information
 * about a given Meal 
 */

@ParseClassName("Habit")
public class Habit extends ParseObject {

    public Habit() {
        // A default constructor is required.
    }

    /* getters */
    public String getHabit() {
        return getString("habit");
    }

    public ParseUser getAuthor() {
        return getParseUser("author");
    }

    public ParseFile getPhotoFile() {
        return getParseFile("photo");
    }

    public String getEmotion() {
        return getString("emotion");
    }

    public String getTime() {
        return getString("time");
    }

    public String getPerson() {
        return getString("person");
    }

    public String getLocation() {
        return getString("location");
    }

    public String getAction() {
        return getString("action");
    }

    /* setters */
    public void setAuthor(ParseUser user) {
        put("author", user);
    }

    public void setHabit(String habit) {
        put("habit", habit);
    }

    public void setEmotion(String emotion) {
        put("emotion", emotion);
    }

    public void setPerson(String person) {
        put("person", person);
    }

    public void setLocation(String location) {
        put("location", location);
    }

    public void setTime(String time) {
        put("time", time);
    }

    public void setAction(String action) {
        put("action", action);
    }

    public void setPhotoFile(ParseFile file) {
        put("photo", file);
    }

}
