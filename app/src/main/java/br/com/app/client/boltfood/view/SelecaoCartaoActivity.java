package br.com.app.client.boltfood.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cooltechworks.creditcarddesign.CardEditActivity;
import com.cooltechworks.creditcarddesign.CreditCardUtils;
import com.cooltechworks.creditcarddesign.CreditCardView;
import com.cooltechworks.creditcarddesign.pager.CardNameFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import br.com.app.client.boltfood.R;
import br.com.app.client.boltfood.model.entity.Cartao;

public class SelecaoCartaoActivity extends AppCompatActivity {

    private LinearLayout cardContainer;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String numCartao;
    private String anoCartao;
    private String mesCartao;
    private String cpfCartao;
    private String cvvCartao;
    private String nomeCartao;
    private String idDocumentoCartao;
    private String firebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecao_cartao);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Forma De Pagamento");

        cardContainer = findViewById(R.id.cardContainer);

        final CreditCardView cartao = new CreditCardView(this);
        cardContainer.addView(cartao);

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            numCartao = (String) extras.get("numCartao");
            anoCartao = (String) extras.get("anoCartao");
            mesCartao = (String) extras.get("mesCartao");
            cpfCartao = (String) extras.get("cpfCartao");
            cvvCartao = (String) extras.get("cvvCartao");
            nomeCartao = (String) extras.get("nomeCartao");
            idDocumentoCartao = (String) extras.get("idDocumentoCartao");

            if(mesCartao.length() == 1){
                mesCartao = 0 + mesCartao;
            }

            cartao.setCardExpiry(mesCartao + "/" + anoCartao);
            cartao.setCardNumber(numCartao.substring(0,4)+"XXXXXXXXXXXX");
            cartao.setCVV(cvvCartao);
            cartao.setCardHolderName(nomeCartao);
        } else {
            db.collection("Cartao").whereEqualTo("idUser", auth.getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if(!queryDocumentSnapshots.isEmpty()){
                        Cartao firebaseCartao = queryDocumentSnapshots.getDocuments().get(0).toObject(Cartao.class);
                        idDocumentoCartao = queryDocumentSnapshots.getDocuments().get(0).getId();

                        numCartao = firebaseCartao.getNumeroCartao();
                        anoCartao = firebaseCartao.getAno();
                        mesCartao = firebaseCartao.getMes();
                        cpfCartao = firebaseCartao.getCpf();
                        cvvCartao = firebaseCartao.getCvv();
                        nomeCartao = firebaseCartao.getNome();

                        if(mesCartao.length() == 1){
                            mesCartao = 0 + mesCartao;
                        }

                        cartao.setCardExpiry(mesCartao + "/" + anoCartao);
                        cartao.setCardNumber(numCartao.substring(0,4)+"XXXXXXXXXXXX");
                        cartao.setCVV(cvvCartao);
                        cartao.setCardHolderName(nomeCartao);
                    }

                }
            });

        }

        final Intent intent = new Intent(getApplicationContext(), CartaoActivity.class);
        cartao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                intent.putExtra("numCartao",numCartao);
                intent.putExtra("anoCartao",anoCartao);
                intent.putExtra("mesCartao",mesCartao);
                intent.putExtra("cpfCartao",cpfCartao);
                intent.putExtra("cvvCartao", cvvCartao);
                intent.putExtra("nomeCartao", nomeCartao);
                intent.putExtra("idDocumentoCartao", idDocumentoCartao);



                CreditCardView creditCardView = (CreditCardView) v;
                String cardNumber = creditCardView.getCardNumber();
                String expiry = creditCardView.getExpiry();
                String cardHolderName = creditCardView.getCardHolderName();
                String cvv = creditCardView.getCVV();
                startActivity(intent);
                kill_activity();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    void kill_activity()
    {
        finish();
    }


}
