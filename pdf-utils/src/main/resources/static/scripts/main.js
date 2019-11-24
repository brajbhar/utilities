/**
 * 
 */
require.config({
    paths: {
        jquery: 'lib/jquery-2.1.1.min',
        bootstrap: 'lib/bootstrap.min',
        ko: 'lib/knockout-3.4.2'
    },
    shim: {
    	bootstrap: ['jquery']
    }
});

define('file-upload-manager', ['jquery'], function($){
	
	var totalFileControls = 1;
	
	$('#mergePdf').on('click', ajaxSubmit);
	$('#addNewPdf').on('click', addNewPdf);
	$('#file1').on('change', function() {
		var labelId = this.id + "-label";
		$('#' + labelId).text($(this).val());
	});
	
	function logMessage() {
		console.log("Jquery Loaded "+ $);
	}
	
	
	function addNewPdf() {
		totalFileControls = totalFileControls + 1;
		var newRow = '<div class="row"><div class="input-group mb-3"><div class="custom-file">' +
			'<input type="file" class="custom-file-input" id="file'+ totalFileControls +'" name="files">' +
			'<label class="custom-file-label" for="file'+ totalFileControls +'" id="file'+ totalFileControls +'-label">Choose file</label>' +
			'</div></div></div>';
		$('#fileControls').append(newRow);
		$('#file'+totalFileControls).on('change', function() {
			var labelId = this.id + "-label";
			$('#' + labelId).text($(this).val());
		});
	}
	
	function ajaxSubmit() {
		var form = $('#pdfForm')[0];
		var data = new FormData(form);
		console.log(data);
		  $("#mergePdf").prop("disabled", true);
				  
		  var xhr = new XMLHttpRequest();
		  xhr.open('POST', '/mergePdf', true);
		  xhr.responseType = 'blob';
		  xhr.onload = function (e) {
		      var blob = xhr.response;
		      var fileName = "filename.pdf";
			  var link = document.createElement('a');
        	  link.href = window.URL.createObjectURL(blob);
        	  link.download = "Filename";
        	  link.click();
        	  console.log("SUCCESS : ", data);
        	  $("#mergePdf").prop("disabled", false);
		  }
		  xhr.send(data);
	}
	
	return {
		logMessage: logMessage,
	}
	
});

define('app', ['jquery', 'ko', 'file-upload-manager'], 
		function($, ko, uploadManager){
	
	return {
		init: function() {
			uploadManager.registerEvents();
			
		}
	}
});

require(['app'], function(app){
	app.init();
});