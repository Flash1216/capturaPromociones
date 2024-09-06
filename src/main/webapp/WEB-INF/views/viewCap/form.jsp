<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<script type="text/javascript">
var fepsCargaCsv = {};
(function()
{
	function setFileInput()
	{
		let data ={ idUsrN:system.user.idUsrN,usrCveStr:system.user.usrCveStr};
		$("#spanFileOutDownload").html("");
		
		$("#input-id").fileinput
		({
			showPreview : false,
			allowedFileExtensions : [ 'csv'],
			showCancel:false,
			uploadUrl : system.currentApp.serviceUrl+ "upload/file/?"+$.param(data),
			fileActionSettings:{showZoom : false, showUpload:false, showRemove:false,showDrag:false	},
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
	
	
		$('#input-id').on('fileuploaded',function(event, data, previewId, index) 
		{
			var form = data.form, files = data.files, extra = data.extra, response = data.response, reader = data.reader;
			if( response.status==200)
			{
				alp.table.bootstrapTable('load', []);
				alp.btnFormatter(response.listLP);
				alp.table.bootstrapTable('load',response.listLP);
				app.dialog.close();	
			}
			else
			{
				psDialog.error(response.message);
			}	
			
			$('#input-id').fileinput('clear');
			$('#input-id').fileinput('enable');
		});
	}
	
	this.init  = function()
	{
		setFileInput();
	};
}).apply(fepsCargaCsv);

$(document).ready(fepsCargaCsv.init);

</script>

<div class="row">
	<div class="col-lg-6 col-md-6 col-sm-8 col-xs-12" style="width: 100%">
		<div class="form-group">
			<label class="col-sm-3 control-label" for="caPagN">
				<span class="ps-color-red"><i class="fa fa-asterisk ast-required"></i></span>
					Pagina
				<span class="ps-span-error"></span>
			</label>
			<div class="col-sm-9">
		  			<input id="caPagN" name="caPagN" maxlength="10" class="form-control" value="">
			</div>
		</div>
		
		<span> </span>
		<input id="input-id" type="file" class="file" data-preview-file-type="text" name="file">		
	</div>
</div>
