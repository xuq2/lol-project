package com.example.cs492final.data;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.List;

public class DbChampionsRepository {
    private ChampionDao dao;

    public DbChampionsRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.dao = db.championDao();
    }

    public void insertChampion(Champion champion) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(champion);
            }
        });
    }

    public LiveData<List<Champion>> getAllChampionsOrderBy(String column) {
        String statement = "SELECT * FROM champions ORDER BY " + column + " ASC";
        SupportSQLiteQuery query = new SimpleSQLiteQuery(statement, new Object[]{});
        return this.dao.getAllChampionsOrderBy(query);
    }

    public LiveData<List<Champion>> getChampionsByTag(String tag, String column) {
        String statement = "SELECT * FROM champions C WHERE EXISTS (SELECT * FROM champions C1 WHERE tag = \"" + tag + "\" AND C.name = C1.name) ORDER BY " + column;
        SupportSQLiteQuery query = new SimpleSQLiteQuery(statement, new Object[]{});
        return this.dao.getChampionsByTag(query);
    }

    public LiveData<List<Champion>> getChampionsByDifficulty(int difficulty, String column) {
        String statement = "SELECT * FROM champions WHERE difficulty = " + difficulty + " ORDER BY " + column;
        SupportSQLiteQuery query = new SimpleSQLiteQuery(statement, new Object[]{});
        return this.dao.getChampionsByDifficulty(query);
    }

    public LiveData<List<Champion>> getChampionsByPartype(String partype, String column) {
        String statement = "SELECT * FROM champions WHERE partype = \"" + partype + "\" ORDER BY " + column;
        SupportSQLiteQuery query = new SimpleSQLiteQuery(statement, new Object[]{});
        return this.dao.getChampionsByPartype(query);
    }

    public LiveData<List<Champion>> getChampionsByTagDifficulty(String tag, int difficulty, String column) {
        String statement = "SELECT * FROM champions C WHERE EXISTS (SELECT * FROM champions C1 WHERE tag = \"" + tag + "\" AND difficulty = " + difficulty + " AND C.name = C1.name) ORDER BY " + column;
        SupportSQLiteQuery query = new SimpleSQLiteQuery(statement, new Object[]{});
        return this.dao.getChampionsByTagDifficulty(query);
    }

    public LiveData<List<Champion>> getChampionsByTagPartype(String tag, String partype, String column) {
        String statement = "SELECT * FROM champions C WHERE EXISTS (SELECT * FROM champions C1 WHERE tag = \"" + tag + "\" AND partype = \"" + partype + "\" AND C.name = C1.name) ORDER BY " + column;
        SupportSQLiteQuery query = new SimpleSQLiteQuery(statement, new Object[]{});
        return this.dao.getChampionsByTagPartype(query);
    }

    public LiveData<List<Champion>> getChampionsByDifficultyPartype(int difficulty, String partype, String column) {
        String statement = "SELECT * FROM champions WHERE difficulty = " + difficulty + " AND partype = \"" + partype + "\" ORDER BY " + column;
        SupportSQLiteQuery query = new SimpleSQLiteQuery(statement, new Object[]{});
        return this.dao.getChampionsByDifficultyPartype(query);
    }

    public LiveData<List<Champion>> getChampionsByAllQuery(String tag, int difficulty, String partype, String column) {
        String statement = "SELECT * FROM champions C WHERE EXISTS (SELECT * FROM champions C1 WHERE tag = \"" + tag + "\" AND difficulty = " + difficulty + " AND partype = \"" + partype + "\" AND C.name = C1.name) ORDER BY " + column;
        SupportSQLiteQuery query = new SimpleSQLiteQuery(statement, new Object[]{});
        return this.dao.getChampionsByAllQuery(query);
    }
}