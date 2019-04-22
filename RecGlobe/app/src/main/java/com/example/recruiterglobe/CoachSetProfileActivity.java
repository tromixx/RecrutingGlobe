package com.example.recruiterglobe;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CoachSetProfileActivity extends AppCompatActivity {

    private Button mSave, mChoose;

    private EditText mFname, mLname, mPhone, mBio, mUni, mUniLnk;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_set_profile);

        mAuth = FirebaseAuth.getInstance();

        mSave = (Button) findViewById(R.id.save);

        mFname = (EditText) findViewById(R.id.fname);
        mLname = (EditText) findViewById(R.id.lname);
        mPhone = (EditText) findViewById(R.id.phone);
        mBio = (EditText) findViewById(R.id.bio);
        mUni = (EditText) findViewById(R.id.uni);
        mUniLnk = (EditText) findViewById(R.id.uniLink);

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fname = mFname.getText().toString();
                final String lname = mLname.getText().toString();
                final String bio = mBio.getText().toString();
                final String uni = mUni.getText().toString();
                final String uniLnk = mUniLnk.getText().toString();
                final String phone = PhoneNumberUtils.formatNumber(mPhone.getText().toString());

                String cId = mAuth.getCurrentUser().getUid();
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("coach").child("CID").setValue(cId);
                mDatabase.child("coach").child("First Name").setValue(fname);
                mDatabase.child("coach").child("Last Name").setValue(lname);
                mDatabase.child("coach").child("Bio").setValue(bio);
                mDatabase.child("coach").child("University").setValue(uni);
                mDatabase.child("coach").child("UniLink").setValue(uniLnk);
                mDatabase.child("coach").child("Phone Number").setValue(phone);


            }
        });
    }
}