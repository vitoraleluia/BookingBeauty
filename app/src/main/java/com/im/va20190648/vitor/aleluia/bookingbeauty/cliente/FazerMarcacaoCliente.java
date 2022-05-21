package com.im.va20190648.vitor.aleluia.bookingbeauty.cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.im.va20190648.vitor.aleluia.bookingbeauty.R;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.Servico;

import java.util.ArrayList;

public class FazerMarcacaoCliente extends AppCompatActivity {

    private ArrayList<Servico> servicosSelecionados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fazer_marcacao_cliente);

        //Obter os servicos selecionados pelo utilizador
        servicosSelecionados = (ArrayList<Servico>) getIntent().getSerializableExtra("servicosSelecionados");
    }
}