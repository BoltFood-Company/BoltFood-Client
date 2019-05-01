package br.com.app.client.boltfood.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.app.client.boltfood.R;
import br.com.app.client.boltfood.model.entity.Restaurante;

class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    ArrayList<String> listaRestaurante;

    public MainAdapter(ArrayList<String> strinList) {
        listaRestaurante = strinList;

    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder viewHolder, int i) {
        viewHolder.nome.setText(listaRestaurante.get(i));
    }

    @Override
    public int getItemCount() {
        return listaRestaurante.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView nome;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.nomeRestaurante);
        }
    }
}
