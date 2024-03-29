package com.example.gruasappmoviles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    //Declarando variables miembro
    EditText emailId, password;
    Button SignInBtn;
    TextView SignUpTextView;
    FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mFirebaseAuth = FirebaseAuth.getInstance();
        //Inflando widgets
        emailId = findViewById(R.id.signinemail_editText);
        password = findViewById(R.id.signinpassword_editText);
        SignUpTextView = findViewById(R.id.signup_textview);
        SignInBtn = findViewById(R.id.signin_button);
        spinner = findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if( mFirebaseUser != null ) {
                    Toast.makeText(MainActivity.this, "Bienvenido!", Toast.LENGTH_SHORT);
                    startActivity(new Intent(MainActivity.this, Home.class));
                }
                else {
                    Toast.makeText(MainActivity.this, "Por favor inicie sesión", Toast.LENGTH_SHORT);
                }
            }
        };
        SignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailId.getText().toString();
                String psw = password.getText().toString();
                //Revisar campos estan vacios
                if (email.isEmpty()) {
                    emailId.setError("Introduzca su correo");
                    emailId.requestFocus();
                }
                else if (psw.isEmpty()) {
                    password.setError("Introduzca una contraseña");
                    password.requestFocus();
                }
                else if (email.isEmpty() && psw.isEmpty()){
                    Toast.makeText(MainActivity.this, "Hay campos vacíos!!", Toast.LENGTH_SHORT).show();
                }
                else if (!(email.isEmpty() && psw.isEmpty())) {
                    spinner.setVisibility(View.VISIBLE);
                    mFirebaseAuth.signInWithEmailAndPassword(email,psw).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "No pudo iniciar sesión!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                startActivity(new Intent(MainActivity.this, Home.class));
                                spinner.setVisibility(View.GONE);
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(MainActivity.this, "Ha ocurrido un error!", Toast.LENGTH_SHORT).show();
                    spinner.setVisibility(View.GONE);
                }
            }
        });
        //Evento click en textview
        SignUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }
}
