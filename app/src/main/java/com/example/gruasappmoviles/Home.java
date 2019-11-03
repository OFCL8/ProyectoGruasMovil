package com.example.gruasappmoviles;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private AppBarConfiguration mAppBarConfiguration;
    FirebaseAuth mFirebaseAuth;
    TextView opStateTxtV;
    //Button opStateBtn;
    ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        opStateTxtV = findViewById(R.id.text_opState);
        //opStateBtn = findViewById(R.id.btn_opState);
        mImage = findViewById(R.id.imageState);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
/*
        opStateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), opStateBtn);
                popup.getMenuInflater().inflate(R.menu.popup_menu,popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.available:
                                opStateTxtV.setText("Disponible");
                                return true;
                            case R.id.eating:
                                opStateTxtV.setText("Comiendo");
                                return true;
                            case R.id.inservice:
                                opStateTxtV.setText("En servicio");
                                return true;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });*/
    }

    public void showPopUp(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menu,popup.getMenu());
        //popup.setOnMenuItemClickListener(this);
        //popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.available:
                //opStateTxtV.setText("Disponible");
                mImage.setImageResource(R.drawable.checkmark);
                Toast.makeText(Home.this, "Disponible!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.eating:
                //opStateTxtV.setText("Comiendo");
                mImage.setImageResource(R.drawable.fast_food);
                Toast.makeText(Home.this, "Comiendo!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.inservice:
                //opStateTxtV.setText("En servicio");
                mImage.setImageResource(R.drawable.tow_truck);
                Toast.makeText(Home.this, "En servicio!", Toast.LENGTH_SHORT).show();
                return true;
        }
        return true;
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
