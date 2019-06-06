package br.com.app.client.boltfood.model.entity;

import com.google.firebase.firestore.DocumentReference;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Objects;


public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String nome;
    private String descricao;
    private String url;
    private long preco;
    private long qtdeEstoque;
    private long qtde;

    private DocumentReference idRestaurante;

    public Produto() {

    }

    public Produto(long qtde, String id, String nome, String descricao, long preco, long qtdeEstoque, DocumentReference restauranteId, String url) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.qtdeEstoque = qtdeEstoque;
        this.idRestaurante = restauranteId;
        this.url = url;
        this.qtde = qtde;
    }

    public long getQtde() {
        return qtde;
    }

    public void setQtde(long qtde) {
        this.qtde = qtde;
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

    public long getPreco() {
        return preco;
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

    public DocumentReference getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(DocumentReference idRestaurante) {
        this.idRestaurante = idRestaurante;
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
