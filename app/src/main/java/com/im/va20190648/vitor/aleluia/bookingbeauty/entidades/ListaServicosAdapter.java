package com.im.va20190648.vitor.aleluia.bookingbeauty.entidades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.im.va20190648.vitor.aleluia.bookingbeauty.R;

import java.util.ArrayList;

public class ListaServicosAdapter extends RecyclerView.Adapter<ListaServicosAdapter.ViewHolder> {

    View view;
    Context context;
    ArrayList<Servico> servicos;
    ArrayList<Servico> servicosSelecionados = new ArrayList<>();
    ListaServicosListener listaServicosListener;

    public ListaServicosAdapter(Context context,  ArrayList<Servico> servicos, ListaServicosListener listaServicosListener) {
        this.context = context;
        this.servicos = servicos;
        this.listaServicosListener = listaServicosListener;
    }

    public View getView() {
        return view;
    }

    @NonNull
    @Override
    public ListaServicosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.rv_selecionar_servicos, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaServicosAdapter.ViewHolder holder, int position) {
        holder.textViewNome.setText(servicos.get(position).getNome().toString());
        holder.textViewPreco.setText(servicos.get(position).getPreco().toString()+"€");

        if(servicos != null && servicos.size() > 0){
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(holder.checkBox.isChecked()){
                        servicosSelecionados.add(servicos.get(position));
                    }else{
                        servicosSelecionados.remove(servicos.get(position));
                    }

                    listaServicosListener.onServicoChecked(servicosSelecionados);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return servicos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewNome, textViewPreco;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.checkBox_Listar);
            textViewNome = itemView.findViewById(R.id.textView_Listar_NomeServico);
            textViewPreco = itemView.findViewById(R.id.textView_Listar_Preco);
        }
    }
}
