package br.com.app.client.boltfood.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private Spinner sexoCliente;

    private Cliente cliente;
    private ClienteController clienteController;

    private final String[] sexos = new String[] { "Selecione", "Masculilno", "Feminino", "Outro" };

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
        sexoCliente = findViewById(R.id.sexoSpinner);

        carregaSexos();

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
        cliente.setCpf(documentoCliente.getText().toString());
        //cliente.setDataNascimento(documentoCliente.getDa);
        cliente.setTelefone(telefoneCliente.getText().toString());
        cliente.setEmail(emailCliente.getText().toString());
        cliente.setSenha(senhaCliente.getText().toString());
        cliente.setId(auth.getUid());
        //cliente.setSexo((masculino.isChecked() ? Sexo.MASCULINO : Sexo.FEMININO));

        auth.createUserWithEmailAndPassword(cliente.getEmail(), cliente.getSenha())
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            clienteController = new ClienteController();
                            clienteController.inserirCliente(cliente);

                            Toast.makeText(getApplicationContext(), getString(R.string.cadastroefetuadocomsucesso), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.usuarionaocadastrado), Toast.LENGTH_LONG).show();
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

    private void carregaSexos(){

        List<String> plantsList = new ArrayList<>(Arrays.asList(sexos));

        ArrayAdapter<String> sexoSpinnerArrayAdapter = new ArrayAdapter<String>(
                getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, plantsList);

        sexoSpinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sexoCliente.setAdapter(sexoSpinnerArrayAdapter);

        /*

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,plantsList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText
                            (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


         */

    }
}
