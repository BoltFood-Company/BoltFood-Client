package br.com.app.client.boltfood.view.util;

import android.widget.EditText;

public class Validacao {

    // VALIDA EDIT TEXT
    public static boolean validarEditText(EditText editText, String msgError) {

        if (editText.getText().toString().equals("")) {
            editText.setError(msgError);
            return false;
        }

        return true;
    }

}
