//I NEED TO COPY MATCHES: VIEWHOLDER, OBJECT, ADAPTER
package com.example.recruiterglobe.Chat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.recruiterglobe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mChatAdaper;
    private RecyclerView.LayoutManager mMatcheLayoutManager;

    private EditText mSendEditText;

    private Button mSendButton;

    private String currentUserID, matchId, chatId;

    DatabaseReference mDatabaseUser, mDatabaseChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        matchId = getIntent().getExtras().getString( key:"matchId");

        currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child("currentUserID").child("connections").child("matches").child(matchId).child("chatId");
        mDatabaseChat = FirebaseDatabase.getInstance().getReference().child("Chat");

        getChatId();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnable(false);
        mRecyclerView.setHasFixedSize(true);
        mMatcheLayoutManager = new LinearLayoutManager(context: ChatActivity.this);
        mRecyclerView.setLayoutManager(mMatcheLayoutManager);
        mChatAdaper = new ChatAdaper(getDataSetChat(), context: ChatActivity.this);
        mRecyclerView.setAdapter(mChatAdaper);

        mSendEditText = findViewById(R.id.message);
        mSendButton = findViewById(R.id.send);

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

            Map newMessage = new HashMap()
            newMessage.put( k: "createdByUser", currentUserID);
            newMessage.put( k: "text", sendMessageText);

            newMessageDb.setValue(newMessage);
        }
        mSendEditText.setText(null);

    }

    private void getChatId(){
        mDatabaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    chatId = dataSnapshot.getValue().toString();
                    mDatabaseChat = mDatabaseChat.child(chatId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }





    private ArrayList<ChatObject> resultsChats = new ArrayList<~>();
    private List<ChatObject> getDataSetChat() { return resultsChats; }
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