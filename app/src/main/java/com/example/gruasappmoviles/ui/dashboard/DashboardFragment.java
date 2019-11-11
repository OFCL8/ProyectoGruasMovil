package com.example.gruasappmoviles.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.text.Normalizer;
import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private RecyclerView mRecyclerView;
    HistoryAdapter mHistoryAdapter;
    ArrayList<Forms> forms;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        mRecyclerView = root.findViewById(R.id.historyRV);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //inicializarDatos();
        mHistoryAdapter = new HistoryAdapter(getContext(), inicializarDatos());
        inicializaAdaptador();

        //mRecyclerView.setAdapter(mHistoryAdapter);

        return root;
    }

    private ArrayList<Forms> inicializarDatos() {
        forms = new ArrayList<>();
        forms.add(new Forms("11/11/2019","1"));
        forms.add(new Forms("10/11/2019","2"));
        forms.add(new Forms("12/11/2019","3"));
        return forms;
    }

    public void inicializaAdaptador() {
        mHistoryAdapter = new HistoryAdapter(getContext(), forms);
        mRecyclerView.setAdapter(mHistoryAdapter);
    }

}