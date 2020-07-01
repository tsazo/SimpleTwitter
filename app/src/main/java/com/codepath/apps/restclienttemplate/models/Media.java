package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

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
}
