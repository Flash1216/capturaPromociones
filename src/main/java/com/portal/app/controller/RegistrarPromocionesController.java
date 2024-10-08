package com.portal.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.portal.app.request.RegistrarPromocionesRequest;
import com.portal.app.response.RegistrarPromocionesResponse;
import com.portal.app.service.CapService;
import com.portal.app.util.Parser;

@RestController
@RequestMapping(value="/service", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RegistrarPromocionesController 
{
	private static final Logger log = LoggerFactory.getLogger(RegistrarPromocionesController.class);
	@Autowired	private CapService service;
	
	@PostMapping("/registrarPromociones")
	public RegistrarPromocionesResponse regPromRes (@RequestBody RegistrarPromocionesRequest request) 
	{	
		log.debug("Controller Registrar: " + new Gson().toJson(request.getListNewReg()));
		
		if(request.getEncodedData() != null) 
		{
			request = Parser.DECODE(request);
			RegistrarPromocionesResponse response = service.setPromos(request);
			return new RegistrarPromocionesResponse(Parser.ENCODE(response));
		} else {
			return service.setPromos(request);
		}		
	}
	
	
	@PostMapping("/loadPromociones")
	public RegistrarPromocionesResponse loadPromos (@RequestBody RegistrarPromocionesRequest request) 
	{	
		log.debug("Controller Load: " + new Gson().toJson(request.getListCapRegProm()));
		
		if(request.getEncodedData() != null) 
		{
			request = Parser.DECODE(request);
			RegistrarPromocionesResponse response = service.getFindProm(request);
			return new RegistrarPromocionesResponse(Parser.ENCODE(response));
		} else {
			return service.getFindProm(request);
		}
		
	}
	
	
	@GetMapping("/promosActivas")
	public RegistrarPromocionesResponse promosRegActivos() 
	{	
		log.debug("Controller Get: " + new Gson().toJson(service.getPromosActivo() ) );
		
		return service.getPromosActivo();
	}
}
