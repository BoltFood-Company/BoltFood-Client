package br.com.app.client.boltfood.controller;

public class PrincipalController {


    public String upperCase(String palavra) {
        return palavra.substring( 0, 1 ).toUpperCase() + palavra.substring( 1 );
    }
}
