package com.example.gruasappmoviles.ui.dashboard;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import com.example.gruasappmoviles.CreateFormActivity;
import com.example.gruasappmoviles.Forms;
import com.example.gruasappmoviles.HistoryAdapter;
import com.example.gruasappmoviles.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
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
    String ID, Date, Plates, Company;
    ProgressDialog dialog;

    @Override
    public void onStart() {
        super.onStart();

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        dialog = ProgressDialog.show(getActivity(), "Cargando", "Por favor, espere...", true);
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
                .orderBy("Fecha", Query.Direction.DESCENDING)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    //Ciclo para asignar los campos de bitácora al recyclerview
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Forms f = new Forms();
                        Date = document.get("Fecha").toString();
                        Plates = document.get("Placas").toString();
                        Company = document.get("Compañía").toString();
                        ID = document.getId();
                        f.setID(ID);
                        f.setDate(Date);
                        f.setPlates(Plates);
                        f.setCompany(Company);
                        f.setImage(R.drawable.forms_history);
                        forms.add(f);
                    }
                    //Asigna todos los valores en el adapter y llena recyclerview
                    mHistoryAdapter = new HistoryAdapter(getContext(), forms);
                    new ItemTouchHelper(itemCallback).attachToRecyclerView(mRecyclerView);
                    mRecyclerView.setAdapter(mHistoryAdapter);
                    dialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "Ha ocurrido un error!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return forms;
    }

    ItemTouchHelper.SimpleCallback itemCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

        @Override
        public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,float dX, float dY,int actionState, boolean isCurrentlyActive){
            new RecyclerViewSwipeDecorator.Builder(getActivity(), c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeRightBackgroundColor(Color.RED)
                    .addSwipeRightActionIcon(R.drawable.ic_delete_black_24dp)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            forms.remove(position);
            mFirestore.collection("Bitacoras").document("Operadores").collection(mFirebaseAuth.getUid()).document(ID).delete();
            mHistoryAdapter.notifyItemChanged(position);
        }
    };
}