package com.example.newsapp;

public class Item {
    private String title;
    private int imageResId;
    private String Desc;
    public Item(String title, int imageResId,String Desc) {
        this.title = title;
        this.imageResId = imageResId;
        this.Desc=Desc;
    }

    public String getTitle() {
        return title;
    }
    public String getDesc() {
        return Desc;
    }

    public int getImageResId() {
        return imageResId;
    }
}
