package br.com.app.client.boltfood.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.app.client.boltfood.R;

public class LoginActivity extends AppCompatActivity {

    private EditText login;
    private EditText password;

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.emailLoginEditText);
        login = findViewById(R.id.emailLoginEditText);
        password = findViewById(R.id.passwordLoginEditText);
    }
    public void logar(View view) {

        //validaçoes campos vazios

        auth.signInWithEmailAndPassword(login.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            login.setError("email invalido");
                            password.setError("senha invalida");
                        }
                    }
                });
    }

    public void cadastrar(View view){
        Intent intent = new Intent(getApplicationContext(), ClienteActivity.class);
        startActivity(intent);
    }
}
