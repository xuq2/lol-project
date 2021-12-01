package com.example.cs492final.data;

import androidx.room.Ignore;

import com.google.gson.JsonObject;

import java.io.Serializable;

public class ChampionInfo implements Serializable {
    private int attack;
    private int defense;
    private int magic;
    private int difficulty;

    @Ignore
    public ChampionInfo() {
        this.attack = 0;
        this.defense = 0;
        this.magic = 0;
        this.difficulty = 0;
    }

    public ChampionInfo(int attack, int defense, int magic, int difficulty) {
        this.attack = attack;
        this.defense = defense;
        this.magic = magic;
        this.difficulty = difficulty;
    }

    @Ignore
    public ChampionInfo(JsonObject json) {
        this.attack = json.getAsJsonPrimitive("attack").getAsInt();
        this.defense = json.getAsJsonPrimitive("defense").getAsInt();
        this.magic = json.getAsJsonPrimitive("magic").getAsInt();
        this.difficulty = json.getAsJsonPrimitive("difficulty").getAsInt();
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getMagic() {
        return magic;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
