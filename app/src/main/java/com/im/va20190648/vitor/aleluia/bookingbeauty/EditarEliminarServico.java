package com.im.va20190648.vitor.aleluia.bookingbeauty;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.Servico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditarEliminarServico extends AppCompatActivity {

    private FirebaseFirestore db;
    private Servico servicoParaEditar;

    private TextInputLayout nome;
    private TextInputLayout duracao;
    private TextInputLayout preco;
    private static final String NOME_TABELA = "servicos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_servico);
        setContentView(R.layout.activity_editar_eliminar_servico);
        db = FirebaseFirestore.getInstance();
        servicoParaEditar = (Servico) getIntent().getSerializableExtra("servicoSelecionado");

        Button buttonEliminar = findViewById(R.id.buttonEliminar);
        buttonEliminar.setBackgroundColor(Color.RED);

        nome = findViewById(R.id.textInputLayoutNomeServico);
        duracao = findViewById(R.id.textInputLayoutDuracao);
        preco = findViewById(R.id.textInputLayoutPreco);

        nome.getEditText().setText(servicoParaEditar.getNome().toString());
        duracao.getEditText().setText(servicoParaEditar.getDuracao().toString());
        preco.getEditText().setText(servicoParaEditar.getPreco().toString());
    }

    public void onClickEditar(View v){
        ArrayList<TextInputLayout> camposTexto = new ArrayList<>();

        camposTexto.add(nome);
        camposTexto.add(duracao);
        camposTexto.add(preco);

        for(TextInputLayout til: camposTexto){
            if(til.getEditText().getText().toString().trim().matches("")) {
                til.setError("O campo não pode estar vazio");
                return;
            }
        }

        Map<String,Object> data = new HashMap<>();
        data.put("nome", nome.getEditText().getText().toString().trim());
        data.put("duracao", duracao.getEditText().getText().toString().trim());
        data.put("preco", preco.getEditText().getText().toString().trim());

        db.collection(NOME_TABELA).document(servicoParaEditar.getId()).set(data, SetOptions.merge());

        Log.d("NaoInteressa", "onClickAdicionar: "+data.toString());
        voltarActivityServicos();
    }

    public void onClickApagar(View v){
        db.collection(NOME_TABELA).document(servicoParaEditar.getId()).delete();
        voltarActivityServicos();
    }

    private void voltarActivityServicos(){
        Intent i = new Intent(this, Servicos.class);
        startActivity(i);
    }
}