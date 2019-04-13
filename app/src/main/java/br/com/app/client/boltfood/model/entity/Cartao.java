package br.com.app.client.boltfood.model.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Cartao implements Serializable {

    private String id;
    private long numeroCartao;
    private Timestamp validadeCartao;
    private int cvcCartao;
    private String nomeTitularCartao;
    private long documentoTitularCartao;

    public Cartao() {

    }

    public Cartao(String id, long numeroCartao, Timestamp validadeCartao, int cvcCartao, String nomeTitularCartao, long documentoTitularCartao) {
        this.id = id;
        this.numeroCartao = numeroCartao;
        this.validadeCartao = validadeCartao;
        this.cvcCartao = cvcCartao;
        this.nomeTitularCartao = nomeTitularCartao;
        this.documentoTitularCartao = documentoTitularCartao;
    }

    public Cartao(long numeroCartao, Timestamp validadeCartao, int cvcCartao, String nomeTitularCartao, long documentoTitularCartao) {
        this.numeroCartao = numeroCartao;
        this.validadeCartao = validadeCartao;
        this.cvcCartao = cvcCartao;
        this.nomeTitularCartao = nomeTitularCartao;
        this.documentoTitularCartao = documentoTitularCartao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(long numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public Timestamp getValidadeCartao() {
        return validadeCartao;
    }

    public void setValidadeCartao(Timestamp validadeCartao) {
        this.validadeCartao = validadeCartao;
    }

    public int getCvcCartao() {
        return cvcCartao;
    }

    public void setCvcCartao(int cvcCartao) {
        this.cvcCartao = cvcCartao;
    }

    public String getNomeTitularCartao() {
        return nomeTitularCartao;
    }

    public void setNomeTitularCartao(String nomeTitularCartao) {
        this.nomeTitularCartao = nomeTitularCartao;
    }

    public long getDocumentoTitularCartao() {
        return documentoTitularCartao;
    }

    public void setDocumentoTitularCartao(long documentoTitularCartao) {
        this.documentoTitularCartao = documentoTitularCartao;
    }
}
