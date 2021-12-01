package com.example.cs492final.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Versions implements Serializable {
    private ArrayList<String> versions;

    public Versions() {
        this.versions = null;
    }

    public Versions(ArrayList<String> versions) {
        this.versions = versions;
    }

    public String getLatestVersion() {
        if(this.versions != null) {
            return this.versions.get(0);
        } else {
            return null;
        }
    }

    public static class JsonDeserializer implements com.google.gson.JsonDeserializer<Versions> {
        @Override
        public Versions deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonArray list = json.getAsJsonArray();
            ArrayList<String> versions = new ArrayList<>();

            for(int i = 0; i < list.size(); i++) {
                versions.add(list.get(i).getAsString());
            }
            return new Versions(versions);
        }
    }
}
