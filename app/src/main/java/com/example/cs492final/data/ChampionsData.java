package com.example.cs492final.data;

import com.google.gson.annotations.SerializedName;

public class ChampionsData {
    @SerializedName("data")
    private Champions data;

    public ChampionsData() {
        this.data = null;
    }

    public ChampionsData(Champions data) {
        this.data = data;
    }

    public Champions getData() {
        return data;
    }

    public void printNames() { this.data.printNames(); }

    public String printChampName(int i) {
        return data.getChampions().get(i).getName();
    }
}
