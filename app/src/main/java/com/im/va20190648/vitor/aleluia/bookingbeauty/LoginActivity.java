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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.im.va20190648.vitor.aleluia.bookingbeauty.modelo.Utilizador;

public class LoginActivity extends AppCompatActivity {

    private EditText passwordLogin;
    private EditText emailLogin;
    private FirebaseAuth mAuth;
    private Utilizador u;
    private Button btLogin;
    private TextView textView;

    //TESTE
    private Button gotoregisto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        passwordLogin = findViewById(R.id.passwordLogin);
        emailLogin = findViewById(R.id.emailLogin);
        btLogin = findViewById(R.id.btLogin);
        textView = findViewById(R.id.textView);

        //TESTE
        gotoregisto = findViewById(R.id.gotoregisto);

        gotoregisto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistoActivity.class));
            }
        });

        //FIM TESTE

       // SpannableString ss = new SpannableString("Ainda não possui uma conta? Efetue o seu registo AQUI");


       // ss.setSpan(new CustomClickableSpan(),0,50, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
       // textView.setText(ss);
       // textView.setMovementMethod(LinkMovementMethod.getInstance());

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                receberDados();
                efetuarLogin();
            }
        });


    }

    private void efetuarLogin() {
        mAuth.signInWithEmailAndPassword(u.getEmail(), u.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(LoginActivity.this, PrincipalActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Autenticação Falhada", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void receberDados() {
        u = new Utilizador();
        u.setEmail(emailLogin.getText().toString());
        u.setPassword(passwordLogin.getText().toString());

    }

  //  public class CustomClickableSpan {
  //      public void onClick(View textView){
  //          startActivity(new Intent(LoginActivity.this, RegistoActivity.class));
  //      }

  //     public void updateDrawState(TextPaint ds){
  //          ds.setColor(Color.MAGENTA);
  //          ds.setUnderlineText(false);
  //     }

  //  }
}