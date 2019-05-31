package br.com.app.client.boltfood.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import br.com.app.client.boltfood.R;
import br.com.app.client.boltfood.controller.PrincipalController;
import br.com.app.client.boltfood.controller.RestauranteHolder;
import br.com.app.client.boltfood.model.entity.Cliente;
import br.com.app.client.boltfood.model.entity.Restaurante;

public class PrincipalActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    private DrawerLayout mDrawerLayout ;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nvDrawer;

    private RecyclerView listaRecycler;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Query query;
    private ProgressBar progressBar;
    private PrincipalController controller;
    private SearchView searchView;
    private FirestoreRecyclerAdapter<Restaurante, RestauranteHolder> adapter;

    private TextView nomeUsuario, emailUsuario;

    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        getSupportActionBar().setTitle("Restaurantes");
        frameLayout = findViewById(R.id.flcontent);

        nvDrawer = findViewById(R.id.navigationView);
        mDrawerLayout = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(nvDrawer);
        progressBar = findViewById(R.id.progressCircle);

        nomeUsuario = nvDrawer.getHeaderView(0).findViewById(R.id.nomeUsuarioTextView);
        emailUsuario = nvDrawer.getHeaderView(0).findViewById(R.id.emailUsuarioTextView);

        carregaCliente();

        controller = new PrincipalController();

        listaRecycler = findViewById(R.id.principalRecyclerView);
        listaRecycler.setLayoutManager(new LinearLayoutManager(this));

        query = db.collection("Restaurante");

        adapter = setAdapter(query);
        listaRecycler.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onResume() {
        super.onResume();

        carregaCliente();
    }

    public void selectItemDrawer(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.pagamento:
                Intent intentFormasPagamento = new Intent(getApplicationContext(), CartaoActivity.class);
                startActivity(intentFormasPagamento);
                break;

            case R.id.sair:
                auth.signOut();
                Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intentLogin);
                finish();
                break;

            case R.id.pedidos:
                Intent intentPedidos = new Intent(getApplicationContext(), HistoricoPedidosActivity.class);
                startActivity(intentPedidos);
                break;

            case R.id.cadastroDrawerItem:
                Intent intentCadastro = new Intent(getApplicationContext(), AlteracaoClienteActivity.class);
                startActivity(intentCadastro);
                break;

                default:
                    break;
        }
        menuItem.setChecked(true);
        mDrawerLayout.closeDrawers();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                selectItemDrawer(menuItem);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
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
            query = db.collection("Restaurante").whereEqualTo("nomeFantasia", busca);
        }else {
            query = db.collection("Restaurante");
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
            query = db.collection("Restaurante").whereEqualTo("nomeFantasia", busca);
        }else {
            query = db.collection("Restaurante");
        }
        adapter = setAdapter(query);
        listaRecycler.setAdapter(adapter);
        adapter.startListening();

        return true;
    }

    public FirestoreRecyclerAdapter setAdapter(Query query) {

        FirestoreRecyclerOptions<Restaurante> options =
                new FirestoreRecyclerOptions.Builder<Restaurante>()
                        .setQuery(query, Restaurante.class)
                        .build();

        return new FirestoreRecyclerAdapter<Restaurante, RestauranteHolder>(options) {

            @NonNull
            @Override
            public RestauranteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.restaurante_card_layout, viewGroup, false);
                final RestauranteHolder viewHolder = new RestauranteHolder(view);
                progressBar.setVisibility(View.INVISIBLE);

                viewHolder.setOnClickListener(new RestauranteHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String id = adapter.getItem(position).getId();
                        String nomeRestaurante = adapter.getItem(position).getNomeFantasia();
                        String notaRestaurante = adapter.getItem(position).getNota();
                        String imagemRestaurante = adapter.getItem(position).getUrl();
                        String bgRestaurante = adapter.getItem(position).geturlBg();

                        Toast.makeText(PrincipalActivity.this, id, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ProdutosActivity.class);

                        intent.putExtra("idRestaurante",id);
                        intent.putExtra("nomeRestaurante", nomeRestaurante);
                        intent.putExtra("notaRestaurante", notaRestaurante);
                        intent.putExtra("imagemRestaurante", imagemRestaurante);
                        intent.putExtra("bgRestaurante", bgRestaurante);

                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        String id = adapter.getItem(position).getId();
                        String nomeRestaurante = adapter.getItem(position).getNomeFantasia();
                        String notaRestaurante = adapter.getItem(position).getNota();
                        String imagemRestaurante = adapter.getItem(position).getUrl();
                        String bgRestaurante = adapter.getItem(position).geturlBg();

                        Toast.makeText(PrincipalActivity.this, id, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ProdutosActivity.class);

                        intent.putExtra("idRestaurante",id);
                        intent.putExtra("idRestaurante",id);
                        intent.putExtra("nomeRestaurante", nomeRestaurante);
                        intent.putExtra("notaRestaurante", notaRestaurante);
                        intent.putExtra("imagemRestaurante", imagemRestaurante);
                        intent.putExtra("bgRestaurante", bgRestaurante);
                        startActivity(intent);
                    }
                });

                return viewHolder;
            }

            @Override
            protected void onBindViewHolder(@NonNull RestauranteHolder holder, int position, @NonNull Restaurante model){
                DocumentSnapshot snapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
                String DocumentId = snapshot.getId();

                model.setId(DocumentId);

                holder.setDescricao(model.getDescricao());
                holder.setNome(model.getNomeFantasia());
                holder.setImagem(model.getUrl(), getApplicationContext());
                holder.setId(DocumentId);

            }
        };


    }

    private void carregaCliente(){
        db.collection("Cliente")
                .whereEqualTo("id", auth.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Cliente cliente = document.toObject(Cliente.class);

                                emailUsuario.setText(cliente.getEmail());
                                nomeUsuario.setText(cliente.getNome());
                            }
                        }
                    }
                });
    }
}