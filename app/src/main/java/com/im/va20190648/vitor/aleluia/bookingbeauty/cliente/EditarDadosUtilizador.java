package com.im.va20190648.vitor.aleluia.bookingbeauty.cliente;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.im.va20190648.vitor.aleluia.bookingbeauty.LoginActivity;
import com.im.va20190648.vitor.aleluia.bookingbeauty.R;

public class EditarDadosUtilizador extends AppCompatActivity {

    FirebaseFirestore db= FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    CollectionReference utilizadores;
    ListenerRegistration registration=null;
    DocumentReference doc = null;

    TextInputLayout nome, email, password, telemovel;

    String nomeUt = null;
    String nomeNovo, emailNovo, passwordNova, telemovelNovo;
    String nomeAnterior, emailAnterior, passwordAnterior, telemovelAnterior;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_editar_dados_utilizador);

        doc = db.collection("utilizadores").document(mAuth.getCurrentUser().getEmail().toString());
        nomeUt = mAuth.getCurrentUser().getEmail().toString();
        utilizadores = db.collection("utilizadores");

        if (registration != null)
            registration.remove();

        nome = (TextInputLayout) findViewById(R.id.nome);
        email = (TextInputLayout) findViewById(R.id.email);
        password = (TextInputLayout) findViewById(R.id.password);
        telemovel = (TextInputLayout) findViewById(R.id.telemovel);


        if (!nomeUt.isEmpty()) {
            registration = utilizadores.document(nomeUt).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot snapshot,
                                    @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.w("PRECOS", "Listen failed.", e);
                        return;
                    }
                    if (snapshot != null && snapshot.exists()) {
                        nomeAnterior=snapshot.get("nome").toString();
                        emailAnterior=snapshot.get("e-mail").toString();
                        passwordAnterior=snapshot.get("password").toString();
                        telemovelAnterior=snapshot.get("telemovel").toString();

                        nome.getEditText().setText(snapshot.get("nome").toString());
                        email.getEditText().setText(snapshot.get("e-mail").toString());
                        password.getEditText().setText(snapshot.get("password").toString());
                        telemovel.getEditText().setText(snapshot.get("telemovel").toString());
                    } else {
                        Log.d("Utilizadores", "Current data: null");
                    }
                }
            });
        }
    }

    public void logout(View v) {
        mAuth.signOut();

        //TODO: Colocar aqui o LoginActivity
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }

    public void botaoPremido(View v) {

        if(nome.getEditText().getText().toString().trim().matches("")) {
            nome.setError("Campo obrigatório!!!");
            return;

        } else if(password.getEditText().getText().toString().trim().matches("")){
            password.setError("Campo obrigatório!!!");
            return;

        } else if(telemovel.getEditText().getText().toString().trim().matches("")) {
            telemovel.setError("Campo obrigatório!!!");
            return;

        } else {
            nomeNovo = nome.getEditText().getText().toString();
            passwordNova = password.getEditText().getText().toString();
            emailNovo = email.getEditText().getText().toString();
            telemovelNovo = telemovel.getEditText().getText().toString();

            if (nomeAnterior.equals(nomeNovo) && emailAnterior.equals(emailNovo) && passwordAnterior.equals(passwordNova) && telemovelAnterior.equals(telemovelNovo)) {
                nome.setError("O nome não foi alterado!!!");
                password.setError("A password não foi alterada!!!");
                telemovel.setError("O número de telemóvel não foi alterado!!!");
                return;

            } else if (passwordNova.length()<=6) {
                password.setError("A password é demasiado curta!!!");
                return;

            } else {
                nome.setErrorEnabled(false);
                password.setErrorEnabled(false);
                telemovel.setErrorEnabled(false);
                mAuth.getCurrentUser().updatePassword(passwordNova);
                doc.update("nome", nomeNovo, "e-mail", emailNovo, "password", passwordNova, "telemovel", telemovelNovo);
                Toast.makeText(this, "Alteração realizada com sucesso!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}