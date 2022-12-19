package com.example.demo.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.Base64Utils;

import com.google.gson.Gson;
import dominio.Imagen;

public class ImagenServices extends ConsumeServices {

	public ImagenServices() {

	}

	public List<Imagen> getAll() throws MalformedURLException, IOException {
		ConsumeServices c = new ConsumeServices("https://apitest-bt.herokuapp.com/api/v1/imagenes", "GET",
				"application/json");
		c.setRequestProperty("user", "User123");
		c.setRequestProperty("password", "Password123");
		List<Imagen> img2 = new ArrayList<>();
		Imagen[] img = new Gson().fromJson(c.getString(), Imagen[].class);
		for (int i = 0; i < img.length; i++) {
			if (img[i].getBase64().matches("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$")) {
				img2.add(img[i]);
			}
			;
		}
		return img2;
	}

	public Imagen postImagen(byte[] bimg, String nombre) throws IOException {
		Imagen imagen = new Imagen();
		imagen.setNombre(nombre);
		System.out.println("image created");
		imagen.setBase64(Base64Utils.encodeToString(bimg));
		ConsumeServices c = new ConsumeServices("https://apitest-bt.herokuapp.com/api/v1/imagenes", "POST","application/json");
		c.setRequestProperty("user", "User123");
		c.setRequestProperty("password", "Password123");
		c.setBody(new Gson().toJson(imagen));
		Imagen img = new Gson().fromJson(c.getString(), Imagen.class);
		return img;
	}
}
