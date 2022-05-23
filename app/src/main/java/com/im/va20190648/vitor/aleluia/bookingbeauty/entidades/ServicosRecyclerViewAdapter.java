package com.im.va20190648.vitor.aleluia.bookingbeauty.entidades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.im.va20190648.vitor.aleluia.bookingbeauty.R;

import java.util.ArrayList;

public class ServicosRecyclerViewAdapter extends RecyclerView.Adapter<ServicosRecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Servico> listaServicos;
    private ServicoListener servicoListener;

    public ServicosRecyclerViewAdapter(Context context, ArrayList<Servico> listaServicos, ServicoListener servicoListener){
        this.context = context;
        this.listaServicos = listaServicos;
        this.servicoListener = servicoListener;
    }

    @NonNull
    @Override
    public ServicosRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_linha_servico, parent, false);
        return new ServicosRecyclerViewAdapter.MyViewHolder(view, servicoListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicosRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.textViewNome.setText(listaServicos.get(position).getNome().toString());
        holder.textViewPreco.setText(listaServicos.get(position).getPreco().toString() + " €");
        holder.textViewDuracao.setText(listaServicos.get(position).getDuracao().toString() + " Minutos");
    }

    @Override
    public int getItemCount() {
        return listaServicos.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewNome, textViewPreco, textViewDuracao;
        ServicoListener servicoListener;
        public MyViewHolder(@NonNull View itemView, ServicoListener servicoListener) {
            super(itemView);

            textViewNome = itemView.findViewById(R.id.textView_NomeServico);
            textViewPreco = itemView.findViewById(R.id.textView_Preco);
            textViewDuracao = itemView.findViewById(R.id.textView_Duracao);

            this.servicoListener = servicoListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            servicoListener.onServicoClick(getAdapterPosition());
        }
    }
}
