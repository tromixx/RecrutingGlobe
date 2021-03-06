package com.example.recruiterglobe.LoginAndProfile;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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




import java.net.URL;


public class AthleteProfileActivity extends AppCompatActivity {


    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(AthleteProfileActivity.this, login_option_activity.class);
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
        Intent intent = new Intent(AthleteProfileActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }



    DatabaseReference athletedb;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;




    public ImageView profilePic;
    public TextView name;
    public TextView bio;
    public TextView fname;
    public TextView lname;
    public TextView UTR;
    public TextView award;
    public TextView city;
    public TextView country;
    public TextView link;
    public TextView nationalRanking;
    public TextView phoneNumber;
    public TextView state;
    public TextView team;


    public static class Athlete {

        public String UTR = "";
        public String award = "";
        public String bio = "";
        public String city = "";
        public String country = "";
        public String fName = "";
        public String lName = "";
        public String link = "";
        public String nationalRanking = "";
        public String phoneNumber = "";
        public String pic = "";
        public String state = "";
        public String team = "";

        public Athlete(String country, String lName, String city, String link, String bio, String pic, String team,
                       String UTR, String award, String fName, URL nationalRanking, String phoneNumber, String state) { };
        public Athlete(){};

    }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete_profile);


        profilePic        = findViewById(R.id.athlete_profile_pic);
        name              = findViewById(R.id.name);
        fname             = findViewById(R.id.fname);
        lname             = findViewById(R.id.lname);
        bio               = findViewById(R.id.bio);
        UTR               = findViewById(R.id.utr);
        award             = findViewById(R.id.award);
        city              = findViewById(R.id.city);
        country           = findViewById(R.id.country);
        link              = findViewById(R.id.link);
        nationalRanking   = findViewById(R.id.natrank);
        phoneNumber       = findViewById(R.id.phone);
        state             = findViewById(R.id.state);
        team              = findViewById(R.id.team);




        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        final String athleteId = currentUser.getUid();
        athletedb = FirebaseDatabase.getInstance().getReference().child("Athlete").child(athleteId);

        athletedb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Athlete athlete = snapshot.getValue(Athlete.class);
                //System.out.println(snapshot.getValue()); testing it pulls the right values
                //System.out.println(athlete.country); testing it pulls the right values

                Context context = getApplicationContext();
                Picasso.with(context).load(athlete.pic).into(profilePic);

                name.setText(athlete.fName + " " + athlete.lName);
                bio.setText(athlete.bio);
                fname.setText(athlete.fName);
                lname.setText(athlete.lName);

                UTR.setText(athlete.UTR);
                award.setText(athlete.award);
                city.setText(athlete.city);
                country.setText(athlete.country);
                link.setText(athlete.link);
                nationalRanking.setText(athlete.nationalRanking);
                phoneNumber.setText(athlete.phoneNumber);
                state.setText(athlete.state);
                team.setText(athlete.team);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

    }



}


