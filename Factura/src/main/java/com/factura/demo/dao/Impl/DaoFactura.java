package com.factura.demo.dao.Impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.factura.demo.dao.IFactura;
import com.factura.demo.entity.Factura;

@Repository
@Transactional
public class DaoFactura implements IFactura {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<Factura> findAll() {
        return em.createQuery("FROM Factura", Factura.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Factura findById(int id) {
        return em.find(Factura.class, id);
    }

    @Override
    @Transactional
    public Factura agregarRegistro(Factura factura) {
        em.persist(factura);
        return factura;
    }

    @Override
    @Transactional
    public void eliminarRegistro(int id) {
        Factura factura = findById(id);
        if (factura != null) {
            em.remove(factura);
        }
    }
}
