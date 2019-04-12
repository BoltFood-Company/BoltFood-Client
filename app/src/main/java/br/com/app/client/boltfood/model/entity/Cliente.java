package br.com.app.client.boltfood.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import br.com.app.client.boltfood.model.entity.enums.Sexo;

public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String nome;
    private String cpf;
    private Date dataNascimento;
    private Integer sexo;
    private String telefone;
    private String email;
    private String senha;

    private List<Pedido> pedidos = new ArrayList<>();

    public Cliente() {

    }

    public Cliente(String id, String nome, String cpf, Date dataNascimento, Sexo sexo, String telefone, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo.getCodigo();
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Sexo getSexo() {
        return Sexo.toEnum(sexo);
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo.getCodigo();
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
