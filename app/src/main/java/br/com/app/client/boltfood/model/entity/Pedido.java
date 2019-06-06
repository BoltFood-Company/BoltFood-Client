package br.com.app.client.boltfood.model.entity;

import com.google.firebase.firestore.DocumentReference;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private Date data;
    private String idCliente;
    private DocumentReference idRestaurante;
    private List<Produto> PedidoItem;
    private long totalPedido;
    private long numeroPedido;
    private String nomeRestaurante;


    public Pedido() {

    }

    public Pedido( String nomeRestaurante, long numeroPedido, long totalPedido, String id,Date data, String idCliente, DocumentReference idRestaurante) {
        this.idCliente = idCliente;
        this.id = id;
        this.totalPedido = totalPedido;
        this.numeroPedido = numeroPedido;
        this.data = data;
        this.idRestaurante = idRestaurante;
    }

    public List<Produto> getPedidoItem() {
        return PedidoItem;
    }

    public String getNomeRestaurante() {
        return nomeRestaurante;
    }

    public void setNomeRestaurante(String nomeRestaurante) {
        this.nomeRestaurante = nomeRestaurante;
    }

    public void setPedidoItem(List<Produto> pedidoItem) {
        PedidoItem = pedidoItem;
    }

    public DocumentReference getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(DocumentReference idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public long getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(long numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public String getId() {
        return id;
    }

    public long getTotalPedido() {
      return totalPedido;
    }

    public void setTotalPedido(long totalPedido) {
        this.totalPedido = totalPedido;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }
}
