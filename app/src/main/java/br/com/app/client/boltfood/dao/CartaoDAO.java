package br.com.app.client.boltfood.dao;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import br.com.app.client.boltfood.model.entity.Cartao;

public class CartaoDAO {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String doc;
    public String inserirCartao(Cartao cartao) {
        db.collection("Cartao").add(cartao).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                doc = documentReference.getId();
            }
        });
        return doc;
    }

    public int atualizaCartao(Cartao cartao, String idDocumento) {
        try {
            db.collection("Cartao")
                .document(idDocumento)
                    .update("ano", cartao.getAno(), "cpf", cartao.getCpf(),
                    "nome", cartao.getNome(), "numeroCartao", cartao.getNumeroCartao(), "cvv", cartao.getCvv(), "mes",cartao.getMes());

            return 1;

        } catch (Exception ex) {
            return 0;
        }
    }


}
