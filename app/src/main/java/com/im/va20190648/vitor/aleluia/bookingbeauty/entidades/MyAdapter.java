package com.im.va20190648.vitor.aleluia.bookingbeauty.entidades;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
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
//TODO: Gonçalo dá fix nisto

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    Context context;
    ArrayList<Marcacao> marcacoes;
    FirebaseFirestore firebaseFirestore;
    String serv = "";
    ArrayList<String> nomes = new ArrayList<String >();

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

        FirebaseFirestore.getInstance().collection("marcacoes")
                .document(marcacoes.get(position).getDocumentId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                List<Map<String, Object>> groups = (List<Map<String, Object>>) task.getResult().get("servicos");
                for (Map<String, Object> group : groups) {
                    String name = group.get("nome").toString();
                    nomes.add(name);
                    serv+=name + "; ";
                    holder.servicosM.setText(serv);

                    Log.i("servicos", "getFleets: " + serv);
                }
                serv="";

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String dataInicio = simpleDateFormat.format(marcacao.getDataInicio());
                String dataFim = simpleDateFormat.format(marcacao.getDataFim());

                holder.dataInicio.setText(dataInicio);
                holder.dataFim.setText(dataFim);
                holder.precoM.setText(String.valueOf(marcacao.getPreco()));

                holder.estadoM.setText(marcacao.getEstado().toString());

            }
        });



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

        TextView dataInicio, dataFim, precoM, servicosM, estadoM;
        ImageView apagar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dataInicio = itemView.findViewById(R.id.tvdataInicio);
            dataFim = itemView.findViewById(R.id.tvdataFim);
            precoM = itemView.findViewById(R.id.tvpreco);
            servicosM = itemView.findViewById(R.id.tvservicos);
            apagar = itemView.findViewById(R.id.imageView4);
            estadoM = itemView.findViewById(R.id.tvestado);
        }

    }

}
