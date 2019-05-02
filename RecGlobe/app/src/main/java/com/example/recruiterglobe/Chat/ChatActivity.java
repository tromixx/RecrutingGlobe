//I NEED TO COPY MATCHES: VIEWHOLDER, OBJECT, ADAPTER
package com.example.recruiterglobe.Chat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.recruiterglobe.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;




public class ChatActivity extends AppCompatActivity {

    Object context;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mChatAdaper;
    private RecyclerView.LayoutManager mMatcheLayoutManager;


    private EditText mSendEditText;

    private String currentUserID, matchId, chatId;

    DatabaseReference mDatabaseUser, mDatabaseChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        String key;
        //matchId = getIntent().getExtras().getString( key:"matchId");

        //currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child("currentUserID").child("connections").child("matches").child(matchId).child("chatId");
        mDatabaseChat = FirebaseDatabase.getInstance().getReference().child("Chat");
//key setNestedScrollEnable ChatAdapter context k List
        getChatId();

        //mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(false);
        //mMatcheLayoutManager = new LinearLayoutManager(context: ChatActivity.this);
        mRecyclerView.setLayoutManager(mMatcheLayoutManager);
        //mChatAdaper = new ChatAdapter(getDataSetChat(), context: ChatActivity.this);
        mRecyclerView.setAdapter(mChatAdaper);

        mSendEditText = findViewById(R.id.message);
        Button mSendButton = findViewById(R.id.send);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }

        });

    }

    private void sendMessage() {
        String sendMessageText = mSendEditText.getText().toString();

        if(!sendMessageText.isEmpty()){
            DatabaseReference newMessageDb = mDatabaseChat.push();

            Map newMessage = new HashMap();
            String k;
            //newMessage.put( k: "createdByUser", currentUserID);
            //newMessage.put( k: "text", sendMessageText);

            newMessageDb.setValue(newMessage);
        }
        mSendEditText.setText(null);

    }

    private void getChatId(){
        mDatabaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    //chatId = dataSnapshot.getValue().toString();
                    mDatabaseChat = mDatabaseChat.child(chatId);
                    getChatMessages();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getChatMessages() {
        mDatabaseChat.addChildEventListener(new ChildEventListener()

        {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()){
                    String message = null;
                    String createdByUser = null;

                    dataSnapshot.child("text").getValue();//message = dataSnapshot.child("text").getValue().toString();
                    dataSnapshot.child("createByUser").getValue();//createdByUser = dataSnapshot.child("createdByUser").getValue().toString();

                    //if(message!=null && createdByUser!=null){
                        //Boolean currentUserBoolean = false;
                        //if(createdByUser.equals(currentUserID)){
                            //currentUserBoolean = true;
                        //}
                        //ChatObject newMessage = new ChatObject(message, currentUserBoolean);
                        //resultsChat.add(newMessage);
                        //mChatAdaper.notifyDataSetChanged();

                    //}
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


    //private ArrayList<ChatObject> resultsChat = new ArrayList<~>();
    //private List<ChatObject> getDataSetChat() { return resultsChat; }
}
/*

//MATCHES VIEW HOLDER
mMatchImage = Image

@override
public void onCLick(View view) {
    Intent intent = new Ontent(view.getContext()), ChatActivity.class);
    Bundle b = new Bundle();
    b.putString("matchId", mMatchId.getText().toString());
    intent.putExtras(b);
    view.getContext().startActivity(intent);
}
 */