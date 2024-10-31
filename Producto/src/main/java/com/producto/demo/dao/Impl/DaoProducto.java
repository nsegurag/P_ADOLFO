package com.producto.demo.dao.Impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.producto.demo.dao.IProducto;
import com.producto.demo.entity.Producto;

@Repository
@Transactional
public class DaoProducto implements IProducto {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        return em.createQuery("FROM Producto", Producto.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findById(int id) {
        return em.find(Producto.class, id);
    }

    @Override
    @Transactional
    public Producto agregarRegistro(Producto producto) {
        em.persist(producto);
        return producto;
    }

    @Override
    @Transactional
    public Producto actualizarRegistro(Producto producto) {
        return em.merge(producto);
    }

    @Override
    @Transactional
    public void eliminarRegistro(int id) {
        Producto producto = findById(id);
        if (producto != null) {
            em.remove(producto);
        }
    }
}
