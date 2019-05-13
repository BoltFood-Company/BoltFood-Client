package br.com.app.client.boltfood.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.app.client.boltfood.R;
import br.com.app.client.boltfood.view.util.Validacao;

public class LoginActivity extends AppCompatActivity {

    private EditText login;
    private EditText password;
    private Button logarButton;
    private TextView cadastrarTextView;
    private TextView esqueciMinhaSenha;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser user;
    //private ProgressBar progressBarLogin;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //progressBarLogin = findViewById(R.id.progressLogin);
        //progressBarLogin.setVisibility(View.INVISIBLE);

        login = findViewById(R.id.emailLoginEditText);
        password = findViewById(R.id.passwordLoginEditText);
        logarButton = findViewById(R.id.loginButton);
        cadastrarTextView = findViewById(R.id.cadastrarTextView);
        esqueciMinhaSenha = findViewById(R.id.esqueceuSenhaTextView);

        user = auth.getCurrentUser();
        //updateUI(user);
        if (user != null){
            Intent intent = new Intent(getApplicationContext(), PrincipalActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void logar(View view) {

        if (!validarCampos())
            return;

        //progressBarLogin.setVisibility(View.VISIBLE);
        statusCampos(false);

        auth.signInWithEmailAndPassword(login.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //progressBarLogin.setVisibility(View.INVISIBLE);

//                            //Create a object SharedPreferences from getSharedPreferences("name_file",MODE_PRIVATE) of Context
//                            sharedPreferences = getSharedPreferences("usuario_logado", MODE_PRIVATE);
//                            editor = sharedPreferences.edit();
//                            editor.putString("logado","true");
//                            //finally, when you are done saving the values, call the commit() method.
//                            editor.commit();


                            user = auth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(), PrincipalNavigationActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            login.setError( getString(R.string.emailinvalido));
                            password.setError( getString(R.string.senhainvalida));
                            //progressBarLogin.setVisibility(View.INVISIBLE);
                            statusCampos(true);
                        }
                    }
               });
    }

    public void cadastrar(View view){
        Intent intent = new Intent(getApplicationContext(), ClienteActivity.class);
        startActivity(intent);
    }

    public void resetSenha (View view) {
        Intent resetSenhaIntent = new Intent(getApplicationContext(), ResetSenhaActivity.class);
        startActivity(resetSenhaIntent);
    }

    private void statusCampos(boolean status) {
        login.setEnabled(status);
        password.setEnabled(status);
        logarButton.setEnabled(status);
        cadastrarTextView.setEnabled(status);
        esqueciMinhaSenha.setEnabled(status);
    }

    private boolean validarCampos(){

        if (!Validacao.validarEditText(login, getString(R.string.validacaoemail))){
            login.requestFocus();
            return false;
        }

        if (!Validacao.validarEditText(password, getString(R.string.validacaonome))){
            password.requestFocus();
            return false;
        }

        return true;
    }
}
