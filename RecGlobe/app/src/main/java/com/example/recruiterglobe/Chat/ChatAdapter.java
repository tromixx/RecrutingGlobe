//INCLOMPLETE
package com.example.recruiterglobe.Chat;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;

import java.util.List;

public class ChatAdapter {
    private List<ChatObject> chatList;
    //private Context context;


    public ChatAdapter(List<ChatObject> matchesList, Context context){
        this.chatList = matchesList;
        //this.context = context;
    }

    //@Override
    public ChatViewHolders onCreateViewHolder(ViewGroup parent, int viewType){

        ViewGroup root;
        Object attachToRoot;
        //View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, root: null, attachToRoot: false);
        //RecyclerView.LayoutParams lp = new RecyclerView, LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //layoutView.setLayoutParams(lp);
        //ChatViewHolders rcv = new ChatViewHolders(layoutView);
        Object rcv = null;


        return (ChatViewHolders) rcv;
    }


    //@Override
    public void onBindViewHolder(ChatViewHolders holder, int position){
        holder.mMessage.setText(chatList.get(position).getMessage());
        if(chatList.get(position).getCurrentUser()){
            holder.mMessage.setGravity(Gravity.END);
            holder.mMessage.setTextColor(Color.parseColor( "#404040"));
            //holder.mContainer.setBackgroundColor(Color.parseColor("#F4F4F4"));
        }else{
            holder.mMessage.setGravity(Gravity.START);
            holder.mMessage.setTextColor(Color.parseColor( "#FFFFFF"));
            //holder.mContainer.setBackgroundColor(Color.parseColor("#2DB4C8"));
        }

    }

    //@Override
    public int getItemCount() {
        return this.chatList.size();
    }
}
