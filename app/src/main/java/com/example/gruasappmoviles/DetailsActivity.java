package com.example.gruasappmoviles;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.drm.DrmStore;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.pdf.PdfDocument;
import android.icu.text.IDNA;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.print.pdf.PrintedPdfDocument;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gruasappmoviles.ui.dashboard.DashboardFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Locale;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

public class DetailsActivity extends AppCompatActivity {
    TextView mDate, mComp, mModel, mType, mYear, mPlates, mColor, mSeries, mContact, mEmail, opName, mIDForm;
    String mTitle, opEmail, IDForm;
    String Company, Model, Type, Plates, ColorF, Series, Contact, Year;
    ImageView mImageView;

    FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore mFirestore;

    private static final int STORAGE_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ActionBar actionBar = getSupportActionBar();
        //Permisos para PDF
        ActivityCompat.requestPermissions(DetailsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

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
        mTitle = intent.getStringExtra("Date");
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

        //Botón para PDF con información de bitácora
        FloatingActionButton fab = findViewById(R.id.topdf);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //we need to handle runtime permission for devices with marshmallow and above
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
                    //system OS >= Marshmallow(6.0), check if permission is enabled or not
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED){
                        //permission was not granted, request it
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, STORAGE_CODE);
                    }
                    else {
                        //permission already granted, call save pdf method
                        savePdf();
                    }
                }
                else {
                    //system OS < Marshmallow, call save pdf method
                    savePdf();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DetailsActivity.this, Home.class));
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
        mFirestore.collection("Bitacoras").document("Operadores").collection(mFirebaseAuth.getUid()).document(IDForm)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot != null && documentSnapshot.exists()) {
                        //Obtiene todos los datos de la bitácora
                            ColorF = documentSnapshot.getString("Color");
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
                            mColor.setText("Color: "+ColorF);
                            mSeries.setText("Serie: "+Series);
                            mContact.setText("Contacto Reliazado: "+Contact);
                }
            }
        });
    }

    private void savePdf() {
        //create object of Document class
        Document mDoc = new Document();
        //pdf file name
        String mFileName = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(System.currentTimeMillis());
        //pdf file path
        String mFilePath = Environment.getExternalStorageDirectory()+ "/" + mFileName + ".pdf";

        try {
            //create instance of PdfWriter class
            PdfWriter.getInstance(mDoc, new FileOutputStream(mFilePath));
            //open the document for writing
            mDoc.open();
            //get text from EditText i.e. mTextEt
            String mText = mIDForm.getText().toString();

            //add author of the document (optional)
            mDoc.addAuthor("Atif Pervaiz");

            //add paragraph to the document
            mDoc.add(new Paragraph(mText));

            //close the document
            mDoc.close();
            //show message that file is saved, it will show file name and file path too
            Toast.makeText(this, mFileName +".pdf\nis saved to\n"+ mFilePath, Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            //if any thing goes wrong causing exception, get and show exception message
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    //handle permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case STORAGE_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission was granted from popup, call savepdf method
                    savePdf();
                }
                else {
                    //permission was denied from popup, show error message
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
