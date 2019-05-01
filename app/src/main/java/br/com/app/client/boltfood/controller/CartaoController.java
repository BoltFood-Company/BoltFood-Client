package br.com.app.client.boltfood.controller;

import br.com.app.client.boltfood.dao.CartaoDAO;
import br.com.app.client.boltfood.model.entity.Cartao;

public class CartaoController {

    private CartaoDAO cartaoDAO;

    public void inserirCliente(Cartao cartao) {
        cartaoDAO = new CartaoDAO();

        cartaoDAO.inserirCartao(cartao);
    }
}
