package br.com.app.client.boltfood.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.common.data.DataHolder;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import br.com.app.client.boltfood.R;
import br.com.app.client.boltfood.controller.PrincipalController;
import br.com.app.client.boltfood.controller.ProdutosHolder;
import br.com.app.client.boltfood.model.entity.Produto;
import br.com.app.client.boltfood.model.entity.Restaurante;
import br.com.app.client.boltfood.view.ProdutoActivity;
import br.com.app.client.boltfood.view.ProdutosActivity;

public class ProdutosFragment extends Fragment {

    private RecyclerView produtosListaRecycler;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Query query;
    private ProgressBar produtosProgressBar;
    private FirestoreRecyclerAdapter<Produto, ProdutosHolder> adapter;
    private DocumentReference ref = db.document("");

    public ProdutosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_produtos, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        produtosListaRecycler = view.findViewById(R.id.produtosFragmentRecyclerView);
        produtosProgressBar = view.findViewById(R.id.produtosProgressBar);

        produtosListaRecycler.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        Restaurante restaurante = (Restaurante)getArguments().getSerializable("obj");
        if(restaurante != null) {
            ref = db.document("Restaurante/" + restaurante.getId());
        }

        query = db.collection("Produto").whereEqualTo("idRestaurante", ref);

        adapter = setAdapter(query);
        produtosListaRecycler.setAdapter(adapter);
        adapter.startListening();

        return view;
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
                produtosProgressBar.setVisibility(View.INVISIBLE);
                viewHolder.setOnClickListener(new ProdutosHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String id = adapter.getItem(position).getId();
                        //Toast.makeText(ProdutosActivity.this, id, Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getApplicationContext(), ProdutoActivity.class);
//                        intent.putExtra("idProduto",id);
//                        startActivity(intent);

                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        String id = adapter.getItem(position).getId();
                        //Toast.makeText(ProdutosActivity.this, id, Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getApplicationContext(), ProdutosActivity.class);
//                        intent.putExtra("idProduto",id);
//                        startActivity(intent);

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
                holder.setImagem(model.getUrl(), getActivity().getApplicationContext());
                holder.setId(DocumentId);
                holder.setpreco(model.getPreco());

            }
        };


    }

}
