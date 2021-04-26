package com.backend.controladores;

import com.backend.dto.CustomMessage;
import com.backend.dto.Mensaje;
import com.backend.entidades_modelo.Lugar;
import com.backend.servicios.LugarServicio;
import com.backend.servicios.RabbitMQService;
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
@RequestMapping("/api/rabbit/")

public class RabbitController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    RabbitMQService rabbitMQService;

    @GetMapping("listall")
    public ResponseEntity<List<CustomMessage>> getLista(){
        CustomMessage cm = (CustomMessage) rabbitMQService.pullMessage();
        log.info(" Obteniendo lista de Lugares");
        return new ResponseEntity(new Mensaje("Text is mandatory"),HttpStatus.OK);
    }

    @PostMapping("nuevo")
    public ResponseEntity<?> create (@RequestBody String cm){

        CustomMessage customMessage = new Gson().fromJson(cm, CustomMessage.class);
        log.info(" Registering Custom Message: "+customMessage.getText());
        if(StringUtils.isBlank(customMessage.getText()))
            return new ResponseEntity(new Mensaje("Text is mandatory"), HttpStatus.BAD_REQUEST);
        /*if(lugarServicio.existePorNombre(lugar.getNombre()))
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        */
        rabbitMQService.send(customMessage);
        return new ResponseEntity(new Mensaje("Message has been saved"), HttpStatus.CREATED);
    }
}
