package com.im.va20190648.vitor.aleluia.bookingbeauty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Servicos extends AppCompatActivity {

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
                        String nome = document.get("nome").toString();
                        Integer preco = Integer.getInteger(document.get("preco").toString());
                        Integer duracao = Integer.getInteger(document.get("duracao").toString());
                        Servico s = new Servico(nome,preco,duracao);
                        listaServicos.add(s);
                    }

                }else{
                    Log.d(TAG, "Falha ao carregar servicos");
                }
            }
        });

        //Mudar isto, nao esta a listar os servicos
        ServicosRecyclerViewAdapter adapter = new ServicosRecyclerViewAdapter(this, listaServicos);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}