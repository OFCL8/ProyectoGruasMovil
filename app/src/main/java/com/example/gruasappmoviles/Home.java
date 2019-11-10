package com.example.gruasappmoviles;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private AppBarConfiguration mAppBarConfiguration;
    FirebaseAuth mFirebaseAuth;

    TextView opStateTxtV, opName;
    ImageView mImage;
    String opEmail, opState;
    private FirebaseFirestore mFirestore;
    String state;

    private void getUserEmailAndState() {
        mFirestore.collection("Users").document(mFirebaseAuth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                //Obtiene email de operador al iniciar sesión
                opEmail = documentSnapshot.getString("Email");
                opState = documentSnapshot.getString("State");
                opName = findViewById(R.id.text_opname);
                opStateTxtV = findViewById(R.id.text_opState);
                opName.setText(opEmail);
                opStateTxtV.setText(opState);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getUserEmailAndState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserEmailAndState();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Botón para abrir pantalla de crear bitácora
        FloatingActionButton fab = findViewById(R.id.saveform);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intToCreate = new Intent(Home.this, CreateFormActivity.class);
                startActivity(intToCreate);
            }
        });
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_dashboard, R.id.nav_info, R.id.action_settings)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        mFirestore = FirebaseFirestore.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
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


    //Verifica estado seleccionado
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        opStateTxtV = findViewById(R.id.text_opState);
        mImage = findViewById(R.id.imageState);

        switch (item.getItemId()){
            case R.id.item_available:
                opStateTxtV.setText("Disponible");
                mImage.setImageResource(R.drawable.checkmark);
                state = opStateTxtV.getText().toString();
                mFirestore.collection("Users").document(mFirebaseAuth.getCurrentUser().getUid()).update("State",state);
                return true;
            case R.id.item_eating:
                opStateTxtV.setText("Comiendo");
                mImage.setImageResource(R.drawable.fast_food);
                state = opStateTxtV.getText().toString();
                mFirestore.collection("Users").document(mFirebaseAuth.getCurrentUser().getUid()).update("State",state);
                return true;
            case R.id.item_onservice:
                opStateTxtV.setText("En servicio");
                mImage.setImageResource(R.drawable.tow_truck);
                state = opStateTxtV.getText().toString();
                mFirestore.collection("Users").document(mFirebaseAuth.getCurrentUser().getUid()).update("State",state);
                return true;
            case R.id.item_Atlas:
                Toast.makeText(this, "Atlas", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_ABBA:
                Toast.makeText(this, "ABBA", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_Qualitas:
                Toast.makeText(this, "Qualitas", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_GeneralSeguros:
                Toast.makeText(this, "General Seguros", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_Yes:
                Toast.makeText(this, "Si", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_No:
                Toast.makeText(this, "No", Toast.LENGTH_SHORT).show();
                return true;
            default: return false;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intLogOut = new Intent(Home.this, MainActivity.class);
                startActivity(intLogOut);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
