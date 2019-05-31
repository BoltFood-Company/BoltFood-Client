package br.com.app.client.boltfood.controller;

import android.app.Activity;

import br.com.app.client.boltfood.dao.ClienteDAO;
import br.com.app.client.boltfood.model.entity.Cliente;

public class ClienteController {

    private ClienteDAO clienteDAO;

    public void inserirCliente(Cliente cliente) {
        clienteDAO = new ClienteDAO();
        clienteDAO.inserirCliente(cliente);
    }

    public int alterarCliente(String idDocument, Cliente cliente) {
        clienteDAO = new ClienteDAO();
         return clienteDAO.alterarCliente(idDocument, cliente);
    }
}
