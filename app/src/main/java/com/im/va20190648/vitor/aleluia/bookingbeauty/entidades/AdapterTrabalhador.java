package com.im.va20190648.vitor.aleluia.bookingbeauty.entidades;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.im.va20190648.vitor.aleluia.bookingbeauty.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdapterTrabalhador extends RecyclerView.Adapter<AdapterTrabalhador.ViewHolderTrabalhador> {

    Context context;
    String serv = "";
    ArrayList<String> nomes = new ArrayList<String >();
    ArrayList<Marcacao> marcacoes;
    ArrayList<Marcacao> marcacoesTrabalhadores = new ArrayList<Marcacao>();
    FirebaseFirestore firebaseFirestore;

    public AdapterTrabalhador(Context context, ArrayList<Marcacao> marcacoes) {
        this.context = context;
        this.marcacoes = marcacoes;
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public AdapterTrabalhador.ViewHolderTrabalhador onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.itemtrabalhador, parent, false);

        return new ViewHolderTrabalhador(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTrabalhador holder, @SuppressLint("RecyclerView") int position) {

        Marcacao marcacao = marcacoes.get(position);

        FirebaseFirestore.getInstance()
                .collection("marcacoes")
                .document(marcacoes.get(position).getDocumentId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        List<Map<String, Object>> groups = (List<Map<String,Object>>) task.getResult().get("servicos");
                        for(Map<String, Object> group : groups) {
                            String name= group.get("nome").toString();
                            nomes.add(name);
                            serv+= name + ";";
                            holder.servicosM.setText(serv);
                        }
                        serv="";
                        String nomeUtlizador = marcacao.getCliente().getNome();
                        SimpleDateFormat hora = new SimpleDateFormat("HH:mm");
                        String horaInicio = hora.format(marcacao.getDataInicio());
                        String horaFim = hora.format(marcacao.getDataFim());
                        SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
                        String dataInicio = data.format(marcacao.getDataInicio());
                        String dataFim = data.format(marcacao.getDataFim());

                        holder.nomeM.setText((nomeUtlizador));
                        holder.dataM.setText(dataInicio);
                        holder.horaInicioM.setText(horaInicio);
                        holder.horaFimM.setText(horaFim);
                        holder.precoM.setText(String.valueOf(marcacao.getPreco()));
                        holder.estadoM.setText(marcacao.getEstado().toString());
                    }
                });




        holder.apagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseFirestore.collection("marcacoes")
                        .document(marcacoes.get(position).getDocumentId())
                        .update("estado", EstadoMarcacao.REJEITADA)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    marcacoes.remove(marcacao);
                                    notifyDataSetChanged();
                                }
                            }
                        });
            }
        });


        holder.adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                marcacao.setEstado(EstadoMarcacao.VALIDADA);
                firebaseFirestore.collection("marcacoesTrabalhadores")
                        .document(marcacoes.get(position).getDocumentId())
                        .set(marcacao)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    marcacoesTrabalhadores.add(marcacao);
                                    notifyDataSetChanged();
                                }
                            }
                        });
                firebaseFirestore.collection("marcacoes")
                        .document(marcacoes.get(position).getDocumentId())
                        .update("estado", EstadoMarcacao.VALIDADA)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    return;
                                }
                            }
                        });
            }
        });

    }

    @Override
    public int getItemCount() {
        return marcacoes.size();
    }


    public static class ViewHolderTrabalhador extends RecyclerView.ViewHolder {

        TextView nomeM, dataM, horaInicioM, horaFimM, precoM, servicosM, estadoM;
        ImageView apagar, adicionar;

        public ViewHolderTrabalhador(@NonNull View itemView) {
            super(itemView);
            nomeM = itemView.findViewById(R.id.tvnomes);
            dataM = itemView.findViewById(R.id.tvdata);
            horaInicioM = itemView.findViewById(R.id.tvhoraInicio);
            horaFimM = itemView.findViewById(R.id.tvhoraFim);
            precoM = itemView.findViewById(R.id.tvpreco);
            servicosM = itemView.findViewById(R.id.tvservicos);
            apagar = itemView.findViewById(R.id.imageView4);
            adicionar = itemView.findViewById(R.id.imageView5);
            estadoM = itemView.findViewById(R.id.tvestado);
        }

    }


}

