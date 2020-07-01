package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Media {
    public String baseURL;

    // empty constructor needed by the Parceler library
    public Media() {}

    // Creates USer object from JSONObject parameter
    public static Media fromJson(JSONObject jsonObject) throws JSONException {
        Media media = new Media();
        media.baseURL = jsonObject.getString("media_url_https");

        return media;
    }

    // Gets the JSONArray of media JSONObjects
    public static List<Media> getMedia(JSONObject extended_entities) throws JSONException {
        Log.i("Tweet", "GOT EXTENDED ENTITIES");
        JSONArray media = extended_entities.getJSONArray("media");

        List<Media> medias = new ArrayList<>();

        for(int i = 0; i < media.length(); i++){
            medias.add(fromJson(media.getJSONObject(i)));
        }

        return medias;
    }
}
