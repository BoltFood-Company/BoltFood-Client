package br.com.app.client.boltfood.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import br.com.app.client.boltfood.R;
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

        /*
        clienteDAO = new ClienteDAO();
        cliente = new Cliente();
        cliente.setNome(nomeCliente.getText().toString());
        cliente.setDocumento(documentoCliente.getText().toString());
        //cliente.setDataNascimento((Date)documentoCliente.getText());
        cliente.setTelefone(telefoneCliente.getText().toString());
        cliente.setEmail(emailCliente.getText().toString());
        cliente.setSenha(senhaCliente.getText().toString());
        cliente.setSexo((masculino.isChecked() ? "Masculino" : "Feminino"));

        clienteDAO.insereCliente(cliente);
        */
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
            nomeCliente.requestFocus();
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
