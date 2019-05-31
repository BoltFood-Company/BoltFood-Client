package br.com.app.client.boltfood.dao;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import br.com.app.client.boltfood.R;
import br.com.app.client.boltfood.controller.ClienteController;
import br.com.app.client.boltfood.model.entity.Cliente;
import br.com.app.client.boltfood.model.entity.enums.Sexo;
import br.com.app.client.boltfood.view.ClienteActivity;

public class ClienteDAO {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void inserirCliente(final Cliente cliente) {
        db.collection("Cliente").add(cliente);
    }

    public int alterarCliente(String idDocument, Cliente cliente) {

        try {
            db.collection("Cliente").document(idDocument).update("cpf", cliente.getCpf(), "dataNascimento", cliente.getDataNascimento(),
                    "nome", cliente.getNome(), "sexo", cliente.getSexo(), "telefone", cliente.getTelefone());

            return 1;

        } catch (Exception ex) {
            return 0;
        }
    }
}
