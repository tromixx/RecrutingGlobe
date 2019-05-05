package com.example.recruiterglobe.LoginAndProfile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recruiterglobe.R;
import com.example.recruiterglobe.Swipe.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class CoachProfileActivity extends AppCompatActivity {

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(CoachProfileActivity.this, login_option_activity.class);
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
        Intent intent = new Intent(CoachProfileActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    DatabaseReference coachdb;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    public ImageView profilePic;
    public TextView name;
    public TextView bio;
    public TextView fname;
    public TextView lname;
    public TextView university;
    public TextView link;
    public TextView phoneNumber;



    public static class Coach {


        public String bio = "";
        public String university = "";
        public String fName = "";
        public String lName = "";
        public String link = "";
        public String phoneNumber = "";
        public String pic = "";

        public Coach(String university, String lName, String link, String bio, String pic,
                       String fName, String phoneNumber) {};
        public Coach(){};

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_profile);

        profilePic        = findViewById(R.id.coach_profile_pic);
        name              = findViewById(R.id.name);
        fname             = findViewById(R.id.fname);
        lname             = findViewById(R.id.lname);
        bio               = findViewById(R.id.bio);
        link              = findViewById(R.id.link);
        phoneNumber       = findViewById(R.id.phone);
        university        = findViewById(R.id.university);


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        final String athleteId = currentUser.getUid();
        coachdb = FirebaseDatabase.getInstance().getReference().child("coach").child(athleteId);

        coachdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Coach coach = snapshot.getValue(Coach.class);

                Context context = getApplicationContext();
                System.out.println(snapshot.getValue());
                Picasso.with(context).load(coach.pic).into(profilePic);

                name.setText(coach.fName + " " + coach.lName);
                bio.setText(coach.bio);
                fname.setText(coach.fName);
                lname.setText(coach.lName);
                university.setText(coach.university);
                link.setText(coach.link);
                phoneNumber.setText(coach.phoneNumber);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

    }




}










