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
import com.google.firebase.firestore.FirebaseFirestore;
import com.im.va20190648.vitor.aleluia.bookingbeauty.R;

import java.util.ArrayList;

public class AdapterTrabalhador extends RecyclerView.Adapter<AdapterTrabalhador.ViewHolderTrabalhador> {

    Context context;
    ArrayList<com.im.va20190648.vitor.aleluia.bookingbeauty.Marcacao> marcacoes;
    ArrayList<com.im.va20190648.vitor.aleluia.bookingbeauty.Marcacao> marcacoesTrabalhadores;
    FirebaseFirestore firebaseFirestore;

    public AdapterTrabalhador(Context context, ArrayList<com.im.va20190648.vitor.aleluia.bookingbeauty.Marcacao> marcacoes) {
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

        //holder.nomeM.setText(("Aqui vai aparecer o nome"));
        holder.dataM.setText(marcacao.data);
        holder.horaInicioM.setText(marcacao.horaInicio);
        holder.horaFimM.setText(marcacao.horaFim);
        holder.precoM.setText(String.valueOf(marcacao.preco));
        holder.servicosM.setText(marcacao.servicos);
        holder.estadoM.setText(marcacao.estado);

        holder.apagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseFirestore.collection("marcacoes")
                        .document(marcacoes.get(position).getDocumentId())
                        .update("estado", "Marcação Rejeitada")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    //marcacoes.remove(marcacao);
                                    notifyDataSetChanged();
                                }
                            }
                        });
            }
        });


        holder.adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseFirestore.collection("marcacoesTrabalhadores")
                        .document(marcacoes.get(position).getDocumentId())
                        .set(marcacao)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    marcacoesTrabalhadores.add(marcacao);
                                }
                            }
                        });
                firebaseFirestore.collection("marcacoesTrabalhadores")
                        .document(marcacoes.get(position).getDocumentId())
                        .update("estado", "Marcação Aceite")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                   notifyDataSetChanged();
                                }
                            }
                        });
                firebaseFirestore.collection("marcacoes")
                        .document(marcacoes.get(position).getDocumentId())
                        .update("estado", "Marcação Aceite")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    //marcacoes.remove(marcacao);
                                    notifyDataSetChanged();
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
            //nomeM = itemView.findViewById(R.id.tvnomes);
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
