package com.im.va20190648.vitor.aleluia.bookingbeauty.esteticista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.im.va20190648.vitor.aleluia.bookingbeauty.R;
import com.im.va20190648.vitor.aleluia.bookingbeauty.cliente.EcraInicialCliente;
import com.im.va20190648.vitor.aleluia.bookingbeauty.cliente.EditarDadosUtilizador;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.Servico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdicionarServico extends AppCompatActivity {

    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_servico);
        db = FirebaseFirestore.getInstance();
    }

    public void onClickAdicionar(View v){

        TextInputLayout nome = findViewById(R.id.textInputLayoutNomeServico);
        TextInputLayout duracao = findViewById(R.id.textInputLayoutDuracao);
        TextInputLayout preco = findViewById(R.id.textInputLayoutPreco);

        ArrayList<TextInputLayout> camposTexto = new ArrayList<>();

        camposTexto.add(nome);
        camposTexto.add(duracao);
        camposTexto.add(preco);

        for(TextInputLayout til: camposTexto){
            if(til.getEditText().getText().toString().trim().matches("")) {
                til.setError(getString(R.string.BB_CampoObrigatorio));
                return;
            }
        }

        Servico servicoAdicionar = new Servico(nome.getEditText().getText().toString(),
                Integer.parseInt(preco.getEditText().getText().toString()),
                Integer.parseInt(duracao.getEditText().getText().toString()));

        Map<String,Object> data = new HashMap<>();
        data.put("nome", servicoAdicionar.getNome());
        data.put("duracao", servicoAdicionar.getDuracao());
        data.put("preco", servicoAdicionar.getPreco());

        db.collection("servicos").add(data);

        Log.d("NaoInteressa", "onClickAdicionar: "+servicoAdicionar.toString());

        Intent i = new Intent(this, Servicos.class);
        startActivity(i);
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