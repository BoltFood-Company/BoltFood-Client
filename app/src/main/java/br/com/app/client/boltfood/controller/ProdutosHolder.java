package br.com.app.client.boltfood.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import br.com.app.client.boltfood.R;

public class ProdutosHolder extends RecyclerView.ViewHolder {

    private final TextView nome;
    private final TextView preco;
    private final ImageView imagem;
    private final TextView idProduto;
    private ProdutosHolder.ClickListener mClickListener;

    public ProdutosHolder(@NonNull View itemView) {
        super(itemView);

        nome = itemView.findViewById(R.id.nomeProduto);
        imagem = itemView.findViewById(R.id.produtoImagem);
        preco = itemView.findViewById(R.id.precoProduto);
        idProduto = itemView.findViewById(R.id.idProduto);


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

    public void setOnClickListener(ProdutosHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }

    public void setId(String id) {idProduto.setText(id);}
    public void setNome(String n) {
        nome.setText(n);
    }
    public void setpreco(String p) {
        preco.setText(p);
    }
    public void setImagem(String i, Context context) {
        Glide.with(context).load(i).into(imagem);
    }
}
