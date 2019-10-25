package com.tetra.biometrics;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    private ArrayList<InterestPlace> mdonationlist;
    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_view_layout2,parent,false);
        ExampleViewHolder evh =new ExampleViewHolder(v);
        return evh;
    }

    public ExampleAdapter(ArrayList<InterestPlace> donationlist){
        mdonationlist=donationlist;

    }


    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder exampleViewHolder, int i) {
        InterestPlace item = mdonationlist.get(i);
        exampleViewHolder.textview1.setText("Place "+ item.name);
        exampleViewHolder.textview2.setText("Address"+ item.address);

    }

    @Override
    public int getItemCount() {
        return mdonationlist.size();
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{

        public TextView textview1;
        public TextView textview2;
        public ExampleViewHolder(@NonNull View itemView) {


            super(itemView);
            textview1 = itemView.findViewById(R.id.info_text);
            textview2 = itemView.findViewById(R.id.info_text1);
        }
    }


}
