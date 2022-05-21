package com.im.va20190648.vitor.aleluia.bookingbeauty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.im.va20190648.vitor.aleluia.bookingbeauty.cliente.EcraInicialCliente;
import com.im.va20190648.vitor.aleluia.bookingbeauty.esteticista.EcraInicialEsteticista;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = new Intent(this, EcraInicialCliente.class);
        startActivity(i);
    }
}