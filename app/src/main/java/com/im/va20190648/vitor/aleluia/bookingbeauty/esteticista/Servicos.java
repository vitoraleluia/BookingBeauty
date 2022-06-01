package com.im.va20190648.vitor.aleluia.bookingbeauty.esteticista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.im.va20190648.vitor.aleluia.bookingbeauty.R;
import com.im.va20190648.vitor.aleluia.bookingbeauty.cliente.EditarDadosUtilizador;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.Servico;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.ServicoListener;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.ServicosRecyclerViewAdapter;

import java.util.ArrayList;

public class Servicos extends AppCompatActivity implements ServicoListener {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference servicos;
    ListenerRegistration registration=null;
    RecyclerView recyclerView;

    String TAG = "Servicos_DEBUG";
    ArrayList<Servico> listaServicos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicos);

        //ligacao com a bd
        servicos = db.collection("servicos");

        recyclerView = findViewById(R.id.recyclerViewServicos);
    }

    @Override
    protected void onStart() {
        listaServicos.clear();
        super.onStart();

        //Ir buscar todos os servicos
        if (registration != null)
            registration.remove();

        servicos.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());//Listar os servicos todos

                        //Colocar todos os servicos em classes
                        String id = document.getId();
                        String nome = document.get("nome").toString();
                        Integer preco = Integer.parseInt(document.get("preco").toString());
                        Integer duracao = Integer.parseInt(document.get("duracao").toString());
                        Servico s = new Servico(id,nome,preco,duracao);
                        listaServicos.add(s);
                    }

                    //Após adicionar todos os servicos deve mostrá-los
                    mostrarServicos();

                }else{
                    Log.d(TAG, "Falha ao carregar servicos");
                    Toast.makeText(Servicos.this, "Sem conexão com a internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void mostrarServicos(){
        ServicosRecyclerViewAdapter adapter = new ServicosRecyclerViewAdapter(this, listaServicos, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void onClickAdicionar(View v){
        //Quando clica em adicionar servico deve ser redirecionado para adicionar servicos
        Intent i = new Intent(this, AdicionarServico.class);
        startActivity(i);

    }

    @Override
    public void onServicoClick(int position) {
        Servico servicoSelecionado = listaServicos.get(position);
        Intent editarServico = new Intent(this, EditarEliminarServico.class);
        editarServico.putExtra("servicoSelecionado", servicoSelecionado);
        startActivity(editarServico);

        //Enviar para o editar/eliminar servico
    }

    public void onClickEditarDados(View v){
        Intent i = new Intent(this, EditarDadosUtilizador.class);
        startActivity(i);
    }

    public void onClickHomePage(View v){
        Intent i = new Intent(this, EcraInicialEsteticista.class);
        startActivity(i);
    }
}