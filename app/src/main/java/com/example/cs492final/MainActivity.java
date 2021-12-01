package com.example.cs492final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.VideoView;

import com.example.cs492final.data.AppDatabase;
import com.example.cs492final.data.Champion;
import com.example.cs492final.data.ChampionWTags;
import com.example.cs492final.data.Champions;
import com.example.cs492final.data.ChampionsData;
import com.example.cs492final.data.ChampionsViewModel;
import com.example.cs492final.data.Versions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private VersionsViewModel versionsViewModel;
    private ChampionsViewModel championsViewModel;
    private DbChampionViewModel dbChampionViewModel;

    private List<ChampionWTags> realChampions;

    private SharedPreferences sharedPreferences;
    private View recyclerView;
    private ChampionAdapter championAdapter;
    private VideoView videoView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get ItemLayout
        LinearLayout linearLayout = findViewById(R.id.item_layout);

        String[] tagArray = getResources().getStringArray(R.array.tag_array);
        Spinner tagSpinner = findViewById(R.id.tag_spinner);
        ArrayAdapter<String> tagAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, R.id.tv_spinner, tagArray);
        tagAdapter.setDropDownViewResource(R.layout.spinner_item);
        tagSpinner.setAdapter(tagAdapter);

        String[] difficultyArray = getResources().getStringArray(R.array.difficulty_array);
        Spinner difficultySpinner = findViewById(R.id.difficulty_spinner);
        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, R.id.tv_spinner, difficultyArray);
        difficultyAdapter.setDropDownViewResource(R.layout.spinner_item);
        difficultySpinner.setAdapter(difficultyAdapter);

        String[] partypeArray = getResources().getStringArray(R.array.partype_array);
        Spinner partypeSpinner = findViewById(R.id.partype_spinner);
        ArrayAdapter<String> partypeAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, R.id.tv_spinner, partypeArray);
        partypeAdapter.setDropDownViewResource(R.layout.spinner_item);
        partypeSpinner.setAdapter(partypeAdapter);



        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        this.versionsViewModel = new ViewModelProvider(this).get(VersionsViewModel.class);
        this.versionsViewModel.loadVersions();

        this.championsViewModel = new ViewModelProvider(this).get(ChampionsViewModel.class);

        this.dbChampionViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(DbChampionViewModel.class);

        this.versionsViewModel.getPatchVersions().observe(
                this,
                new Observer<Versions>() {
                    @Override
                    public void onChanged(Versions versions) {
                        if(versions != null) {
                            String version = versions.getLatestVersion();
                            String currVersion = sharedPreferences.getString(getString(R.string.pref_version_key), "0");
                            if(!version.equals(currVersion)) {
                                Log.d(TAG, "Change preference");
                                SharedPreferences.Editor edit = sharedPreferences.edit();
                                edit.putString(getString(R.string.pref_version_key), version);
                                edit.apply();
                            }
                        }
                    }
                });

        this.championsViewModel.getChampionsData().observe(
                this,
                new Observer<ChampionsData>() {
                    @Override
                    public void onChanged(ChampionsData championsData) {
                        if(championsData != null) {
                            Log.d(TAG, "insert to database");
                            ChampionsData champsData = championsData;
                            Champions champions = champsData.getData();
                            for(Champion champion : champions.getChampions()) {
                                dbChampionViewModel.insertChampion(champion);
                            }
                        }
                    }
                }
        );




        Button searchButton = (Button)findViewById(R.id.btn_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tagText = tagSpinner.getSelectedItem().toString();
                String difficultyText = difficultySpinner.getSelectedItem().toString();
                String partypeText = partypeSpinner.getSelectedItem().toString();
                Intent intent = new Intent(getApplicationContext(), ChampionListActivity.class);
                intent.putExtra(ChampionListActivity.EXTRA_TAG_TEXT, tagText);
                intent.putExtra(ChampionListActivity.EXTRA_DIFFICULTY_TEXT, difficultyText);
                intent.putExtra(ChampionListActivity.EXTRA_PARTYPE_TEXT, partypeText);
                startActivity(intent);
            }
        });


        videoView = findViewById(R.id.video_VV);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.league_of_legends_cinema;
        Uri uri = Uri.parse(path);
        videoView.setVideoURI(uri);

        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "start");
        videoView.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(getString(R.string.pref_version_key))) {
            Log.d(TAG, "load new version");
            String version = sharedPreferences.getString(key, "0");
            championsViewModel.loadChampions(version);
        }


    }

}
















