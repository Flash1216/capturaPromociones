<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<script type="text/javascript">
var capform = {};
(function()
{
	const dialog = app.dialog;
	var loading = psDialog.loading();
	let fileName;
	let id;

 	function ejecutar(obj) {
		console.log("DATA" + JSON.stringify(obj.data));	
		
		loading.open();
		system.service
		({
			url: "registrarPromociones",
			data: { listNewReg : obj.data },
			callback: function(response) 
			{
				psDialog.info( response.message );
				system.service({
					encoded: false,
					url: "loadPromociones",
					data: { cap_id_n: "", cap_nom_str: "", cap_est_str: "" },
					callback:(r) =>
						{
							app.table.cap.setData(r.capRegProm);							
						}
				}).always(function(){ loading.close(); });				
			}			
		}).always(function(){ loading.close(); });
		dialog.close();
 	}
	
	function validar() {
		let form = $("#formRegProm");
		app.cleanForm(form);
		let obj = {data: [], valido: true};
		let objToFocus = null;
		
		let objSerial = app.serialize(form);
		$.each(objSerial, (id, value) =>
		{
			if( $("#"+id).prop('required') && objSerial[id] == null)
				{	
					obj.valido = false;
					objSerial.valido = false;
					app.markAsError( $("#"+id));
					objToFocus = objToFocus == null ? $("#"+id) : objToFocus;
				}			
		});
		
		if(capform.id == null) {
			psDialog.error("El Archivo de Promociones, no ha sido cargado.\n Verifique.");
			obj.valido=false;
			objToFocus = objToFocus == null ? $("#input-id") : objToFocus;
			return;
		}
		
		if ( objToFocus != null) { objToFocus.focus(); }
		
		//Aqui validar... obj.valido = falsXe
		let articulo = $("#id_art_n").val();
		let nomcorto = $("#cap_nom_str").val();
		let descripcion = $("#cap_desc_str").val();
		let fecinicio = $("#cap_fecini_dt").val();
		let descfecini = $("#cap_descini_str").val();
		let fecfin = $("#cap_fecfin_dt").val();
		let descfecfin = $("#cap_descfin_str").val();
		let publish = $("#cap_fecproini_dt").val();
		let publishend = $("#cap_fecprofin_dt").val();
 		let nomarchivo = capform.fileName;
 		let capId = capform.id;
 		
	
		let data = [{ 
			"cap_id_n": capId,
			"id_art_n": articulo, 
			"cap_nom_str" : nomcorto, 
			"cap_desc_str": descripcion, 
			"cap_fecini_dt": fecinicio,
			"cap_descini_str" : descfecini,	
			"cap_fecfin_dt" : fecfin, 
			"cap_descfin_str" : descfecfin,
			"cap_fecproini_dt": publish, 
			"cap_fecprofin_dt": publishend,
 			"cap_file_str" : nomarchivo, 
			"id_usr_n" : system.user.usrCveStr }];
		obj.data = data;
		console.log(obj)
		return obj;
	}
	function onClickbtnOK() {
		system.blockButton($(this));
		let obj = validar();
		if(obj.valido) {
			ejecutar(obj);
		}
	}	
	this.init = () => {
		dialog.setTitle("Registrar");
		dialog.getButton("btnOK").html("Registrar");
		dialog.getButton("btnOK").click(onClickbtnOK);
		
		app.setDatePicker($("#cap_fecini_dt"), $("#cap_fecfin_dt"));
		app.setDatePicker($("#cap_fecproini_dt"), $("#cap_fecprofin_dt"));

		let estSelec = $("#tipoFechaIni").val();
		if(estSelec == "") {
			$("#cap_descini_str").parent().parent().hide();
			$("#cap_descfin_str").parent().parent().hide();
			$("#cap_fecini_dt").parent().parent().hide();
			$("#cap_fecfin_dt").parent().parent().hide();	
		}
		
		
		
		$('#tipoFechaIni').on('change', function() {
			let est = this.value;
				switch(est) {
					case 'F':
						$("#cap_descini_str").parent().parent().hide();
						$("#cap_descini_str").val("");
						$("#cap_descini_str").prop('required', false);
						$("#cap_fecini_dt").parent().parent().show();
						$("#cap_fecini_dt").prop('required', true);
						break;
					case 'D':
						$("#cap_fecini_dt").parent().parent().hide();
						$("#cap_fecini_dt").val("");
						$("#cap_fecini_dt").prop('required', false);
						$("#cap_descini_str").parent().parent().show();
						$("#cap_descini_str").prop('required', true);
						break;
					case '':
						$("#cap_descini_str").parent().parent().hide();
						$("#cap_descini_str").val("");
						$("#cap_fecini_dt").parent().parent().hide();
						$("#cap_fecini_dt").val("");
						break;
				}
		});
		
		$('#tipoFechaFin').on('change', function() {
			let est = this.value;
				switch(est) {
					case 'F':
						$("#cap_descfin_str").parent().parent().hide();
						$("#cap_descfin_str").val("");
						$("#cap_descfin_str").prop('required', false);
						$("#cap_fecfin_dt").parent().parent().show();
						$("#cap_fecfin_dt").prop('required', true);
						break;
					case 'D':
						$("#cap_descfin_str").parent().parent().show();
						$("#cap_descfin_str").prop('required', true);
						$("#cap_fecfin_dt").parent().parent().hide();
						$("#cap_fecfin_dt").val("");
						$("#cap_fecfin_dt").prop('required', false);
						break;
					case '':
						$("#cap_descfin_str").parent().parent().hide();
						$("#cap_descfin_str").val("");
						$("#cap_fecfin_dt").parent().parent().hide();
						$("#cap_fecfin_dt").val("");
						break;
				}
		});
		
		$("#input-id").fileinput
    	({
    		showPreview : false,
    		allowedFileExtensions : [ 'xls','xlsx','ods'],
    		showCancel: false,
    		uploadUrl : system.currentApp.serviceUrl+ "upload/uploadFile",
    		ajaxSettings : {
    			beforeSend : function(xhr) 
    			{
    				xhr.setRequestHeader(system.Base64.decode(system.currentApp.token),
    									 system.Base64.decode(system.currentApp.type)+ 
    									 system.Base64.encode(atob(system.currentApp.key)+ 
    									 system.Base64.decode(system.currentApp.value)));
    			},
    		}
    	});
		
		$('#input-id').on('fileuploaded',function(event, data, previewId, index) {
    		let response = data.response;
    		
    		if( response.status == 0 )
    		{	
    			console.log(response.fileName);
    			capform.fileName = response.fileName;
    			capform.id = response.consecId;
    		}
    		else
    		{
    			psDialog.error(response.message);
    		}	
    	});
		
	} 
	
}).apply(capform);

$(document).ready( capform.init );

</script>

<form id="formRegProm" class="form-horizontal panel-body">
	<div class="form-group">
		<label class="col-sm-4 control-label">
			<span class="ps-color-red"><i class="fa fa-asterisk ast-required"></i></span>
			ID Art Repr: 
			<span class="ps-span-error"></span>
		</label>
		<div class="col-sm-7">
       		<input id="id_art_n" name="id_art_n" maxlength="8" required="required" class="form-control" value="" oninput="this.value = this.value.replace(/[^0-9]/g, '');">
    	</div>
	</div>
	<div class="form-group">
		<label class="col-sm-4 control-label">
			<span class="ps-color-red"><i class="fa fa-asterisk ast-required"></i></span>
				Nombre Corto:
			<span class="ps-span-error"></span>
		</label>
		<div class="col-sm-7">
       		<input id="cap_nom_str" name="cap_nom_str"  required="required" maxlength="25" class="form-control" value="">
    	</div>
	</div>
	<div class="form-group">
		<label class="col-sm-4 control-label">
			<span class="ps-color-red"><i class="fa fa-asterisk ast-required"></i></span>
				Descripción: 
			<span class="ps-span-error"></span>
		</label>
		<div class="col-sm-7">
   			<input id="cap_desc_str" name="cap_desc_str" required="required" maxlength="100" class="form-control" value="">
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-4 control-label" for="f_tipo">
			<span class="ps-color-red"><i class="fa fa-asterisk ast-required"></i></span>
				Tipo Fecha Ini:
			<span class="ps-span-error"></span>
		</label>
		<div class="col-sm-8">
			<select id="tipoFechaIni" name="estatus" class="input-group">
				<option value="" selected="selected">-</option>
				<option value="F" data-icon="fa fa-square green">Fecha</option>		
				<option value="D" data-icon="fa fa-square text-orange">Descripción</option>
			</select>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-4 control-label">
			<span class="ps-color-red"><i class="fa fa-asterisk ast-required"></i></span>
				Fecha Inicio:
			<span class="ps-span-error"></span>
		</label>
		<div class="col-sm-7">
			<input id="cap_fecini_dt" name="cap_fecini_dt" class="form-control">
		</div>								
	</div>
	<div class="form-group" >
		<label class="col-sm-4 control-label" for="descFecInicio">
			<span class="ps-color-red"><i class="fa fa-asterisk ast-required"></i></span>
			Desc Fecha Ini: 
			<span class="ps-span-error"></span>
		</label>
		<div class="col-sm-7">
       		<input id="cap_descini_str" name="cap_descini_str" maxlength="50" class="form-control" value="">
    	</div>
	</div>	
	<div class="form-group">
		<label class="col-sm-4 control-label" for="f_tipo">
			<span class="ps-color-red"><i class="fa fa-asterisk ast-required"></i></span>
				Tipo Fecha Fin:
			<span class="ps-span-error"></span>
		</label>
		<div class="col-sm-8">
			<select id="tipoFechaFin" name="estatus" class="input-group">
				<option value="" selected="selected">-</option>
				<option value="F" data-icon="fa fa-square green">Fecha</option>		
				<option value="D" data-icon="fa fa-square text-orange">Descripción</option>
			</select>
		</div>				
	</div>
	<div class="form-group">
		<label class="col-sm-4 control-label">
			<span class="ps-color-red"><i class="fa fa-asterisk ast-required"></i></span>
				Fecha Fin: 
			<span class="ps-span-error"></span>
		</label>
		<div class="col-sm-7">
			<input id="cap_fecfin_dt" name="cap_fecfin_dt" class="form-control">
		</div>								
	</div>
	<div class="form-group">
		<label class="col-sm-4 control-label" for="descFecFin">
			<span class="ps-color-red"><i class="fa fa-asterisk ast-required"></i></span>
			Desc Fecha Fin:
			<span class="ps-span-error"></span>
		</label>
		<div class="col-sm-7">
       		<input id="cap_descfin_str" name="cap_descfin_str" maxlength="50" class="form-control" value="">
    	</div>
	</div>
	<div class="form-group">
		<label class="col-sm-4 control-label" for="promFechaIni">
			<span class="ps-color-red"><i class="fa fa-asterisk ast-required"></i></span>
				Inicio Promoción: 
			<span class="ps-span-error"></span>
		</label>
		<div class="col-sm-7">
			<input id="cap_fecproini_dt" name="cap_fecproini_dt" required="required" class="form-control">
		</div>								
	</div>
	<div class="form-group">
		<label class="col-sm-4 control-label" for="promFechaFin">
			<span class="ps-color-red"><i class="fa fa-asterisk ast-required"></i></span>
				Fin Promoción: 
			<span class="ps-span-error"></span>
		</label>
		<div class="col-sm-7">
			<input id="cap_fecprofin_dt" name="cap_fecprofin_dt" required="required" class="form-control">
		</div>								
	</div>
	
	<div class="form-group">
		<label class="col-sm-4 control-label">
			<span class="ps-color-red"><i class="fa fa-asterisk ast-required"></i></span>
			Cargar Archivo: 
			<span class="ps-span-error"></span>
		</label>
		<div class="col-sm-7">
       		<input id="input-id" type="file" class="file" data-preview-file-type="text" name="file" required="required">
    	</div>
	</div>		
</form>