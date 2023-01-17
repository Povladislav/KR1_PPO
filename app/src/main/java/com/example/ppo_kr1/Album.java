package com.example.ppo_kr1;

import android.net.Uri;

public class Album {
    private String name;
    private String group;
    private int year;
    private int drawable = -1;
    private Uri uri;
    private String pathToImage = "";
    private boolean isChecked = false;

    Album(String name, String group, int year,
          int drawable) {
        this.name = name;
        this.group = group;
        this.year = year;
        this.drawable = drawable;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public void setChecked(boolean selected) {
        isChecked = selected;
    }

    public void setPathToImage(String pathToImage) {
        this.pathToImage = pathToImage;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public int getYear() {
        return year;
    }

    public int getDrawable() {
        return drawable;
    }

    public Uri getUri() {
        return uri;
    }

    public boolean getChecked() {
        return isChecked;
    }

    public String getPathToImage() {
        return pathToImage;
    }
}