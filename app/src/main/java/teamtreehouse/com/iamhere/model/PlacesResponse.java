package teamtreehouse.com.iamhere.model;


import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Anurag Goyal
 * Copyright (C) 2017 Apple, Inc. All rights reserved.
 */
public class PlacesResponse {

    @SerializedName("results")
    public List<Place> places;


}
