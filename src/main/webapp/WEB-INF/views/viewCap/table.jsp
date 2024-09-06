<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<style> .danger{ background-color: white; color: red; font-weight: bold } .info{ background-color: white; color: black; font-weight: bold } </style>
<table  id="tableCap"
		data-toolbar-name="tableCap_tb"
		data-show-columns="true"
		data-height="450"
  		data-min-height="450"
  		data-height-vh="60"
  		data-pagination="true"
  		data-page-size="1000"
   		data-page-list="[1000, 2500,5000]"  		
  		data-unique-id = "id"
  		data-icons="system.icons" 
		>
	<thead>	
		<tr>
			<th data-field="img"	data-min-width="120"	data-formatter="cap.trImage"	data-align="center">Imagen</th>
			
			<th data-field="id_art_n"	data-align="center">Articulo</th>
									
			<th data-field="cap_nom_str"	data-align="center">Nombre Corto</th>
									
			<th data-field="cap_desc_str" 	data-min-width="180"	data-align="center">Descripcion</th>
									
			<th data-field="cap_fecini_dt"	data-align="center">Fecha Inicio</th>
			
			<th data-field="cap_descini_str"	data-min-width="150" 	data-align="center">Desc Fecha Inicio</th>
									
			<th data-field="cap_fecfin_dt"	data-align="center">Fecha Fin</th>
			
			<th data-field="cap_descfin_str"	data-min-width="150"	data-align="center">Desc Fecha Fin</th>
									
			<th data-field="cap_file_str"	data-align="center">Archivo</th>
									
			<th data-field="cap_fecproini_dt"	data-align="center">Publicar el día</th>
			
			<th data-field="cap_fecprofin_dt"	data-align="center">Bajar el día</th>
									
			<th data-field="cap_est_str"	data-align="center"	data-min-width="140" data-formatter="cap.iconFormatter">Estatus</th>
									
			<th data-field="cap_fecreg_dt"	data-cell-style=""	data-align="left">Fecha de Registro</th>
									
			<th data-field="id_usr_n"	data-sortable="false"	data-formatter="" data-align="center">Usuario</th>
									
			<th data-field="action"	data-sortable="false"	data-formatter="" data-align="center" >Acciones</th>
			
		</tr>
	</thead>
</table>



