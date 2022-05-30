package com.im.va20190648.vitor.aleluia.bookingbeauty.esteticista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.im.va20190648.vitor.aleluia.bookingbeauty.R;
import com.im.va20190648.vitor.aleluia.bookingbeauty.cliente.EditarDadosUtilizador;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.AdapterAgenda;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.AdapterTrabalhador;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.EstadoMarcacao;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.Marcacao;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.Servico;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.Utilizador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AgendaActivity extends AppCompatActivity {

    TextView vazio;
    RecyclerView recyclerView;
    AdapterAgenda adapterAgenda;
    ArrayList<Marcacao> marcacoes;
    Date dataF, dataIni;
    private FirebaseFirestore db;
    private String name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();

        vazio = findViewById(R.id.vazio);

        marcacoes = new ArrayList<Marcacao>();

        adapterAgenda = new AdapterAgenda(this, marcacoes);

        recyclerView.setAdapter(adapterAgenda);

        db.collection("marcacoesTrabalhadores")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                String documentId = documentSnapshot.getId();

                                Timestamp timeIni = (Timestamp) documentSnapshot.get("dataInicio");
                                dataIni=timeIni.toDate();

                                Timestamp timeFim = (Timestamp) documentSnapshot.get("dataFim");
                                dataF=timeFim.toDate();

                                ArrayList<Servico> serv = (ArrayList<Servico>) documentSnapshot.get("servicos");

                                Map<String, Object> groups = documentSnapshot.getData();
                                for (Map.Entry<String, Object> entry : groups.entrySet()) {
                                    if (entry.getKey().equals("cliente")) {
                                        Map<String, Object> nomes = (Map<String, Object>) entry.getValue();
                                        for (Map.Entry<String, Object> e : nomes.entrySet()) {
                                            if (e.getKey().equals("nome")) {
                                                name = e.getValue().toString();
                                            }
                                        }
                                    }
                                }

                                Utilizador u = new Utilizador();
                                u.setNome(name);
                                // u.setEmail(documentSnapshot.get("email").toString());

                                Marcacao marcacao = new Marcacao(u,dataIni, dataF, Integer.parseInt(documentSnapshot.get("preco").toString()), serv, EstadoMarcacao.valueOf(documentSnapshot.get("estado").toString()));

                                marcacao.setDocumentId(documentId);
                                marcacoes.add(marcacao);
                                adapterAgenda.notifyDataSetChanged();
                            }
                            if (task.getResult().isEmpty()) {
                                vazio.setText("Não há marcações a apresentar!!!");
                            }
                        }
                    }
                });
    }

    public void onClickEditarDados(View v){
        Intent i = new Intent(this, EditarDadosUtilizador.class);
        startActivity(i);
    }
}