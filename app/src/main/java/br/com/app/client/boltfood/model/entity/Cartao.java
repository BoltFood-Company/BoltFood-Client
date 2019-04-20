package br.com.app.client.boltfood.model.entity;

import java.io.Serializable;

public class Cartao implements Serializable {
    private static final long serialVersionUID = 1L;

    private String cpf, mes, ano, cvv, numeroCartao, nome, idUser;;

    public Cartao() {

    }

    public Cartao(String cpf, String mes, String ano, String cvv, String numeroCartao, String nome, String idUser) {
        this.cpf = cpf;
        this.mes = mes;
        this.ano = ano;
        this.cvv = cvv;
        this.numeroCartao = numeroCartao;
        this.nome = nome;
        this.idUser = idUser;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
