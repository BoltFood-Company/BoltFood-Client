package br.com.app.client.boltfood.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.app.client.boltfood.R;
import br.com.app.client.boltfood.model.entity.Restaurante;

public class AdapterRestaurante extends RecyclerView.Adapter<AdapterRestaurante.MyViewHolder> {

    private List<Restaurante> restaurantes;

    public AdapterRestaurante(List<Restaurante> restaurantes) {
        this.restaurantes = restaurantes;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_restaurante, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        Restaurante restaurante = restaurantes.get(i);
        holder.nomeEmpresa.setText(restaurante.getNomeFantasia());
        //holder.categoria.setText(restaurante.getCategoria() + " - ");
        //holder.tempo.setText(restaurante.getTempo() + " Min");
        //holder.entrega.setText("R$ " + restaurante.getPrecoEntrega().toString());

        //Carregar imagem
        //String urlImagem = empresa.getUrlImagem();
        //Picasso.get().load( urlImagem ).into( holder.imagemEmpresa );

    }

    @Override
    public int getItemCount() {
        return restaurantes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imagemEmpresa;
        TextView nomeEmpresa;
        TextView categoria;
        TextView tempo;
        TextView entrega;

        public MyViewHolder(View itemView) {
            super(itemView);

            nomeEmpresa = itemView.findViewById(R.id.textNomeRestaurante);
            categoria = itemView.findViewById(R.id.textCategoriaRestaurante);
            tempo = itemView.findViewById(R.id.textTempoRestaurante);
            entrega = itemView.findViewById(R.id.textEntregaRestaurante);
            imagemEmpresa = itemView.findViewById(R.id.imageRestaurante);
        }
    }
}
