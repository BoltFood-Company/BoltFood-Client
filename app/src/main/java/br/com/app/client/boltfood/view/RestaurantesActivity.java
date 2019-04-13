package br.com.app.client.boltfood.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.app.client.boltfood.R;
import br.com.app.client.boltfood.adapter.AdapterRestaurante;
import br.com.app.client.boltfood.controller.RestauranteController;
import br.com.app.client.boltfood.dao.RestauranteDAO;
import br.com.app.client.boltfood.model.entity.Restaurante;

public class RestaurantesActivity extends AppCompatActivity {



    private RecyclerView restaurantesRecyclerView;
    private List<Restaurante> restaurantes = new ArrayList<>();
    private AdapterRestaurante adapterRestaurante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurantes);

        inicializarComponentes();

        configurarRecyclerView();

        recuperarRestaurantes();
    }

    private void inicializarComponentes(){
        restaurantesRecyclerView = findViewById(R.id.restaurantesRecyclerView);
    }

    private void configurarRecyclerView(){
        restaurantesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        restaurantesRecyclerView.setHasFixedSize(true);
        adapterRestaurante = new AdapterRestaurante(restaurantes);
        restaurantesRecyclerView.setAdapter(adapterRestaurante);
    }

    private void recuperarRestaurantes() {
        RestauranteController restauranteController = new RestauranteController();
        restaurantes = restauranteController.buscarRestaurantes();
        adapterRestaurante.notifyDataSetChanged();
    }
}
