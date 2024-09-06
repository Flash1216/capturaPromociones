var $viewer={};
(function()
{
	let oHeight = 0;
	let factor = 100;
	
	function zoomOut()
	{ 
		if( factor >=20 )
		{
			factor-=10;
			let h = oHeight*(factor/100);
			$("#_prevImgView").css("height",h+"px");
			$("#spanZoomFactor").html(factor+"%  ");
		}	
	}
	function zoomIn()
	{
		if( factor <=190 )
		{
			factor+=10;
			let h = oHeight*(factor/100);
			$("#_prevImgView").css("height",h+"px");
			$("#spanZoomFactor").html(factor+"%  ");
		}
	}
	
	
	this.showPreview = function(item,service)
	{
		$("#psZoomOut").click(zoomOut);
		$("#psZoomIn").click(zoomIn);
		$("#spanZoomFactor").html("100%  ");
		oHeight = 0;
		factor = 100;
		
		$('#zoomSlider').on('input', function() 
		{
		     let zoomValue = $(this).val();
		     let scale = zoomValue/100; 
		     let h = oHeight*(zoomValue/100);
		     $("#spanZoomFactor").html(zoomValue+"%  ");
			$("#_prevImgView").css("height",h+"px");
		});
		
		let data={	id:item.id,
					ext:item.ext,
					attachment:false,
					fileName:item.name};
		let doc =  system.currentApp.repoUrl+service+"?data="+system.Base64.encode(JSON.stringify(data));
		let content="No disponible";
		
		if( item.isPdf )
		{
			$("#zoomSlider").remove();
			$("#psZoomOut").remove();
			$("#psZoomIn").remove();
			$("#spanZoomFactor").remove();
			content = "<object alt='Vista no disponible' style='width:100%; height:97%;' data='"+doc+"'></object>";
			$("#_divPreviewFile").html(content);
		}else 
		{
		 	content = "<img id='_prevImgView' alt='Vista no disponible' src='"+doc+"' ></img>";
			$("#_divPreviewFile").html(content);
			setTimeout(function(){oHeight = $("#_prevImgView").height();},500);
		}
	};
}).apply($viewer);
