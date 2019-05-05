package com.example.recruiterglobe.Match;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recruiterglobe.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MatchAdapterCoach extends RecyclerView.Adapter<MatchViewHolderCoach> {
    private List<MatchObjectCoach> matchList;
    Context context;

    public MatchAdapterCoach(List<MatchObjectCoach> matchList, Context context){
        this.matchList = matchList;
        this.context = context;
    }
    @NonNull
    @Override
    public MatchViewHolderCoach onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.coach_match_item, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        MatchViewHolderCoach rcv = new MatchViewHolderCoach((layoutView));

        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolderCoach holderCoach, int position) {
        holderCoach.mMatchId.setText(matchList.get(position).getUserId());
        holderCoach.mMatchName.setText(matchList.get(position).getName());
        if(!matchList.get(position).getPic().equals(null)){
            Picasso.with(context).load(matchList.get(position).getPic()).into(holderCoach.mMatchImage);
        }

    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }
}
