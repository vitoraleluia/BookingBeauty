package com.im.va20190648.vitor.aleluia.bookingbeauty;

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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    Context context;
    ArrayList<Marcacao> marcacoes;
    FirebaseFirestore firebaseFirestore;

    public MyAdapter(Context context, ArrayList<Marcacao> marcacoes) {
        this.context = context;
        this.marcacoes = marcacoes;
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Marcacao marcacao = marcacoes.get(position);

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
                        .delete()
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

    }

    @Override
    public int getItemCount() {
        return marcacoes.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dataM, horaInicioM, horaFimM, precoM, servicosM, estadoM;
        ImageView apagar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dataM = itemView.findViewById(R.id.tvdata);
            horaInicioM = itemView.findViewById(R.id.tvhoraInicio);
            horaFimM = itemView.findViewById(R.id.tvhoraFim);
            precoM = itemView.findViewById(R.id.tvpreco);
            servicosM = itemView.findViewById(R.id.tvservicos);
            apagar = itemView.findViewById(R.id.imageView4);
            estadoM = itemView.findViewById(R.id.tvestado);
        }

    }

}
