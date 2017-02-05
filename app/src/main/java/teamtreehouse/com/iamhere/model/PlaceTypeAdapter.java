package teamtreehouse.com.iamhere.model;


import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anurag Goyal
 * Copyright (C) 2017 Apple, Inc. All rights reserved.
 */
public class PlaceTypeAdapter extends TypeAdapter<List<Place>> {

    @Override
    public void write(JsonWriter out, List<Place> value) throws IOException {

    }

    @Override
    public List<Place> read(JsonReader in) throws IOException {
        List<Place> places = new ArrayList<>();

        in.beginArray();
        while (in.hasNext()){
            places.add(readPlaceData(in));
        }
        in.endArray();
        return places;
    }

    private Place readPlaceData(JsonReader in) throws IOException{

        Place place = new Place();

        if (JsonToken.NULL == in.peek()) {
            in.nextNull();
            return place;
        }

        in.beginObject();
        while (in.hasNext()) {
            final String fieldName = in.nextName();

            if("icon".equals(fieldName)){
                place.setIconUrl(nextOptionalString(in));
            } else if("name".equals(fieldName)){
                place.setTitle(nextOptionalString(in));
            } else if("vicinity".equals(fieldName)){
                place.setAddress(nextOptionalString(in));
            }
        }
        in.endObject();
        return place;
    }

    public String nextOptionalString(JsonReader in) throws IOException {
        if (JsonToken.NULL.equals(in.peek())) {
            in.nextNull();
            return null;
        }
        return in.nextString();
    }
}
