package com.producto.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.producto.demo.dao.IProducto;
import com.producto.demo.entity.Producto;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private IProducto productoDao;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Producto>> getAllProductos() {
        List<Producto> lista = productoDao.findAll();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable int id) {
        Producto producto = productoDao.findById(id);
        return producto != null ? new ResponseEntity<>(producto, HttpStatus.OK)
                                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Producto> agregarProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoDao.agregarRegistro(producto);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable int id, @RequestBody Producto producto) {
        Producto productoExistente = productoDao.findById(id);
        if (productoExistente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (producto.getNombre() != null) productoExistente.setNombre(producto.getNombre());
        if (producto.getDescripcion() != null) productoExistente.setDescripcion(producto.getDescripcion());
        if (producto.getPrecio() != null) productoExistente.setPrecio(producto.getPrecio());
        if (producto.getStock() != null) productoExistente.setStock(producto.getStock());

        Producto productoActualizado = productoDao.actualizarRegistro(productoExistente);
        return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable int id) {
        Producto producto = productoDao.findById(id);
        if (producto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productoDao.eliminarRegistro(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
