package com.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BackendApplication {

	/*
	Si quiero agregar otras propiedades, por ejemplo
	* Bean para agregar mapeo de CORS
	* Crear un singleton para que se encargue de instanciar algunos objetos al iniciar el servicio
	 */
	/*@Bean
	public WebMvcConfigurer configure() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("*").allowedOrigins("*");
			}
		};
	}

	public static void main(String[] args) {
		//SpringApplication.run(BackendApplication.class, args);
		ConfigurableApplicationContext applicationContext = SpringApplication.run(BackendApplication.class, args);
		//ConfiguradorSingleton configuradorSingleton = applicationContext.getBean(ConfiguradorSingleton.class);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//Accesos accesos = applicationContext.getBean(Accesos.class);

	}
	*/
	/*
		Si quiero que instancie directamente la aplicación web base
	 */
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
