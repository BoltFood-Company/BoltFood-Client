package br.com.app.client.boltfood.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.w3c.dom.Text;

import br.com.app.client.boltfood.R;

public class HistoricoPedidoHolder extends RecyclerView.ViewHolder {

    private final TextView nomePedido;
    private final TextView preco;
    private final ImageView imagem;
    private final TextView idPedido;
    private final TextView idRestaurante;
    private final TextView dataDoPedido;

    private HistoricoPedidoHolder.ClickListener mClickListener;


    public HistoricoPedidoHolder(@NonNull View itemView) {
        super(itemView);

        nomePedido = itemView.findViewById(R.id.nomeHistoricoPedido);
        preco = itemView.findViewById(R.id.precoPedidoHistorico);
        imagem = itemView.findViewById(R.id.pedidoImagem);
        idPedido = itemView.findViewById(R.id.idHistoricoPedido);
        dataDoPedido = itemView.findViewById(R.id.dataPedidoHistorico);
        idRestaurante = itemView.findViewById(R.id.idHistoricoRestaurante);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });
    }

    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(HistoricoPedidoHolder.ClickListener clickListener){
        mClickListener =  clickListener;
    }

    public void setId(String id) {idPedido.setText(id);}
    public void setNomePedido(String n) {
        nomePedido.setText(n);
    }
    public void setpreco(String p) {
        preco.setText(p);
    }
    public void setDataDoPedido(String data){dataDoPedido.setText(data);}
    public void setIdRestaurante(String id) {idRestaurante.setText(id);}
    public void setImagem(String i, Context context) {
        Glide.with(context).load(i).apply(RequestOptions.circleCropTransform()).into(imagem);
    }


}
