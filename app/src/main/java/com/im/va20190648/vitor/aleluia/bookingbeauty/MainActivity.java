package com.im.va20190648.vitor.aleluia.bookingbeauty;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import io.grpc.okhttp.internal.Util;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db= FirebaseFirestore.getInstance();

    CollectionReference utilizadores;
    ListenerRegistration registration=null;
    DocumentReference doc = db.collection("utilizadores").document("Cliente1");

    TextInputLayout nome, email, password, telemovel;

    String nomeUt = "Cliente1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);


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
//warning: the field ‘descricao’ must exist in the NoSQL document
                        nome.getEditText().setText(snapshot.get("nome").toString());
                        email.getEditText().setText(snapshot.get("e-mail").toString());
                        password.getEditText().setText(snapshot.get("password").toString());
                        telemovel.getEditText().setText(snapshot.get("telemovel").toString());
                    } else {
                        Log.d("PRECOS", "Current data: null");
                    }
                }
            });
        }
    }


    public void botaoPremido(View v) {

        Intent i = new Intent(getApplicationContext(), VerMarcacoesActivity.class);
        startActivity(i);

        /*String nomeNovo = nome.getEditText().getText().toString();
        String passwordNova = password.getEditText().getText().toString();
        String emailNovo = email.getEditText().getText().toString();
        String telemovelNovo = telemovel.getEditText().getText().toString();
        doc.update("nome", nomeNovo, "e-mail", emailNovo, "password", passwordNova, "telemovel",telemovelNovo);

         */
    }

}