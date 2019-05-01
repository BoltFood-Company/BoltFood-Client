package br.com.app.client.boltfood.dao;

import com.google.firebase.firestore.FirebaseFirestore;

import br.com.app.client.boltfood.model.entity.Cartao;

public class CartaoDAO {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void inserirCartao(Cartao cartao) {
        db.collection("Cartao").add(cartao);


    }
}
