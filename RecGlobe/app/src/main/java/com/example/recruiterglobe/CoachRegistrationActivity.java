package com.example.recruiterglobe;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class CoachRegistrationActivity extends AppCompatActivity {

    private Button mRegister;

    private EditText mEmail, mPassword,mfName, mlName;

    private TextView mLogin;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_registration);

        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null){
                    Intent intent = new Intent(CoachRegistrationActivity.this, CoachSetProfileActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        mRegister = (Button) findViewById(R.id.coach_register);

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mfName = (EditText) findViewById(R.id.fname);
        mlName = (EditText) findViewById(R.id.lname);


        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();
                final String fname = mfName.getText().toString();
                final String lname = mlName.getText().toString();
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(CoachRegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(CoachRegistrationActivity.this, "Sign up error", Toast.LENGTH_SHORT).show();
                        }else{
                                String cId = mAuth.getCurrentUser().getUid();
                                DatabaseReference coachDB = FirebaseDatabase.getInstance().getReference().child("coach").child(cId);
                                coachDB.child("fName").setValue(fname);
                                coachDB.child("lName").setValue(lname);
                                Intent intent = new Intent(CoachRegistrationActivity.this, CoachSetProfileActivity.class);
                                startActivity(intent);
                            }
                    }
                });

            }
        });

        mLogin = (TextView) findViewById(R.id.lnkLogin);
        mLogin.setMovementMethod(LinkMovementMethod.getInstance());
        mLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(CoachRegistrationActivity.this, CoachLoginActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }
}
