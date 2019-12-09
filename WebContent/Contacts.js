contactList = {};

$(document).ready(function() {
	
	displayContacts();
	
	$('#addContact').click(function(){
		var newContact = {
				rName: $("#newContactName").val(),
				rSurname: $("#newContactSurname").val(),
				rEmail: $("#newContactEmail").val(),
		};
		console.log(JSON.stringify(newContact));
		$.ajax({
			type: "post",
			url: "https://4aiz0zwpj2.execute-api.ca-central-1.amazonaws.com/Alpha/addRecipient",
			data: JSON.stringify(newContact),
			dataType: "json",
			async: false,
			success: function(flag){
				console.log(flag);
				if(flag.statusCode == '200')
					displayContacts();
				else
					alert("Failed to add the recipient.");
			},
			error: function(jqXHR,textStatus,errorThrown){
				alert("Failed to add the recipient.");
				console.log(textStatus,errorThrown);
			}
		});
	});
});

function displayContacts(){
	$("#contactbody").html("");
	$.ajax({
		url: "https://4aiz0zwpj2.execute-api.ca-central-1.amazonaws.com/Alpha/listRecipients",
		type: "post",
		dataType: "json",
		async: false,
		data: "",
		success: function(data){
			if(data.statusCode == '200'){
				contactList = eval(data.body);
				//console.log(contactList);
				contactLen = contactList.length;
				for(var i = 0; i < contactLen; i++){
					$('#contactbody').append("<tr id="+contactList[i].recipientID+">"
							+"<td id='recipientName"+contactList[i].recipientID+"'>"+contactList[i].recipientName+"</td>"
							+"<td id='recipientEmail"+contactList[i].recipientID+"'>"+contactList[i].recipientEmail+"</td>"
							+"<td>"//+"<button type='button' class='btn btn-success edtBtn' id='Edt"+contactList[i].recipientID+"' onclick=ctEdt('"+contactList[i].recipientID+"')>" +"Edt"+"</button>"
							+" "+"<button type='button' class='btn btn-danger delBtn' id='Del"+contactList[i].recipientID+"' onclick=ctDel('"+contactList[i].recipientID+"')>" +"Del"+"</button></td>");
				}
			}	
			else
				alert("Failed to load recipients.");
		},
		error: function(jqXHR,textStatus,errorThrown){
			alert("Failed to load recipients.");
			console.log(textStatus, errorThrown);
		}
	});
}


/*function ctEdt(index){
	var EdtContact = {recipientID: index};
	$.ajax({
		type: "post",
		url: "https://4aiz0zwpj2.execute-api.ca-central-1.amazonaws.com/Alpha/updateRecipient",
		data: JSON.stringify(EdtContact),
		dataType: "json",
		async: false,
		success: function(data) {
			if(data.statusCode == '200') {
				localStorage.setItem('contactInfo', data.body);
			}
			else
				alert("Failed to update the recipient.");
		},
		error: function(jqXHR,textStatus,errorThrown){
			alert("Failed to update the recipient.");
			console.log(textStatus,errorThrown);
		}
	});
}*/	


function ctDel(index){
	var delContact = {recipientID: index};
	$.ajax({
		type: "post",
		url: "https://4aiz0zwpj2.execute-api.ca-central-1.amazonaws.com/Alpha/deleteRecipient",
		data: JSON.stringify(delContact),
		dataType: "json",
		async: false,
		success: function(flag) {
			if(flag.statusCode == '200')
				displayContacts();
			else
				alert("Failed to delete the recipient.");
		},
		error: function(jqXHR,textStatus,errorThrown){
			alert("Failed to delete the recipient.");
			console.log(textStatus,errorThrown);
		}
	});
	
}