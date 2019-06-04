package br.com.app.client.boltfood.controller;

import br.com.app.client.boltfood.dao.CartaoDAO;
import br.com.app.client.boltfood.model.entity.Cartao;

public class CartaoController {

    private CartaoDAO cartaoDAO;

    public String inserirCartao(Cartao cartao) {
        cartaoDAO = new CartaoDAO();
        return cartaoDAO.inserirCartao(cartao);
    }

    public void atualizarCartao(Cartao cartao, String idDocumento) {
        cartaoDAO = new CartaoDAO();
        cartaoDAO.atualizaCartao(cartao, idDocumento);
    }


}
