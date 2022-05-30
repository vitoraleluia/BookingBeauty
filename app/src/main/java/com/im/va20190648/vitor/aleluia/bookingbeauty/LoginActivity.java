package com.im.va20190648.vitor.aleluia.bookingbeauty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.im.va20190648.vitor.aleluia.bookingbeauty.cliente.EcraInicialCliente;
import com.im.va20190648.vitor.aleluia.bookingbeauty.entidades.Utilizador;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.im.va20190648.vitor.aleluia.bookingbeauty.esteticista.EcraInicialEsteticista;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout passwordLogin, emailLogin;
    private FirebaseAuth mAuth;
    private Utilizador u;
    private Button btLogin;
    private TextView textView;
    private FirebaseFirestore db;
    private CollectionReference utilizadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        passwordLogin = (TextInputLayout) findViewById(R.id.password);
        emailLogin = (TextInputLayout) findViewById(R.id.email);
        btLogin = findViewById(R.id.btLogin);
        textView = findViewById(R.id.textView);
        db = FirebaseFirestore.getInstance();
        utilizadores = db.collection("utilizadores");

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistoActivity.class));
            }
        });

        SpannableString ss = new SpannableString(getString(R.string.BB_CriarConta));


        ss.setSpan(new CustomClickableSpan(),0,43, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                receberDados();
                efetuarLogin();
            }
        });


    }

    private void efetuarLogin() {
        mAuth.signInWithEmailAndPassword(emailLogin.getEditText().getText().toString(), passwordLogin.getEditText().getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(u.getTipoUtilizador()==0){
                                startActivity(new Intent(LoginActivity.this, EcraInicialCliente.class));
                            }else{
                                startActivity(new Intent(LoginActivity.this, EcraInicialEsteticista.class));
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, getString(R.string.BB_AutenticacaoFalhada), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void receberDados() {
        Log.d("TAG", "receberDados: " + emailLogin.getEditText().getText().toString());
        utilizadores.document(emailLogin.getEditText().getText().toString())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        u = new Utilizador();
                        u.setEmail(documentSnapshot.getString("e-mail"));
                        u.setPassword(documentSnapshot.getString("password"));
                        u.setTipoUtilizador(Integer.parseInt(documentSnapshot.get("tipoUser").toString()));
                    }
                });
    }

    public class CustomClickableSpan {
        public void onClick(View textView){
            startActivity(new Intent(LoginActivity.this, RegistoActivity.class));
        }

       public void updateDrawState(TextPaint ds){
            ds.setColor(Color.MAGENTA);
            ds.setUnderlineText(false);
       }

    }
}