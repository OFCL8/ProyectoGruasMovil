package com.example.gruasappmoviles;


import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryAdapter extends RecyclerView.Adapter<Holder> {

    Context context;
    ArrayList<Forms> Forms;

    public HistoryAdapter(Context c, ArrayList<Forms> forms) {
        this.context = c;
        this.Forms = forms;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_forms, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.mDate.setText(Forms.get(position).getDate());
        holder.mType.setText(Forms.get(position).getType());
        holder.mPlates.setText(Forms.get(position).getPlates());
        holder.mCompany.setText(Forms.get(position).getCompany());
    }

    @Override
    public int getItemCount() {
        return Forms.size();
    }
}
