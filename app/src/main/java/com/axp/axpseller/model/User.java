package com.axp.axpseller.model;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by YY on 2017/11/17.
 */
public class User implements Serializable {
   private String name;
   private String icon;

    public User(String name, String icon, String id) {
        this.name = name;
        this.icon = icon;
        this.id = id;
    }

    public User() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    private String id;
}
