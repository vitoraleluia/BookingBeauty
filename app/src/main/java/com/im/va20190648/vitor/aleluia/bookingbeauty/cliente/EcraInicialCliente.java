package com.im.va20190648.vitor.aleluia.bookingbeauty.cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.im.va20190648.vitor.aleluia.bookingbeauty.R;

public class EcraInicialCliente extends AppCompatActivity {


    ImageView imageServicos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecra_inicial_cliente);

        imageServicos = findViewById(R.id.imageServicos);
        imageServicos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EcraInicialCliente.this, SelecionarServicos.class);
                startActivity(i);
            }
        });
    }

    public void onClickMarcacoes(View v){
        startActivity(new Intent(EcraInicialCliente.this, VerMarcacoesActivity.class));
    }

    public void onClickSobre(View v){
        //TODO: Depois do Tiago implementar esta classe
    }

    public void onClickEditarDados(View v){
        Intent i = new Intent(EcraInicialCliente.this, EditarDadosUtilizador.class);
        startActivity(i);
    }

}