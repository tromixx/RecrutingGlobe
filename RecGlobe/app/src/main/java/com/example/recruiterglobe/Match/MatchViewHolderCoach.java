package com.example.recruiterglobe.Match;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recruiterglobe.R;

public class MatchViewHolderCoach extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mMatchId, mMatchName;
    public ImageView mMatchImage;

    public MatchViewHolderCoach(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mMatchId = (TextView) itemView.findViewById(R.id.matchId);
        mMatchName = (TextView) itemView.findViewById(R.id.matchName);
        mMatchImage = (ImageView) itemView.findViewById(R.id.matchImage);
    }

    @Override
    public void onClick(View view) {

    }
}
