package br.com.app.client.boltfood.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

import br.com.app.client.boltfood.R;

public class LoginActivity extends AppCompatActivity {

    private EditText login;
    private EditText password;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //DocumentReference docRef = db.collection("Cliente").document("sAHCQgoPjzCcORLa35Rs");
        //System.out.println(docRef);
    }

    public void logar(View view) {

    }

    public void cadastrar(View view){
        Intent intent = new Intent(getApplicationContext(), ClienteActivity.class);
        startActivity(intent);
    }

    private class DocumentReference {
    }
}
