package br.com.app.client.boltfood.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.app.client.boltfood.R;
import br.com.app.client.boltfood.controller.ClienteController;
import br.com.app.client.boltfood.model.entity.Cliente;
import br.com.app.client.boltfood.view.util.Constantes;
import de.hdodenhof.circleimageview.CircleImageView;

public class AlteracaoClienteActivity extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    private EditText nome;
    private EditText telefone;
    private EditText documento;
    private EditText dataNascimento;
    private Spinner sexo;
    private CircleImageView imagemUsuario;

    private String idDocumentCliente = "";
    private Cliente cliente;
    private String urlImagemUsuario;

    private Bitmap imgPerfil;

    private final String[] sexos = new String[] { "Selecione", "Masculino", "Feminino", "Outro" };

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
        imagemUsuario = findViewById(R.id.imagemPerfilAlteracao);

        carregaSexos();
        carregaCliente();
    }

    private void carregaImagem(){
        if (auth.getCurrentUser().getPhotoUrl() != null){
            Glide.with(getApplicationContext()).load(auth.getCurrentUser().getPhotoUrl()).into(imagemUsuario);
            Log.d("IMAGEM",auth.getCurrentUser().getPhotoUrl().toString());
        } else {
            imagemUsuario.setImageResource(R.drawable.padrao);
        }
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

                                if (!cliente.getUrlImagem().equals("")) {
                                    Toast.makeText(getApplicationContext(), cliente.getUrlImagem(), Toast.LENGTH_LONG).show();
                                    Glide.with(getApplicationContext()).load(cliente.getUrlImagem()).into(imagemUsuario);
                                }
                                else
                                    imagemUsuario.setImageResource(R.drawable.padrao);

                                if (cliente.getSexo() != null && !cliente.getSexo().equals("")){
                                    switch (cliente.getSexo().toLowerCase()) {
                                        case "masculino":
                                            sexo.setSelection(1);
                                            break;

                                        case "feminino":
                                            sexo.setSelection(2);
                                            break;

                                        case "outro":
                                            sexo.setSelection(3);
                                            break;

                                        default:
                                            sexo.setSelection(0);
                                            break;
                                    }
                                }
                            }
                        }
                    }
                });
    }

    public void alterarCadastro(View v) {
        Cliente alterarCliente = new Cliente();
        alterarCliente.setNome(nome.getText().toString());

        alterarCliente.setCpf(documento.getText().toString());
        alterarCliente.setTelefone(telefone.getText().toString());
        alterarCliente.setDataNascimento(dataNascimento.getText().toString());

        if (!urlImagemUsuario.equals(""))
            alterarCliente.setUrlImagem(urlImagemUsuario);

        if (sexo.getSelectedItemPosition() > 0)
            alterarCliente.setSexo(sexo.getSelectedItem().toString());
        else
            alterarCliente.setSexo("");

        ClienteController clienteController = new ClienteController();
        if (clienteController.alterarCliente(idDocumentCliente, alterarCliente) > 0) {
            Toast.makeText(getApplicationContext(), "Cadastro atualizado!", Toast.LENGTH_LONG).show();
            salvarImagemStorage();
        } else {
            Toast.makeText(getApplicationContext(), "Não foi possível atualizar. Tente mais tarde!", Toast.LENGTH_LONG).show();
        }
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

    public void tirarFoto(View view){
        Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(cameraIntent, Constantes.TIRAR_FOTO_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if(resultCode != Activity.RESULT_CANCELED) {
            switch (requestCode){
                case Constantes.TIRAR_FOTO_CAMERA:

                    if (data != null){
                        Bundle bundle = data.getExtras();
                        if (bundle != null){
                            imgPerfil = (Bitmap) bundle.get("data");
                            imagemUsuario.setImageBitmap(imgPerfil);
                            salvarImagemStorage();
                        }
                    }

                    break;

                case Constantes.PEGAR_FOTO_GALERIA:

                    break;
            }
        }
    }

    private void salvarImagemStorage() {
        //recuperar imagem
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imgPerfil.compress(Bitmap.CompressFormat.PNG, 80, baos);
        byte[] dadosImagem = baos.toByteArray();

        //salvar no firebase
        StorageReference imagemRef = storageReference
                .child("imagens")
                .child("perfil")
                .child(auth.getUid() + ".png");

        UploadTask uploadTask = imagemRef.putBytes(dadosImagem);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Falha ao fazer upload da imagem", Toast.LENGTH_LONG).show();
                urlImagemUsuario = "";
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(getApplicationContext(), "urlString", Toast.LENGTH_LONG).show();

                storageReference.child(Constantes.CAMINHO_IMAGEM_PERFIL + auth.getUid() + ".png").getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        Toast.makeText(getApplicationContext(), task.getResult().toString(), Toast.LENGTH_LONG).show();
                        urlImagemUsuario = task.getResult().toString();
                    }
                });
            }
        });
    }
}
