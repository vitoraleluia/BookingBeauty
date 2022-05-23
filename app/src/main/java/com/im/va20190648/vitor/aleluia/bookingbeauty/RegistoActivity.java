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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.Utilizador;

import java.util.HashMap;

public class RegistoActivity extends AppCompatActivity {

    private EditText nomeRegisto;
    private EditText passwordRegisto;
    private EditText emailRegisto;
    private EditText ntelemovelRegisto;
    private Button btRegistar;
    private FirebaseAuth mAuth;
    private Utilizador u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo);

        nomeRegisto = findViewById(R.id.nomeRegisto);
        passwordRegisto = findViewById(R.id.passwordRegisto);
        emailRegisto = findViewById(R.id.emailRegisto);
        ntelemovelRegisto = findViewById(R.id.ntelemovelRegisto);
        mAuth = FirebaseAuth.getInstance();

        btRegistar = findViewById(R.id.btRegistar);



        btRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getNome = nomeRegisto.getText().toString();
                String getEmail = emailRegisto.getText().toString();
                String getPassword = passwordRegisto.getText().toString();
                String getNTelemovel = ntelemovelRegisto.getText().toString();

                HashMap<String, Object> hashMap = new HashMap<>();

                hashMap.put("nome", getNome);
                hashMap.put("e-mail", getEmail);
                hashMap.put("password", getPassword);
                hashMap.put("telemovel", getNTelemovel);

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

                registarUser();
                criarUser();
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
                            startActivity(new Intent(RegistoActivity.this, PrincipalActivity.class));
                        }else{
                            Toast.makeText(RegistoActivity.this,"Erro ao criar um utilizador", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void registarUser() {
    if(nomeRegisto.getText().toString()==""||passwordRegisto.getText().toString()==""|passwordRegisto.getText().toString()==""||ntelemovelRegisto.getText().toString()==""){
        Toast.makeText(this,"Preencha todos os campos do registo",Toast.LENGTH_LONG);
    }else{
        u = new Utilizador();
        u.setNome(nomeRegisto.getText().toString());
        u.setEmail(emailRegisto.getText().toString());
        u.setNtelemovel(ntelemovelRegisto.getText().toString());
        u.setPassword(passwordRegisto.getText().toString());

    }
    }
}