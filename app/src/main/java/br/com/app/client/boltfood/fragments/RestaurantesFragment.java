package br.com.app.client.boltfood.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import br.com.app.client.boltfood.R;
import br.com.app.client.boltfood.controller.PrincipalController;
import br.com.app.client.boltfood.controller.RestauranteHolder;
import br.com.app.client.boltfood.model.entity.Restaurante;
import br.com.app.client.boltfood.view.PrincipalActivity;
import br.com.app.client.boltfood.view.PrincipalNavigationActivity;
import br.com.app.client.boltfood.view.ProdutosActivity;

public class RestaurantesFragment extends Fragment {

    private RecyclerView restaurantesListaRecycler;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Query query;
    private ProgressBar restaurantesProgressBar;
    private PrincipalController controller;
    private SearchView searchView;
    private FirestoreRecyclerAdapter<Restaurante, RestauranteHolder> adapter;


    public RestaurantesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_restaurantes, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        restaurantesListaRecycler = view.findViewById(R.id.restaurantesRecyclerView);
        restaurantesProgressBar = view.findViewById(R.id.restaurantesProgressBar);

        restaurantesListaRecycler.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        query = db.collection("Restaurante");
        adapter = setAdapter(query);
        restaurantesListaRecycler.setAdapter(adapter);
        adapter.startListening();

        return view;
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
                restaurantesProgressBar.setVisibility(View.INVISIBLE);
                viewHolder.setOnClickListener(new RestauranteHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        carregaProdutosFragment(position);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        carregaProdutosFragment(position);
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
                holder.setImagem(model.getUrl(), getActivity().getApplicationContext());
                holder.setId(DocumentId);
            }
        };
    }

    private void carregaProdutosFragment(int positionItem){
        Bundle bundle = new Bundle();
        bundle.putSerializable("obj", adapter.getItem(positionItem));

        ProdutosFragment produtosFragment = new ProdutosFragment();
        produtosFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutContainer, produtosFragment);
        fragmentTransaction.commit();
    }

}
