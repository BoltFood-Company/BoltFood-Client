package br.com.app.client.boltfood.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import br.com.app.client.boltfood.R;
import br.com.app.client.boltfood.model.entity.Restaurante;

public class PrincipalActivity extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    private DrawerLayout mDrawerLayout ;
    private ActionBarDrawerToggle mToggle;
    private RecyclerView listaRecycler;
    private RecyclerView.Adapter listaAdapter;
    private RecyclerView.LayoutManager listaManager;
    private NavigationView nvDrawer;

    private ProgressBar progressBar;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static ArrayList<Restaurante> mArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        nvDrawer = findViewById(R.id.navigationView);
        mDrawerLayout = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(nvDrawer);
        progressBar = findViewById(R.id.progressCircle);

        Task<QuerySnapshot> docRef = db.collection("Restaurante").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<Restaurante> rest = queryDocumentSnapshots.toObjects(Restaurante.class);
                        mArrayList.addAll(rest);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }) .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show();
                    }
                });

        listaRecycler = findViewById(R.id.principalRecyclerView);

        listaRecycler.setHasFixedSize(true);
        listaManager = new LinearLayoutManager(this);
        listaAdapter = new MainAdapter(mArrayList, getApplicationContext());
        listaRecycler.setLayoutManager(listaManager);
        listaRecycler.setAdapter(listaAdapter);
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
}