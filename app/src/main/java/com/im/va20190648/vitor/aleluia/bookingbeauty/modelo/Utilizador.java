package com.im.va20190648.vitor.aleluia.bookingbeauty.modelo;

import com.google.firebase.database.DatabaseReference;
import com.im.va20190648.vitor.aleluia.bookingbeauty.config.ConfiguracaoFirebase;

public class Utilizador {
    private String id;
    private String nome;
    private String email;
    private String password;
    private String ntelemovel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getNtelemovel() {
        return ntelemovel;
    }

    public void setNtelemovel(String ntelemovel) {
        this.ntelemovel = ntelemovel;
    }

    public void guardarDados() {
        DatabaseReference firebase = ConfiguracaoFirebase.getFirebaseDatabase();
        firebase.child("Utilizadores").child(this.id).setValue(this);
        //podemos mudar o setvalue para cada dado que queremos guardar para nao guardar a password na BD
    }
}