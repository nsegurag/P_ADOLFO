package com.cliente.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cliente.demo.dao.ICliente;
import com.cliente.demo.entity.Cliente;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ICliente clienteDao;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Cliente>> getAllClientes() {
        List<Cliente> lista = clienteDao.findAll();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable int id) {
        Cliente cliente = clienteDao.findById(id);
        if (cliente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }
    
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Cliente> agregarCliente(@RequestBody Cliente cliente) {
        Cliente nuevoCliente = clienteDao.agregarRegistro(cliente);
        return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable int id) {
        Cliente cliente = clienteDao.findById(id);
        if (cliente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        clienteDao.eliminarRegistro(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable int id, @RequestBody Cliente cliente) {
        Cliente clienteExistente = clienteDao.findById(id);
        if (clienteExistente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (cliente.getNombre() != null) {
            clienteExistente.setNombre(cliente.getNombre());
        }
        if (cliente.getEmail() != null) {
            clienteExistente.setEmail(cliente.getEmail());
        }
        if (cliente.getTelefono() != null) {
            clienteExistente.setTelefono(cliente.getTelefono());
        }
        if (cliente.getDireccion() != null) {
            clienteExistente.setDireccion(cliente.getDireccion());
        }

        Cliente clienteActualizado = clienteDao.agregarRegistro(clienteExistente);
        return new ResponseEntity<>(clienteActualizado, HttpStatus.OK);
    }
}
