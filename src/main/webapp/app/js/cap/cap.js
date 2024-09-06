var cap = {};
(function()
{	
	let $dialog = null
	this.table = null;
	let row	   = null; 
	var loading = psDialog.loading();
	
	this.init  = function()
	{
		let loading = psDialog.loading().open();
		$.when( system.getForm({url:"core/pageTemplate"}),
				system.getForm({url:"viewCap/filtro"}),
				system.getForm({url:"viewCap/table"}), 
				loadData()
		).done(function(template,filtroTemplate, tableTemplate)
		{
			$("#divTemplateContainer").html(template[0]);
			$("#divFiltro").html(filtroTemplate[0]);
			$("#divTableContainer").html(tableTemplate[0]);

			initMenu();
		}).always(function(){ loading.close(); });
	};
	
	this.trImage = (value, row) => {
		let idArt = row.id_art_n;
		if(idArt == undefined) return "";
		
		let img = ' <p> <img style="height: 75px;	max-width: 100%; cursor:zoom-in;" '+
			      ' src="'+app.imgServer+idArt+'.jpg" alt="-" onclick="app.showImgArt('+idArt+')" /> </p>';
				  
		return img;						
	}
	
	this.trImageCat = (value, row) => {
			let idCat = row.id_art_n;
			if(idCat == undefined) return "";
			
			let img = '<p> <img style="height: 75px;	max-width: 100%; cursor:zoom-in;" '+
				      ' src="'+app.imgServerCat+idCat+'.jpg" alt="-" onclick="app.showImgCat('+idCat+')" /> </p>';
					  
			return img;						
	}
	
	this.iconFormatter = (value, row) => {
		let est = row.cap_est_str;
		let estDesc = row.cap_descest_str;
		let iconFormatt = ""
		switch(est) {
			case 'P':
				iconFormatt = "<span><i class='fa fa-square' style='color: blue' aria-hidden='true'></i> "+ estDesc +" </span>";
				break;
			case 'A':
				iconFormatt = "<span><i class='fa fa-square' style='color: green' aria-hidden='true'></i> "+ estDesc +" </span>";
				break;
			case 'E':
				iconFormatt = "<span><i class='fa fa-square' style='color: orange' aria-hidden='true'></i> "+ estDesc +" </span>";
				break;
			case 'C':
				iconFormatt = "<span><i class='fa fa-square' style='color: red' aria-hidden='true'></i> "+ estDesc +" </span>";
				break;
		}
		return iconFormatt;
	}
	
	this.alerta = (id)=>{
		alert(id);
	};
	
	this.f_btnAgregar = ()=>
	{
		app.loadForm("viewCap/formnew");
	}
	
	function toolbarButtons()	{
		return {
			btnAdd: 
			{	icon:"fa-fw fa fa-plus",
				text:"Registrar publicación",
				btnClass:"primary",
				title:"Nuevo registro",
				onclick:"cap.f_btnAgregar( $(this) )"				
			}
		};
	};
	
	function loadData() {
		loading.open();
		system.service({
			encoded: false,
			url: "registrarPromociones",
			data: { cap_id_n: "", cap_nom_str: "", cap_est_str: "" },
			callback:(r) =>
				{
					app.table.cap.setData(r.capRegProm);
					psDialog.info("REGISTROS ACTUALIZADOS");
				}
		}).always(function(){
			loading.close();
		})
	};
	
	this.searchData = () => {
		let textName = $("#nom_prom_str").val().toUpperCase();
		let filterEst =  $("#estatus").val();
		loading.open();
		system.service({
			encoded: false,
			url: "registrarPromociones",
			data: { cap_id_n: "", cap_nom_str: textName, cap_est_str: filterEst },
			callback:(r) => 
				{
					app.table.cap.clear();
					app.table.cap.setData(r.capRegProm);
				}
		}).always(function(){
			loading.close();
		})
	};
	
	function initMenu(){
		app.table.cap = $("#tableCap");
		app.setTableOps( app.table.cap );
		app.crearTabla({ table:app.table.cap, toolbarButtons: toolbarButtons() });
		$("#f_btnBuscar").click(function() { cap.searchData() })		
	}
}).apply(cap);

$(document).ready( cap.init );
