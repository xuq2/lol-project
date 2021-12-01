package com.example.cs492final.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.JsonObject;

@Entity(primaryKeys = {"name","tag"}, tableName = "champions")
public class Champion {
    @NonNull
    private String name;
    private String title;
    private String blurb;
    @Embedded
    private ChampionInfo info;
    @NonNull
    private String tag;
    private String partype;
    @Embedded
    private ChampionStats stats;
    @ColumnInfo(name = "image_name")
    private String imageName;

    @Ignore
    public Champion() {
        this.name = null;
        this.title = null;
        this.blurb = null;
        this.info = null;
        this.tag = null;
        this.partype = null;
        this.stats = null;
    }

    public Champion(String name, String title, String blurb, ChampionInfo info, String tag,
                    String partype, ChampionStats stats) {
        this.name = name;
        this.title = title;
        this.blurb = blurb;
        this.info = info;
        this.tag = tag;
        this.partype = partype;
        this.stats = stats;
    }

    public Champion(JsonObject json, String tag) {
        this.name = json.getAsJsonPrimitive("name").getAsString();
        this.title = json.getAsJsonPrimitive("title").getAsString();
        this.blurb = json.getAsJsonPrimitive("blurb").getAsString();
        this.info = new ChampionInfo(json.getAsJsonObject("info"));
        this.tag = tag;
        this.partype = json.getAsJsonPrimitive("partype").getAsString();
        this.stats = new ChampionStats(json.getAsJsonObject("stats"));
        this.imageName = json.getAsJsonObject("image").getAsJsonPrimitive("full").getAsString();
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getBlurb() {
        return blurb;
    }

    public ChampionInfo getInfo() {
        return info;
    }

    public String getTag() {
        return tag;
    }

    public String getPartype() {
        return partype;
    }

    public ChampionStats getStats() {
        return stats;
    }

    public String getImageName() {
        return imageName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public void setInfo(ChampionInfo info) {
        this.info = info;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setPartype(String partype) {
        this.partype = partype;
    }

    public void setStats(ChampionStats stats) {
        this.stats = stats;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}