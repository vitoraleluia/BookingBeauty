package com.im.va20190648.vitor.aleluia.bookingbeauty.entidades;

import java.util.ArrayList;
import java.util.Date;

public class Marcacao {
    /*
     * Marcacao
     * Utilizador
     * Data
     * HoraInício
     * HoraFim
     * Preco
     * Lista de servicos
     * Estado (Por validar ou validada)
     */

    //private Utilizador utilizador;
    private Date dataInicio, dataFim;
    private Integer preco;
    private ArrayList<Servico> servicos;
    private Enum estado;

    //TODO: Adicionar utilizador para a marcacao
    public Marcacao(Date dataInicio, Date dataFim, Integer preco, ArrayList<Servico> servicos, Enum estado) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.preco = preco;
        this.servicos = servicos;
        this.estado = estado;
    }

    public Marcacao(Date dataInicio, Date dataFim) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public Integer getPreco() {
        return preco;
    }

    public ArrayList<Servico> getServicos() {
        return servicos;
    }

    public Enum getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "Marcacao{" +
                "dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", preco=" + preco +
                ", servicos=" + servicos +
                ", estado=" + estado +
                '}';
    }
}
