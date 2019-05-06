package com.example.recruiterglobe.Match;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recruiterglobe.Chat.ChatActivity;
import com.example.recruiterglobe.R;

public class MatchViewHolderAthlete extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView mMatchId, mMatchName;
    public ImageView mMatchImage;

    public MatchViewHolderAthlete(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mMatchId = (TextView) itemView.findViewById(R.id.matchId);
        mMatchName = (TextView) itemView.findViewById(R.id.matchName);
        mMatchImage = (ImageView) itemView.findViewById(R.id.matchImage);
    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(view.getContext(), ChatActivity.class);
        Bundle b = new Bundle();
        b.putString("matchId", mMatchId.getText().toString());
        intent.putExtras(b);
        view.getContext().startActivity(intent);
    }
}
