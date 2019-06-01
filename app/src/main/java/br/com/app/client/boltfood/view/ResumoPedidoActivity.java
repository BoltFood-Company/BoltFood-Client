package br.com.app.client.boltfood.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.protobuf.StringValue;

import java.util.List;

import br.com.app.client.boltfood.R;
import br.com.app.client.boltfood.controller.ProdutosHolder;
import br.com.app.client.boltfood.controller.ResumoPedidoAdapter;
import br.com.app.client.boltfood.model.entity.Pedido;
import br.com.app.client.boltfood.model.entity.Produto;

public class ResumoPedidoActivity extends AppCompatActivity {

    private TextView nome;
    private TextView total;
    private TextView numeroPedidoResumo;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ResumoPedidoAdapter listaAdapater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_pedido);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Pedido");

        progressBar = findViewById(R.id.progressCircleProdutos);

        Bundle extras = getIntent().getExtras();

        nome = findViewById(R.id.nomeResumoPedido);
        total = findViewById(R.id.precoResumoPedido);
        numeroPedidoResumo = findViewById(R.id.numeroPedidoResumo);


        if(extras != null) {
            String idPedido = (String) extras.get("idPedido");
            long numeroPedido = (long) extras.get("numeroPedido");
            String nomeRestaurante = (String) extras.get("nomeRestaurante");
            String urlRestaurante = (String) extras.get("urlRestaurante");
            String totalPedido = (String) extras.get("totalPedido");

            nome.setText(nomeRestaurante);
            total.setText("Total: " + totalPedido);
            numeroPedidoResumo.setText("N: " + numeroPedido);


            db.document("Pedido/" + idPedido)
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d("doc", "DocumentSnapshot data: " + document.getData());
                            Pedido pedido = document.toObject(Pedido.class);
                            List<Produto> listaProdutos = pedido.getPedidoItem();

                            recyclerView = findViewById(R.id.recyclerResumoPedido);

                            layoutManager = new LinearLayoutManager(getApplicationContext());
                            listaAdapater = new ResumoPedidoAdapter(listaProdutos, getApplicationContext());

                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(listaAdapater);

                        } else {
                            Log.d("doc", "No such document");
                        }
                    } else {
                        Log.d("doc", "get failed with ", task.getException());
                    }
                }

            });
        }






    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public FirestoreRecyclerAdapter setAdapter(Query query) {

        FirestoreRecyclerOptions<Produto> options =
                new FirestoreRecyclerOptions.Builder<Produto>()
                        .setQuery(query, Produto.class)
                        .build();

        return new FirestoreRecyclerAdapter<Produto, ProdutosHolder>(options) {

            @NonNull
            @Override
            public ProdutosHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.produto_card_layout, viewGroup, false);
                final ProdutosHolder viewHolder = new ProdutosHolder(view);

                progressBar.setVisibility(View.INVISIBLE);

                viewHolder.setOnClickListener(new ProdutosHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {


                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });

                return viewHolder;

            }

            @Override
            protected void onBindViewHolder(@NonNull ProdutosHolder holder, int position, @NonNull Produto model){
                DocumentSnapshot snapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
                String DocumentId = snapshot.getId();
                model.setId(DocumentId);

                holder.setNome(model.getNome());
                holder.setImagem(model.getUrl(), getApplicationContext());
                holder.setId(DocumentId);
                holder.setpreco(model.getPreco());

            }
        };


    }
}
