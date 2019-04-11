package br.com.app.client.boltfood.view;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.app.client.boltfood.R;
import br.com.app.client.boltfood.controller.ClienteController;
import br.com.app.client.boltfood.model.entity.Cliente;
import br.com.app.client.boltfood.model.entity.enums.Sexo;
import br.com.app.client.boltfood.model.entity.util.Documento;
import br.com.app.client.boltfood.model.entity.util.Validacao;

public class ClienteActivity extends AppCompatActivity {

    private EditText nomeCliente;
    private EditText documentoCliente;
    private EditText dataNascimentoCliente;
    private EditText telefoneCliente;
    private EditText emailCliente;
    private EditText senhaCliente;
    private RadioButton masculino;
    private RadioButton feminino;

    private Cliente cliente;
    private ClienteController clienteController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        nomeCliente = findViewById(R.id.nomeClienteEditText);
        documentoCliente = findViewById(R.id.documentoEditText);
        dataNascimentoCliente = findViewById(R.id.dataNascimentoEditText);
        telefoneCliente = findViewById(R.id.telefoneEditText);
        emailCliente = findViewById(R.id.emailEditText);
        senhaCliente = findViewById(R.id.passwordEditText);
        masculino = findViewById(R.id.masculinoRadioButton);
        feminino = findViewById(R.id.femininoRadioButton);
    }

    public void cadastrar(View view) {

        if (!validaCampos()) {
            return;
        }

        //Valida Se email já existe.
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(emailCliente.getText().toString(), senhaCliente.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            cliente = new Cliente();
                            cliente.setNome(nomeCliente.getText().toString());
                            cliente.setCpf(documentoCliente.getText().toString());
                            //cliente.setDataNascimento(documentoCliente.getDa);
                            cliente.setTelefone(telefoneCliente.getText().toString());
                            cliente.setEmail(emailCliente.getText().toString());
                            cliente.setSenha(senhaCliente.getText().toString());
                            cliente.setSexo((masculino.isChecked() ? Sexo.MASCULINO : Sexo.FEMININO));

                            clienteController = new ClienteController();
                            clienteController.inserirCliente(cliente);

                        } else {
                            emailCliente.setError(getString(R.string.emailexistente));
                            emailCliente.requestFocus();
                        }
                    }
                });


    }

    private boolean validaCampos(){

        if (!Validacao.validarEditText(nomeCliente, getString(R.string.validacaonome))){
            nomeCliente.requestFocus();
            return false;
        }

        if (!Validacao.validarEditText(emailCliente, getString(R.string.validacaoemail))){
            emailCliente.requestFocus();
            return false;
        }

        if (!Validacao.validarEditText(senhaCliente, getString(R.string.validacaosenha))){
            senhaCliente.requestFocus();
            return false;
        }

        if (!Validacao.validarEditText(documentoCliente, getString(R.string.validacaodocumento))){
            documentoCliente.requestFocus();
            return false;
        }

        if (!Documento.isValidCPF(documentoCliente.getText().toString())){
            documentoCliente.setError(getString(R.string.cpfinvalido));
            return false;
        }

        return true;
    }
}
