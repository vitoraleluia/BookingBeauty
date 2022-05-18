package com.im.va20190648.vitor.aleluia.bookingbeauty.entidades;

public class Servico {
    private String nome;
    private Integer preco;
    private Integer duracao;


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
}
