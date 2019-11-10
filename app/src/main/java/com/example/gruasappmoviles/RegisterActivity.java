package com.example.gruasappmoviles;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    //Declarando variables miembro
    EditText emailId, password, confpass, tel;
    Button SignUpBtn;
    TextView SignInTextView;
    FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        //Inflando widgets
        emailId = findViewById(R.id.regemail_editText);
        password = findViewById(R.id.marca_editText);
        confpass = findViewById(R.id.tipo_editText);
        tel = findViewById(R.id.año_editText);
        SignInTextView = findViewById(R.id.signin_textview);
        SignUpBtn = findViewById(R.id.register_button);
        //Metodo click
        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailId.getText().toString();
                final String psw = password.getText().toString();
                final String psw2 = confpass.getText().toString();
                final String telephone = tel.getText().toString();
                //Revisar campos estan vacios
                if (email.isEmpty()) {
                    emailId.setError("Introduzca un correo");
                    emailId.requestFocus();
                }
                else if (psw.isEmpty()) {
                    password.setError("Introduzca una contraseña");
                    password.requestFocus();
                }
                else if (!psw.equals(psw2)) {
                    Toast.makeText(RegisterActivity.this, "La contraseña debe coincidir!", Toast.LENGTH_SHORT).show();
                }
                else if (email.isEmpty() || psw.isEmpty() || psw2.isEmpty() || telephone.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Hay campos vacíos!", Toast.LENGTH_SHORT).show();
                }
                else if (!(email.isEmpty() && psw.isEmpty() && psw2.isEmpty() && telephone.isEmpty())) {
                    mFirebaseAuth.createUserWithEmailAndPassword(email,psw).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Intente de nuevo!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                startActivity(new Intent(RegisterActivity.this, Home.class));
                                Map<String, String> userinfo = new HashMap<>();

                                userinfo.put("Email",email);
                                userinfo.put("Password",psw);
                                userinfo.put("Phone",telephone);
                                mFirestore.collection("Users").document(mFirebaseAuth.getCurrentUser().getUid()).set(userinfo);
                                startActivity(new Intent(RegisterActivity.this, Home.class));
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "Ha ocurrido un error!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Evento click en textview
        SignInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });
    }
}
