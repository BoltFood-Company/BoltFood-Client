package br.com.app.client.boltfood.model.entity;

import com.google.firebase.firestore.DocumentReference;

import java.io.Serializable;

public class PedidoItem implements Serializable {

    public DocumentReference getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(DocumentReference idProduto) {
        this.idProduto = idProduto;
    }

    public int getQtde() {
        return qtde;
    }

    public void setQtde(int qtde) {
        this.qtde = qtde;
    }

    private DocumentReference idProduto;
    private int qtde;
}
