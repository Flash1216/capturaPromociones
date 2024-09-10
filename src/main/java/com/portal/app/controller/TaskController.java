package com.portal.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.portal.app.response.RegistrarPromocionesResponse;
import com.portal.app.service.CapService;
import com.priceshoes.taskinfo.TaskInfo;

@RestController
@RequestMapping(value="/service", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
public class TaskController 
{
	private static final Logger log = LoggerFactory.getLogger(TaskController.class);
	
	@Autowired private  CapService service;
	
//	cron properties
	@Value("${promociones.t1.desc}")	private String t1Desc;
	@Value("${promociones.t1.prefix}")	private String t1Prefix;
	@Value("${promociones.t1.enabled}")	private boolean t1Enabled;
	String ambiente = TaskInfo.DESARROLLO;
	String tipoEjecucion = TaskInfo.TASK_MANUAL;
	
	@PostMapping(value = "/taskPromos")
	public RegistrarPromocionesResponse runTaskPromos()
	{
		RegistrarPromocionesResponse response = new RegistrarPromocionesResponse(); 
		TaskInfo taskInfo = new TaskInfo();
		
		
		log.info(t1Prefix+" | ** TAREA PROGRAMADA: " + t1Prefix);
		Long idBitacora = taskInfo.registrarInicio(t1Prefix,ambiente,tipoEjecucion,log);
		log.info("== ** Bitacora registrada " + idBitacora );
		//---------------------------------
		
		response.setMessage( service.taskActualizaInfo() );
		//---------------------------------
		taskInfo.registrarFin(idBitacora, TaskInfo.FINALIZADA, t1Prefix, ambiente, response.getMessage(), log);
		log.info(t1Prefix+" | ** FIN DE TAREA PROGRAMADA: "+t1Prefix);
		
		log.info("response: " + new Gson().toJson(response));
		return response;
	}
	
	
	
}
