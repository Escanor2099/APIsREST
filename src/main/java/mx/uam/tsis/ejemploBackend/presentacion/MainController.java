package mx.uam.tsis.ejemploBackend.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

/**
 * Controlador web
 * 
 * @author aaron
 *
 */
@Controller
@Slf4j 
class MainController {
	
	@GetMapping("/")
	public String index() {
		
		log.info("se invocó el método index()");
		
		return "index";
	}
}
