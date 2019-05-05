package com.example.recruiterglobe.LoginAndProfile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.recruiterglobe.R;

public class login_option_activity extends AppCompatActivity {


    private Button mAthlete, mCoach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_option_activity);

        mAthlete = (Button) findViewById(R.id.athlete);
        mCoach = (Button) findViewById(R.id.coach);

        mAthlete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_option_activity.this, AtheleteLoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
        mCoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_option_activity.this, CoachLoginActivity.class);
                startActivity(intent);
                finish();
                return;

            }
        });
    }
}
