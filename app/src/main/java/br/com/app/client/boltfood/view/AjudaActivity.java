package br.com.app.client.boltfood.view;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import br.com.app.client.boltfood.BuildConfig;
import br.com.app.client.boltfood.R;

public class AjudaActivity extends AppCompatActivity {

    private TextView versao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajuda);

        versao = findViewById(R.id.versaoTextView);

        versao.setText(getString(R.string.versao) + " " + BuildConfig.VERSION_NAME + "." + BuildConfig.VERSION_CODE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
