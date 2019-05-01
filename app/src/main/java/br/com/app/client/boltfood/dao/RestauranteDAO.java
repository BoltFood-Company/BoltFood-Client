package br.com.app.client.boltfood.dao;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import br.com.app.client.boltfood.model.entity.Restaurante;

public class RestauranteDAO {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public List<Restaurante> buscarRestaurantes(){

        CollectionReference docRef = db.collection("Restaurante");
        docRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Restaurante> restaurantesList = queryDocumentSnapshots.toObjects(Restaurante.class);
            }
        });

        return null;
    }
}
