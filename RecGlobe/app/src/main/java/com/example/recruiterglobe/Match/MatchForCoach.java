package com.example.recruiterglobe.Match;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.recruiterglobe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MatchForCoach extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mMatchAdapter;
    private RecyclerView.LayoutManager mMatchLayoutManager;

    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_for_coach);

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        mMatchLayoutManager = new LinearLayoutManager(MatchForCoach.this);
        mRecyclerView.setLayoutManager(mMatchLayoutManager);
        mMatchAdapter = new MatchAdapterCoach(getDataSetMatch(), MatchForCoach.this);
        mRecyclerView.setAdapter(mMatchAdapter);

        getUserMatchId();

        mMatchAdapter.notifyDataSetChanged();
    }

    private void getUserMatchId() {
        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("coach").child(currentUserId).child("connection").child("match");
        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 if(dataSnapshot.exists()){
                     for(DataSnapshot match : dataSnapshot.getChildren()){
                         fetchMatchInformation(match.getKey());
                        }
                 }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void fetchMatchInformation(String key) {
        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Athlete").child(key);
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String userId = dataSnapshot.getKey();
                    String name = "";
                    String pic = "";

                    if(dataSnapshot.child("fName").getValue()!=null){
                        name = dataSnapshot.child("fName").getValue().toString();
                    }

                    if(dataSnapshot.child("pic").getValue()!=null){
                        pic = dataSnapshot.child("pic").getValue().toString();
                    }

                    MatchObjectCoach obj = new MatchObjectCoach(userId, name, pic);
                    resultMatch.add(obj);
                    mMatchAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private ArrayList<MatchObjectCoach> resultMatch = new ArrayList<MatchObjectCoach>();
    private List<MatchObjectCoach> getDataSetMatch() {
        return resultMatch;
    }
}
