package br.com.app.client.boltfood.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import br.com.app.client.boltfood.R;
import br.com.app.client.boltfood.view.util.Mascara;

public class CartaoActivity extends AppCompatActivity {

    private EditText numeroCartao;
    private EditText validadeCartao;
    private EditText cvcCartao;
    private EditText nomeCartao;
    private EditText documentoCartao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartao);

        numeroCartao = findViewById(R.id.numeroCartaoEditText);
        validadeCartao = findViewById(R.id.validadeCartaoEditText);
        cvcCartao = findViewById(R.id.cvcCartaoEditText);
        nomeCartao = findViewById(R.id.nomeTitularCartaoEditText);
        documentoCartao = findViewById(R.id.documentoCartaoEditText);

        Mascara mascaraNumeroCartao = new Mascara("#### #### #### ####", numeroCartao);
        numeroCartao.addTextChangedListener(mascaraNumeroCartao);

        Mascara mascaraValidadeCartao = new Mascara("##/####", validadeCartao);
        validadeCartao.addTextChangedListener(mascaraValidadeCartao);

        Mascara mascaraDocumentoTitular = new Mascara("###.###.###-##", documentoCartao);
        documentoCartao.addTextChangedListener(mascaraDocumentoTitular);
    }

    public void cadastrarCartao(View view) {

    }
}
