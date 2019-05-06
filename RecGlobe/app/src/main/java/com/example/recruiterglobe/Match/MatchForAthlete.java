package com.example.recruiterglobe.Match;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.recruiterglobe.LoginAndProfile.CoachProfileActivity;
import com.example.recruiterglobe.LoginAndProfile.login_option_activity;
import com.example.recruiterglobe.R;
import com.example.recruiterglobe.Swipe.MainActivity;
import com.example.recruiterglobe.Swipe.SecondMainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MatchForAthlete extends AppCompatActivity {


    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MatchForAthlete.this, login_option_activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout_button) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed (){
        Intent intent = new Intent(MatchForAthlete.this, SecondMainActivity.class);
        startActivity(intent);
        finish();
    }


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mMatchAdapter;
    private RecyclerView.LayoutManager mMatchLayoutManager;

    private String currentUserId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_for_athlete);

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        mMatchLayoutManager = new LinearLayoutManager(MatchForAthlete.this);
        mRecyclerView.setLayoutManager(mMatchLayoutManager);
        mMatchAdapter = new MatchAdapterAthlete(getDataSetMatch(), MatchForAthlete.this);
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

                    MatchObjectAthlete obj = new MatchObjectAthlete(userId, name, pic);
                    resultMatch.add(obj);
                    //System.out.println("@@@@@@@@@@@@@@@@@@@@@!!!!!!!!!!!!!!!!!!!!!!!!!@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@!!!!!!!!!");
                    //System.out.println(obj.getName());
                    System.out.println(resultMatch);
                    mMatchAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private ArrayList<MatchObjectAthlete> resultMatch = new ArrayList<MatchObjectAthlete>();
    private List<MatchObjectAthlete> getDataSetMatch() {
        return resultMatch;
    }
}
