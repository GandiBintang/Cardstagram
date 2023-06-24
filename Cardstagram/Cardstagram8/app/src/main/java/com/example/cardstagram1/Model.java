package com.example.cardstagram1;

import android.net.Uri;

import java.io.Serializable;
import java.net.URI;

public class Model implements Serializable{

    private String image;
    private String title;
    private String desc;

    public Model(String image, String title, String desc) {
        this.image = image;
        this.title = title;
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
