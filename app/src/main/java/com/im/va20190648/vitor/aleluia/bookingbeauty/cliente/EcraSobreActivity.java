package com.im.va20190648.vitor.aleluia.bookingbeauty.cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.im.va20190648.vitor.aleluia.bookingbeauty.R;

public class EcraSobreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecra_sobre);
    }

    public void onClickDirecoes(View view) {
        Uri location = Uri.parse("geo:0,0?q=Av. do Empresário, Campus da Talagueira, Zona do Lazer, 6000-767 Castelo Branco");

        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
        startActivity(mapIntent);
    }

    public void onClickEditarDados(View v){
        Intent i = new Intent(this, EditarDadosUtilizador.class);
        startActivity(i);
    }
}