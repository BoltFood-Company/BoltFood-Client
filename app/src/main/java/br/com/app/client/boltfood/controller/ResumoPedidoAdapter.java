package br.com.app.client.boltfood.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.List;

import br.com.app.client.boltfood.R;
import br.com.app.client.boltfood.model.entity.Produto;

public class ResumoPedidoAdapter extends RecyclerView.Adapter<ResumoPedidoAdapter.ViewHolder> {

    private List<Produto> listaProdutos;
    private Context mContext;
    private NumberFormat nf = NumberFormat.getCurrencyInstance();


    public ResumoPedidoAdapter(List<Produto> lProdutos, Context context) {
        listaProdutos = lProdutos;
        mContext = context;
    }

    @NonNull
    @Override
    public ResumoPedidoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.produto_card_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResumoPedidoAdapter.ViewHolder viewHolder, int i) {
        String nome = listaProdutos.get(i).getNome();
        String preco = nf.format(listaProdutos.get(i).getPreco());
        String qtde = listaProdutos.get(i).getQtde()+ " X ";

        viewHolder.nome.setText(nome);
        viewHolder.preco.setText(preco);
        viewHolder.qtde.setText(qtde);
        Glide.with(mContext).load(listaProdutos.get(i).getUrl()).into(viewHolder.imagem);

    }

    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView nome;
        private final TextView preco;
        private final TextView qtde;
        private final ImageView imagem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.nomeProduto);
            imagem = itemView.findViewById(R.id.produtoImagem);
            preco = itemView.findViewById(R.id.precoProduto);
            qtde = itemView.findViewById(R.id.qtdeProduto);

        }
    }
}
