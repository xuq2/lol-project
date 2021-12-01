package com.example.cs492final.data;

import androidx.room.Ignore;

import com.google.gson.JsonObject;

import java.io.Serializable;

public class ChampionStats implements Serializable {
    private int hp;
    private double hpperlevel;
    private double mp;
    private double mpperlevel;
    private int movespeed;
    private double armor;
    private double armorperlevel;
    private double spellblock;
    private double spellblockperlevel;
    private int attackrange;
    private double hpregen;
    private double hpregenperlevel;
    private double mpregen;
    private double mpregenperlevel;
    private int crit;
    private int critperlevel;
    private double attackdamage;
    private double attackdamageperlevel;
    private double attackspeedperlevel;
    private double attackspeed;

    @Ignore
    public ChampionStats() {
        this.hp = 0;
        this.hpperlevel = 0;
        this.mp = 0;
        this.mpperlevel = 0;
        this.movespeed = 0;
        this.armor = 0;
        this.armorperlevel = 0;
        this.spellblock = 0;
        this.spellblockperlevel = 0;
        this.attackrange = 0;
        this.hpregen = 0;
        this.hpregenperlevel = 0;
        this.mpregen = 0;
        this.mpregenperlevel = 0;
        this.crit = 0;
        this.critperlevel = 0;
        this.attackdamage = 0;
        this.attackdamageperlevel = 0;
        this.attackspeedperlevel = 0;
        this.attackspeed = 0;
    }

    public ChampionStats(int hp, double hpperlevel, double mp, double mpperlevel, int movespeed,
                         double armor, double armorperlevel, double spellblock, double spellblockperlevel,
                         int attackrange, double hpregen, double hpregenperlevel, double mpregen,
                         double mpregenperlevel, int crit, int critperlevel, double attackdamage,
                         double attackdamageperlevel, double attackspeedperlevel, double attackspeed) {
         this.hp = hp;
         this.hpperlevel = hpperlevel;
         this.mp = mp;
         this.mpperlevel = mpperlevel;
         this.movespeed = movespeed;
         this.armor = armor;
         this.armorperlevel = armorperlevel;
         this.spellblock = spellblock;
         this.spellblockperlevel = spellblockperlevel;
         this.attackrange = attackrange;
         this.hpregen = hpregen;
         this.hpregenperlevel = hpregenperlevel;
         this.mpregen = mpregen;
         this.mpregenperlevel = mpregenperlevel;
         this.crit = crit;
         this.critperlevel = critperlevel;
         this.attackdamage = attackdamage;
         this.attackdamageperlevel = attackdamageperlevel;
         this.attackspeedperlevel = attackspeedperlevel;
         this.attackspeed = attackspeed;
    }

    @Ignore
    public ChampionStats(JsonObject json) {
        this.hp = json.getAsJsonPrimitive("hp").getAsInt();
        this.hpperlevel = json.getAsJsonPrimitive("hpperlevel").getAsDouble();
        this.mp = json.getAsJsonPrimitive("mp").getAsDouble();
        this.mpperlevel = json.getAsJsonPrimitive("mpperlevel").getAsDouble();
        this.movespeed = json.getAsJsonPrimitive("movespeed").getAsInt();
        this.armor = json.getAsJsonPrimitive("armor").getAsDouble();
        this.armorperlevel = json.getAsJsonPrimitive("armorperlevel").getAsDouble();
        this.spellblock = json.getAsJsonPrimitive("spellblock").getAsDouble();
        this.spellblockperlevel = json.getAsJsonPrimitive("spellblockperlevel").getAsDouble();
        this.attackrange = json.getAsJsonPrimitive("attackrange").getAsInt();
        this.hpregen = json.getAsJsonPrimitive("hpregen").getAsDouble();
        this.hpregenperlevel = json.getAsJsonPrimitive("hpregenperlevel").getAsDouble();
        this.mpregen = json.getAsJsonPrimitive("mpregen").getAsDouble();
        this.mpregenperlevel = json.getAsJsonPrimitive("mpregenperlevel").getAsDouble();
        this.crit = json.getAsJsonPrimitive("crit").getAsInt();
        this.critperlevel = json.getAsJsonPrimitive("critperlevel").getAsInt();
        this.attackdamage = json.getAsJsonPrimitive("attackdamage").getAsDouble();
        this.attackdamageperlevel = json.getAsJsonPrimitive("attackdamageperlevel").getAsDouble();
        this.attackspeedperlevel = json.getAsJsonPrimitive("attackspeedperlevel").getAsDouble();
        this.attackspeed = json.getAsJsonPrimitive("attackspeed").getAsDouble();
    }

    public int getHp() {
        return hp;
    }

    public double getHpperlevel() {
        return hpperlevel;
    }

    public double getMp() {
        return mp;
    }

    public double getMpperlevel() {
        return mpperlevel;
    }

    public int getMovespeed() {
        return movespeed;
    }

    public double getArmor() {
        return armor;
    }

    public double getArmorperlevel() {
        return armorperlevel;
    }

    public double getSpellblock() {
        return spellblock;
    }

    public double getSpellblockperlevel() {
        return spellblockperlevel;
    }

    public int getAttackrange() {
        return attackrange;
    }

    public double getHpregen() {
        return hpregen;
    }

    public double getHpregenperlevel() {
        return hpregenperlevel;
    }

    public double getMpregen() {
        return mpregen;
    }

    public double getMpregenperlevel() {
        return mpregenperlevel;
    }

    public int getCrit() {
        return crit;
    }

    public int getCritperlevel() {
        return critperlevel;
    }

    public double getAttackdamage() {
        return attackdamage;
    }

    public double getAttackdamageperlevel() {
        return attackdamageperlevel;
    }

    public double getAttackspeedperlevel() {
        return attackspeedperlevel;
    }

    public double getAttackspeed() {
        return attackspeed;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setHpperlevel(double hpperlevel) {
        this.hpperlevel = hpperlevel;
    }

    public void setArmor(double armor) {
        this.armor = armor;
    }

    public void setArmorperlevel(double armorperlevel) {
        this.armorperlevel = armorperlevel;
    }

    public void setAttackdamage(double attackdamage) {
        this.attackdamage = attackdamage;
    }

    public void setAttackdamageperlevel(double attackdamageperlevel) {
        this.attackdamageperlevel = attackdamageperlevel;
    }

    public void setAttackrange(int attackrange) {
        this.attackrange = attackrange;
    }

    public void setAttackspeed(double attackspeed) {
        this.attackspeed = attackspeed;
    }

    public void setAttackspeedperlevel(double attackspeedperlevel) {
        this.attackspeedperlevel = attackspeedperlevel;
    }

    public void setCrit(int crit) {
        this.crit = crit;
    }

    public void setCritperlevel(int critperlevel) {
        this.critperlevel = critperlevel;
    }

    public void setHpregen(double hpregen) {
        this.hpregen = hpregen;
    }

    public void setHpregenperlevel(double hpregenperlevel) {
        this.hpregenperlevel = hpregenperlevel;
    }

    public void setMovespeed(int movespeed) {
        this.movespeed = movespeed;
    }

    public void setMp(double mp) {
        this.mp = mp;
    }

    public void setMpperlevel(double mpperlevel) {
        this.mpperlevel = mpperlevel;
    }

    public void setMpregen(double mpregen) {
        this.mpregen = mpregen;
    }

    public void setMpregenperlevel(double mpregenperlevel) {
        this.mpregenperlevel = mpregenperlevel;
    }

    public void setSpellblock(double spellblock) {
        this.spellblock = spellblock;
    }

    public void setSpellblockperlevel(double spellblockperlevel) {
        this.spellblockperlevel = spellblockperlevel;
    }
}