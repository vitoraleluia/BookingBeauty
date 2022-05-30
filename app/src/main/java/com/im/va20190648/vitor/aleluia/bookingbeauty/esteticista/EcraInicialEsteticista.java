package com.im.va20190648.vitor.aleluia.bookingbeauty.esteticista;

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
import com.im.va20190648.vitor.aleluia.bookingbeauty.cliente.EditarDadosUtilizador;

public class EcraInicialEsteticista extends AppCompatActivity {

    private TextView textViewNome;
    private FirebaseFirestore db;
    private CollectionReference utilizadores;
    private FirebaseAuth auth;
    private String emailuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecra_inicial_esteticista);

        textViewNome = findViewById(R.id.textViewNomeEsteticista);

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
                    textViewNome.setText(task.getResult().get("nome")+ ".");
                }else{
                    Log.d("QUERY_USER", "onComplete: Erro ao fazer query na BD");
                }
            }
        });
    }

    public void onClickServicos(View v){
        Intent servicos = new Intent(this, Servicos.class);
        startActivity(servicos);
    }

    public void onClickMarcacoes(View v){
        startActivity(new Intent(EcraInicialEsteticista.this, VerMarcacoesTrabalhadorActivity.class));
    }

    public void onClickAgenda(View v){
        startActivity(new Intent(EcraInicialEsteticista.this, AgendaActivity.class));
    }

    public void onClickEditarDados(View v){
        Intent i = new Intent(this, EditarDadosUtilizador.class);
        startActivity(i);
    }

}