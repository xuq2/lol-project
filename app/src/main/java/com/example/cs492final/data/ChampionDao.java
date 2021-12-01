package com.example.cs492final.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.List;

@Dao
public interface ChampionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Champion champion);

    @RawQuery(observedEntities = Champion.class)
    LiveData<List<Champion>> getAllChampionsOrderBy(SupportSQLiteQuery query);

    @RawQuery(observedEntities = Champion.class)
    LiveData<List<Champion>> getChampionsByTag(SupportSQLiteQuery query);

    @RawQuery(observedEntities = Champion.class)
    LiveData<List<Champion>> getChampionsByDifficulty(SupportSQLiteQuery query);

    @RawQuery(observedEntities = Champion.class)
    LiveData<List<Champion>> getChampionsByPartype(SupportSQLiteQuery query);

    @RawQuery(observedEntities = Champion.class)
    LiveData<List<Champion>> getChampionsByTagDifficulty(SupportSQLiteQuery query);

    @RawQuery(observedEntities = Champion.class)
    LiveData<List<Champion>> getChampionsByTagPartype(SupportSQLiteQuery query);

    @RawQuery(observedEntities = Champion.class)
    LiveData<List<Champion>> getChampionsByDifficultyPartype(SupportSQLiteQuery query);

    @RawQuery(observedEntities = Champion.class)
    LiveData<List<Champion>> getChampionsByAllQuery(SupportSQLiteQuery query);
}
