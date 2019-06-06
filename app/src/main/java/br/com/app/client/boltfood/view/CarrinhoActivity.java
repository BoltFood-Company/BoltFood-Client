package br.com.app.client.boltfood.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import br.com.app.client.boltfood.R;
import br.com.app.client.boltfood.controller.CarrinhoAdapter;
import br.com.app.client.boltfood.controller.PedidoController;
import br.com.app.client.boltfood.controller.ResumoPedidoAdapter;
import br.com.app.client.boltfood.model.entity.Pedido;
import br.com.app.client.boltfood.model.entity.Produto;
import br.com.app.client.boltfood.view.util.Constantes;

public class CarrinhoActivity extends AppCompatActivity {

    private static List<Produto> produtos = new ArrayList<>();

    private TextView textViewPrecoTotalPedido;
    private ProgressBar progressBarLoadCarrinho;
    private Button buttonRealizarPedido;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CarrinhoAdapter listaAdapater;

    private DocumentReference idRestaurante;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    private long totalPedido = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Carrinho");

        progressBarLoadCarrinho = findViewById(R.id.progressBarLoadCarrinho);

        textViewPrecoTotalPedido = findViewById(R.id.textViewPrecoTotalPedido);
        buttonRealizarPedido = findViewById(R.id.buttonRealizarPedido);

        recyclerView = findViewById(R.id.recyclerViewCarrinho);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        listaAdapater = new CarrinhoAdapter(produtos, getApplicationContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listaAdapater);
        progressBarLoadCarrinho.setVisibility(View.INVISIBLE);


        if (!produtos.isEmpty()) {
            for (Produto p : produtos) {
                totalPedido += p.getPrecoNumerico() * p.getQtde();
            }
            textViewPrecoTotalPedido.setText(NumberFormat.getCurrencyInstance().format(totalPedido));
        }
        textViewPrecoTotalPedido.setText(NumberFormat.getCurrencyInstance().format(totalPedido));

        buttonRealizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth auth = FirebaseAuth.getInstance();

                Pedido pedido = new Pedido();
                pedido.setIdCliente(auth.getUid());
                pedido.setData(new Date());
                pedido.setPedidoItem(produtos);
                pedido.setNumeroPedido(new Random().nextInt(1000) + 1);
                pedido.setTotalPedido(totalPedido);
                idRestaurante = db.document("Restaurante/" + produtos.get(0).getIdRestaurante().getId());
                pedido.setIdRestaurante(idRestaurante);

                new PedidoController().inserir(pedido);

                produtos.clear();

                finish();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static int adicionarProduto(Produto produto, Context context) {
        if (produtos.isEmpty()) {
            produtos.add(produto);
            //Toast.makeText(context.getApplicationContext(), "Produto adicionado ao Carrinho!", Toast.LENGTH_SHORT).show();
            return Constantes.PRODUTO_ADICIONADO;
        }

        try {

            if (!produto.getIdRestaurante().getId().equals(produtos.get(0).getIdRestaurante().getId())) {
                throw new RuntimeException();
            }

            boolean existeLista = false;
            for (Produto p : produtos) {
                if (p.equals(produto)) {
                    existeLista = true;
                    if (p.getQtde() + produto.getQtde() <= p.getQtdeEstoque()) {
                        p.setQtde(p.getQtde() + produto.getQtde());
                    } else {
                        throw new RuntimeException();
                    }
                }
            }
            if (!existeLista) {
                produtos.add(produto);

            }
            return Constantes.PRODUTO_ADICIONADO;

        } catch (RuntimeException e) {
            Toast.makeText(context.getApplicationContext(), "Quantidade acima do valor disponível!", Toast.LENGTH_SHORT).show();
            return Constantes.PRODUTO_SEM_ESTOQUE;
        }

    }
}
