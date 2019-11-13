package com.example.gruasappmoviles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CreateFormActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    //Declarando variables miembro
    EditText marcatxt, tipotxt, añotxt, placastxt, colortxt, serietxt;
    String compañia = "";
    String contacto = "";
    FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore mFirestore;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CreateFormActivity.this, Home.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_form);

        Toolbar mTopToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);

        //Botón para guardar información de bitácora
        FloatingActionButton fab = findViewById(R.id.saveform);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String marca = marcatxt.getText().toString();
                final String tipo = tipotxt.getText().toString();
                final String año = añotxt.getText().toString();
                final String placas = placastxt.getText().toString();
                final String color = colortxt.getText().toString();
                final String serie = serietxt.getText().toString();
                final String userUID = mFirebaseAuth.getUid();

                //Revisar campos estan vacios
                if (!(marca.isEmpty() || tipo.isEmpty() || año.isEmpty() || placas.isEmpty() || color.isEmpty() || serie.isEmpty()
                        || compañia.equals("") || contacto.equals(""))){

                    Map<String, String> formsinfo = new HashMap<>();
                    //Obteniendo fecha y dando formato sencillo
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    String formattedDate = df.format(c);
                    //Agregando información de la bitácora
                    formsinfo.put("Marca",marca);
                    formsinfo.put("Tipo",tipo);
                    formsinfo.put("Año",año);
                    formsinfo.put("Placas",placas);
                    formsinfo.put("Color",color);
                    formsinfo.put("Serie",serie);
                    formsinfo.put("Compañía",compañia);
                    formsinfo.put("Contacto Realizado",contacto);
                    formsinfo.put("Fecha",formattedDate);

                    mFirestore.collection("Bitacoras").document("Operadores").collection(userUID)
                            .document(UUID.randomUUID().toString()).set(formsinfo).addOnCompleteListener(CreateFormActivity.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(CreateFormActivity.this, "Intente de nuevo!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                startActivity(new Intent(CreateFormActivity.this, Home.class));
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(CreateFormActivity.this, "Favor de llenar todos los campos!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        //Inflando widgets
        marcatxt = findViewById(R.id.marca_editText);
        tipotxt = findViewById(R.id.tipo_editText);
        añotxt = findViewById(R.id.año_editText);
        placastxt = findViewById(R.id.placas_editText);
        colortxt = findViewById(R.id.color_editText);
        serietxt = findViewById(R.id.serie_editText);
    }
    //Comportamiento Popup Menu
    public void popupMenu(View v, int popupmenu) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(popupmenu);
        popup.show();
    }
    //Metodo para menu desplegable para seleccion de estado
    public void showPopUpState(View v) {
        popupMenu(v,R.menu.popup_menu_state);
    }

    //Metodo para menu desplegable para seleccion de compañia
    public void showPopUpCompany(View v) {
        popupMenu(v,R.menu.popup_menu_company);
    }
    //Metodo para menu desplegable para seleccion de compañia
    public void showPopUpRealizedContact(View v) {
        popupMenu(v,R.menu.popup_menu_realized_contact);
    }

    //Verifica estado seleccionado
    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()){
            case R.id.item_Atlas:
                compañia = "Atlas";
                Toast.makeText(this, "Atlas", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_ABBA:
                compañia = "ABBA";
                Toast.makeText(this, "ABBA", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_Qualitas:
                compañia = "Qualitas";
                Toast.makeText(this, "Qualitas", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_GeneralSeguros:
                compañia = "General Seguros";
                Toast.makeText(this, "General Seguros", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_Yes:
                contacto = "Si";
                Toast.makeText(this, "Si", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_No:
                contacto = "No";
                Toast.makeText(this, "No", Toast.LENGTH_SHORT).show();
                return true;
            default: return false;
        }
    }
}
