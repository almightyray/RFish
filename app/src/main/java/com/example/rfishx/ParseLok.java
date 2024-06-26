package com.example.rfishx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParseLok {
    public List<HashMap<String, String>> parse(JSONObject object) {
        JSONArray jPlaces = null;
        try {
            JSONArray jObject = new JSONArray();
            jPlaces = jObject.getJSONArray(Integer.parseInt("result"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlaces(jPlaces);
    }
    private List<HashMap<String, String>> getPlaces(JSONArray jPlaces) {
        int placesCount = jPlaces.length();
        List<HashMap<String, String>> placelist = new ArrayList<>();
        HashMap<String, String> place = null;
        for (int i=0; i < placesCount;i++) {
            try {
                place = (HashMap<String, String>) getPlaces(((JSONObject) jPlaces.get(i)).names());
                placelist.add(place);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return placelist;
    }
    private HashMap<String, String> getPlace(JSONObject jPlace) {
        HashMap<String, String> place = new HashMap<>();
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        try {
            //Extract nama
            if (!jPlace.isNull("name")) {
                placeName = jPlace.getString("name");
            }
            //Extract nama jalan
            if (!jPlace.isNull("vicinity")) {
                vicinity = jPlace.getString("vicinity");
            }
            latitude =
                    jPlace.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude =
                    jPlace.getJSONObject("geometry").getJSONObject("location").getString("lng");
            place.put("place_name", placeName);
            place.put("vicinity", vicinity);
            place.put("lat", latitude);
            place.put("lng", longitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return place;
    }
}
