package com.cliente.demo.dao.Impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cliente.demo.dao.ICliente;
import com.cliente.demo.entity.Cliente;

@Repository
@Transactional
public class DaoCliente implements ICliente {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return em.createQuery("FROM Cliente", Cliente.class).getResultList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Cliente findById(int id) {
        return em.find(Cliente.class, id);
    }
   
    @Override
    @Transactional
    public Cliente agregarRegistro(Cliente cliente) {
        em.persist(cliente);
        return cliente;
    }
    
    @Override
    @Transactional
    public void eliminarRegistro(int id) {
        Cliente cliente = findById(id);
        if (cliente != null) {
            em.remove(cliente);
        }
    }
}

