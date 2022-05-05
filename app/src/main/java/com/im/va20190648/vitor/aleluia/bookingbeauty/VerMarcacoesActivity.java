package com.im.va20190648.vitor.aleluia.bookingbeauty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class VerMarcacoesActivity extends AppCompatActivity {

    TextView vazio;
    RecyclerView recyclerView;
    ArrayList<Marcacao> marcacoes;
    MyAdapter myAdapter;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_ver_marcacoes);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();

        vazio = findViewById(R.id.vazio);

        marcacoes = new ArrayList<Marcacao>();
        myAdapter = new MyAdapter(VerMarcacoesActivity.this, marcacoes);

        recyclerView.setAdapter(myAdapter);

        db.collection("marcacoes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        String documentId = documentSnapshot.getId();
                        Marcacao marcacao = documentSnapshot.toObject(Marcacao.class);
                        marcacao.setDocumentId(documentId);
                        marcacoes.add(marcacao);
                        myAdapter.notifyDataSetChanged();
                    }
                    if (task.getResult().isEmpty()) {
                        vazio.setText("Está um pouco vazio por aqui, faça mais marcações!!!");
                    }
                }
            }
        });
    }

    public void verDadosUtilizador(View v) {
        Intent i = new Intent(getApplicationContext(), VerMarcacoesTrabalhadorActivity.class);
        startActivity(i);
    }
}