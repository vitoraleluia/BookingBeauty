package com.im.va20190648.vitor.aleluia.bookingbeauty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class VerMarcacoesTrabalhadorActivity extends AppCompatActivity {

    TextView vazio;
    RecyclerView recyclerView;
    AdapterTrabalhador adapterTrabalhador;
    ArrayList<Marcacao> marcacoes;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_ver_marcacoes_trabalhador);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();

        vazio = findViewById(R.id.vazio);

        marcacoes = new ArrayList<Marcacao>();
        adapterTrabalhador = new AdapterTrabalhador(VerMarcacoesTrabalhadorActivity.this, marcacoes);

        recyclerView.setAdapter(adapterTrabalhador);

        db.collection("marcacoes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        String documentId = documentSnapshot.getId();
                        Marcacao marcacao = documentSnapshot.toObject(Marcacao.class);
                        marcacao.setDocumentId(documentId);
                        marcacoes.add(marcacao);
                        adapterTrabalhador.notifyDataSetChanged();
                    }
                    if (task.getResult().isEmpty()) {
                        vazio.setText("Não há marcações a apresentar!!!");
                    }
                }
            }
        });
    }

    public void verDadosUtilizador(View v) {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}