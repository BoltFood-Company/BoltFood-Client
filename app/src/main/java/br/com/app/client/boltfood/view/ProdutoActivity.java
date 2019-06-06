package br.com.app.client.boltfood.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.NumberFormat;

import br.com.app.client.boltfood.R;
import br.com.app.client.boltfood.model.entity.Produto;
import br.com.app.client.boltfood.view.util.Constantes;

public class ProdutoActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ProgressBar progressBarLoadProduto;
    private ImageView imageViewProduto;
    private TextView textViewNomeProduto;
    private TextView textViewDescricaoProduto;
    private Button buttonQtdeMais;
    private TextView textViewQuantidade;
    private Button buttonQtdeMenos;
    private TextView textViewValorTotal;
    private Button buttonAdicionarProduto;

    private Produto produto;
    private int quantidade = 1;
    private long precoTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.produto));

        progressBarLoadProduto = findViewById(R.id.progressBarLoadProduto);
        imageViewProduto = findViewById(R.id.imageViewProduto);
        textViewNomeProduto = findViewById(R.id.textViewNomeProduto);
        textViewDescricaoProduto = findViewById(R.id.textViewDescricaoProduto);
        buttonQtdeMais = findViewById(R.id.buttonQtdeMais);
        textViewQuantidade = findViewById(R.id.textViewQuantidade);
        buttonQtdeMenos = findViewById(R.id.buttonQtdeMenos);
        textViewValorTotal = findViewById(R.id.textViewValorTotal);
        buttonAdicionarProduto = findViewById(R.id.buttonAdicionarProduto);

        Bundle extras = getIntent().getExtras();
        String idProduto;
        if(extras != null) {
            idProduto = extras.getString("idProduto");

            db.document("Produto/" + idProduto).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {

                            produto = documentSnapshot.toObject(Produto.class);

                            progressBarLoadProduto.setVisibility(View.INVISIBLE);

                            Glide.with(getApplicationContext()).load(produto.getUrl()).into(imageViewProduto);
                            textViewNomeProduto.setText(produto.getNome());
                            textViewDescricaoProduto.setText(produto.getDescricao());
                            textViewQuantidade.setText(String.valueOf(quantidade));
                            precoTotal = produto.getPrecoNumerico();
                            textViewValorTotal.setText(NumberFormat.getCurrencyInstance().format(precoTotal));
                        }
                    }
                }
            });
        }

        buttonQtdeMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantidade != 1) {
                    quantidade--;
                    precoTotal -= produto.getPrecoNumerico();
                    textViewQuantidade.setText(String.valueOf(quantidade));
                    textViewValorTotal.setText(NumberFormat.getCurrencyInstance().format(precoTotal));
                }
            }
        });


        buttonQtdeMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantidade != produto.getQtdeEstoque()) {
                    quantidade++;
                    precoTotal += produto.getPrecoNumerico();
                    textViewQuantidade.setText(String.valueOf(quantidade));
                    textViewValorTotal.setText(NumberFormat.getCurrencyInstance().format(precoTotal));
                }
                else
                    Toast.makeText(getApplicationContext(), getString(R.string.quantidademaximadisponivel), Toast.LENGTH_SHORT).show();
            }
        });


        buttonAdicionarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                produto.setQtde(quantidade);
                int retorno = CarrinhoActivity.adicionarProduto(produto, getApplicationContext());

                if (retorno == Constantes.PRODUTO_ADICIONADO){
                    Snackbar snackbar = Snackbar.make(v, "", Snackbar.LENGTH_LONG);
                    View view = snackbar.getView();
                    view.setMinimumHeight(50);

                    view.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.color.colorPrimaryDark));
                    snackbar.setActionTextColor(Color.WHITE);

                    TextView tv = view.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setTextColor(Color.WHITE);

                    snackbar.setText(getString(R.string.itemadicionadoaocarrinho));
                    snackbar.setAction(getString(R.string.irparaocarrinho), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent carrinhoIntent = new Intent(getApplicationContext(), CarrinhoActivity.class);
                            startActivity(carrinhoIntent);
                        }
                    });
                    snackbar.show();
                }
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
}
