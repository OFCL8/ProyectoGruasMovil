package com.example.gruasappmoviles;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.drm.DrmStore;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.Year;

public class DetailsActivity extends AppCompatActivity {

    TextView mDate, mComp, mModel, mType, mYear, mPlates, mColor, mSeries, mContact, mEmail, opName, mIDForm;
    String opEmail, IDForm;
    ImageView mImageView;

    FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ActionBar actionBar = getSupportActionBar();

        mIDForm = findViewById(R.id.IDForms);
        mDate = findViewById(R.id.DetailsDate);
        mComp = findViewById(R.id.Companydetails);
        mModel = findViewById(R.id.Modeldetails);
        mType = findViewById(R.id.Typedetails);
        mYear = findViewById(R.id.Yeardetails);
        mPlates = findViewById(R.id.Platesdetails);
        mColor = findViewById(R.id.Colordetails);
        mSeries = findViewById(R.id.Seriesdetails);
        mContact = findViewById(R.id.Contactdetails);
        mEmail = findViewById(R.id.Emaildetails);

        mImageView = findViewById(R.id.DetailsPic);

        Intent intent = getIntent();
        String mTitle = intent.getStringExtra("Date");
        IDForm = intent.getStringExtra("ID");

        byte[] mBytes = getIntent().getByteArrayExtra("Image");
        Bitmap bitmap = BitmapFactory.decodeByteArray(mBytes, 0, mBytes.length);

        actionBar.setTitle(mTitle);
        mImageView.setImageBitmap(bitmap);
        mDate.setText(mTitle);
        mIDForm.setText("ID Bitácora: "+IDForm);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        getUserEmail();
        getFormsData();
    }

    public void getUserEmail() {
        mFirestore.collection("Users").document(mFirebaseAuth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                //Obtiene email de operador
                opEmail = documentSnapshot.getString("Email");
                opName = findViewById(R.id.Emaildetails);
                opName.setText("Correo: "+opEmail);
            }
        });
    }

    public void getFormsData() {
        //Accede a las bitácoras seleccionada por el operador
        mFirestore.collection("Bitacoras").document("Operadores").collection(mFirebaseAuth.getUid()).document(IDForm).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot != null && documentSnapshot.exists()) {
                        //Obtiene todos los datos de la bitácora
                            String Company, Model, Type, Plates, Color, Series, Contact, Year;
                            Color = documentSnapshot.getString("Color");
                            Plates = documentSnapshot.getString("Placas");
                            Type = documentSnapshot.getString("Tipo");
                            Company = documentSnapshot.getString("Compañía");
                            Series = documentSnapshot.getString("Serie");
                            Contact = documentSnapshot.getString("Contacto Realizado");
                            Year = documentSnapshot.getString("Año");
                            Model = documentSnapshot.getString("Marca");

                            //Asigna todos los valores en los textview
                            mComp.setText("Compañía: "+Company);
                            mModel.setText("Marca: "+Model);
                            mType.setText("Tipo: "+Type);
                            mYear.setText("Año: "+Year);
                            mPlates.setText("Placas: "+Plates);
                            mColor.setText("Color: "+Color);
                            mSeries.setText("Serie: "+Series);
                            mContact.setText("Contacto Reliazado: "+Contact);
                }
            }
        });
    }
}
