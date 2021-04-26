package com.backend.servicios;

import com.backend.entidades_modelo.*;

import com.backend.repositorios.LugarRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LugarServicio {

    @Autowired
    LugarRepositorio LugarRepositorio;

    @Autowired
    LugarRepositorio lugarRepositorio;



    public List<Lugar> obtenerTodos() {
        List<Lugar> lista = LugarRepositorio.findAll();
        return lista;
    }

    public Optional<Lugar> obtenerPorId(Long id) {
        return LugarRepositorio.findById(id);
    }

    public Optional<Lugar> obtenerPorNombre(String ni) {
        return LugarRepositorio.findByNombre(ni);
    }

    public void guardar(Lugar Lugar) {
        LugarRepositorio.save(Lugar);
    }

    public void borrar(Long id) {
        LugarRepositorio.deleteById(id);
    }

    public boolean existePorNombre(String ni) {
        return LugarRepositorio.existsByNombre(ni);
    }

    public boolean existePorId(Long id) {
        return LugarRepositorio.existsById(id);
    }

    public Long cantidadDeRegistros (){
        return LugarRepositorio.count();
    }

}
