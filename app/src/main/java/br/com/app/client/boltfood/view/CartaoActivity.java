package br.com.app.client.boltfood.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.app.client.boltfood.R;
import br.com.app.client.boltfood.controller.CartaoController;
import br.com.app.client.boltfood.model.entity.Cartao;
import br.com.app.client.boltfood.model.entity.util.Documento;
import br.com.app.client.boltfood.model.entity.util.Validacao;

public class CartaoActivity extends AppCompatActivity {

    private EditText numeroCartao;
    private EditText validadeMes;
    private EditText validadeAno;
    private EditText cvv;
    private EditText nomeTitular;
    private EditText cpfTitular;
    private Button salvar;

    private Cartao cartao;
    private CartaoController cartaoController;

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartao);

        numeroCartao = findViewById(R.id.numeroCartaoEditText);
        validadeMes = findViewById(R.id.validadeMesEditText);
        validadeAno = findViewById(R.id.validadeAnoEditText);
        nomeTitular = findViewById(R.id.nomeTitularEditText);
        cpfTitular = findViewById(R.id.cpfTitularEditText);
        cvv = findViewById(R.id.cvvEditText);
        salvar = findViewById(R.id.salvarBtn);

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cadastrar(v);
            }
        });

    }

    public void Cadastrar(View view) {
        if (!validaCampos()) {
            return;
        }
        final FirebaseUser currentUser = auth.getCurrentUser();

        cartao = new Cartao();

        cartao.setNumeroCartao(numeroCartao.getText().toString());
        cartao.setAno(validadeAno.getText().toString());
        cartao.setMes(validadeMes.getText().toString());
        cartao.setCpf(cpfTitular.getText().toString());
        cartao.setCvv(cvv.getText().toString());
        cartao.setNome(nomeTitular.getText().toString());
        cartao.setIdUser(currentUser.getUid());

        cartaoController = new CartaoController();
        cartaoController.inserirCliente(cartao);

    }

    private boolean validaCampos(){

        if (!Validacao.validarEditText(numeroCartao,"Número do Cartão Não Informado")){
            numeroCartao.requestFocus();
            return false;
        }

        if (!Validacao.validarEditText(validadeMes, "Mês da validade do Cartão não Informada")){
            validadeMes.requestFocus();
            return false;
        }

        if (!Validacao.validarEditText(validadeAno, "Ano da validade do Cartão não Informada")){
            validadeAno.requestFocus();
            return false;
        }

        if (!Validacao.validarEditText(cvv, "Código do cartão não Informado")){
            cvv.requestFocus();
            return false;
        }

        if (!Validacao.validarEditText(nomeTitular, "Nome do Titular do cartão não Informado")){
            nomeTitular.requestFocus();
            return false;
        }

        if (!Validacao.validarEditText(cpfTitular, "CPF não Informado")){
            cpfTitular.requestFocus();
            return false;
        }

        if (!Documento.isValidCPF(cpfTitular.getText().toString())){
            cpfTitular.setError(getString(R.string.cpfinvalido));
            return false;
        }

        return true;
    }
}
