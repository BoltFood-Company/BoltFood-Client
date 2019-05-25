package br.com.app.client.boltfood.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import br.com.app.client.boltfood.R;

public class RestauranteHolder extends RecyclerView.ViewHolder {

    private final ImageView imagem;
    private final TextView nome;
    private final TextView descricao;
    private final TextView idRestaurante;
    private RestauranteHolder.ClickListener mClickListener;

    public RestauranteHolder(@NonNull View itemView) {
        super(itemView);
        imagem = itemView.findViewById(R.id.restauranteImagem);
        nome = itemView.findViewById(R.id.nomeRestaurante);
        descricao = itemView.findViewById(R.id.descricaoRestaurante);
        idRestaurante = itemView.findViewById(R.id.idRestaurante);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());

            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemLongClick(v, getAdapterPosition());
                return true;
            }
        });


    }

    public interface ClickListener{
         void onItemClick(View view, int position);
         void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(RestauranteHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }

    public void setNome(String n) {
        nome.setText(n);
    }

    public void setDescricao(String d) {
        descricao.setText(d);
    }

    public void setId (String id) {idRestaurante.setText(id);}

    public void setImagem(String i, Context context) {
        Glide.with(context).load(i).into(imagem);
    }


}
