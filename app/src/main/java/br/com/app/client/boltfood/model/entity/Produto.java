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

    private Restaurante restaurante;

    private Set<ItemPedido> itens = new HashSet<>();

    public Produto() {

    }

    public Produto(String id, String nome, String descricao, long preco, Integer quantidade, Restaurante restaurante, String url) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.qtdeEstoque = qtdeEstoque;
        this.restaurante = restaurante;
        this.url = url;
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

    public void setPreco(long preco) {
        this.preco = preco;
    }

    public long getQtdeEstoque() {
        return qtdeEstoque;
    }

    public void setQtdeEstoque(long qtdeEstoque) {
        this.qtdeEstoque = qtdeEstoque;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
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
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(id, produto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
