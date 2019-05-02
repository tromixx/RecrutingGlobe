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

public class AtheleteLoginActivity extends AppCompatActivity {


        private Button mLogin;

        private TextView mlnkRegister;

        private EditText mEmail, mPassword;

        private FirebaseAuth mAuth;
        private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_athelete_login);

            mAuth = FirebaseAuth.getInstance();
            firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user != null){
                        Intent intent = new Intent(AtheleteLoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        return;
                    }
                }
            };

            mLogin = (Button) findViewById(R.id.athlete_login);

            mEmail = (EditText) findViewById(R.id.email);
            mPassword = (EditText) findViewById(R.id.password);

            mLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String email = mEmail.getText().toString();
                    final String password = mPassword.getText().toString();
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(AtheleteLoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(AtheleteLoginActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
                            }else{
                                Intent intent = new Intent(AtheleteLoginActivity.this, SecondMainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }


                    });
                }
            });
            mlnkRegister = (TextView) findViewById(R.id.lnkRegister);
            mlnkRegister.setMovementMethod(LinkMovementMethod.getInstance());
            mlnkRegister.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent = new Intent(AtheleteLoginActivity.this, AtheleteRegistrationActivity.class);
                    startActivity(intent);
                }
            });
    }
}
