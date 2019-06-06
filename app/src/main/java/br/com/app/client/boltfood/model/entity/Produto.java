package br.com.app.client.boltfood.model.entity;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String nome;
    private String descricao;
    private String url;
    private long preco;
    private long qtdeEstoque;
    private long qtde;

    private String restauranteId;

    private Set<ItemPedido> itens = new HashSet<>();

    public Produto() {

    }

    public Produto(long qtde, String id, String nome, String descricao, long preco, long qtdeEstoque, String restauranteId, String url) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.qtdeEstoque = qtdeEstoque;
        this.restauranteId = restauranteId;
        this.url = url;
        this.qtde = qtde;
    }

    public long getQtde() {
        return qtde;
    }

    public void setQtde(long qtde) {
        this.qtde = qtde;
    }

    public List<Pedido> getPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        for (ItemPedido item : itens) {
            pedidos.add(item.getPedido());
        }
        return pedidos;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPreco() {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        return nf.format(preco);
    }

    public long getPrecoNumerico() {
        return preco;
    }

    public void setPreco(long preco) {
        this.preco = preco;
    }

    public long getQtdeEstoque() {
        return qtdeEstoque;
    }

    public void setQtdeEstoque(long qtdeEstoque) {
        this.qtdeEstoque = qtdeEstoque;
    }

    public String getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(String restauranteId) {
        this.restauranteId = restauranteId;
    }

    public Set<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(Set<ItemPedido> itens) {
        this.itens = itens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produto)) return false;
        Produto produto = (Produto) o;
        return Objects.equals(id, produto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
