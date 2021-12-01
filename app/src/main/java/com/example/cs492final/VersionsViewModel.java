package com.example.cs492final;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs492final.data.Versions;
import com.example.cs492final.data.VersionsRepository;

public class VersionsViewModel extends ViewModel {
    private VersionsRepository repository;
    private LiveData<Versions> patchVersions;

    public VersionsViewModel() {
        this.repository = new VersionsRepository();
        this.patchVersions = repository.getPatchVersions();
    }

    public LiveData<Versions> getPatchVersions() {
        return this.patchVersions;
    }

    public void loadVersions() {
        this.repository.loadVersion();
    }
}