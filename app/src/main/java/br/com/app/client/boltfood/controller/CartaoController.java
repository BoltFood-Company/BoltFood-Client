package br.com.app.client.boltfood.controller;

import br.com.app.client.boltfood.dao.CartaoDAO;
import br.com.app.client.boltfood.model.entity.Cartao;

public class CartaoController {

    private CartaoDAO cartaoDAO;

    public CartaoController(){
        cartaoDAO = new CartaoDAO();
    }

    public void inserirCartao(Cartao cartao) {
        cartaoDAO.inserirCartao(cartao);
    }
}
