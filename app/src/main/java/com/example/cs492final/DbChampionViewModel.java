package com.example.cs492final;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cs492final.data.Champion;
import com.example.cs492final.data.DbChampionsRepository;

import java.util.List;

public class DbChampionViewModel extends AndroidViewModel {
    private DbChampionsRepository repository;

    public DbChampionViewModel(@NonNull Application application) {
        super(application);
        this.repository = new DbChampionsRepository(application);
    }

    public void insertChampion(Champion champion) {
        this.repository.insertChampion(champion);
    }

    public LiveData<List<Champion>> getAllChampionsOrderBy(String column) {
        return this.repository.getAllChampionsOrderBy(column);
    }

    public LiveData<List<Champion>> getChampionsByTag(String tag, String column) {
        return this.repository.getChampionsByTag(tag, column);
    }

    public LiveData<List<Champion>> getChampionsByDifficulty(int difficulty, String column) {
        return this.repository.getChampionsByDifficulty(difficulty, column);
    }

    public LiveData<List<Champion>> getChampionsByPartype(String partype, String column) {
        return this.repository.getChampionsByPartype(partype, column);
    }

    public LiveData<List<Champion>> getChampionsByTagDifficulty(String tag, int difficulty, String column) {
        return this.repository.getChampionsByTagDifficulty(tag, difficulty, column);
    }

    public LiveData<List<Champion>> getChampionsByTagPartype(String tag, String partype, String column) {
        return this.repository.getChampionsByTagPartype(tag, partype, column);
    }

    public LiveData<List<Champion>> getChampionsByDifficultyPartype(int difficulty, String partype, String column) {
        return this.repository.getChampionsByDifficultyPartype(difficulty, partype, column);
    }

    public LiveData<List<Champion>> getChampionsByAllQuery(String tag, int difficulty, String partype, String column) {
        return this.repository.getChampionsByAllQuery(tag, difficulty, partype, column);
    }
}