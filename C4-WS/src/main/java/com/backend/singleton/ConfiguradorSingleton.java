package com.backend.singleton;
import com.backend.entidades_modelo.*;
import com.backend.servicios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ConfiguradorSingleton implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(ConfiguradorSingleton.class);
    @Autowired
    LugarServicio lugarServicio;

    //Cómo importar variables de configuración al proyecto
    @Value("${spring.datasource.password}")
    private String pwd;


    @Override
    public void run(String... args) throws Exception {
        log.info("DB PWD IS: "+pwd);

        // Cómo instanciar datos en la BD (puede ser bueno o malo depende de lo que se haga
        if (this.lugarServicio.cantidadDeRegistros()<=0){
            log.info(" DB is empty, is time to push registries");
            Lugar lugar = new Lugar("lujan", "un pueblo grande");
            lugarServicio.guardar(lugar);

            lugar = new Lugar("caba", "una ciudad demasiado grande");
            lugarServicio.guardar(lugar);

            lugar = new Lugar("pilar", "ni fu ni fa");
            lugarServicio.guardar(lugar);

        }
    }
}
