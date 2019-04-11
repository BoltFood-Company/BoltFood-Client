package br.com.app.client.boltfood.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Universidade implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String cnpj;
    private String razaoSocial;
    private String nomeFantasia;
    private String telefone;
    private String endereco;

    private List<Restaurante> restaurantes = new ArrayList<>();

    public Universidade() {

    }

    public Universidade(Integer id, String cnpj, String razaoSocial, String nomeFantasia, String telefone, String endereco) {
        this.id = id;
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public List<Restaurante> getRestaurantes() {
        return restaurantes;
    }

    public void setRestaurantes(List<Restaurante> restaurantes) {
        this.restaurantes = restaurantes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Universidade that = (Universidade) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
