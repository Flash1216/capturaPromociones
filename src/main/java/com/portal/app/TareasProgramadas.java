package com.portal.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import com.portal.app.service.CapService;
import com.priceshoes.taskinfo.TaskInfo;
import com.priceshoes.taskinfo.TaskInfoRequest;
import com.priceshoes.taskinfo.TaskInfoResponse;

public class TareasProgramadas 
{
	private static final Logger log = LoggerFactory.getLogger(TareasProgramadas.class);
	
	@Autowired private CapService service;
	
//	cron properties
	@Value("${promociones.t1.desc}")	private String t1Desc;
	@Value("${promociones.t1.prefix}")	private String t1Prefix;
	@Value("${promociones.t1.enabled}")	private boolean t1Enabled;
	
	@Scheduled(cron = "${promociones.t1.cron}")
	public void CapTask1() 
	{
		String prefix	= t1Prefix;
		String desc		= t1Desc;
		boolean enabled	= t1Enabled;
		String  ambiente = TaskInfo.DESARROLLO;
		String  tipoEjecucion = TaskInfo.TASK_PROGRAMADA;
		
		TaskInfo taskInfo = new TaskInfo(); 
		TaskInfoRequest taskRequest = new TaskInfoRequest();
		taskRequest.setPrefixTask(prefix);
		taskRequest.setTipoEjecucion(tipoEjecucion);
		taskRequest.setEnvironment(ambiente);
		
		TaskInfoResponse response = taskInfo.getTaskInfoSlf4(taskRequest, log);
		
		if(enabled && response.getStatus()==0 && response.getTask().isEnabled() )
		{
			log.info(prefix+" | ** TAREA PROGRAMADA: " + desc);
			Long idBitacora = taskInfo.registrarInicio(prefix,ambiente,tipoEjecucion, log);
			String obs = "";
			log.info("== ** Bitacora registrada " + idBitacora);
			//---------------------------------
			
			obs = service.taskActualizaInfo();
			
			//---------------------------------
			taskInfo.registrarFin(idBitacora, TaskInfo.FINALIZADA, prefix,ambiente, obs, log);
			log.info("RESPUESTA SERVICE: " + obs);
			log.info(prefix+" | ** FIN DE TAREA PROGRAMADA: "+desc);
		}else{
			log.info(prefix+" | ** TAREA INACTIVA: " + t1Desc);
		}	
	}
}
