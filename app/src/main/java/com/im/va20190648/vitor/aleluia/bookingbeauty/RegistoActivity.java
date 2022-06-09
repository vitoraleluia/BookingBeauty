package com.im.va20190648.vitor.aleluia.bookingbeauty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.im.va20190648.vitor.aleluia.bookingbeauty.cliente.EcraInicialCliente;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.Utilizador;

import java.util.HashMap;

public class RegistoActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private CollectionReference utilizadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo);

        db = FirebaseFirestore.getInstance();
        utilizadores = db.collection("utilizadores");
        auth = FirebaseAuth.getInstance();
    }

    public void onClickRegistar(View v){
        TextInputLayout nomeTIL = findViewById(R.id.nome);
        TextInputLayout emailTIL = findViewById(R.id.email);
        TextInputLayout telemovelTIL = findViewById(R.id.telemovel);
        TextInputLayout passwordTIL = findViewById(R.id.password);

        String nome = nomeTIL.getEditText().getText().toString();
        String email = emailTIL.getEditText().getText().toString();
        String telemovel = telemovelTIL.getEditText().getText().toString();
        String password = passwordTIL.getEditText().getText().toString();

        //Verificação dos dados
        if(!nome.matches("/^[a-z ,.'-]+$/i")){
            nomeTIL.setError(getString(R.string.BB_NomeInvalido));
            return;
        }

        if(utilizadores.document(email).get().getResult().exists()){
            emailTIL.setError(getString(R.string.BB_EmailJaExiste));
            return;
        }

        if(!telemovel.matches("/^[0-9]{9}$")){
           telemovelTIL.setError(getString(R.string.BB_NumeroInvalido));
           return;
        }

        if(password.length() < 8){
            passwordTIL.setError(getString(R.string.invalid_password));
            return;
        }
    }
}