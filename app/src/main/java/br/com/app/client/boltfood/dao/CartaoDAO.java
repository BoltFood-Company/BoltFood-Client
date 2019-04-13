package br.com.app.client.boltfood.dao;

import com.google.firebase.firestore.FirebaseFirestore;

import br.com.app.client.boltfood.model.entity.Cartao;

public class CartaoDAO {

    private FirebaseFirestore db;

    public CartaoDAO() {
        db = FirebaseFirestore.getInstance();
    }

    public void inserirCartao(Cartao cartao) {

    }
}
