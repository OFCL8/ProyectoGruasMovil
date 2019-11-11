package com.example.gruasappmoviles;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Holder extends RecyclerView.ViewHolder {

    ImageView mImageView;
    TextView mDate, mFormsID;

    public Holder(@NonNull View itemView) {
        super(itemView);

        this.mImageView = itemView.findViewById(R.id.historyPic);
        this.mDate = itemView.findViewById(R.id.Date);
        this.mFormsID = itemView.findViewById(R.id.FormsID);

    }
}
