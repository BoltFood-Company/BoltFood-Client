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
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.StringValue;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.app.client.boltfood.R;
import br.com.app.client.boltfood.controller.HistoricoPedidoController;
import br.com.app.client.boltfood.controller.HistoricoPedidoHolder;
import br.com.app.client.boltfood.controller.RestauranteHolder;
import br.com.app.client.boltfood.model.entity.Cliente;
import br.com.app.client.boltfood.model.entity.Pedido;
import br.com.app.client.boltfood.model.entity.Produto;
import br.com.app.client.boltfood.model.entity.Restaurante;

public class HistoricoPedidosActivity extends AppCompatActivity {

    private RecyclerView historicoPedidoRecycler;
    private ProgressBar progressBar;
    private Query query;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirestoreRecyclerAdapter<Pedido, HistoricoPedidoHolder> adapter;
    private DocumentReference ref;
    private HistoricoPedidoController controller;
    private String nomeRestaurante;
    private String imagemRestaurante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_pedidos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Histórico de Pedidos");

        historicoPedidoRecycler = findViewById(R.id.historicoPedidosRecycler);
        progressBar = findViewById(R.id.progressBarHistoricoPedidos);

        historicoPedidoRecycler.setLayoutManager(new LinearLayoutManager(this));
        Toast.makeText(this, auth.getUid(), Toast.LENGTH_SHORT).show();
        query = db.collection("Pedido").whereEqualTo("idCliente", auth.getUid());
        adapter = setAdapter(query);
        historicoPedidoRecycler.setAdapter(adapter);
        adapter.startListening();
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public FirestoreRecyclerAdapter setAdapter(Query query){

        FirestoreRecyclerOptions<Pedido> options =
                new FirestoreRecyclerOptions.Builder<Pedido>()
                    .setQuery(query, Pedido.class)
                    .build();

        return new FirestoreRecyclerAdapter<Pedido, HistoricoPedidoHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull final HistoricoPedidoHolder holder, int position, @NonNull final Pedido model) {
                DocumentSnapshot snapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
                final String DocumentId = snapshot.getId();
                String ref = model.getIdRestaurante().getId();

                DocumentReference docRef = db.collection("Restaurante").document(ref);
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Restaurante restaurante = documentSnapshot.toObject(Restaurante.class);

                        nomeRestaurante = restaurante.getNomeFantasia();
                        imagemRestaurante = restaurante.getUrl();

                        holder.setImagem(imagemRestaurante,getApplicationContext());
                        holder.setNomePedido(nomeRestaurante);

                        model.setId(DocumentId);
                        holder.setId(DocumentId);
                        holder.setpreco("Total " + model.getTotalPedido());

                        holder.setDataDoPedido(DateFormat.getDateInstance().format(model.getData()));
                        holder.setIdRestaurante(model.getIdRestaurante().getId());

                    }
                });
            }


            @NonNull
            @Override
            public HistoricoPedidoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.historico_pedido_card_layout, viewGroup, false);
                final HistoricoPedidoHolder viewHolder = new HistoricoPedidoHolder(view);
                progressBar.setVisibility(View.INVISIBLE);

                viewHolder.setOnClickListener(new HistoricoPedidoHolder.ClickListener(){

                    @Override
                    public void onItemClick(View view, int position) {
                        String id = adapter.getItem(position).getId();
                        String total = adapter.getItem(position).getTotalPedido();
                        long numeroPedido = adapter.getItem(position).getNumeroPedido();


                        Intent intent = new Intent(getApplicationContext(), ResumoPedidoActivity.class);

                        intent.putExtra("idPedido",id);
                        intent.putExtra("numeroPedido", numeroPedido);
                        intent.putExtra("nomeRestaurante", nomeRestaurante);
                        intent.putExtra("urlRestaurante", imagemRestaurante);
                        intent.putExtra("totalPedido", total);

                        startActivity(intent);

                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                    }
                });
                return viewHolder;
            }
        };
    }
}
