<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<style>
.lbl-filter {margin-bottom: 0px !important; margin-top: 10px; }
.bg-white{ background-color: #FFFFFF !important; }
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
		<option value="A" data-icon="fa fa-square red">Activos</option>		
		<option value="E" >Expirados</option>
		<option value="P" >Por Publicar</option>
		<option value="C" >Cancelados</option>
	</select>	
</div>

<div class="col-lg-2 col-md-4 col-sm-6 col-xs-6">
	<label class="control-label lbl-filter">&nbsp;</label>
	<button id="f_btnBuscar" class="btn btn-primary form-control">
	<i class="fa fa-search" aria-hidden="true"></i> Buscar</button>
</div>