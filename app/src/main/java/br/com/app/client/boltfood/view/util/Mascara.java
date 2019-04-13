package br.com.app.client.boltfood.view.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;
import java.util.Set;

public class Mascara implements TextWatcher {

    private String mascara;
    private EditText editText;
    private Set<String> symbolMask = new HashSet<String>();
    private boolean isUpdating;
    private String old = "";

    public Mascara(String _mascara, EditText _editText) {
        this.mascara = _mascara;
        this.editText = _editText;
        initSymbolMask();
    }

    private void initSymbolMask(){
        for (int i=0; i < mascara.length(); i++){
            char ch = mascara.charAt(i);
            if (ch != '#')
                symbolMask.add(String.valueOf(ch));
        }
    }

    public static String unmask(String s, Set<String> replaceSymbols) {

        for (String symbol : replaceSymbols)
            s = s.replaceAll("["+symbol+"]","");

        return s;
    }

    public static String mask(String formato, String text){
        String maskedText="";
        int i =0;
        for (char m : formato.toCharArray()) {
            if (m != '#') {
                maskedText += m;
                continue;
            }
            try {
                maskedText += text.charAt(i);
            } catch (Exception e) {
                break;
            }
            i++;
        }
        return maskedText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String str = unmask(s.toString(), symbolMask);
        String _mascara = "";

        if (isUpdating) {
            old = str;
            isUpdating = false;
            return;
        }

        if(str.length() > old.length())
            _mascara = mask(mascara,str);
        else
            _mascara = s.toString();

        isUpdating = true;

        editText.setText(_mascara);
        editText.setSelection(_mascara.length());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
