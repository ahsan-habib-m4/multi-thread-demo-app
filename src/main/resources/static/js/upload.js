$(document).ready(function() {
	var fileInput = $("#fileInput");
  
	fileInput.on("change", function() {
		$("#overlay").show();
		var file = fileInput[0].files[0];
		var formData = new FormData();
		formData.append('file', file);
		var url = "/api/upload";
		$.ajax({
			url: url,
			type: 'POST',
			data: formData,
			processData: false,
			contentType: false,
			success: function() {
				$("#overlay").hide();

				getData();
			},
		});
	});
	getData();
	
});


function getData() {
	if ( $.fn.DataTable.isDataTable('#peopleTable') ) {
	  $('#peopleTable').DataTable().destroy();
	}

	$('#peopleTable tbody').empty();
	
	$("#peopleTable").DataTable({
		serverSide: true,
		processing: true,
		ordering: false,
		searching: false,
		order: [[0, 'desc']],
		ajax: {
			url: "/peoples",
			"type": "GET",
			data: function(d) {
				var defaults = {
					'offset': d.start,
					'limit': d.length
				};
				return defaults;
			},
			'dataSrc': 'peopleList',
		},
		
		columns: [
			{ 'data': 'userId', "bSortable": false, },
			{ 'data': 'firstName', "bSortable": false, },
			{ 'data': 'lastName', "bSortable": false, },
			{ 'data': 'sex', "bSortable": false, },
			{ 'data': 'email', "bSortable": false, },
			{ 'data': 'phone', "bSortable": false, },
			{ 'data': 'dateOfBirth', "bSortable": false, }
		]
	});
		
}