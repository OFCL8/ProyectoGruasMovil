package com.example.gruasappmoviles;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView mImageView;
    TextView mDate, mType, mPlates, mCompany;
    ItemClickListener mItemClickListener;

    public Holder(@NonNull View itemView) {
        super(itemView);

        this.mImageView = itemView.findViewById(R.id.historyPic);
        this.mDate = itemView.findViewById(R.id.Date);
        this.mType = itemView.findViewById(R.id.FormsType);
        this.mPlates = itemView.findViewById(R.id.Plates_data);
        this.mCompany = itemView.findViewById(R.id.Company_name);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.mItemClickListener.onItemClickListener(v,getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener ic) {
        this.mItemClickListener = ic;
    }
}
