package com.example.recruiterglobe;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class CoachSetProfileActivity extends AppCompatActivity {

    private Button mSave, mChoose;

    private EditText mPhone, mBio, mUni, mUniLnk;

    private ImageView mProfileImage;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    private DatabaseReference mDatabase;

    private Uri resultUri;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_set_profile);

        mAuth = FirebaseAuth.getInstance();
        String cId = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("coach").child(cId);

        mSave = (Button) findViewById(R.id.save);

        mPhone = (EditText) findViewById(R.id.phone);
        mBio = (EditText) findViewById(R.id.bio);
        mUni = (EditText) findViewById(R.id.uni);
        mUniLnk = (EditText) findViewById(R.id.uniLink);

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
        final String uni = mUni.getText().toString();
        final String uniLnk = mUniLnk.getText().toString();
        final String phone = PhoneNumberUtils.formatNumber(mPhone.getText().toString());

        Map coachProfile = new HashMap();
        coachProfile.put("bio", bio);
        coachProfile.put("university", uni);
        coachProfile.put("uniLink", uniLnk);
        coachProfile.put("phoneNumber", phone);
        mDatabase.updateChildren(coachProfile);
        if(resultUri != null){
            StorageReference filepath = FirebaseStorage.getInstance().getReference().child("pic").child(userId);
            Bitmap bitmap = null;

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUri);
            } catch (IOException e) {
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
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    Map userInfo = new HashMap();
                    userInfo.put("profileImageUrl", downloadUrl.toString());
                    mUserDatabase.updateChildren(userInfo);

                    finish();
                    return;
                }
            });
        }else{
            finish();
        }




    }
}