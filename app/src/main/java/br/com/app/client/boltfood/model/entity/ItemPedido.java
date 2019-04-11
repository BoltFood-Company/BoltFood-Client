package br.com.app.client.boltfood.model.entity;

import java.io.Serializable;
import java.util.Objects;

public class ItemPedido implements Serializable {
    private static final long serialVersionUID = 1L;

    private Pedido pedido;
    private Produto produto;
    private Double preco;
    private Integer quantidade;

    public ItemPedido() {

    }

    public ItemPedido(Pedido pedido, Produto produto, Double preco, Integer quantidade) {
        this.pedido = pedido;
        this.produto = produto;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getQuatidade() {
        return quantidade;
    }

    public void setQuatidade(Integer quatidade) {
        this.quantidade = quatidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemPedido that = (ItemPedido) o;
        return Objects.equals(pedido, that.pedido) &&
                Objects.equals(produto, that.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pedido, produto);
    }
}
