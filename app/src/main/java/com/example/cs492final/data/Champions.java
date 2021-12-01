package com.example.cs492final.data;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Champions implements Serializable {
    private static final String TAG = Champions.class.getSimpleName();
    private List<Champion> champions;

    public Champions() {
        this.champions = null;
    }

    public Champions(List<Champion> champions) {
        this.champions = champions;
    }

    public List<Champion> getChampions() {
        return champions;
    }

    public void printNames() {
        for(Champion champion : champions) {
            Log.d(TAG, champion.getName());
        }
    }

    public List<ChampionWTags> toChampWithTags() {
        List<ChampionWTags> withTags = new ArrayList<>();
        ChampionWTags championWTags = null;
        String previousName = null;
        for(Champion champion : this.champions) {
            if(champion.getName().equals(previousName)) {
                championWTags.addTag(champion.getTag());
            } else {
                if(championWTags != null) {
                    withTags.add(championWTags);
                }
                championWTags = new ChampionWTags(champion);
            }
            previousName = champion.getName();
        }
        withTags.add(championWTags);
        return withTags;
    }

    public static class JsonDeserializer implements com.google.gson.JsonDeserializer<Champions> {
        @Override
        public Champions deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject dataObj = json.getAsJsonObject();
            Set<String> keySet = dataObj.keySet();
            List<String> listKeySet = new ArrayList<>(keySet);
            List<Champion> champions = new ArrayList<>();

            Log.d(TAG, "dataObj: " + dataObj);
            Log.d(TAG, "size: " + dataObj.size());
            for(String key : listKeySet) {
                JsonObject champObj = dataObj.getAsJsonObject(key);
                JsonArray tags = champObj.getAsJsonArray("tags");
                for(int i = 0; i < tags.size(); i++) {
                    champions.add(new Champion(champObj, tags.get(i).getAsString()));
                    Log.d(TAG, "Champobj: " + champObj);
                }
            }
            return new Champions(champions);
        }
    }
}