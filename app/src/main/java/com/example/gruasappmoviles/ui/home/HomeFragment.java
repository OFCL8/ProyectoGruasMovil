package com.example.gruasappmoviles.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.gruasappmoviles.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class HomeFragment extends Fragment {

    FirebaseAuth mFirebaseAuth;
    String opEmail, opState;
    TextView opStateTxtV, opName;
    ImageView mImage;
    private FirebaseFirestore mFirestore;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        mFirestore = FirebaseFirestore.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirestore.collection("Users").document(mFirebaseAuth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                //Obtiene email de operador al iniciar sesión
                opEmail = documentSnapshot.getString("Email");
                opState = documentSnapshot.getString("State");
                opName = root.findViewById(R.id.text_opname);
                opStateTxtV = root.findViewById(R.id.text_opState);
                mImage = root.findViewById(R.id.imageState);
                opName.setText(opEmail);
                opStateTxtV.setText(opState);
                switch (opState)
                {
                    case "Disponible":
                        mImage.setImageResource(R.drawable.checkmark);
                        break;
                    case "Comiendo":
                        mImage.setImageResource(R.drawable.fast_food);
                        break;
                    case "En servicio":
                        mImage.setImageResource(R.drawable.tow_truck);
                        break;
                    default: break;

                }
            }
        });
        return root;
    }
}