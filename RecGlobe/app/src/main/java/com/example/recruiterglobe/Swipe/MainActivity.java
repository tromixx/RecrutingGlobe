package com.example.recruiterglobe.Swipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.recruiterglobe.LoginAndProfile.CoachProfileActivity;
import com.example.recruiterglobe.Match.MatchForCoach;
import com.example.recruiterglobe.R;
import com.example.recruiterglobe.LoginAndProfile.login_option_activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class  MainActivity extends AppCompatActivity {

    private cards cards_data[];
    private com.example.recruiterglobe.Swipe.arrayAdapter arrayAdapter;
    private int i;
    private Button mProfile, mMatch;
    //private Button message;
    public ImageView image;


    private FirebaseAuth mAuth;
    private String currentUId;

    private DatabaseReference AthleteDb;

    ListView listView;
    List<cards> rowItems;


    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, login_option_activity.class);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AthleteDb = FirebaseDatabase.getInstance().getReference().child("Athlete");
        mAuth = FirebaseAuth.getInstance();
        currentUId = mAuth.getCurrentUser().getUid();

        getAthleteUser();

        mProfile = (Button) findViewById(R.id.profile);
        mMatch = (Button) findViewById(R.id.match);

        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CoachProfileActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        mMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MatchForCoach.class);
                startActivity(intent);
                finish();
                return;
            }
        });

/*
        message = findViewById(R.id.message);

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
*/

        rowItems= new ArrayList<cards>();
        arrayAdapter = new arrayAdapter(this, R.layout.item, rowItems);

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                cards obj = (cards) dataObject;
                String userId = obj.getUserId();
                AthleteDb.child(userId).child("connection").child("nope").child(currentUId).setValue(true);
                Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                cards obj = (cards) dataObject;
                String userId = obj.getUserId();
                AthleteDb.child(userId).child("connection").child("yup").child(currentUId).setValue(true);
                isConnectionMatched(userId);
                Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

            }
        });

        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(MainActivity.this, "click", Toast.LENGTH_SHORT).show();
            }
        });




    }

    private void isConnectionMatched(String userId) {
        DatabaseReference currentConnectionDb = FirebaseDatabase.getInstance().getReference().child("coach").child(currentUId).child("connection").child("yup").child(userId);
        currentConnectionDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Toast.makeText(MainActivity.this, "New Match", Toast.LENGTH_LONG).show();
                    AthleteDb.child(dataSnapshot.getKey()).child("connection").child("match").child(currentUId).setValue(true);
                    FirebaseDatabase.getInstance().getReference().child("coach").child(currentUId).child("connection").child("match").child(dataSnapshot.getKey()).setValue(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getAthleteUser() {
        DatabaseReference AthleteDB = FirebaseDatabase.getInstance().getReference().child("Athlete");
        AthleteDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists() && !dataSnapshot.child("connection").child("nope").hasChild(currentUId) &&
                        !dataSnapshot.child("connection").child("yup").hasChild(currentUId) ) {
                    cards Item = new cards(dataSnapshot.getKey(), dataSnapshot.child("fName").getValue().toString(),
                            dataSnapshot.child("pic").getValue().toString(), dataSnapshot.child("bio").getValue().toString()
                            , dataSnapshot.child("UTR").getValue().toString(), dataSnapshot.child("nationalRanking").getValue().toString());

                    rowItems.add(Item);
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}

