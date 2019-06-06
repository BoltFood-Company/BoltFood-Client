package br.com.app.client.boltfood.controller;

import br.com.app.client.boltfood.dao.PedidoDAO;
import br.com.app.client.boltfood.model.entity.Pedido;


public class PedidoController {

    private static PedidoDAO pedidoDAO = new PedidoDAO();

    public void inserir(Pedido pedido) {
        pedidoDAO.inserir(pedido);

    }
}
