package com.example.gruasappmoviles.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gruasappmoviles.CreateFormActivity;
import com.example.gruasappmoviles.Forms;
import com.example.gruasappmoviles.HistoryAdapter;
import com.example.gruasappmoviles.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;

import java.lang.reflect.Type;
import java.text.Normalizer;
import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DashboardFragment extends Fragment {

    private RecyclerView mRecyclerView;
    HistoryAdapter mHistoryAdapter;
    ArrayList<Forms> forms;
    FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore mFirestore;

    @Override
    public void onStart() {
        super.onStart();

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        mRecyclerView = root.findViewById(R.id.historyRV);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHistoryAdapter = new HistoryAdapter(getContext(), inicializarDatos());

        return root;
    }

    private ArrayList<Forms> inicializarDatos() {
        //Arraylist que guarda información de bitácora
        forms = new ArrayList<>();
        //Accede a las bitácoras generadas por el operador en sesión
        mFirestore.collection("Bitacoras").document("Operadores").collection(mFirebaseAuth.getUid())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    //Ciclo para asignar los campos de bitácora al recyclerview
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Forms f = new Forms();
                        String Date, Type, Plates, Company;
                        Date = document.get("Fecha").toString();
                        Plates = document.get("Placas").toString();
                        Type = document.get("Tipo").toString();
                        Company = document.get("Compañía").toString();
                        f.setDate(Date);
                        f.setType(Type);
                        f.setPlates(Plates);
                        f.setCompany(Company);
                        forms.add(f);
                    }
                    //Asigna todos los valores en el adapter y llena recyclerview
                    mHistoryAdapter = new HistoryAdapter(getContext(), forms);
                    mRecyclerView.setAdapter(mHistoryAdapter);
                } else {
                    Toast.makeText(getContext(), "Ha ocurrido un error!", Toast.LENGTH_SHORT).show();
                }
            }

        });

        return forms;
    }

    public void inicializaAdaptador() {

    }

}