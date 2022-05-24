package com.im.va20190648.vitor.aleluia.bookingbeauty.cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.im.va20190648.vitor.aleluia.bookingbeauty.R;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.Marcacao;
import com.im.va20190648.vitor.aleluia.bookingbeauty.esteticista.VerMarcacoesTrabalhadorActivity;
//TODO: Gonçalo dá fix nisto
//import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.MyAdapter;

import java.util.ArrayList;

public class VerMarcacoesActivity extends AppCompatActivity {

    TextView vazio;
    RecyclerView recyclerView;
    ArrayList<Marcacao> marcacoes;

    //TODO: Gonçalo dá fix nisto
    //MyAdapter myAdapter;

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
        //TODO: Gonçalo dá fix nisto
        //myAdapter = new MyAdapter(VerMarcacoesActivity.this, marcacoes);

        //TODO: Gonçalo dá fix nisto
        //recyclerView.setAdapter(myAdapter);

        db.collection("marcacoes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        String documentId = documentSnapshot.getId();
                        Marcacao marcacao = documentSnapshot.toObject(Marcacao.class);
                        //TODO: Gonçalo dá fix nisto
                        //marcacao.setDocumentId(documentId);
                        marcacoes.add(marcacao);
                        //TODO: Gonçalo dá fix nisto
                        //myAdapter.notifyDataSetChanged();
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

    public void onClickEditarDados(View v){
        Intent i = new Intent(this, EditarDadosUtilizador.class);
        startActivity(i);
    }
}