package br.com.app.client.boltfood.dao;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import br.com.app.client.boltfood.model.entity.Pedido;

public class PedidoDAO {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String doc;
    public void inserir(final Pedido pedido) {
        db.collection("Pedido").add(pedido).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                pedido.setId(documentReference.getId());
                db.collection("Pedido").document(pedido.getId()).update("id", pedido.getId());
            }
        });


    }
}
