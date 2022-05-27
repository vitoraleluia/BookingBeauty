package com.im.va20190648.vitor.aleluia.bookingbeauty.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Marcacao implements Serializable {
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

    private Utilizador cliente;
    private Date dataInicio, dataFim;
    private Integer preco;
    private ArrayList<Servico> servicos;
    private Enum estado;
    private String documentId;

    public Marcacao() {
    }

    //TODO: Adicionar utilizador para a marcacao
    public Marcacao(Utilizador cliente, Date dataInicio, Date dataFim, Integer preco, ArrayList<Servico> servicos, Enum estado) {
        this.cliente = cliente;
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

    public Utilizador getCliente() {
        return cliente;
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

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public void setPreco(Integer preco) {
        this.preco = preco;
    }

    public void setServicos(ArrayList<Servico> servicos) {
        this.servicos = servicos;
    }

    public void setEstado(Enum estado) {
        this.estado = estado;
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
