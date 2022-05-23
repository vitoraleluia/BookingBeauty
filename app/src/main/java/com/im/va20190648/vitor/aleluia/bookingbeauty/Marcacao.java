package com.im.va20190648.vitor.aleluia.bookingbeauty;

import io.grpc.internal.DnsNameResolver;

public class Marcacao {

    String nome, data, horaInicio, horaFim, servicos, documentId, estado;
    int preco;

    public Marcacao() {

    }

    public Marcacao(String data, String horaInicio, String horaFim, int preco, String servicos) {
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.preco = preco;
        this.servicos = servicos;
    }

    public Marcacao(String nome, String data, String horaInicio, String horaFim, int preco, String servicos) {
        this.nome = nome;
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.preco = preco;
        this.servicos = servicos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }

    public int getPreco() {
        return preco;
    }

    public void setPreco(int preco) {
        this.preco = preco;
    }

    public String getServicos() {
        return servicos;
    }

    public void setServicos(String servicos) {
        this.servicos = servicos;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
