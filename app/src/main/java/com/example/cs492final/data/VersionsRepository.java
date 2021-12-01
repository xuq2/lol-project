package com.example.cs492final.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VersionsRepository {
    private static final String TAG = VersionsRepository.class.getSimpleName();
    private static final String BASE_URL = "https://ddragon.leagueoflegends.com/api/";

    private MutableLiveData<Versions> patchVersions;

    private LeagueService leagueService;

    public VersionsRepository() {
        this.patchVersions = new MutableLiveData<>();
        this.patchVersions.setValue(null);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Versions.class, new Versions.JsonDeserializer())
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        this.leagueService = retrofit.create(LeagueService.class);
    }

    public LiveData<Versions> getPatchVersions() {
        return this.patchVersions;
    }
    public void loadVersion() {
        this.patchVersions.setValue(null);
        Call<Versions> req = this.leagueService.fetchVersions();
        req.enqueue(new Callback<Versions>() {
            @Override
            public void onResponse(Call<Versions> call, Response<Versions> response) {
                if(response.code() == 200) {
                    patchVersions.setValue(response.body());
                    Log.d(TAG, "Success" + response.body());
                } else {
                    Log.d(TAG, "unsuccessful API request: " + call.request().url());
                    Log.d(TAG, "  -- response status code: " + response.code());
                    Log.d(TAG, "  -- response: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<Versions> call, Throwable t) {
                Log.d(TAG, "unsuccessful API request: " + call.request().url());
                t.printStackTrace();
            }
        });
    }
}