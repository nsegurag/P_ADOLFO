package com.factura.demo.dao;

import java.util.List;

import com.factura.demo.entity.Factura;

public interface IFactura {
    List<Factura> findAll();
    Factura findById(int id);
    Factura agregarRegistro(Factura factura);
    void eliminarRegistro(int id);
}
