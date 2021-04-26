package com.backend.controladores;
import com.backend.dto.Mensaje;
import com.backend.entidades_modelo.*;
import com.backend.servicios.LugarServicio;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/lugar/")
public class LugarControlador {
    
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    LugarServicio lugarServicio;


    @GetMapping("listartodos")
    public ResponseEntity<List<Lugar>> getLista(){
        List<Lugar> lista = lugarServicio.obtenerTodos();
        log.info(" Obteniendo lista de Lugares");
        return new ResponseEntity<List<Lugar>>(lista, HttpStatus.OK);
    }


    @GetMapping("detalle/{id}")
    public ResponseEntity<Lugar> getOne(@PathVariable Long id){
        log.info(" Obteniendo detalle de Lugar: "+id);

        if(!lugarServicio.existePorId(id))
            return new ResponseEntity(new Mensaje("no existe ese lugar"), HttpStatus.NOT_FOUND);
        Lugar Lugar = lugarServicio.obtenerPorId(id).get();
        return new ResponseEntity<Lugar>(Lugar, HttpStatus.OK);
    }

    @PostMapping("nuevo")
    public ResponseEntity<?> create (@RequestBody String lug){

        Lugar lugar = new Gson().fromJson(lug, Lugar.class);
        log.info(" Insertando nuevo Lugar: "+lugar.getNombre());
        if(StringUtils.isBlank(lugar.getNombre()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(lugarServicio.existePorNombre(lugar.getNombre()))
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);

        lugarServicio.guardar(lugar);
        return new ResponseEntity(new Mensaje("Lugar guardado"), HttpStatus.CREATED);
    }


    @PutMapping("actualizar/{id}")
    public ResponseEntity<?> update(@RequestBody Lugar Lugar, @PathVariable("id") Long id){
        log.info(" Actualizar Lugar: "+id);
        if(!lugarServicio.existePorId(id))
            return new ResponseEntity(new Mensaje("no existe el Lugar "+lugarServicio.obtenerPorId(id)), HttpStatus.NOT_FOUND);
        if(StringUtils.isBlank(Lugar.getNombre()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);



        Lugar baseLugar = lugarServicio.obtenerPorId(id).get();
        baseLugar.setDescripcion(Lugar.getDescripcion());
        baseLugar.setNombre(Lugar.getNombre());

        lugarServicio.guardar(baseLugar);
        return new ResponseEntity(new Mensaje("Lugar actualizado"), HttpStatus.CREATED);
    }

    @DeleteMapping("borrar/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        log.info(" Borrando Lugar: "+id);
        if(!lugarServicio.existePorId(id))
            return new ResponseEntity(new Mensaje("no existe ese Lugar"), HttpStatus.NOT_FOUND);
        lugarServicio.borrar(id);
        return new ResponseEntity(new Mensaje("Lugar eliminado"), HttpStatus.OK);
    }


}
