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
import com.google.protobuf.StringValue;
import com.im.va20190648.vitor.aleluia.bookingbeauty.R;
import com.im.va20190648.vitor.aleluia.bookingbeauty.cliente.EditarDadosUtilizador;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.AdapterTrabalhador;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.EstadoMarcacao;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.Marcacao;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.Servico;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.Utilizador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class VerMarcacoesTrabalhadorActivity extends AppCompatActivity {

    TextView vazio;
    RecyclerView recyclerView;
    AdapterTrabalhador adapterTrabalhador;
    ArrayList<Marcacao> marcacoes;
    Date dataF, dataIni;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ver_marcacoes_trabalhador);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();

        vazio = findViewById(R.id.vazio);

        marcacoes = new ArrayList<Marcacao>();

        adapterTrabalhador = new AdapterTrabalhador(VerMarcacoesTrabalhadorActivity.this, marcacoes);

        recyclerView.setAdapter(adapterTrabalhador);

        db.collection("marcacoes")
                .whereNotIn("estado", Arrays.asList(EstadoMarcacao.VALIDADA, EstadoMarcacao.REJEITADA))
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().isEmpty()) {
                        vazio.setText(getString(R.string.BB_AgendaVazia));
                    }
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        String documentId = documentSnapshot.getId();

                        Timestamp timeIni = (Timestamp) documentSnapshot.get("dataInicio");
                        dataIni=timeIni.toDate();

                        Timestamp timeFim = (Timestamp) documentSnapshot.get("dataFim");
                        dataF=timeFim.toDate();

                        ArrayList<Servico> serv = (ArrayList<Servico>) documentSnapshot.get("servicos");
                        
                        Utilizador u = new Utilizador();
                        u.setNome(documentSnapshot.get("nome").toString());
                       // u.setEmail(documentSnapshot.get("email").toString());

                        Marcacao marcacao = new Marcacao(u,dataIni, dataF, Integer.parseInt(documentSnapshot.get("preco").toString()), serv, EstadoMarcacao.valueOf(documentSnapshot.get("estado").toString()));

                        marcacao.setDocumentId(documentId);
                        marcacoes.add(marcacao);
                        adapterTrabalhador.notifyDataSetChanged();
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