var app={};
(function()
{
	const  store ={};
	this.dialog = null;
	this.getStore = 		function(id){ return store[id]; };
	this.onErrorImgSmallDefault = 	function($this){ $this.src = system.currentApp.baseUrl+"app/img/default.png";	};
	this.imgServer = "http://static.priceshoes.com.mx/imgs/small/articulo/";
	this.imgServerCat = "http://static.priceshoes.com.mx/imgs/mini/catalogo/";
	this.today = '';
	
	this.icons = 
	{
		columns : "fa fa-columns",
		toggle : "fa fa-th-list",
		refresh : "fa fa-refresh",
        paginationSwitchDown: ' glyphicon glyphicon-collapse-down icon-chevron-down',
        paginationSwitchUp: 'glyphicon glyphicon-collapse-up icon-chevron-up',
        detailOpen: 'glyphicon glyphicon-plus icon-plus',
        detailClose: 'glyphicon glyphicon-minus icon-minus',
        export:'glyphicon glyphicon-export'	
	};
	
	this.setDatePicker = function(fechaIni,fechaFin,params) 
	{	
		let opts = {	maxViewMode: 2,
						format:'dd/mm/yyyy',
						todayHighlight:true,
						autoclose:true,
						language:SYS_LANG,
						orientation:"bottom auto",
						clearBtn:true};
		
		let options = $.extend({},opts, params);
		
		if( fechaFin==undefined || fechaFin==null)
		{ fechaIni.datepicker(options); }	
		else
		{
			let startDate = new Date();
			let fromEndDate = new Date();
			
			fechaIni.datepicker(options).on('changeDate',function(selected) 
			{	if(selected.date!=undefined)
				{	startDate = new Date(selected.date.valueOf());
					startDate.setDate(startDate.getDate(new Date(selected.date.valueOf())));
					fechaFin.datepicker('setStartDate',startDate);				
				}
				else{ fechaFin.datepicker('setStartDate','');	}	
			});

			fechaFin.datepicker(options).on('changeDate',function(selected) 
			{	if(selected.date!=undefined)
				{	fromEndDate = new Date(selected.date.valueOf());
					fromEndDate.setDate(fromEndDate.getDate(new Date(selected.date.valueOf())));
					fechaIni.datepicker('setEndDate',fromEndDate);
				}
				else{ fechaIni.datepicker('setEndDate',''); }
			});
		}
	};
	
	this.crearTabla = ( params )=>
	{	
		let defaults=
		{
			table:null,
			sidePagination:'client',
			ajax:null, 
			delay:500 , 
			toolbarButtons:null, 
			toolbarButtonsPos:"prepend",
			onExpandRow:null ,
			onPostBody:null ,
			onClickRow:null,
			reorderableRows:false,
			heightVh:null
		};
		
		let opts = $.extend({},defaults, params);
		let $table = opts.table;
		
		$table.bootstrapTable_1_23("destroy"); 
		$table.data("sidePagination",opts.sidePagination);
		$table.data("ajax",opts.ajax);
		$table.data("reorderableRows",opts.reorderableRows);
		
		if( opts.heightVh != null ){ $table.data("heightVh",opts.heightVh ); }
		
		$table.bootstrapTable_1_23
		({	 
			 icons:app.icons,
			 rowStyle:system.rowStyle,
			 onClickRow: (row, $element, field)=>{ if( opts.onClickRow ==null){ $table.find('.success').removeClass('success'); $element.addClass("success");} },
		     onPostBody:(renderedData)=>{ setTimeout(()=>{ $table.bootstrapTable_1_23("resetView")},500); if(opts.onPostBody!=null){	opts.onPostBody(renderedData); }},
		     onExpandRow: (index, row, $detail)=>{ if( opts.onExpandRow!=null) opts.onExpandRow($detail,row); }
		});
		
		if($table.data("toolbarName")!=undefined &&  opts.toolbarButtons !=null )
		{
			let $toolbar = $("#"+$table.data("toolbarName"));
			let btnDefaults={icon:null,btnClass:"default",onclick:null,title:null,text:null,permission:null,show:true,data:""};
			
			$.each( opts.toolbarButtons ,(i,btnParams)=>
			{
				let btnOpts = $.extend({},btnDefaults, btnParams);
				let crear = btnOpts.show;
				crear = btnOpts.permission!=null ? psPermiso.eval(btnOpts.permission) && btnOpts.show :btnOpts.show;

				if( crear )
				{
					let onclick = btnOpts.onclick!=null ? 'onclick="'+btnOpts.onclick+'" ':'';
					let icon = btnOpts.icon!=null ? '<i class="'+btnOpts.icon+'"></i>':'';
					let title= btnOpts.title!=null ? 'title="'+btnOpts.title+'" ':''; 
					let text = btnOpts.text!=null ? ' '+btnOpts.text : '';
					let btnProt='<button '+btnOpts.data+' '+title+' '+onclick+' class="btn btn-'+btnOpts.btnClass+' " style="outline:none;">'+icon+text+'</button>';
					
					if( opts.toolbarButtonsPos=='prepend')
						$toolbar.prepend(btnProt);
					else
						$toolbar.append(btnProt);
				}	
			});
		} 
		
		$table.innerFixDropDowns();
	};
	
	this.setSelect = function(params)
	{	
		let defaults={ 	store:[], defaultOption:false,defaultOptionLabel:'-', 
						slt:null, selectpicker:false, 
						minItemsToShow:1, 
						warningMsg:null, optionSelected:null,
						id:"id",value:"value"};
		let opts = $.extend({},defaults, params);
		
		if( opts.store.length >= opts.minItemsToShow )
			{ opts.slt.parent().show(); }
		if( opts.store.length == 0 && opts.warningMsg != null )
			{psDialog.warning(opts.warningMsg);	return false;}
		
		opts.slt.setStore({ store:opts.store, 
							defaultOption:opts.defaultOption,
							defaultOptionLabel:opts.defaultOptionLabel,
							optionSelected:opts.optionSelected,
							id:opts.id, value:opts.value});
		
		if( opts.selectpicker)
		{ 
			try{ opts.slt.selectpicker('destroy');  }catch(e){}
			opts.slt.selectpicker({width:'100%'}); 
		}
	};
	
	this.cleanForm = function( form )
	{	
		form.find(".has-error").removeClass("has-error");
		form.find(".ps-span-error").html("");
	};
	
	this.markAsError = function(obj,mensaje)
	{	
		let msg = mensaje!=null ? mensaje :"<br class='hidden-xs'>requerido"; 
		obj.parent().addClass("has-error");	
		obj.parent().parent().find('.ps-span-error').html(msg );
	};
	
	this.markSltAsError = function(obj,mensaje)
	{
		let msg = mensaje!=null ? mensaje :"<br class='hidden-xs'>requerido"; 
		obj.parent().parent().addClass("has-error");	
		obj.parent().parent().parent().find('.ps-span-error').html(msg);
	}
	
	this.serialize = function(formulario)
	{	
		let objeto = {valido:true};
		$.each(formulario.serializeArray(),function(index,element)
		{
			objeto[element.name]= ( 	   element.value == null || 
									$.trim(element.value)== ""   || 
									$.trim(element.value)== "-1" ? null : $.trim(element.value) ) ;	
		});
		return objeto; 
	};
	
	//Utils
	this.showImgArtFunc= function ()
	{
		let idArt = $(this).data("idart");
		app.showImgArt(idArt);
	};
	
	this.showImgCatFunc = function () 
	{
		let idCat = $(this).data("idcat");
		app.showImgCat(idCat);
	}
		
	this.showImgArt = function(idArt) 
	{	
		try	{  PhotoViewer.show('http://static.priceshoes.com.mx/imgs/medium/articulo/'+idArt+'.jpg', 'Artículo: '+idArt); }
		catch(e)
		{	let dialog = psDialog.dialog({
				title : "Id art: " + idArt,
				message : '<div align="center" style="overflow:auto;" > <img src="http://static.priceshoes.com.mx/imgs/medium/articulo/'+ idArt + '.jpg" '+
							' style=" max-height:450px; " onError="app.onErrorImgSmallDefault(this)" > </div>',
				btnCancelLabel : "Cerrar",
				showBtnOK : false,
				showBtnCancel : true
			});
			dialog.open();
			dialog.getButton("btnOK").click(function(){dialog.close();});
        }
	};
	
	this.showImgCat = function(idCat) 
	{	
		try	{  PhotoViewer.show('http://static.priceshoes.com.mx/imgs/large/catalogo/'+idCat+'.jpg', 'Artículo: '+idCat); }
		catch(e)
		{	let dialog = psDialog.dialog({
				title : "Cve Catalogo: " + idCat,
				message : '<div align="center" style="overflow:auto;" > <img src="http://static.priceshoes.com.mx/imgs/large/catalogo/'+ idCat + '.jpg" '+
							' style=" max-height:450px; " onError="app.onErrorImgSmallDefault(this)" > </div>',
				btnCancelLabel : "Cerrar",
				showBtnOK : false,
				showBtnCancel : true
			});
			dialog.open();
			dialog.getButton("btnOK").click(function(){dialog.close();});
        }
	};
	
	this.confirm = function(params)
	{	
		let defaults={title:"", icon:'', 
					  message:"", size:"small", align:"left", url:null, data:{}, callback:null};
		
		let opts = $.extend({},defaults, params);
		
		app.dialog = psDialog.dialog
		({ title: '<i class="'+opts.icon+'"></i> '+opts.title,
			 message: '<div align="'+opts.align+'"><p class="ps-confirm">'+opts.message+'</p></div>',
			 size:opts.size,
			 btnCancelLabel:"Cerrar",
			 btnOKLabel:"Confirmar"
		}).open();
		
		app.dialog.getButton("btnOK").click(function()
		{	system.blockButton( $(this) );
			let loading = psDialog.loading().open();
			system.service
			({ encoded:true,
				url:opts.url,
				data:opts.data,
				callback:function(response)
				{
					if(opts.callback!=null){ opts.callback(response); }
					app.dialog.close();
				}
			}).always(function(){ loading.close(); });
		});
	};
	
	this.loadDashboard = function(){ };
	
	this.loadForm = function(formUrl,data,size)
	{	
		let formData= data==undefined?{}:data;
		let formSize= size==undefined?"normal":size;
		system.getForm({url:formUrl,data:formData})
		.done(function(f){ app.dialog=psDialog.dialog({message:f,size:formSize}).open();});
	};
	
	this.getToday = function()
	{
		return system.service
		({	url:"getDate",
			callback:function(r){	app.today = r.today;} 
		});
	};
	
	this.activaTab=(tab)=>
	{
	    $('.nav-tabs a[href="#' + tab + '"]').tab('show');
	};
	
	this.onSelectTab = (_tab,callback )=>
	{
		$('.nav-tabs a[href="#'+_tab+'"]').on('shown.bs.tab',(e)=>
	    {   
	    	callback();
	    });
	}
	
	this.moneyNoDecFormatter =(valor)=>
	{
		let n = valor;
		decPlaces = 0;
		decSeparator = ".";
		thouSeparator = ",";
		sign = n < 0 ? "$-" : "$";
		let i = parseInt(n = Math.abs(+n || 0).toFixed(decPlaces)) + "";
		let j = (j = i.length) > 3 ? j % 3 : 0;
		return sign
				+ (j ? i.substr(0, j) + thouSeparator : "")
				+ i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + thouSeparator)
				+ (decPlaces ? decSeparator
						+ Math.abs(n - i).toFixed(decPlaces).slice(2) : "");
	};

	//funciones auxiliares de tablas
	this.table = {};
	
	this.setTableOps = ( _$table )=>
	{
		_$table.clear=()=>{  			 _$table.bootstrapTable_1_23("load",[]);};
		_$table.setData=(data)=>{ 		 _$table.bootstrapTable_1_23("load",data); };
		_$table.getData=()=>{ 	  return _$table.bootstrapTable_1_23("getData"); };
		_$table.getRow =( id )=>{ return _$table.bootstrapTable_1_23("getRowByUniqueId", id); };
		_$table.removeRow = (id)=>{ 	 _$table.bootstrapTable_1_23("removeByUniqueId", id); };
		_$table.updateRow = (id,row,replace)=> 
		{ _$table.bootstrapTable_1_23("updateByUniqueId",{id:id,row:row,replace:(replace==undefined?true:replace) });  };
		_$table.getSelections = ()=>{ return _$table.bootstrapTable('getSelections'); };
		_$table.resetView = (delay)=> {	setTimeout( ()=>{ _$table.bootstrapTable_1_23("resetView"); }, delay==undefined?100:delay);   };
	}
	//
	
	this.detalle = {rowId:null};
	this.isDetView = false;
	
	this.backToMainPanel=()=>
	{
		system.blockButton( $(this) );
		$("#divPanelPricipal").show();
		$("#divPanelDetalleBody").html(""); 
		$("#divPanelDetalle").hide();
		$("#divLblFolioDet").html("");
		app.detalle={rowId:null};
		app.table.bootstrapTable_1_23("resetView");
		app.isDetView = false;
	};
	
	this.showDetail = (param)=>
	{
		$("#divPanelPricipal").hide();
		$("#divPanelDetalleBody").html("");
		$("#divPanelDetalle").show();
		$("#divLblFolioDet").html("");
		app.isDetView = true;
		app.detalle={ rowId: param.id };
		system.getForm({ url:param.form })
		.done(function(form){$("#divPanelDetalleBody").html(form); });
		
		if( param.btnRefresh != null && param.btnRefresh )
		{
			$("#btnReloadDetView").show();
		}
	}
	
	this.setDismiss = (msg)=>
	{
		app.divDismiss = psDialog.infoDismiss
							({  scrollTo:false,
								id: "divDismiss",
								message: msg
							});
	};
	
	this.onlyNumbers = ( $el )=>
	{
		$el.on('input', function(evt) {
            let cursorPosition = this.selectionStart;
            let currentValue = $(this).val();
            let newValue = currentValue.replace(/[^0-9.]/g, '');
            $(this).val(newValue );
            this.setSelectionRange(cursorPosition, cursorPosition);
        });
	};
	
	this.onlyUpperCase = ( $el )=>
	{
		$el.on('input', function(evt) {
            let cursorPosition = this.selectionStart;
            let currentValue = $(this).val();
            let newValue = currentValue.toUpperCase();
            $(this).val(newValue );
            this.setSelectionRange(cursorPosition, cursorPosition);
        });
	};
	
	this.onlyLowerCase = ($el)=> 
	{
		$el.on('input', function(evt) {
            let cursorPosition = this.selectionStart;
            let currentValue = $(this).val();
            let newValue = currentValue.toLowerCase();
            $(this).val(newValue );
            this.setSelectionRange(cursorPosition, cursorPosition);
        });
	};
	
	this.capitalizeFirstLetter = (str)=> 
	{
	      if (str == null || str.length === 0) return str; 
	      return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
	};
	
	this.showArchivo = function(params)
	{
		let defaults={title:"Documento",item:{id:null,ext:null,name:"file",idPdf:false}, icon:'fa fa-eye',service:null};
		let opts = $.extend({},defaults, params);
		let body = '<div id="_divPreviewFile" align="center" style="border:solid 1px #da0080; height:75vh; max-height: 75vh; overflow: auto;" ></div>';
		app.dialog = psDialog.dialog
		({	title: '<i class="'+opts.icon+'"></i> '+opts.title,
			message:body,
			showBtnOK:false,
			size:"wide"
		});
		
		app.dialog.getModalDialog().addClass("dialog-preview-pdf");
		app.dialog.open();
		
		let btnZoomOut = '<button id="psZoomOut" class="btn btn-default"> <i class="fa fa-search-minus" aria-hidden="true"></i> Alejar</button>';
		let btnZoomIn = '<button id="psZoomIn" class="btn btn-default"> <i class="fa fa-search-plus" aria-hidden="true"></i> Acercar</button>';
		let zoomFactor = '<span id="spanZoomFactor"></span>';
		let btnDownload= '<button id="psBtnPrevDownload" class="btn btn-success"><i class="fa fa-download"></i> Descargar</button><a href="#" id="_ligaDescarga" style="display:none"></a>';
		let buttonContainer = app.dialog.getButton("btnOK").parent();
		let zoomSlider = '<input type="range" id="zoomSlider" min="50" max="300" value="100" style="display: inline !important; width:250px; margin-right: 50px;" >';
		buttonContainer.prepend( btnDownload );
		buttonContainer.prepend( zoomFactor );
		buttonContainer.prepend( zoomSlider );
		
		if( opts.item.id == null || opts.service == null ){  return false; }
		
		setTimeout(function()
		{
			$viewer.showPreview(opts.item,opts.service); 
			let _params={attachment:true, id:opts.item.id, ext:opts.item.ext, fileName:opts.item.name };
			let _data = system.Base64.encode(JSON.stringify(_params));
			let doc = system.currentApp.repoUrl+opts.service+"?data="+_data;	
			$("#_ligaDescarga").attr("href",doc);
			$("#psBtnPrevDownload").click( ()=>{ document.getElementById("_ligaDescarga").click(); });
		    
		},300) ;
	};
	
	
	this.fullscreen = ( div , status )=> 
	{
		if( status=="on" ) { div.addClass("div-fullscreen") }
		else { div.removeClass("div-fullscreen") }
		
	};

}).apply(app);




