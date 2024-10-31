package com.producto.demo.dao;

import java.util.List;

import com.producto.demo.entity.Producto;


public interface IProducto {
    List<Producto> findAll();
    Producto findById(int id);
    Producto agregarRegistro(Producto producto);
    Producto actualizarRegistro(Producto producto);
    void eliminarRegistro(int id);
}
