package br.com.app.client.boltfood.controller;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.List;

import br.com.app.client.boltfood.R;
import br.com.app.client.boltfood.model.entity.Produto;
import br.com.app.client.boltfood.view.CarrinhoActivity;


public class CarrinhoAdapter extends RecyclerView.Adapter<CarrinhoAdapter.ViewHolder> {

    private List<Produto> listaProdutos;
    private Context mContext;
    private NumberFormat nf = NumberFormat.getCurrencyInstance();

    public CarrinhoAdapter(List<Produto> listaProdutos, Context mContext) {
        this.listaProdutos = listaProdutos;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CarrinhoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_produto_carrinho, viewGroup, false);
        return new CarrinhoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarrinhoAdapter.ViewHolder viewHolder,final int i) {

        viewHolder.nome.setText(listaProdutos.get(i).getNome());
        viewHolder.preco.setText(nf.format(listaProdutos.get(i).getPreco()));
        viewHolder.qtde.setText(listaProdutos.get(i).getQtde()+ " X ");
        Glide.with(mContext).load(listaProdutos.get(i).getUrl()).into(viewHolder.imagem);

        viewHolder.imageApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext.getApplicationContext(), "Produto apagado do Carrinho!", Toast.LENGTH_SHORT).show();
                listaProdutos.remove(i);
                CarrinhoActivity.atualizar();
            }
        });

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
        private final ImageView imageApagar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.nomeProdutoCarrinho);
            imagem = itemView.findViewById(R.id.produtoImagemCarrinho);
            preco = itemView.findViewById(R.id.precoProdutoCarrinho);
            qtde = itemView.findViewById(R.id.qtdeProdutoCarrinho);
            imageApagar = itemView.findViewById(R.id.imageApagarProdutoCarrinho);

        }
    }
}
