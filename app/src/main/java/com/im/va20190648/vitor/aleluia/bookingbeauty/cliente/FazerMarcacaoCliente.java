package com.im.va20190648.vitor.aleluia.bookingbeauty.cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.DateTime;
import com.im.va20190648.vitor.aleluia.bookingbeauty.R;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.EstadoMarcacao;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.Marcacao;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.Servico;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.Utilizador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FazerMarcacaoCliente extends AppCompatActivity {

    private ArrayList<Servico> servicosSelecionados;
    private CalendarView calendario;
    private Spinner spinner;

    private Date dataSelecionada;
    int ano, mes, dia;
    private ArrayList<Marcacao> listaMarcacoes = new ArrayList<>();
    private ArrayList<String> horarioFuncionamento = new ArrayList<>(
            Arrays.asList("9:00","9:30","10:00","10:30","11:00","11:30", "14:00","14:30", "15:00","15:30","16:00","16:30","17:00","17:30"));
    private ArrayList<String> horasDisponiveis = new ArrayList<>();

    private FirebaseFirestore db;
    private CollectionReference marcacoes;

    private FirebaseAuth auth;

    private Utilizador cliente =  new Utilizador();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fazer_marcacao_cliente);

        auth = FirebaseAuth.getInstance();

        //Obter os servicos selecionados pelo utilizador
        servicosSelecionados = (ArrayList<Servico>) getIntent().getSerializableExtra("servicosSelecionados");

        Calendar dataMinima = Calendar.getInstance();
        dataMinima.add(Calendar.DAY_OF_MONTH, 1);

        db = FirebaseFirestore.getInstance();
        marcacoes = db.collection("marcacoes");

        getMarcacoes();
        calendario = findViewById(R.id.calendarView);
        calendario.setDate(System.currentTimeMillis(),false,false);
        calendario.setMinDate(dataMinima.getTime().getTime());

        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int anoSelecionado, int mesSelecionado, int diaSelecionado) {
                ano = anoSelecionado;
                mes = mesSelecionado + 1;
                dia = diaSelecionado;

                setSpinner();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Obter cliente
        db.collection("utilizadores").document(auth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot result = task.getResult();
                    cliente.setEmail(result.getString("e-mail"));
                    cliente.setNome(result.getString("nome"));
                    cliente.setNtelemovel(result.getString("telemovel"));
                }
            }
        });
    }

    private void definirHorasDisponiveis() {
        if(horasDisponiveis != null)
            horasDisponiveis.clear();

        horasDisponiveis.addAll(horarioFuncionamento);

        //Para cada Marcacao
        if(listaMarcacoes == null)
            return;

        for(Marcacao m: listaMarcacoes){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String dataInicio = simpleDateFormat.format(m.getDataInicio());

            String aux = dia+"/"+mes+"/"+ano;

            Date auxDate = null;
            try {
                auxDate = new SimpleDateFormat("dd/MM/yyyy").parse(aux);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String dataSelecionada = simpleDateFormat.format(auxDate);
            if(dataInicio.equals(dataSelecionada)) {
                //Interar pelo array de horas e definir apenas as dispn??veis

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                String dataInicioHorasMinutos = sdf.format(m.getDataInicio());
                String dataFimHorasMinutos = sdf.format(m.getDataFim());
                for (String horaFuncionamento : horarioFuncionamento) {
                    if (!checktimings(horaFuncionamento, dataInicioHorasMinutos) && checktimings(horaFuncionamento, dataFimHorasMinutos) || horaFuncionamento.equals(dataInicioHorasMinutos))
                        horasDisponiveis.remove(horaFuncionamento);
                }
            }
        }
    }

    private boolean checktimings(String time, String endtime) {

        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(time);
            Date date2 = sdf.parse(endtime);

            if(date1.before(date2)) {
                return true;
            } else {

                return false;
            }
        } catch (ParseException e){
            e.printStackTrace();
        }
        return false;
    }

    private void setSpinner() {
        definirHorasDisponiveis();
        spinner = findViewById(R.id.spinnerHoras);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,horasDisponiveis);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //adapter.setNotifyOnChange(true);
    }

    private void getMarcacoes(){
        if(listaMarcacoes != null)
            listaMarcacoes.clear();

        marcacoes.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Timestamp timestampDataInicioM = (Timestamp) document.get("dataInicio");
                        Timestamp timestampDataFimM = (Timestamp) document.get("dataFim");

                        Date dataInicioM = timestampDataInicioM.toDate();
                        Date dataFimM = timestampDataFimM.toDate();

                        Marcacao m = new Marcacao(dataInicioM, dataFimM);
                        listaMarcacoes.add(m);
                    }

                    setSpinner();
                }else{
                    Toast.makeText(FazerMarcacaoCliente.this, getString(R.string.BB_OcorreuErro), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onclickMarcacao(View view){
        if(cliente.getEmail() == null){
            Toast.makeText(this, getString(R.string.BB_TenteNovamente), Toast.LENGTH_SHORT).show();
            return;
        }

        //Formata????o da data selecionada para o formato de data
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String dataString = dia + "/" + mes + "/" + ano + " " + spinner.getSelectedItem().toString();

        try {
            dataSelecionada = simpleDateFormat.parse(dataString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Calculo da duracao e do preco dos servicos
        int duracao = 0, preco = 0;
        for(Servico s: servicosSelecionados){
            duracao += s.getDuracao();
            preco += s.getPreco();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(dataSelecionada);
        cal.add(Calendar.MINUTE, duracao);
        String dataFimString = simpleDateFormat.format(cal.getTime());
        Date dataFim = null;

        try {
            dataFim = simpleDateFormat.parse(dataFimString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Criacao do objeto marcacao
        Marcacao m = new Marcacao(cliente,dataSelecionada,dataFim,preco,servicosSelecionados, EstadoMarcacao.NAO_VALIDADA);
        Map<String, Object> marcacoesBaseDados = new HashMap<>();

        marcacoesBaseDados.put("email",cliente.getEmail());
        marcacoesBaseDados.put("nome",cliente.getNome());
        marcacoesBaseDados.put("dataInicio",m.getDataInicio());
        marcacoesBaseDados.put("dataFim",m.getDataFim());
        marcacoesBaseDados.put("preco",m.getPreco());
        marcacoesBaseDados.put("servicos",m.getServicos());
        marcacoesBaseDados.put("estado",m.getEstado());

        try {
            marcacoes.add(marcacoesBaseDados);
        }catch(Exception e){
            Toast.makeText(this, "Selecione o dia", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent finalMarcacao = new Intent(this, FimMarcacao.class);
        finalMarcacao.putExtra("marcacao", m);
        startActivity(finalMarcacao);
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