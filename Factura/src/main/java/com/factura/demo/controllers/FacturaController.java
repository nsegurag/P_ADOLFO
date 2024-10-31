package com.factura.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.factura.demo.dao.IFactura;
import com.factura.demo.entity.Factura;
import com.producto.demo.entity.Producto;

@RestController
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private IFactura facturaDao;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Factura>> getAllFacturas() {
        List<Factura> lista = facturaDao.findAll();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Factura> getFacturaById(@PathVariable int id) {
        Factura factura = facturaDao.findById(id);
        return factura != null ? new ResponseEntity<>(factura, HttpStatus.OK)
                               : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Factura> agregarFactura(@Validated @RequestBody Factura factura) {
        String clienteUrl = "http://localhost:8080/clientes/" + factura.getClienteId();
        String productoUrl = "http://localhost:8082/productos/" + factura.getProductoId();

        try {
            String descripcionCliente = restTemplate.getForObject(clienteUrl, String.class);
            if (descripcionCliente == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Producto producto = restTemplate.getForObject(productoUrl, Producto.class);
            if (producto == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            factura.setDescripcionCliente(descripcionCliente);
            factura.setDescripcionProducto(producto.getDescripcion());
            factura.setTotal(producto.getPrecio());

            Factura nuevaFactura = facturaDao.agregarRegistro(factura);
            return new ResponseEntity<>(nuevaFactura, HttpStatus.CREATED);
        } catch (HttpClientErrorException e) {

            System.err.println("Error en la llamada REST: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFactura(@PathVariable int id) {
        Factura factura = facturaDao.findById(id);
        if (factura == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        facturaDao.eliminarRegistro(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
