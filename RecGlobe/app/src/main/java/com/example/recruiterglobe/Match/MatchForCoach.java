package com.example.recruiterglobe.Match;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.recruiterglobe.R;

import java.util.ArrayList;
import java.util.List;

public class MatchForCoach extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mMatchAdapter;
    private RecyclerView.LayoutManager mMatchLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_for_coach);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        mMatchLayoutManager = new LinearLayoutManager(MatchForCoach.this);
        mRecyclerView.setLayoutManager(mMatchLayoutManager);
        mMatchAdapter = new MatchAdapterCoach(getDataSetMatch(), MatchForCoach.this);
        mRecyclerView.setAdapter(mMatchAdapter);

        for(int i = 0; i < 100; i++){
            MatchObjectCoach obj = new MatchObjectCoach(Integer.toString(i));
            resultMatch.add(obj);
        }


        mMatchAdapter.notifyDataSetChanged();
    }

    private ArrayList<MatchObjectCoach> resultMatch = new ArrayList<MatchObjectCoach>();
    private List<MatchObjectCoach> getDataSetMatch() {
        return resultMatch;
    }
}
