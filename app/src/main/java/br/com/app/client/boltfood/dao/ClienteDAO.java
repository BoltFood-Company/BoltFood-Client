package br.com.app.client.boltfood.dao;

import com.google.firebase.firestore.FirebaseFirestore;

import br.com.app.client.boltfood.model.entity.Cliente;

public class ClienteDAO {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void inserirCliente(Cliente cliente) {
        db.collection("Cliente").add(cliente);
    }
}
