package com.example.cs492final.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ChampionsViewModel extends ViewModel {
    private ChampionsRepository repository;
    private LiveData<ChampionsData> championsData;

    public ChampionsViewModel() {
        this.repository = new ChampionsRepository();
        this.championsData = repository.getChampionsData();
    }

    public LiveData<ChampionsData> getChampionsData() { return this.championsData; }

    public void loadChampions(String version) {
        this.repository.loadChampions(version);
    }
}
