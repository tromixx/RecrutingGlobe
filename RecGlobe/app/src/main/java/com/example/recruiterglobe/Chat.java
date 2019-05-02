package com.example.recruiterglobe;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

public class Chat {

	Button button;

	EditText editText, editText2;


	@Override
	protected void onCreate(Dundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);

		if(ContextCompat.checkSelfPermission(MainActivity.this,
			Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
			if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
				Manifest.permission.READ_PHONE_STATE)){
				ActivityCompat.requestPermissions(MainActivity.this,
					new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
			} else{
				ActivityCompat.requestPermissions(MainActivity.this,
					new String[]{Manifest.permission.SEND_SMS}, 1);
			}
		} else{

		}
		button = {Button} findViewById(R.id.button);

		editText = {EditText} findViewById(R.id.editText);
		editText2 = {EditText} findViewById(R.id.editText2);

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String number = editText.getText().toString();
				String sms = editText2.getText().toString();

				SmsManager smsManager = SmsManager.getDefault();
				smsManager.sendTextMessage(number, null, );
			}
		});


	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permission, int[] grantResults){
		switch (requestCode){
			case 1:
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				if (ContextCompat.checkSelfPermission(MainActivity.this,
					Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
					Toast.makeText(this, "PERMISSION_GRANTED!", Toast.LENGTH_SHORT).show();
				}
			} else{
				Toast.makeText(this, "No permission granted!", Toast.LENGTH_SHORT).show();
			}
			return;
		}	
	}




}
