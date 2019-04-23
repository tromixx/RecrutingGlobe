package com.example.recruiterglobe;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AthleteSetProfileActivity extends AppCompatActivity {

    private Button mSave;

    private EditText mPhone, mBio, mCity, mState, mCountry, mNranking, mUTR, mAward, mTeam, mLink;

    private ImageView mProfileImage;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    private DatabaseReference mDatabase;

    private String aId;

    private Uri resultUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete_set_profile);

        mAuth = FirebaseAuth.getInstance();
        aId = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Athlete").child(aId);

        mSave = (Button) findViewById(R.id.save);

        mPhone = (EditText) findViewById(R.id.phone);
        mBio = (EditText) findViewById(R.id.bio);
        mCity = (EditText) findViewById(R.id.city);
        mState = (EditText) findViewById(R.id.state);
        mCountry = (EditText) findViewById(R.id.country);
        mNranking = (EditText) findViewById(R.id.nranking);
        mUTR = (EditText) findViewById(R.id.utr);
        mAward = (EditText) findViewById(R.id.award);
        mTeam = (EditText) findViewById(R.id.team);
        mLink = (EditText) findViewById(R.id.link);

        mProfileImage = (ImageView) findViewById(R.id.profileImage);

        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });


        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }


    private void saveData() {
        final String bio = mBio.getText().toString();
        final String city = mCity.getText().toString();
        final String state = mState.getText().toString();
        final String country = mCountry.getText().toString();
        final String nRanking = mNranking.getText().toString();
        final String UTR = mUTR.getText().toString();
        final String award = mAward.getText().toString();
        final String team = mTeam.getText().toString();
        final String link = mLink.getText().toString();
        final String phone = PhoneNumberUtils.formatNumber(mPhone.getText().toString());

        Map atheleteProfile = new HashMap();
        atheleteProfile.put("bio", bio);
        atheleteProfile.put("city", city);
        atheleteProfile.put("state", state);
        atheleteProfile.put("phoneNumber", phone);
        atheleteProfile.put("country", country);
        atheleteProfile.put("nationalRanking", nRanking);
        atheleteProfile.put("UTR", UTR);
        atheleteProfile.put("award", award);
        atheleteProfile.put("team", team);
        atheleteProfile.put("link", link);
        atheleteProfile.put("phoneNumber", phone);
        mDatabase.updateChildren(atheleteProfile);
        if (resultUri != null) {
            StorageReference filepath = FirebaseStorage.getInstance().getReference().child("pic").child(aId);
            Bitmap bitmap = null;

            try{
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUri);
            }catch(IOException e){
                e.printStackTrace();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = filepath.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    finish();
                }
            });
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful());
                    Uri downloadUrl = urlTask.getResult();

                    final String sdownload_url = String.valueOf(downloadUrl);

                    Log.d("The URL",sdownload_url);


                    Map atheleteProfile = new HashMap();
                    atheleteProfile.put("pic", sdownload_url);
                    mDatabase.updateChildren(atheleteProfile);

                    finish();
                    return;
                }
            });
        }else{
            finish();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            final Uri imageUri = data.getData();
            resultUri = imageUri;
            mProfileImage.setImageURI(resultUri);
        }
    }
}
