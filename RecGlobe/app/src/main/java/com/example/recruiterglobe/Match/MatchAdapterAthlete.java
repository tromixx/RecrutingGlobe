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

public class MatchAdapterAthlete extends RecyclerView.Adapter<MatchViewHolderAthlete> {
    private List<MatchObjectAthlete> matchList;
    Context context;

    public MatchAdapterAthlete(List<MatchObjectAthlete> matchList, Context context){
        this.matchList = matchList;
        this.context = context;
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@!!!!!!!!!");
        System.out.println(matchList);
    }
    @NonNull
    @Override
    public MatchViewHolderAthlete onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.athlete_match_item, null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        MatchViewHolderAthlete rcv = new MatchViewHolderAthlete((layoutView));

        return rcv;
    }


    @Override
    public void onBindViewHolder(@NonNull MatchViewHolderAthlete holderAthlete, int position) {
        holderAthlete.mMatchId.setText(matchList.get(position).getUserId());
        holderAthlete.mMatchName.setText(matchList.get(position).getName());
        if(!matchList.get(position).getPic().equals(null)){
            Picasso.with(context).load(matchList.get(position).getPic()).into(holderAthlete.mMatchImage);
        }

    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }
}
