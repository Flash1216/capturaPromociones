<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<script type="text/javascript">
var filtro = {};
(function()
{
	function datePicker()
	{
		$("#estatus").selectpicker();
	}
	
	this.init  = function()
	{
		datePicker();
	};
}).apply(filtro);

$(document).ready(filtro.init);

</script>


<style>
.lbl-filter {margin-bottom: 0px !important; margin-top: 10px; }
.bg-white{ background-color: #FFFFFF !important; }
.text-orange { color: orange }
.text-blue { color: blue }
.text-grey { color: grey }
</style>

<div class="col-lg-2 col-md-4 col-sm-6 col-xs-6">
	<label class="control-label lbl-filter"> 
		Nombre : 
	</label>
	<div class="">
      		<input id="nom_prom_str" name="nom_prom_str"   maxlength="20" class="form-control" value="">
   	</div>
</div>
<div class="col-lg-2 col-md-4 col-sm-6 col-xs-6 ">
	<label class="control-label lbl-filter" for="f_tipo">
		Estatus:
	</label>
	<select id="estatus" name="estatus" class="form-control">
		<option value="" selected="selected">Todos</option>
		<option value="A" data-icon="fa fa-square green">Activos</option>		
		<option value="E" data-icon="fa fa-square text-orange">Expirados</option>
		<option value="P" data-icon="fa fa-square text-blue">Por Publicar</option>
		<option value="C" data-icon="fa fa-square red">Cancelados</option>
		<option value="I" data-icon="fa fa-square yellow">Inactivos</option>
		<option value="R" data-icon="fa fa-square text-grey">Registrados</option>
	</select>	
</div>

<div class="col-lg-2 col-md-4 col-sm-6 col-xs-6">
	<label class="control-label lbl-filter">&nbsp;</label>
	<button id="f_btnBuscar" class="btn btn-primary form-control">
	<i class="fa fa-search" aria-hidden="true"></i> Buscar</button>
</div>