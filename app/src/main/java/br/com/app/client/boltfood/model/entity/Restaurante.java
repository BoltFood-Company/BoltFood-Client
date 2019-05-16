package br.com.app.client.boltfood.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Restaurante implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String cnpj;
    private String razaoSocial;
    private String nomeFantasia;
    private String telefone;
    private String url;
    private String descricao;
    private String nota;
    private String urlBg;

    private Universidade universidade;

    private List<Produto> produtos = new ArrayList<>();

    public Restaurante() {

    }

    public Restaurante(String id, String cnpj, String razaoSocial, String nomeFantasia, String telefone, Universidade universidade, String url, String descricao, String nota, String urlBg) {
        this.id = id;
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.telefone = telefone;
        this.universidade = universidade;
        this.url = url;
        this.descricao = descricao;
        this.nota = nota;
        this.urlBg = urlBg;
    }

    public String geturlBg() {return urlBg;}

    public void seturlBg(String bgUrl) {this.urlBg = bgUrl;}

    public String getNota() {return nota;}

    public void setNota(String nota) {this.nota = nota;}

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Universidade getUniversidade() {
        return universidade;
    }

    public void setUniversidade(Universidade universidade) {
        this.universidade = universidade;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurante that = (Restaurante) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
