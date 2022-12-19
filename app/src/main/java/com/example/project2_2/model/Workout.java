package com.example.project2_2.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

/**
 * Restaurant POJO.
 */
@IgnoreExtraProperties
public class Workout {

    public static final String FIELD_DATE = "date";
    public static final String FIELD_TIME = "time";
    public static final String FIELD_DISTANCE = "distance";
    public static final String FIELD_RATING = "rating";
    public static final String FIELD_INDEX = "index";

    private String date;
    private int time;
    private int distance;
    private int rating;
    private int index;

    public Workout() {}

    public Workout(String date, int time, int distance, int rating, int index) {
        this.date = date;
        this.time = time;
        this.distance = distance;
        this.rating = rating;
        this.index = index;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }




}
