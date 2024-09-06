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
		
		if ( objToFocus != null) { objToFocus.focus(); }
		
		//Aqui validar... obj.valido = falsXe
		let articulo = $("#id_ref_n").val();
		let nomcorto = $("#nom_prom_str").val();
		let descripcion = $("#desc_prom_str").val();
		let fecinicio = $("#fec_ini_dt").val();
		let descfecini = $("#desc_fecini_str").val();
		let fecfin = $("#fec_fin_dt").val();
		let descfecfin = $("#desc_fecfin_str").val();
		let publish = $("#fec_upprom_dt").val();
		let publishend = $("#fec_dwnprom_dt").val();
		let nomarchivo = $("#nom_arch_str").val();
	
		let data = [{ 
			"id_ref_n": articulo, 
			"nom_prom_str" : nomcorto, 
			"desc_prom_str": descripcion, 
			"fec_ini_dt": fecinicio,
			"desc_fecini_str" : descfecini,	
			"fec_fin_dt" : fecfin, 
			"desc_fecfin_str" : descfecfin,
			"fec_upprom_dt": publish, 
			"fec_dwnprom_dt": publishend,
			"nom_arch_str" : nomarchivo, 
			"usr_cve_pstr" : system.user.usrCveStr}];
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
		
		app.setDatePicker($("#fec_ini_dt"), $("#fec_fin_dt"));
		app.setDatePicker($("#fec_upprom_dt"), $("#fec_dwnprom_dt"));

		$("#desc_fecini_str").parent().parent().hide();
		$("#desc_fecfin_str").parent().parent().hide();
		$("#fec_ini_dt").prop('required', true);
		$("#fec_fin_dt").prop('required', true);
		
		$("#spanFechaIni").click(function(){
			let checkFecIni = $("#spanFechaIni").prop('checked');
			if(checkFecIni == false) {
				$("#desc_fecini_str").parent().parent().hide();
				$("#desc_fecini_str").val("");
				$("#desc_fecini_str").prop('required', false);
				$("#fec_ini_dt").show();
				$("#fec_ini_dt").prop('required', true);
			} else {
				$("#fec_ini_dt").hide();
				$("#fec_ini_dt").val("");
				$("#fec_ini_dt").prop('required', false);
				$("#desc_fecini_str").parent().parent().show();
				$("#desc_fecini_str").prop('required', true);
			}			
		});
		
		$("#spanFechaFin").click(function(){
			let checkFecFin = $("#spanFechaFin").prop('checked');
			if(checkFecFin == false) {
				$("#desc_fecfin_str").parent().parent().hide();
				$("#desc_fecfin_str").val("");
				$("#desc_fecfin_str").prop('required', false);
				$("#fec_fin_dt").show();
				$("#fec_fin_dt").prop('required', true);
			} else {
				$("#desc_fecfin_str").parent().parent().show();
				$("#desc_fecfin_str").prop('required', true);
				$("#fec_fin_dt").hide();
				$("#fec_fin_dt").val("");
				$("#fec_fin_dt").prop('required', false);
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
       		<input id="id_ref_n" name="id_ref_n" maxlength="10" required="required" class="form-control" value="">
    	</div>
	</div>
	<div class="form-group">
		<label class="col-sm-4 control-label">
			<span class="ps-color-red"><i class="fa fa-asterisk ast-required"></i></span>
			Nombre Corto:
			<span class="ps-span-error"></span>
		</label>
		<div class="col-sm-7">
       		<input id="nom_prom_str" name="nom_prom_str"  required="required" maxlength="20" class="form-control" value="">
    	</div>
	</div>
	<div class="form-group">
		<label class="col-sm-4 control-label">
			<span class="ps-color-red"><i class="fa fa-asterisk ast-required"></i></span>
				Descripción: 
			<span class="ps-span-error"></span>
		</label>
		<div class="col-sm-7">
   			<input id="desc_prom_str" name="desc_prom_str" required="required" maxlength="90" class="form-control" value="">
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-4 control-label">
			<span class="ps-color-red"><i class="fa fa-asterisk ast-required"></i></span>
				Fecha Inicio:
			<span class="ps-span-error"></span>
		</label>
		<div class="input-group date col-sm-8">
			<input id="fec_ini_dt" name="fec_ini_dt" class="form-control">				
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
       		<input id="desc_fecini_str" name="desc_fecini_str" maxlength="90" class="form-control" value="">
    	</div>
	</div>
	<div class="form-group">
		<label class="col-sm-4 control-label">
			<span class="ps-color-red"><i class="fa fa-asterisk ast-required"></i></span>
				Fecha Fin: 
			<span class="ps-span-error"></span>
		</label>
		<div class="input-group date col-sm-8">
			<input id="fec_fin_dt" name="fec_fin_dt" class="form-control">				
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
       		<input id="desc_fecfin_str" name="desc_fecfin_str" maxlength="10" class="form-control" value="">
    	</div>
	</div>
	<div class="form-group">
		<label class="col-sm-5 control-label" for="promFechaIni">
			<span class="ps-color-red"><i class="fa fa-asterisk ast-required"></i></span>
				Publicar Promoción el día: 
			<span class="ps-span-error"></span>
		</label>
		<div class="col-sm-5">
			<input id="fec_upprom_dt" name="fec_upprom_dt" required="required" class="form-control">
		</div>								
	</div>
	<div class="form-group">
		<label class="col-sm-5 control-label" for="promFechaFin">
			<span class="ps-color-red"><i class="fa fa-asterisk ast-required"></i></span>
				Bajar Promoción el día: 
			<span class="ps-span-error"></span>
		</label>
		<div class="col-sm-5">
			<input id="fec_dwnprom_dt" name="fec_dwnprom_dt" required="required" class="form-control">
		</div>								
	</div>
	<div class="form-group">
		<label class="col-sm-4 control-label">
			<span class="ps-color-red"><i class="fa fa-asterisk ast-required"></i></span>
			Nombre de Archivo:  
			<span class="ps-span-error"></span>
		</label>
		<div class="col-sm-7">
       		<input id="nom_arch_str" required="required" name="nom_arch_str" maxlength="90" class="form-control" value="">
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