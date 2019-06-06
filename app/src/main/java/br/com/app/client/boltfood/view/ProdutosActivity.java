package br.com.app.client.boltfood.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import br.com.app.client.boltfood.R;
import br.com.app.client.boltfood.controller.PrincipalController;
import br.com.app.client.boltfood.controller.ProdutosHolder;
import br.com.app.client.boltfood.model.entity.Produto;

public class ProdutosActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView listaRecycler;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Query query;
    private ProgressBar progressBar;
    private PrincipalController controller;
    private SearchView searchView;
    private FirestoreRecyclerAdapter<Produto, ProdutosHolder> adapter;
    private DocumentReference ref = db.document("");

    private TextView headerNome;
    private TextView headerEstrela;
    private ImageView headerImagem;
    private LinearLayout header;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

        header = findViewById(R.id.restauranteHeader);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Refeições");

        progressBar = findViewById(R.id.progressCircleProdutos);

        controller = new PrincipalController();

        listaRecycler = findViewById(R.id.produtosRecyclerView);
        listaRecycler.setLayoutManager(new LinearLayoutManager(this));

        headerNome = findViewById(R.id.restauranteNomeHeader);
        headerEstrela = findViewById(R.id.restauranteEstrelaHeader);
        headerImagem = findViewById(R.id.restauranteImageHeader);

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            String idRestaurante = (String) extras.get("idRestaurante");
            String nomeRestaurante = (String) extras.get("nomeRestaurante");
            String notaRestaurante = (String) extras.get("notaRestaurante");
            String imagemRestaurante = (String) extras.get("imagemRestaurante");
            String bgRestaurante = (String) extras.get("bgRestaurante");

            ref = db.document("Restaurante/" + idRestaurante);

            headerNome.setText(nomeRestaurante);
            headerEstrela.setText(notaRestaurante);
            Glide.with(getApplicationContext()).load(imagemRestaurante).into(headerImagem);
            Glide.with(this).load(bgRestaurante).centerCrop().into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        header.setBackground(resource);
                    }
                }
            });
        }

        query = db.collection("Produto").whereEqualTo("idRestaurante", ref);

        adapter = setAdapter(query);
        listaRecycler.setAdapter(adapter);
        adapter.startListening();

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.pesquisar);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String q) {
        String busca;
        if(q.length() != 0) {
            busca = controller.upperCase(q);
            query = db.collection("Produto").whereEqualTo("idRestaurante",ref).whereEqualTo("nome", busca);
        }else {
            query = db.collection("Produto").whereEqualTo("idRestaurante",ref);
        }
        adapter = setAdapter(query);
        listaRecycler.setAdapter(adapter);
        adapter.startListening();

        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(searchView.getWindowToken(), 0);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String q) {
        String busca;
        if(q.length() != 0) {
            busca = controller.upperCase(q);
            query = db.collection("Produto").whereEqualTo("idRestaurante",ref).whereEqualTo("nome", busca);
        }else {
            query = db.collection("Produto").whereEqualTo("idRestaurante",ref);
        }
        adapter = setAdapter(query);
        listaRecycler.setAdapter(adapter);
        adapter.startListening();

        return true;
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
                            String id = adapter.getItem(position).getId();
                            Toast.makeText(ProdutosActivity.this, id, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), ProdutoActivity.class);
                            intent.putExtra("idProduto",id);
                            startActivity(intent);

                        }

                        @Override
                        public void onItemLongClick(View view, int position) {
                            String id = adapter.getItem(position).getId();
                            Toast.makeText(ProdutosActivity.this, id, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), ProdutosActivity.class);
                            intent.putExtra("idProduto",id);
                            startActivity(intent);

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
