package com.im.va20190648.vitor.aleluia.bookingbeauty.esteticista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.im.va20190648.vitor.aleluia.bookingbeauty.R;

public class EcraInicialEsteticista extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecra_inicial_esteticista);
    }

    public void onClickServicos(View v){
        Intent servicos = new Intent(this, Servicos.class);
        startActivity(servicos);
    }
}