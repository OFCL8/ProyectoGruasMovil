package com.example.gruasappmoviles;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Holder extends RecyclerView.ViewHolder {

    ImageView mImageView;
    TextView mDate, mType, mPlates, mCompany;

    public Holder(@NonNull View itemView) {
        super(itemView);

        this.mImageView = itemView.findViewById(R.id.historyPic);
        this.mDate = itemView.findViewById(R.id.Date);
        this.mType = itemView.findViewById(R.id.FormsType);
        this.mPlates = itemView.findViewById(R.id.Plates_data);
        this.mCompany = itemView.findViewById(R.id.Company_name);

    }
}
