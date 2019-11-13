package com.example.gruasappmoviles;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
    public void onBindViewHolder(@NonNull final Holder holder, int position) {
        holder.mDate.setText(Forms.get(position).getDate());
        holder.mPlates.setText(Forms.get(position).getPlates());
        holder.mCompany.setText(Forms.get(position).getCompany());
        holder.mID.setText(Forms.get(position).getID());
        holder.mImageView.setImageResource(Forms.get(position).getImage());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                String DetailsTitle = Forms.get(position).getDate();
                String IDForms = Forms.get(position).getID();
                BitmapDrawable bitmapDrawable = (BitmapDrawable)holder.mImageView.getDrawable();

                Bitmap bitmap = bitmapDrawable.getBitmap();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();//Image will get stream and bytes

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); //it will compress our image

                byte[] bytes = stream.toByteArray();

                //get our data with intent
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("Date", DetailsTitle);
                intent.putExtra("Image",bytes);
                intent.putExtra("ID", IDForms);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Forms.size();
    }
}
