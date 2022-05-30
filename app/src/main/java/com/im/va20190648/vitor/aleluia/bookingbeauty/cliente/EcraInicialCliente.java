package com.im.va20190648.vitor.aleluia.bookingbeauty.cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.im.va20190648.vitor.aleluia.bookingbeauty.R;

public class EcraInicialCliente extends AppCompatActivity {

    private TextView textViewNome;
    private FirebaseFirestore db;
    private CollectionReference utilizadores;
    private FirebaseAuth auth;
    private String emailuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecra_inicial_cliente);

        textViewNome = findViewById(R.id.textViewNomeCliente);
        textViewNome.setText("Olá");

        db = FirebaseFirestore.getInstance();
        utilizadores = db.collection("utilizadores");

        auth = FirebaseAuth.getInstance();
        emailuser = auth.getCurrentUser().getEmail();

    }

    @Override
    protected void onStart() {
        super.onStart();

        utilizadores.document(emailuser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    textViewNome.setText("Olá, " + task.getResult().get("nome")+ ".");
                }else{
                    Log.d("QUERY_USER", "onComplete: Erro ao fazer query na BD");
                }
            }
        });
    }

    public void onClickMarcacoes(View v){
        startActivity(new Intent(EcraInicialCliente.this, VerMarcacoesActivity.class));
    }

    public void onClickSobre(View v){
        //TODO: Depois do Tiago implementar esta classe
        startActivity(new Intent(this, EcraSobreActivity.class));
    }

    public void onClickServicos(View v){
        Intent i = new Intent(EcraInicialCliente.this, SelecionarServicos.class);
        startActivity(i);
    }

    public void onClickEditarDados(View v){
        Intent i = new Intent(EcraInicialCliente.this, EditarDadosUtilizador.class);
        startActivity(i);
    }

}