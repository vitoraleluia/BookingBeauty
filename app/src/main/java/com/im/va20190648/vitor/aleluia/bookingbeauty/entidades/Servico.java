package com.im.va20190648.vitor.aleluia.bookingbeauty.entidades;

import java.io.Serializable;

public class Servico implements Serializable {
    private String id;
    private String nome;
    private Integer preco;
    private Integer duracao;

    public Servico() {
    }

    //Construtor para editar/listar os servicos existentes
    public Servico(String id, String nome, Integer preco, Integer duracao) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.duracao = duracao;
    }

    //Construtor para adicionar novos servicos
    public Servico(String nome, Integer preco, Integer duracao) {
        this.nome = nome;
        this.preco = preco;
        this.duracao = duracao;
    }

    public String getNome() {
        return nome;
    }

    public Integer getPreco() {
        return preco;
    }

    public Integer getDuracao() {
        return duracao;
    }

    @Override
    public String toString() {
        return "Servico{" +
                "nome='" + nome + '\'' +
                ", preco=" + preco +
                ", duracao=" + duracao +
                '}';
    }

    public String getId() {
        return id;
    }
}
