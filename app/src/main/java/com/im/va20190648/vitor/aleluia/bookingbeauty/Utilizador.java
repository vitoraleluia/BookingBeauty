package com.im.va20190648.vitor.aleluia.bookingbeauty;

public class Utilizador {

    private String nome;
    private String email;
    private String password;
    private String telemovel;

    public Utilizador(String nome, String email, String password, String telemovel) {
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.telemovel = telemovel;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelemovel() {
        return telemovel;
    }

    public void setTelemovel(String telemovel) {
        this.telemovel = telemovel;
    }
}
