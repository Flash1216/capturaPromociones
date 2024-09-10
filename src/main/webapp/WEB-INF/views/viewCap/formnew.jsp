<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<script type="text/javascript">
var capform = {};
(function()
{
	const dialog = app.dialog;
	var loading = psDialog.loading();

	function ejecutar(obj) {
		console.log("DATA" + JSON.stringify(obj.data));
		loading.open();
		system.service
		({
			url: "registrarPromociones",
			data: { listCapRegProm : obj.data },
			callback: function(response) 
			{
				//"VALIDACIONES A APLICAR"
				//alp.btnFormatter(response.listLP);
				//alp.table.bootstrapTable('load', response.listLP);
				//app.table.cap.setData(data);
				psDialog.info(response.message );
			}			
		}).always(function(){ loading.close(); });
	}
	
	function validar() {
		let form = $("#formRegProm");
		app.cleanForm(form);
		let obj = {data: [], valido: true};
		let objToFocus = null;
		
		let objSerial = app.serialize(form);
		$.each(objSerial, (id, value) =>
		{
			if( $("#"+id).prop('required') && objSerial[id] == null )
				{	
					obj.valido = false;
					objSerial.valido = false;
					app.markAsError( $("#"+id));
					objToFocus = objToFocus == null ? $("#"+id) : objToFocus;
				}			
		});
		
// 		$("#input-id").fileinput
//     	({
//     		showPreview : false,
//     		allowedFileExtensions : [ 'xls','xlsx','ods'],
//     		showCancel:false,
//     		uploadUrl : system.currentApp.serviceUrl+ "upload/uploadFile",
//     		ajaxSettings : {
//     			beforeSend : function(xhr) 
//     			{
//     				xhr.setRequestHeader(system.Base64.decode(system.currentApp.token),
//     									 system.Base64.decode(system.currentApp.type)+ 
//     									 system.Base64.encode(atob(system.currentApp.key)+ 
//     									 system.Base64.decode(system.currentApp.value)));
//     			},
//     		}
//     	});
		
		$('#input-id').on('fileuploaded',function(event, data, previewId, index) {
    		let response = data.response;
    		
    		if( response.status == 0 )
    		{	
    			console.log(response.fileName);
    			promo.fileName = response.fileName;
    		}
    		else
    		{
    			psDialog.error(response.message);
    		}	
    	});
		
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
		let nomarchivo = $("#cap_file_str").val();
		let archivo	= $("#input-id").val();
	
		let data = [{ 
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
			"usr_cve_pstr" : system.user.usrCveStr,
			"input-id" : archivo }];
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

		$("#cap_descini_str").parent().parent().hide();
		$("#cap_descfin_str").parent().parent().hide();
		$("#cap_fecini_dt").prop('required', true);
		$("#cap_fecfin_dt").prop('required', true);
		
		$("#spanFechaIni").click(function(){
			let checkFecIni = $("#spanFechaIni").prop('checked');
			if(checkFecIni == false) {
				$("#cap_descini_str").parent().parent().hide();
				$("#cap_descini_str").val("");
				$("#cap_descini_str").prop('required', false);
				$("#cap_fecini_dt").show();
				$("#cap_fecini_dt").prop('required', true);
			} else {
				$("#cap_fecini_dt").hide();
				$("#cap_fecini_dt").val("");
				$("#cap_fecini_dt").prop('required', false);
				$("#cap_descini_str").parent().parent().show();
				$("#cap_descini_str").prop('required', true);
			}			
		});
		
		$("#spanFechaFin").click(function(){
			let checkFecFin = $("#spanFechaFin").prop('checked');
			if(checkFecFin == false) {
				$("#cap_descfin_str").parent().parent().hide();
				$("#cap_descfin_str").val("");
				$("#cap_descfin_str").prop('required', false);
				$("#cap_fecfin_dt").show();
				$("#cap_fecfin_dt").prop('required', true);
			} else {
				$("#cap_descfin_str").parent().parent().show();
				$("#cap_descfin_str").prop('required', true);
				$("#cap_fecfin_dt").hide();
				$("#cap_fecfin_dt").val("");
				$("#cap_fecfin_dt").prop('required', false);
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
			ID Art Representativo: 
			<span class="ps-span-error"></span>
		</label>
		<div class="col-sm-7">
       		<input id="id_art_n" name="id_art_n" maxlength="10" required="required" class="form-control" value="">
    	</div>
	</div>
	<div class="form-group">
		<label class="col-sm-4 control-label">
			<span class="ps-color-red"><i class="fa fa-asterisk ast-required"></i></span>
			Nombre Corto:
			<span class="ps-span-error"></span>
		</label>
		<div class="col-sm-7">
       		<input id="cap_nom_str" name="cap_nom_str"  required="required" maxlength="20" class="form-control" value="">
    	</div>
	</div>
	<div class="form-group">
		<label class="col-sm-4 control-label">
			<span class="ps-color-red"><i class="fa fa-asterisk ast-required"></i></span>
				Descripción: 
			<span class="ps-span-error"></span>
		</label>
		<div class="col-sm-7">
   			<input id="cap_desc_str" name="cap_desc_str" required="required" maxlength="90" class="form-control" value="">
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-4 control-label">
			<span class="ps-color-red"><i class="fa fa-asterisk ast-required"></i></span>
				Fecha Inicio:
			<span class="ps-span-error"></span>
		</label>
		<div class="input-group date col-sm-8">
			<input id="cap_fecini_dt" name="cap_fecini_dt" class="form-control">				
				<span class="input-group-addon">
					<input id="spanFechaIni" type="checkbox" class="">Sin Fecha de Inicio
				</span>
		</div>								
	</div>
	<div class="form-group" >
		<label class="col-sm-5 control-label" for="descFecInicio">
			<span class="ps-color-red"><i class="fa fa-asterisk ast-required"></i></span>
			Descripción Fecha Inicio: 
			<span class="ps-span-error"></span>
		</label>
		<div class="col-sm-7">
       		<input id="cap_descini_str" name="cap_descini_str" maxlength="90" class="form-control" value="">
    	</div>
	</div>
	<div class="form-group">
		<label class="col-sm-4 control-label">
			<span class="ps-color-red"><i class="fa fa-asterisk ast-required"></i></span>
				Fecha Fin: 
			<span class="ps-span-error"></span>
		</label>
		<div class="input-group date col-sm-8">
			<input id="cap_fecfin_dt" name="cap_fecfin_dt" class="form-control">				
				<span class="input-group-addon">
					<input id="spanFechaFin" type="checkbox" class="">Sin Fecha de Fin
				</span>
		</div>								
	</div>
	<div class="form-group">
		<label class="col-sm-4 control-label" for="descFecFin">
			<span class="ps-color-red"><i class="fa fa-asterisk ast-required"></i></span>
			Descripción Fecha Fin:
			<span class="ps-span-error"></span>
		</label>
		<div class="col-sm-6">
       		<input id="cap_descfin_str" name="cap_descfin_str" maxlength="10" class="form-control" value="">
    	</div>
	</div>
	<div class="form-group">
		<label class="col-sm-5 control-label" for="promFechaIni">
			<span class="ps-color-red"><i class="fa fa-asterisk ast-required"></i></span>
				Publicar Promoción el día: 
			<span class="ps-span-error"></span>
		</label>
		<div class="col-sm-5">
			<input id="cap_fecproini_dt" name="cap_fecproini_dt" required="required" class="form-control">
		</div>								
	</div>
	<div class="form-group">
		<label class="col-sm-5 control-label" for="promFechaFin">
			<span class="ps-color-red"><i class="fa fa-asterisk ast-required"></i></span>
				Bajar Promoción el día: 
			<span class="ps-span-error"></span>
		</label>
		<div class="col-sm-5">
			<input id="cap_fecprofin_dt" name="cap_fecprofin_dt" required="required" class="form-control">
		</div>								
	</div>
	<div class="form-group">
		<label class="col-sm-4 control-label">
			<span class="ps-color-red"><i class="fa fa-asterisk ast-required"></i></span>
			Nombre de Archivo:  
			<span class="ps-span-error"></span>
		</label>
		<div class="col-sm-7">
       		<input id="cap_file_str" required="required" name="cap_file_str" maxlength="90" class="form-control" value="">
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