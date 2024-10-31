package com.factura.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "factura")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "cliente_id")
    private int clienteId;

    @Column(name = "producto_id")
    private int productoId;

    @Column(name = "descripcion_cliente")
    private String descripcionCliente;

    @Column(name = "descripcion_producto")
    private String descripcionProducto;

    @Column(name = "total")
    private Double total;
}
