package com.im.va20190648.vitor.aleluia.bookingbeauty.cliente;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.im.va20190648.vitor.aleluia.bookingbeauty.R;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.EstadoMarcacao;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.Marcacao;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.MyAdapter;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.Servico;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.Utilizador;
import com.im.va20190648.vitor.aleluia.bookingbeauty.esteticista.VerMarcacoesTrabalhadorActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class VerMarcacoesActivity extends AppCompatActivity {

    TextView vazio;
    RecyclerView recyclerView;
    ArrayList<Marcacao> marcacoes;

    Date dataF, dataIni;

    MyAdapter myAdapter;
    private FirebaseAuth auth= FirebaseAuth.getInstance();
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ver_marcacoes);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();

        vazio = findViewById(R.id.vazio);

        marcacoes = new ArrayList<Marcacao>();

        myAdapter = new MyAdapter(VerMarcacoesActivity.this, marcacoes);

        recyclerView.setAdapter(myAdapter);

        db.collection("marcacoes")
                .whereEqualTo("email", auth.getCurrentUser().getEmail().toString())
                //.orderBy("dataInicio", Query.Direction.ASCENDING)
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

                        Utilizador u = new Utilizador();
                        u.setNome(documentSnapshot.get("nome").toString());
                        u.setEmail(documentSnapshot.get("email").toString());

                        Marcacao marcacao = new Marcacao(u,dataIni, dataF, Integer.parseInt(documentSnapshot.get("preco").toString()), serv, EstadoMarcacao.valueOf(documentSnapshot.get("estado").toString()));

                        marcacao.setDocumentId(documentId);
                        marcacoes.add(marcacao);

                        myAdapter.notifyDataSetChanged();
                    }
                    if (task.getResult().isEmpty()) {
                        vazio.setText(R.string.BB_SemMarcacoes);
                    }
                }
            }
        });
    }

    public void onClickEditarDados(View v){
        Intent i = new Intent(this, EditarDadosUtilizador.class);
        startActivity(i);
    }

    public void onClickHomePage(View v){
        Intent i = new Intent(this, EcraInicialCliente.class);
        startActivity(i);
    }
}