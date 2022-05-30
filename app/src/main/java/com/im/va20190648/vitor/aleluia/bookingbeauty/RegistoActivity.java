package com.im.va20190648.vitor.aleluia.bookingbeauty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.im.va20190648.vitor.aleluia.bookingbeauty.cliente.EcraInicialCliente;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.Utilizador;

import java.util.HashMap;

public class RegistoActivity extends AppCompatActivity {

    private TextInputLayout nomeRegisto, passwordRegisto, emailRegisto, ntelemovelRegisto;
    private Button btRegistar;
    private FirebaseAuth mAuth;
    private Utilizador u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo);

        nomeRegisto = (TextInputLayout) findViewById(R.id.nome);
        passwordRegisto = (TextInputLayout) findViewById(R.id.password);
        emailRegisto = (TextInputLayout) findViewById(R.id.email);
        ntelemovelRegisto = (TextInputLayout) findViewById(R.id.telemovel);
        mAuth = FirebaseAuth.getInstance();

        btRegistar = findViewById(R.id.btRegistar);

        btRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getNome = nomeRegisto.getEditText().getText().toString();
                String getEmail = emailRegisto.getEditText().getText().toString();
                String getPassword = passwordRegisto.getEditText().getText().toString();
                String getNTelemovel = ntelemovelRegisto.getEditText().getText().toString();

                registarUser();
                criarUser();

                HashMap<String, Object> hashMap = new HashMap<>();

                hashMap.put("nome", getNome);
                hashMap.put("e-mail", getEmail);
                hashMap.put("password", getPassword);
                hashMap.put("telemovel", getNTelemovel);
                hashMap.put("tipoUser",u.getTipoUtilizador());

                FirebaseFirestore.getInstance().collection("utilizadores")
                        .document(getEmail.toString())
                        .set(hashMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(RegistoActivity.this, "Dados guardados com sucesso", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegistoActivity.this,""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                ;
            }
        });

    }

    private void criarUser() {
        mAuth.createUserWithEmailAndPassword(u.getEmail(),u.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            u.setId(user.getUid());
                            u.guardarDados();
                            startActivity(new Intent(RegistoActivity.this, EcraInicialCliente.class));
                        }else{
                            Toast.makeText(RegistoActivity.this,"Erro ao criar um utilizador", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void registarUser() {
        if(nomeRegisto.getEditText().getText().toString()==""||passwordRegisto.getEditText().getText().toString()==""|passwordRegisto.getEditText().getText().toString()==""||ntelemovelRegisto.getEditText().getText().toString()==""){
            Toast.makeText(this,"Preencha todos os campos do registo",Toast.LENGTH_LONG);
        } else{
            u = new Utilizador();
            u.setNome(nomeRegisto.getEditText().getText().toString());
            u.setEmail(emailRegisto.getEditText().getText().toString());
            u.setNtelemovel(ntelemovelRegisto.getEditText().getText().toString());
            u.setPassword(passwordRegisto.getEditText().getText().toString());
        }
    }
}