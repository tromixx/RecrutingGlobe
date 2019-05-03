package com.example.recruiterglobe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class arrayAdapter extends ArrayAdapter<cards> {

    Context context;

    public arrayAdapter(Context context, int resourceId, List<cards> items){
        super(context, resourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        cards card_item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView bio = (TextView) convertView.findViewById(R.id.bio);
        TextView university = (TextView) convertView.findViewById(R.id.university);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        TextView utr = (TextView) convertView.findViewById(R.id.utr);
        TextView natrank = (TextView) convertView.findViewById(R.id.natrank);

        name.setText(card_item.getName());
        bio.setText(card_item.getBio());
        utr.setText(card_item.getUtr());
        natrank.setText(card_item.getNatrank());



        Picasso.with(getContext()).load(card_item.getProfileImageUrl()).into(image);

        System.out.println("Profile!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        System.out.println(card_item.getProfileImageUrl());

        return convertView;
    }
}