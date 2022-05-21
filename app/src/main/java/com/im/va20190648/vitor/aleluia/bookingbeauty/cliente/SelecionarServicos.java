package com.im.va20190648.vitor.aleluia.bookingbeauty.cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.ListaServicosListener;
import com.im.va20190648.vitor.aleluia.bookingbeauty.R;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.ListaServicosAdapter;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.Servico;

import java.util.ArrayList;

public class SelecionarServicos extends AppCompatActivity implements ListaServicosListener {

    private RecyclerView recyclerView;
    private ArrayList<Servico> listaServicos = new ArrayList<Servico>();
    private ArrayList<Servico> servicosSelecionados = new ArrayList<Servico>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference servicos;
    ListenerRegistration registration=null;
    TextView preco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_servicos);

        //ligacao com a bd
        servicos = db.collection("servicos");

        preco = (TextView) findViewById(R.id.textView_Listar_Total);
        recyclerView = findViewById(R.id.recyclerViewListarServicos);
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
                        //Log.d("ListarServicos", document.getId() + " => " + document.getData());//Listar os servicos todos

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
                    //Log.d(TAG, "Falha ao carregar servicos");
                    //Toast.makeText(Servicos.this, "Sem conexão com a internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void mostrarServicos(){
        ListaServicosAdapter adapter = new ListaServicosAdapter(this, listaServicos, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onServicoChecked(ArrayList<Servico> servicos) {
        servicosSelecionados = servicos;
       Integer total = 0;
        for(Servico s: servicos){
            total += s.getPreco();
        }

        String precoString = "Total: "+total.toString()+"€";
        preco.setText(precoString);
    }

    public void onclickProsseguir(View v){
        if( servicosSelecionados.size() == 0 ){
            Toast.makeText(this, "Sem serviços selecionados", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}