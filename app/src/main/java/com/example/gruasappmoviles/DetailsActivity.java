package com.example.gruasappmoviles;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.drm.DrmStore;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    TextView mDetailsTitle, mDetails;
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ActionBar actionBar = getSupportActionBar();

        mDetailsTitle = findViewById(R.id.DetailsDate);
        mDetails = findViewById(R.id.details);
        mImageView = findViewById(R.id.DetailsPic);

        Intent intent = getIntent();
        String mTitle = intent.getStringExtra("Date");

        byte[] mBytes = getIntent().getByteArrayExtra("Image");
        Bitmap bitmap = BitmapFactory.decodeByteArray(mBytes, 0, mBytes.length);

        actionBar.setTitle(mTitle);

        mDetailsTitle.setText(mTitle);
        mImageView.setImageBitmap(bitmap);
    }
}
