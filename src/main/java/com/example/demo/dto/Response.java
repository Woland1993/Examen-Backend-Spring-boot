package com.example.demo.dto;

import dominio.Imagen;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {

private Imagen[] imagenes;
private Imagen imagen;
	
}
