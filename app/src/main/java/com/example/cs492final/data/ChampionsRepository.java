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

public class ChampionsRepository {
    private static final String TAG = ChampionsRepository.class.getSimpleName();
    private static final String BASE_URL = "https://ddragon.leagueoflegends.com/";

    private LeagueService leagueService;
    private MutableLiveData<ChampionsData> championsData;

    public ChampionsRepository() {
        championsData = new MutableLiveData<>();
        championsData.setValue(null);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Champions.class, new Champions.JsonDeserializer())
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        this.leagueService = retrofit.create(LeagueService.class);
    }

    public LiveData<ChampionsData> getChampionsData() {
        return this.championsData;
    }

    public void loadChampions(String version) {
        Call<ChampionsData> req = this.leagueService.fetchChampions(version);
        req.enqueue(new Callback<ChampionsData>() {
            @Override
            public void onResponse(Call<ChampionsData> call, Response<ChampionsData> response) {
                if(response.code() == 200) {
                    championsData.setValue(response.body());
                    Log.d(TAG, "Success " + response.body().getData().getChampions().get(153).getName());
                } else {
                    Log.d(TAG, "unsuccessful API request: " + call.request().url());
                    Log.d(TAG, "  -- response status code: " + response.code());
                    Log.d(TAG, "  -- response: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<ChampionsData> call, Throwable t) {
                Log.d(TAG, "unsuccessful API request: " + call.request().url());
                t.printStackTrace();
            }
        });
    }
}
