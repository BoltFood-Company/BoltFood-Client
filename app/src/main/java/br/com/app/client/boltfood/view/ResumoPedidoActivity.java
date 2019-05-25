package br.com.app.client.boltfood.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import br.com.app.client.boltfood.R;

public class ResumoPedidoActivity extends AppCompatActivity {

    private TextView nome;
    private TextView total;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_pedido);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Pedido");

        Bundle extras = getIntent().getExtras();

        nome = findViewById(R.id.nomeResumoPedido);
        total = findViewById(R.id.precoResumoPedido);

        if(extras != null) {
            String idPedido = (String) extras.get("idPedido");
            String nomeRestaurante = (String) extras.get("nomeRestaurante");
            String urlRestaurante = (String) extras.get("urlRestaurante");
            String totalPedido = (String) extras.get("totalPedido");

            nome.setText(nomeRestaurante);
            total.setText("Total: " + totalPedido);


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
