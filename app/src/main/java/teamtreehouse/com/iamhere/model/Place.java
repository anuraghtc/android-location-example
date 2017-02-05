package teamtreehouse.com.iamhere.model;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Anurag Goyal
 * Copyright (C) 2017 Apple, Inc. All rights reserved.
 */
public class Place {

    @SerializedName("name")
    String title;
    @SerializedName("vicinity")
    String address;
    @SerializedName("icon")
    String iconUrl;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIconUrl(String url) {
        this.iconUrl = url;
    }

    public String getTitle() {
        return title;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
