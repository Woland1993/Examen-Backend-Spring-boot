package com.example.demo.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.services.ConsumeServices;
import com.example.demo.services.ImagenServices;

import ch.qos.logback.core.util.ContentTypeUtil;
import dominio.Imagen;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

@RestController
@Service
public class Controller {

	private final RestTemplate restTemplate;

	public Controller(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@GetMapping("getALL")
	List<Imagen> getALL() throws MalformedURLException, IOException {
		ImagenServices is = new ImagenServices();
		return is.getAll();
	}

	@PostMapping("imagen")
	Imagen imagen(@RequestBody byte[] imagen, @RequestParam String nombre) throws IOException {
		ImagenServices is = new ImagenServices();
		if(balidarFormato(nombre)){
			return is.postImagen(imagen, nombre);	
		}else {
			Imagen i=new Imagen();
			i.setNombre("formato no válido");
			return i;
		}
		
	}

	@PostMapping("64")
	boolean imagen(@RequestBody String imagen) throws IOException {

		return imagen.matches("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$");
	}

	public boolean balidarFormato(String nombre) {
		try {
		String[] s = nombre.split("\\.");
		switch (s[1].toLowerCase()) {
		case "jpg":
		case "png":
		case "gif":
			return true;
		default:
			return false;
		}
		}catch(Exception e) {
			System.out.print("no es un archivo valido");
			return false;
		}
	}
}
