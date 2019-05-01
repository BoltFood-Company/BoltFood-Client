package br.com.app.client.boltfood.controller;

import java.util.List;

import br.com.app.client.boltfood.dao.RestauranteDAO;
import br.com.app.client.boltfood.model.entity.Restaurante;

public class RestauranteController {

    private RestauranteDAO restauranteDAO;

    public RestauranteController(){
        restauranteDAO = new RestauranteDAO();
    }

    public List<Restaurante> buscarRestaurantes(){
        return restauranteDAO.buscarRestaurantes();

    }


}
