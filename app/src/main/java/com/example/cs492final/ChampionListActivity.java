package com.example.cs492final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cs492final.data.Champion;
import com.example.cs492final.data.AppDatabase;
import com.example.cs492final.data.ChampionWTags;
import com.example.cs492final.data.Champions;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ChampionListActivity extends AppCompatActivity implements ChampionAdapter.OnChampionClickListener {
    private static final String TAG = ChampionListActivity.class.getSimpleName();
    public static final String EXTRA_TAG_TEXT = "ChampionListActivity.Tag";
    public static final String EXTRA_DIFFICULTY_TEXT = "ChampionListActivity.Difficulty";
    public static final String EXTRA_PARTYPE_TEXT = "ChampionListActivity.Partype";


    private String tag;
    private String difficulty;
    private String partype;
    private String orderBy; // Add Sort by option to preference and make this string reflect that value instead of hard coding
    private String ordering;

    private ProgressBar loadingIndicatorPB;
    private TextView errorMessageTV;


    private List<ChampionWTags> championsData;
    private DbChampionViewModel dbChampionViewModel;

    private RecyclerView recyclerView;
    private ChampionAdapter championAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champion_list);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        orderBy=preferences.getString(
                getString(R.string.pref_ordered_by_key),
                getString(R.string.pref_ordered_default)
        );
        ordering=preferences.getString(
                getString(R.string.pref_asc_dsc_key),
                getString(R.string.pref_asc_dsc_default)
                );

        this.loadingIndicatorPB = findViewById(R.id.pb_loading_indicator);
        this.errorMessageTV = findViewById(R.id.tv_error_message);


        Log.d("ordered by is", orderBy);


        Intent intent = getIntent();

        if(intent != null && intent.hasExtra(EXTRA_TAG_TEXT) && intent.hasExtra(EXTRA_DIFFICULTY_TEXT)
                && intent.hasExtra(EXTRA_PARTYPE_TEXT)) {
            this.dbChampionViewModel = new ViewModelProvider(
                    this,
                    new ViewModelProvider.AndroidViewModelFactory(getApplication())
            ).get(DbChampionViewModel.class);
            this.recyclerView = findViewById(R.id.champion_recycle);
            this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
            this.championAdapter = new ChampionAdapter(this);
            this.recyclerView.setAdapter(this.championAdapter);

            this.tag = intent.getStringExtra(EXTRA_TAG_TEXT);
            this.difficulty = intent.getStringExtra(EXTRA_DIFFICULTY_TEXT);
            this.partype = intent.getStringExtra(EXTRA_PARTYPE_TEXT);

            getChampionsData(orderBy);
        }



    }

    private void getChampionsData(String orderBy) {
        if(!this.tag.equals("All") && this.difficulty.equals("All") && this.partype.equals("All")) {
            getChampionsByTag(this.tag, orderBy);
        } else if(this.tag.equals("All") && !this.difficulty.equals("All") && this.partype.equals("All")) {
            getChampionsByDifficulty(Integer.parseInt(this.difficulty), orderBy);
        } else if(this.tag.equals("All") && this.difficulty.equals("All") && !this.partype.equals("All")) {
            getChampionsByPartype(this.partype, orderBy);
        } else if(!this.tag.equals("All") && !this.difficulty.equals("All") && this.partype.equals("All")) {
            getChampionsByTagDifficulty(this.tag, Integer.parseInt(this.difficulty), orderBy);
        } else if(!this.tag.equals("All") && this.difficulty.equals("All") && !this.partype.equals("All")) {
            getChampionsByTagPartype(this.tag, this.partype, orderBy);
        } else if(this.tag.equals("All") && !this.difficulty.equals("All") && !this.partype.equals("All")) {
            getChampionsByDifficultyPartype(Integer.parseInt(this.difficulty), this.partype, orderBy);
        } else if(!this.tag.equals("All") && !this.difficulty.equals("All") && !this.partype.equals("All")) {
            getChampionsByAllQuery(this.tag, Integer.parseInt(this.difficulty), this.partype, orderBy);
        } else if(this.tag.equals("All") && this.difficulty.equals("All") && this.partype.equals("All")) {
            getAllChampionsOrderBy(orderBy);
        }
    }

    private void printChampsWithTags() {
        for(ChampionWTags champion : championsData) {
            if(champion!=null) {
                Log.d("Champs are", champion.getName() + " " + champion.getTags());
            }
        }
        if(ordering.equals("Ascending")){
            if(championsData != null && championsData.get(0) != null) {
                errorMessageTV.setVisibility(View.INVISIBLE);
                this.championAdapter.updateChampionData(this.championsData);
            }else{
                errorMessageTV.setVisibility(View.VISIBLE);
            }
        }
        else{
            Collections.reverse(championsData);
            if(championsData != null && championsData.get(0) != null) {
                errorMessageTV.setVisibility(View.INVISIBLE);
                this.championAdapter.updateChampionData(this.championsData);
            }else{
                errorMessageTV.setVisibility(View.VISIBLE);
            }
        }


    }

    private void getAllChampionsOrderBy(String column) {
        this.dbChampionViewModel.getAllChampionsOrderBy(column).observe(
                this,
                new Observer<List<Champion>>() {
                    @Override
                    public void onChanged(List<Champion> champions) {
                        Champions champs = new Champions(champions);
                        championsData = champs.toChampWithTags();
                        printChampsWithTags();
                    }
                }
        );
    }

    private void getChampionsByTag(String tag, String column) {
        this.dbChampionViewModel.getChampionsByTag(tag, column).observe(
                this,
                new Observer<List<Champion>>() {
                    @Override
                    public void onChanged(List<Champion> champions) {
                        Champions champs = new Champions(champions);
                        championsData = champs.toChampWithTags();
                        printChampsWithTags();
                    }
                }
        );
    }

    private void getChampionsByDifficulty(int difficulty, String column) {
        this.dbChampionViewModel.getChampionsByDifficulty(difficulty, column).observe(
                this,
                new Observer<List<Champion>>() {
                    @Override
                    public void onChanged(List<Champion> champions) {
                        Champions champs = new Champions(champions);
                        championsData = champs.toChampWithTags();
                        printChampsWithTags();
                    }
                }
        );
    }

    private void getChampionsByPartype(String partype, String column) {
        this.dbChampionViewModel.getChampionsByPartype(partype, column).observe(
                this,
                new Observer<List<Champion>>() {
                    @Override
                    public void onChanged(List<Champion> champions) {
                        Champions champs = new Champions(champions);
                        championsData = champs.toChampWithTags();
                        printChampsWithTags();
                    }
                }
        );
    }

    private void getChampionsByTagDifficulty(String tag, int difficulty, String column) {
        this.dbChampionViewModel.getChampionsByTagDifficulty(tag, difficulty, column).observe(
                this,
                new Observer<List<Champion>>() {
                    @Override
                    public void onChanged(List<Champion> champions) {
                        Champions champs = new Champions(champions);
                        championsData = champs.toChampWithTags();
                        printChampsWithTags();
                    }
                }
        );
    }

    private void getChampionsByTagPartype(String tag, String partype, String column) {
        this.dbChampionViewModel.getChampionsByTagPartype(tag, partype, column).observe(
                this,
                new Observer<List<Champion>>() {
                    @Override
                    public void onChanged(List<Champion> champions) {
                        Champions champs = new Champions(champions);
                        championsData = champs.toChampWithTags();
                        printChampsWithTags();
                    }
                }
        );
    }

    private void getChampionsByDifficultyPartype(int difficulty, String partype, String column) {
        this.dbChampionViewModel.getChampionsByDifficultyPartype(difficulty, partype, column).observe(
                this,
                new Observer<List<Champion>>() {
                    @Override
                    public void onChanged(List<Champion> champions) {
                        Champions champs = new Champions(champions);
                        championsData = champs.toChampWithTags();
                        printChampsWithTags();
                    }
                }
        );
    }

    private void getChampionsByAllQuery(String tag, int difficulty, String partype, String column) {
        this.dbChampionViewModel.getChampionsByAllQuery(tag, difficulty, partype, column).observe(
                this,
                new Observer<List<Champion>>() {
                    @Override
                    public void onChanged(List<Champion> champions) {
                        Champions champs = new Champions(champions);
                        championsData = champs.toChampWithTags();
                        printChampsWithTags();
                    }
                }
        );
    }

    @Override
    public void onChampionClick(ChampionWTags champion) {
        Intent intent = new Intent(this, ChampDetailActivity.class);
        intent.putExtra(ChampDetailActivity.EXTRA_CHAMPION_DATA, champion);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.google_intent,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.google_intent:
                google_intent();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    void google_intent(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://na.leagueoflegends.com"));
        Intent intent = Intent.createChooser(browserIntent , "Choose browser");
        startActivity(intent);
    }
}