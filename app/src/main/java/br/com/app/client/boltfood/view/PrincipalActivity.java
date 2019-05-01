package br.com.app.client.boltfood.view;


import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import br.com.app.client.boltfood.R;
import br.com.app.client.boltfood.model.entity.Restaurante;

public class PrincipalActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout ;
    private ActionBarDrawerToggle mToggle;
    private RecyclerView listaRecycler;
    private RecyclerView.Adapter listaAdapter;
    private RecyclerView.LayoutManager listaManager;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static ArrayList<Restaurante> mArrayList = new ArrayList<>();
    private static ArrayList<String> stringList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        stringList.add("Bar do Manuel");
        stringList.add("Bar Da Maria");
        stringList.add("Bar do Manuel");
        stringList.add("Bar Da Maria");
        stringList.add("Bar do Manuel");
        stringList.add("Bar Da Maria");
        stringList.add("Bar do Manuel");
        stringList.add("Bar Da Maria");
        stringList.add("Bar do Manuel");
        stringList.add("Bar Da Maria");
        stringList.add("Bar do Manuel");
        stringList.add("Bar Da Maria");

        Task<QuerySnapshot> docRef = db.collection("Restaurante").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        List<Restaurante> rest = queryDocumentSnapshots.toObjects(Restaurante.class);

                        mArrayList.addAll(rest);

                        Toast.makeText(getApplicationContext(), "PEGOU ALGUMA COISA AQUI", Toast.LENGTH_LONG).show();

                    }
                }) .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show();
                    }
                });

        listaRecycler = (RecyclerView) findViewById(R.id.principalRecyclerView);

        listaRecycler.setHasFixedSize(true);
        listaManager = new LinearLayoutManager(this);
        listaAdapter = new MainAdapter(stringList);
        listaRecycler.setLayoutManager(listaManager);
        listaRecycler.setAdapter(listaAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
