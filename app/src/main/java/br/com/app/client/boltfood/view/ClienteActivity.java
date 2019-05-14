package br.com.app.client.boltfood.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.app.client.boltfood.R;
import br.com.app.client.boltfood.controller.ClienteController;
import br.com.app.client.boltfood.model.entity.Cliente;
import br.com.app.client.boltfood.model.entity.enums.Sexo;
import br.com.app.client.boltfood.view.util.Documento;
import br.com.app.client.boltfood.view.util.Mascara;
import br.com.app.client.boltfood.view.util.Validacao;

public class ClienteActivity extends AppCompatActivity {

    private EditText nomeCliente;
    private EditText documentoCliente;
    private EditText dataNascimentoCliente;
    private EditText telefoneCliente;
    private EditText emailCliente;
    private EditText confirmacaoEmailCliente;
    private EditText senhaCliente;
    private EditText confirmacaoSenhaCliente;
    private RadioButton masculino;
    private RadioButton feminino;

    private Cliente cliente;
    private ClienteController clienteController;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        nomeCliente = findViewById(R.id.nomeClienteEditText);
        documentoCliente = findViewById(R.id.documentoEditText);
        dataNascimentoCliente = findViewById(R.id.dataNascimentoEditText);
        telefoneCliente = findViewById(R.id.telefoneEditText);
        emailCliente = findViewById(R.id.emailEditText);
        confirmacaoEmailCliente = findViewById(R.id.confirmacaoEmailEditText);
        senhaCliente = findViewById(R.id.passwordEditText);
        confirmacaoSenhaCliente = findViewById(R.id.confirmacaoSenhaEditText);
        masculino = findViewById(R.id.masculinoRadioButton);
        feminino = findViewById(R.id.femininoRadioButton);

        Mascara mascaraTelefoneCliente = new Mascara("(##)#########", telefoneCliente);
        telefoneCliente.addTextChangedListener(mascaraTelefoneCliente);

        Mascara mascaraDataNascimentoCliente = new Mascara("##/##/####", dataNascimentoCliente);
        dataNascimentoCliente.addTextChangedListener(mascaraDataNascimentoCliente);

        Mascara mascaraDocumentoCliente = new Mascara("###.###.###-##", documentoCliente);
        documentoCliente.addTextChangedListener(mascaraDocumentoCliente);
    }

    public void cadastrar(View view) {

        if (!validaCampos()) {
            return;
        }

        cliente = new Cliente();
        cliente.setNome(nomeCliente.getText().toString());
        //cliente.setCpf(documentoCliente.getText().toString());
        //cliente.setDataNascimento(documentoCliente.getDa);
        //cliente.setTelefone(telefoneCliente.getText().toString());
        cliente.setEmail(emailCliente.getText().toString());
        cliente.setSenha(senhaCliente.getText().toString());
        //cliente.setSexo((masculino.isChecked() ? Sexo.MASCULINO : Sexo.FEMININO));

        auth.createUserWithEmailAndPassword(cliente.getEmail(), cliente.getSenha())
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            clienteController = new ClienteController();
                            clienteController.inserirCliente(cliente);

                            Toast.makeText(getApplicationContext(), "Cadastro Efetuado com Sucesso", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        } else {

                        }
                    }
                });
    }

    private boolean validaCampos(){

        if (!Validacao.validarEditText(nomeCliente, getString(R.string.validacaonome))){
            nomeCliente.requestFocus();
            return false;
        }

//        if (!Validacao.validarEditText(documentoCliente, getString(R.string.validacaodocumento))){
//            documentoCliente.requestFocus();
//            return false;
//        }

//        if (!Documento.isValidCPF(documentoCliente.getText().toString())){
//            documentoCliente.setError(getString(R.string.cpfinvalido));
//            return false;
//        }

//        if (!Validacao.validarEditText(telefoneCliente, getString(R.string.validacaotelefone))){
//            telefoneCliente.requestFocus();
//            return false;
//        }

        if (!Validacao.validarEditText(emailCliente, getString(R.string.validacaoemail))){
            emailCliente.requestFocus();
            return false;
        }

        if (!Validacao.validarEditText(senhaCliente, getString(R.string.validacaosenha))){
            senhaCliente.requestFocus();
            return false;
        }

        if (!emailCliente.getText().toString().equals(confirmacaoEmailCliente.getText().toString())){
            confirmacaoSenhaCliente.setError(getString(R.string.validacaoconfirmacaoemail));
            confirmacaoEmailCliente.requestFocus();
            return false;
        }

        if (!senhaCliente.getText().toString().equals(confirmacaoSenhaCliente.getText().toString())){
            confirmacaoSenhaCliente.setError(getString(R.string.validacaoconfirmacaosenha));
            confirmacaoSenhaCliente.requestFocus();
            return false;
        }

        return true;
    }
}
