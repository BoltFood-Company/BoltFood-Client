package br.com.app.client.boltfood.view;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.app.client.boltfood.R;
import br.com.app.client.boltfood.model.entity.Cliente;

public class AlteracaoClienteActivity extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private EditText nome;
    private EditText telefone;
    private EditText documento;
    private EditText dataNascimento;
    private Spinner sexo;

    private String idDocumentCliente = "";

    private final String[] sexos = new String[] { "Selecione", "Masculilno", "Feminino", "Outro" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alteracao_cliente);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Cadastro");

        nome = findViewById(R.id.nomeAlteracaoClienteEditText);
        telefone = findViewById(R.id.telefoneAlteracaoEditText);
        documento = findViewById(R.id.documentoAlteracaoEditText);
        dataNascimento = findViewById(R.id.dataNascimentoAlteracaoEditText);
        sexo = findViewById(R.id.sexoAlteracaoClienteSpinner);

        carregaCliente();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaSexos();
        carregaCliente();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void carregaCliente(){
        db.collection("Cliente")
                .whereEqualTo("id", auth.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                idDocumentCliente = document.getId();
                                Cliente cliente = document.toObject(Cliente.class);

                                if (cliente.getNome() != null && !cliente.getNome().equals(""))
                                    nome.setText(cliente.getNome());

                                if (cliente.getCpf() != null && !cliente.getCpf().equals(""))
                                    documento.setText(cliente.getCpf());

                                if (cliente.getTelefone() != null && !cliente.getTelefone().equals(""))
                                    telefone.setText(cliente.getTelefone());

                                if (cliente.getDataNascimento() != null && !cliente.getDataNascimento().equals(""))
                                    dataNascimento.setText(cliente.getDataNascimento());

                                if (cliente.getSexo() != null && !cliente.getSexo().equals("")){

                                }
                            }
                        }
                    }
                });
    }

    private void carregaSexos(){

        List<String> plantsList = new ArrayList<>(Arrays.asList(sexos));

        ArrayAdapter<String> sexoSpinnerArrayAdapter = new ArrayAdapter<String>(
                getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, plantsList){

            @Override
            public boolean isEnabled(int position) {
                if (position == 0){
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        sexoSpinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sexo.setAdapter(sexoSpinnerArrayAdapter);
    }
}
