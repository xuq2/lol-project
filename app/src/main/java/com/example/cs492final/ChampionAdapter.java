package com.example.cs492final;

import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cs492final.data.ChampionWTags;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChampionAdapter extends RecyclerView.Adapter<ChampionAdapter.ChampionViewHolder> {
    //champion data
    private List<ChampionWTags>championData;
    private OnChampionClickListener onChampionClickListener;

    public interface OnChampionClickListener {
        void onChampionClick(ChampionWTags champion);
    }

    public ChampionAdapter(OnChampionClickListener onChampionClickListener){
        this.onChampionClickListener = onChampionClickListener;
    }

    @NonNull
    @Override
    public ChampionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.champion_list_item, parent, false);
        return new ChampionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChampionViewHolder holder, int position) {
        holder.bind(championData.get(position));

    }
    public void updateChampionData(List<ChampionWTags> championWTags) {
        this.championData = championWTags;

        // update UI
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(this.championData == null) {
            return 0;
        } else {
            return championData.size();
        }
    }

    class ChampionViewHolder extends RecyclerView.ViewHolder{
        TextView champion_name;
        TextView champion_title;
        TextView champion_tag;
        TextView champion_image;


        public ChampionViewHolder(@NonNull View itemView) {
            super(itemView);
            champion_name=itemView.findViewById(R.id.champion_name);
            champion_title=itemView.findViewById(R.id.champion_title);
            champion_tag=itemView.findViewById(R.id.champion_tag);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onChampionClickListener.onChampionClick(championData.get(getAdapterPosition()));
                }
            });
        }

        public void bind(ChampionWTags champion) {
            champion_name.setText(champion.getName());
            champion_title.setText(champion.getTitle());
            champion_tag.setText(champion.getTags().toString());
        }
    }
}
