package com.example.recruiterglobe;

import android.content.Intent;
import android.os.FileObserver;
import android.print.PrinterId;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

public class SecondMainActivity extends AppCompatActivity {

    private cards2 cards_data[];
    private arrayAdapter2 arrayAdapter;
    private int i;
    private Button mProfile;

    private FirebaseAuth mAuth;

    private String currentUId;

    private DatabaseReference coachDb;

    ListView listView;
    List<cards2> rowItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_main);

        coachDb = FirebaseDatabase.getInstance().getReference().child("coach");
        mAuth = FirebaseAuth.getInstance();
        currentUId = mAuth.getCurrentUser().getUid();

        getCoachUser();

        mProfile = (Button) findViewById(R.id.profile);

        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondMainActivity.this, AthleteProfileActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });


        rowItems = new ArrayList<cards2>();
        arrayAdapter = new arrayAdapter2(this, R.layout.item2, rowItems);



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
                cards2 obj = (cards2) dataObject;
                String userId = obj.getUserId();
                coachDb.child(userId).child("connection").child("nope").child(currentUId).setValue(true);
                Toast.makeText(SecondMainActivity.this, "left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                cards2 obj = (cards2) dataObject;
                String userId = obj.getUserId();
                coachDb.child(userId).child("connection").child("yup").child(currentUId).setValue(true);
                Toast.makeText(SecondMainActivity.this, "right", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(SecondMainActivity.this, "click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getCoachUser() {
        DatabaseReference coachDB = FirebaseDatabase.getInstance().getReference().child("coach");
        coachDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists() && !dataSnapshot.child("connection").child("nope").hasChild(currentUId) && !dataSnapshot.child("connection").child("yup").hasChild(currentUId) ) {
                    cards2 Item2 = new cards2(dataSnapshot.getKey(), dataSnapshot.child("fName").getValue().toString());

                    rowItems.add(Item2);
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




