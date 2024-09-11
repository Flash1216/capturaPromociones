package com.portal.app.controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.portal.app.dto.ConsecDto;
import com.portal.app.response.RegistrarPromocionesResponse;
import com.portal.app.service.CapService;
import com.portal.app.util.Constants;

@RestController
@RequestMapping(value="/service/upload",method = RequestMethod.POST,produces="application/json; charset=utf-8")
public class UploadController {
		private static final Logger log = LoggerFactory.getLogger(UploadController.class);
		
		@Autowired	private Environment env;
		@Autowired	private CapService service;
		
		@RequestMapping("/uploadFile")
		public RegistrarPromocionesResponse uploadFile(MultipartFile file)
		{
			RegistrarPromocionesResponse response = new RegistrarPromocionesResponse();
			ConsecDto consec = new ConsecDto();
			log.info("Lectura de archivo " );
			try
			{	
				consec = service.getConsecutive();
				String id = new Gson().toJson(consec);
				JsonObject jsonObject = new JsonParser().parse(id).getAsJsonObject();					
		        int capId = jsonObject.get("cap_id_n").getAsInt(); // Extrae el valor como int
					
				log.info("CONSECUTIVO: " + capId );
				String fileName = file.getOriginalFilename();
				String ext 	= fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
				String fileNameComplete = fileName.substring(0, fileName.lastIndexOf(".")).toLowerCase();
				
				String repo = env.getProperty("promociones.repo.file.path");
				
				log.info("name: " + fileNameComplete);
				log.info("File:"+ fileName);
				log.info("Ext:"+ext);
				
				
				Path destinationFile = Paths.get(repo + capId + "." + ext );
				
				try (InputStream inputStream = file.getInputStream()) 
				{
					Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
				}
				
				log.info("Archivo creado " + destinationFile);	
				response.setFileName(fileNameComplete + "." + ext);
				response.setConsecId(capId);
				
			}
			catch(Exception e)
			{
				log.error(e.getMessage(),e);
				response.setStatus(Constants.ERROR);
				response.setMessage("No fue posible leer el archivo." + e.getMessage());
			}
			
			return response;
		}
}
