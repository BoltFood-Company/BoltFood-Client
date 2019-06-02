package br.com.app.client.boltfood.view;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import br.com.app.client.boltfood.R;
import br.com.app.client.boltfood.controller.CartaoController;
import br.com.app.client.boltfood.model.entity.Cartao;
import br.com.app.client.boltfood.view.util.Documento;
import br.com.app.client.boltfood.view.util.Validacao;


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

    private String numCartao;
    private String anoCartao;
    private String mesCartao;
    private String cpfCartao;
    private String cvvCartao;
    private String nomeCartao;
    private String idDocumentoCartao;
    private Bundle extras;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartao);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Cadastro de Cartão");

        numeroCartao = findViewById(R.id.numeroCartaoEditText);

        validadeMes = findViewById(R.id.validadeMesEditText);
        validadeAno = findViewById(R.id.validadeAnoEditText);
        nomeTitular = findViewById(R.id.nomeTitularEditText);
        cpfTitular = findViewById(R.id.cpfTitularEditText);
        cvv = findViewById(R.id.cvvEditText);
        salvar = findViewById(R.id.salvarBtn);

        extras = getIntent().getExtras();

        if(extras != null) {
            getSupportActionBar().setTitle("Edição de Cartão");
            numCartao = (String) extras.get("numCartao");
            anoCartao = (String) extras.get("anoCartao");
            mesCartao = (String) extras.get("mesCartao");
            cpfCartao = (String) extras.get("cpfCartao");
            cvvCartao = (String) extras.get("cvvCartao");
            nomeCartao = (String) extras.get("nomeCartao");
            idDocumentoCartao = (String) extras.get("idDocumentoCartao");

            numeroCartao.setText(numCartao);
            validadeMes.setText(mesCartao);
            validadeAno.setText(anoCartao);
            nomeTitular.setText(nomeCartao);
            cpfTitular.setText(cpfCartao);
            cvv.setText(cvvCartao);
        }

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cadastrar(v);
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void Cadastrar(View view) {
        if (!validaCampos()) {
            return;
        }
        final FirebaseUser currentUser = auth.getCurrentUser();

        cartao = new Cartao();

        String numCartao = numeroCartao.getText().toString();
        String anoCartao = validadeAno.getText().toString();
        String mesCartao = validadeMes.getText().toString();
        String cpfCartao = cpfTitular.getText().toString();
        String cvvCartao = cvv.getText().toString();
        String nomeCartao = nomeTitular.getText().toString();
        String userId = currentUser.getUid();

        cartao.setNumeroCartao(numCartao);
        cartao.setAno(anoCartao);
        cartao.setMes(mesCartao);
        cartao.setCpf(cpfCartao);
        cartao.setCvv(cvvCartao);
        cartao.setNome(nomeCartao);
        cartao.setIdUser(userId);

        cartaoController = new CartaoController();
        final Intent intent = new Intent(getApplicationContext(), SelecaoCartaoActivity.class);


        intent.putExtra("numCartao",numCartao);
        intent.putExtra("anoCartao",anoCartao);
        intent.putExtra("mesCartao",mesCartao);
        intent.putExtra("cpfCartao",cpfCartao);
        intent.putExtra("cvvCartao", cvvCartao);
        intent.putExtra("nomeCartao", nomeCartao);


        if(idDocumentoCartao == null) {
            cartaoController.inserirCartao(cartao);

            db.collection("Cartao").whereEqualTo("idUser", auth.getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if(!queryDocumentSnapshots.isEmpty()){
                        idDocumentoCartao = queryDocumentSnapshots.getDocuments().get(0).getId();

                        intent.putExtra("idDocumentoCartao", idDocumentoCartao);
                        Toast.makeText(getApplicationContext(), "Cartão Cadastrado Com Sucesso", Toast.LENGTH_SHORT).show();


                    }

                }
            });
            startActivity(intent);
            this.finish();
        } else {
            cartaoController.atualizarCartao(cartao, idDocumentoCartao);
            Toast.makeText(getApplicationContext(), "Cartão Atualizado Com Sucesso", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            this.finish();
        }

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

        return true;

    }
}
