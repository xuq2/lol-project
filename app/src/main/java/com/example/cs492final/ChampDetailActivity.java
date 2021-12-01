package com.example.cs492final;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs492final.data.ChampionStats;
import com.example.cs492final.data.ChampionWTags;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ChampDetailActivity extends AppCompatActivity {
    private static final String TAG = ChampDetailActivity.class.getSimpleName();
    public static final String EXTRA_CHAMPION_DATA = "Champions";

    private ChampionWTags champion;
    private ImageView imageTV;
    private Toast infoToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.champ_detail_activity);

        Intent intent = getIntent();
        if(intent != null&&intent.hasExtra(EXTRA_CHAMPION_DATA)){
            this.champion = (ChampionWTags)intent.getSerializableExtra(EXTRA_CHAMPION_DATA);
            ChampionStats stats = champion.getStats();

            // Grab Image View
            this.imageTV = findViewById(R.id.detail_image);

            // Grab Text Views
            TextView nameTV = findViewById(R.id.detail_name);
            TextView titleTV = findViewById(R.id.detail_title);
            TextView tagsTV = findViewById(R.id.detail_tags);
            TextView blurbTV = findViewById(R.id.detail_blurb);

            TextView statHPTV = findViewById(R.id.detail_stat_hp);
            TextView statHPRegenTV = findViewById(R.id.detail_stat_hpregen);
            TextView statMPTV = findViewById(R.id.detail_stat_mp);
            TextView statMPRegenTV = findViewById(R.id.detail_stat_mpregen);
            TextView statMoveSpeedTV = findViewById(R.id.detail_stat_movespeed);
            TextView statArmorTV = findViewById(R.id.detail_stat_armor);
            TextView statSpellBlockTV = findViewById(R.id.detail_stat_spellblock);
            TextView statCritTV = findViewById(R.id.detail_stat_crit);
            TextView statAttackDamageTV = findViewById(R.id.detail_stat_attackdamage);
            TextView statAttackSpeedTV = findViewById(R.id.detail_stat_attackspeed);
            TextView statAttackRangeTV = findViewById(R.id.detail_stat_attackrange);

            // Set champion image
            new ImageFetchTask().execute();

            // Set generic text views
            nameTV.setText(champion.getName());
            titleTV.setText(champion.getTitle().toLowerCase());
            tagsTV.setText(buildTagString(champion.getTags()));
            blurbTV.setText(champion.getBlurb());

            // Set stats stuff
            statHPTV.setText("HP: " + stats.getHp() + " (+" + stats.getHpperlevel() + ")");
            statHPRegenTV.setText("HP Regen: " + stats.getHpregen() + " (+" + stats.getHpregenperlevel() + ")");
            statMPTV.setText("MP: " + stats.getMp() + " (+" + stats.getMpperlevel() + ")");
            statMPRegenTV.setText("MP Regen: " + stats.getMpregen() + " (+" + stats.getMpregenperlevel() + ")");
            statMoveSpeedTV.setText("Move Speed: " + stats.getMovespeed());
            statArmorTV.setText("Armor: " + stats.getArmor() + " (+" + stats.getArmorperlevel() + ")");
            statSpellBlockTV.setText("Spellblock: " + stats.getSpellblock() + " (+" + stats.getSpellblockperlevel() + ")");
            statCritTV.setText("Crit: " + stats.getCrit());
            statAttackDamageTV.setText("Attack Damage: " + stats.getAttackdamage() + " (+" + stats.getAttackdamageperlevel() + ")");
            statAttackSpeedTV.setText("Attack Speed: " + stats.getAttackspeed() + " (+" + stats.getAttackdamageperlevel() + ")");
            statAttackRangeTV.setText("Attack Range: " + stats.getAttackrange());

            // Setup info button toast
            ImageView infoTV = findViewById(R.id.detail_stat_info);
            infoTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "click!");
                    if (infoToast != null)
                        infoToast.cancel();
                    infoToast = Toast.makeText(
                            getBaseContext(),
                            "The (+X) is the amount the stat increases per level",
                            Toast.LENGTH_LONG
                    );
                    infoToast.show();
                }
            });
        }
    }

    // Returns all of a champions tags in a single formatted string
    private String buildTagString(ArrayList<String> tagList) {
        String tags = "";

        for (int i = 0; i < tagList.size(); i++) {
            tags += tagList.get(i);

            if (i != tagList.size() - 1)
                tags += ", ";
        }

        return tags;
    }

    public class ImageFetchTask extends AsyncTask<Void, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(Void ... voids) {
            String version = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                    .getString(getString(R.string.pref_version_key), "0");
            String strUrl = "https://ddragon.leagueoflegends.com/cdn/" + version + "/img/champion/" + champion.getImageName();
            URL url = null;
            try {
                url = new URL(strUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bmp = null;
            try {
                assert url != null;
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "Fetching");
            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap image) {
            super.onPostExecute(image);
            if(imageTV == null) {
                Log.d(TAG, "Ohno");
            } else {
                Log.d(TAG, "huh?");
            }
            imageTV.setImageBitmap(image);

            Log.d(TAG, "Complete");
        }
    }
}