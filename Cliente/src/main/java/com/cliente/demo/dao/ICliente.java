package com.cliente.demo.dao;

import java.util.List;

import com.cliente.demo.entity.Cliente;


public interface ICliente {
    List<Cliente> findAll();
    Cliente findById(int id);
    Cliente agregarRegistro(Cliente cliente);
    void eliminarRegistro(int id);
}