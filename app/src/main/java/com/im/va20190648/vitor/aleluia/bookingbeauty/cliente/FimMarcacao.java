package com.im.va20190648.vitor.aleluia.bookingbeauty.cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;

import com.im.va20190648.vitor.aleluia.bookingbeauty.R;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.Marcacao;

import java.util.Calendar;

public class FimMarcacao extends AppCompatActivity {

    private Marcacao marcacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fim_marcacao);

        marcacao = (Marcacao) getIntent().getSerializableExtra("marcacao");
    }

    public void onClickAddCalendario(View v) {
        // Event is on January 23, 2021 -- from 7:30 AM to 10:30 AM.
        Intent calendarIntent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
        Calendar beginTime = Calendar.getInstance();
        beginTime.setTime(marcacao.getDataInicio());

        Calendar endTime = Calendar.getInstance();
        endTime.setTime(marcacao.getDataFim());
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.Events.TITLE, "Marcação Booking Beauty");
        calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Campus da Talagueira");
        startActivity(calendarIntent);
    }

    public void onClickPaginaInicial(View v) {
        Intent i = new Intent(this, EcraInicialCliente.class);
        startActivity(i);
    }

    public void onClickEditarDados(View v){
        Intent i = new Intent(this, EditarDadosUtilizador.class);
        startActivity(i);
    }

    public void onClickHomePage(View v){
        Intent i = new Intent(this, EcraInicialCliente.class);
        startActivity(i);
    }
}