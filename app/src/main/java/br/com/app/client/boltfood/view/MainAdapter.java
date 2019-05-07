package br.com.app.client.boltfood.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.app.client.boltfood.R;
import br.com.app.client.boltfood.model.entity.Restaurante;

class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    ArrayList<Restaurante> listaRestaurante;
    Context context;
    public MainAdapter(ArrayList<Restaurante> restauranteList, Context applicationContext) {
        listaRestaurante = restauranteList;
        context = applicationContext;

    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder viewHolder, int i) {
        viewHolder.nome.setText(listaRestaurante.get(i).getNomeFantasia());
        viewHolder.descricao.setText(listaRestaurante.get(i).getDescricao());

    }

    @Override
    public int getItemCount() {
        return listaRestaurante.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView nome;
        private TextView descricao;
        private ImageView cardImage;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            nome = itemView.findViewById(R.id.nomeRestaurante);
            descricao = itemView.findViewById(R.id.descricaoRestaurante);
            cardImage = itemView.findViewById(R.id.imageRestaurante);

        }
    }
}
