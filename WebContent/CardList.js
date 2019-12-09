orientationMap = {
		"Landscape": "1",
		"Portrait": "2"
};

eventMap = {
		"Anniversary": "1",
		"Back to School": "2",
		"Baptism": "3",
		"Christening": "4",
		"Baby": "5",
		"Bar/Bat Mitzvah": "6",
		"Birthday": "7",
		"Confirmation": "8",
		"Congratulations": "9",
		"Encouragement": "10",
		"First Communion": "11",
		"Get Well": "12",
		"Graduation": "13",
		"Retirement": "14",
		"Sympathy": "15",
		"Teacher Appreciation": "16",
		"Thank You": "17",
		"Wedding": "18"
}

cardList = {};
rList = [];
eList = [];

$(document).ready(function() {
	
	displayCards();
	
	$(".r-option").on("click",function(){
		$('.r-option').removeClass('active');
		$(this).addClass('active');
		newR = $(this).text();
		$('#recipientSelect').html(newR);
		//console.log(newR);
		$('#eventSelect').html('Event');
		$('#cardbody').html("");
		for(var i in cardList){
			if(cardList[i].recipientName == newR){
				$('#cardbody').append("<tr id="+cardList[i].cardID+">"
						+"<td id='recipientName"+cardList[i].cardID+"'>"+cardList[i].recipientName+"</td>"
						+"<td id='recipientEmail"+cardList[i].cardID+"'>"+cardList[i].recipientEmail+"</td>"
						+"<td id='event"+cardList[i].cardID+"'>"+cardList[i].event+"</td>"
						+"<td>"+"<button type='button' class='btn btn-success edtBtn' id='Edt"+cardList[i].cardID+"' onclick=cdEdt('"+cardList[i].cardID+"')>" +"Edt"+"</button>"
						+" "+"<button type='button' class='btn btn-danger delBtn' id='Del"+cardList[i].cardID+"' onclick=cdDel('"+cardList[i].cardID+"')>" +"Del"+"</button>" 
						+" "+"<button type='button' class='btn btn-warning dupBtn' id='Dup"+cardList[i].cardID+"' onclick='cdDup("+JSON.stringify(cardList[i])+")'>" +"Dup" +"</button>"
						+" "+"<button type='button' class='btn btn-info dupBtn' id='Link"+cardList[i].cardID+"' onclick=cdLink('"+cardList[i].cardID+"')>" +"Link" +"</button></td>");
			}
		}
	});	
	
	$(".e-option").on("click",function(){
		$('.e-option').removeClass('active');
		$(this).addClass('active');
		newE = $(this).text();
		$('#eventSelect').html(newE);
		//console.log(newE);
		$('#recipientSelect').html('Recipient');
		$('#cardbody').html("");
		for(var i in cardList){
			if(cardList[i].event == newE){
				$('#cardbody').append("<tr id="+cardList[i].cardID+">"
						+"<td id='recipientName"+cardList[i].cardID+"'>"+cardList[i].recipientName+"</td>"
						+"<td id='recipientEmail"+cardList[i].cardID+"'>"+cardList[i].recipientEmail+"</td>"
						+"<td id='event"+cardList[i].cardID+"'>"+cardList[i].event+"</td>"
						+"<td>"+"<button type='button' class='btn btn-success edtBtn' id='Edt"+cardList[i].cardID+"' onclick=cdEdt('"+cardList[i].cardID+"')>" +"Edt"+"</button>"
						+" "+"<button type='button' class='btn btn-danger delBtn' id='Del"+cardList[i].cardID+"' onclick=cdDel('"+cardList[i].cardID+"')>" +"Del"+"</button>" 
						+" "+"<button type='button' class='btn btn-warning dupBtn' id='Dup"+cardList[i].cardID+"' onclick='cdDup("+JSON.stringify(cardList[i])+")'>" +"Dup" +"</button>"
						+" "+"<button type='button' class='btn btn-info dupBtn' id='Link"+cardList[i].cardID+"' onclick=cdLink('"+cardList[i].cardID+"')>" +"Link" +"</button></td>");
			}
		}
	});	
	
	$("#listAllCards").click(function(){
		$('#recipientSelect').html('Recipient');
		$('#eventSelect').html('Event');
		displayCards();
	})
	
	$("#newCardOrientation a").click(function(){
		$('.orientation-option').removeClass('active');
		$(this).addClass('active');
		newCardOrientation = $(this).text();
		$('#cardOrientation').html(newCardOrientation);
	});
	
	$("#newCardEvent a").click(function(){
		$('.event-option').removeClass('active');
		$(this).addClass('active');
		newCardEvent = $(this).text();
		$('#eventType').html(newCardEvent);
	});	
	
	$('#createCard').click(function(){
		var newCard = {
				cardOrientation: orientationMap[newCardOrientation],
				recipientName: $("#newRecipientName").val(),
				recipientEmail: $("#newRecipientEmail").val(),
				eventType: eventMap[newCardEvent]
		};
		//console.log(JSON.stringify(newCard));
		$.ajax({
			type: "post",
			url: "https://eexxck49h4.execute-api.ca-central-1.amazonaws.com/Beta/createCard",
			data: JSON.stringify(newCard),
			dataType: "json",
			async: false,
			success: function(flag){
				if(flag.statusCode == '200')
					displayCards();
				else
					alert("Failed to create the card.");
			},
			error: function(jqXHR,textStatus,errorThrown){
				alert("Failed to create the card.");
				console.log(textStatus,errorThrown);
			}
		});
	});
});

function displayCards(){
	$("#cardbody").html("");
	$.ajax({
		url: "https://eexxck49h4.execute-api.ca-central-1.amazonaws.com/Beta/displayCards",
		type: "get",
		dataType: "json",
		async: false,
		data: "",
		success: function(data){
			if(data.statusCode == '200'){
				cardList = eval(data.body);
				//console.log(cardList);
				cardLen = cardList.length;
				rList = [];
				eList = [];
				for(var i = 0; i < cardLen; i++){
					if(rList.indexOf(cardList[i].recipientName) == -1)
						rList.push(cardList[i].recipientName);
					if(eList.indexOf(cardList[i].event) == -1)
						eList.push(cardList[i].event);
					$('#cardbody').append("<tr id="+cardList[i].cardID+">"
							+"<td id='recipientName"+cardList[i].cardID+"'>"+cardList[i].recipientName+"</td>"
							+"<td id='recipientEmail"+cardList[i].cardID+"'>"+cardList[i].recipientEmail+"</td>"
							+"<td id='event"+cardList[i].cardID+"'>"+cardList[i].event+"</td>"
							+"<td>"+"<button type='button' class='btn btn-success edtBtn' id='Edt"+cardList[i].cardID+"' onclick=cdEdt('"+cardList[i].cardID+"')>" +"Edt"+"</button>"
							+" "+"<button type='button' class='btn btn-danger delBtn' id='Del"+cardList[i].cardID+"' onclick=cdDel('"+cardList[i].cardID+"')>" +"Del"+"</button>" 
							+" "+"<button type='button' class='btn btn-warning dupBtn' id='Dup"+cardList[i].cardID+"' onclick='cdDup("+JSON.stringify(cardList[i])+")'>" +"Dup" +"</button>"
							+" "+"<button type='button' class='btn btn-info dupBtn' id='Link"+cardList[i].cardID+"' onclick=cdLink('"+cardList[i].cardID+"')>" +"Link" +"</button></td>");
				}
				for(var r in rList)
					$('#recipientList').append("<a class='dropdown-item r-option'>"+rList[r]+"</a>");
				for(var e in eList)
					$('#eventList').append("<a class='dropdown-item e-option'>"+eList[e]+"</a>");
			}	
			else
				alert("Failed to load cards.");
		},
		error: function(jqXHR,textStatus,errorThrown){
			alert("Failed to load cards.");
			console.log(textStatus, errorThrown);
		}
	});
}


function cdEdt(index){
	var EdtCard = {cardID: index};
	$.ajax({
		type: "post",
		url: "https://eexxck49h4.execute-api.ca-central-1.amazonaws.com/Beta/getCard",
		data: JSON.stringify(EdtCard),
		dataType: "json",
		async: false,
		success: function(data) {
			//console.log(eval(data));
			if(data.statusCode == '200') {
				//console.log(JSON.stringify(data.body));
				localStorage.setItem('cardInfo', data.body);
				window.open('edit.html');
				//console.log(localStorage.getItem('cardInfo'));
			}
			else
				alert("Failed to modify the card.");
		},
		error: function(jqXHR,textStatus,errorThrown){
			alert("Failed to modify the card.");
			console.log(textStatus,errorThrown);
		}
	});
}	


function cdDel(index){
	var delCard = {cardID: index};
	$.ajax({
		type: "post",
		url: "https://eexxck49h4.execute-api.ca-central-1.amazonaws.com/Beta/deleteCard",
		data: JSON.stringify(delCard),
		dataType: "json",
		async: false,
		success: function(flag) {
			if(flag.statusCode == '200')
				displayCards();
			else
				alert("Failed to delete the card.");
		},
		error: function(jqXHR,textStatus,errorThrown){
			alert("Failed to delete the card.");
			console.log(textStatus,errorThrown);
		}
	});
	
}

function cdDup(card){
	card = eval(card);
	var dupCard = {
			cardOrientation: orientationMap[newCardOrientation],
			recipientName: $("#newRecipientName").val(),
			recipientEmail: $("#newRecipientEmail").val(),
			eventType: eventMap[newCardEvent],
			cardID: card.cardID
			};
	console.log();
	$.ajax({
		type: "post",
		url: "https://eexxck49h4.execute-api.ca-central-1.amazonaws.com/Beta/duplicateCard",
		data: JSON.stringify(dupCard),
		dataType: "json",
		async: false,
		success: function(flag) {
			if(flag.statusCode == '200')
				displayCards();
			else
				alert("Failed to duplicate the card.");
		},
		error: function(jqXHR,textStatus,errorThrown){
			alert("Failed to duplicate the card.");
			console.log(textStatus,errorThrown);
		}
	});
}

function cdLink(index){
	window.open('cardpreview.html#'+index);
/*	var linkCard = {cardID: index};
	$.ajax({
		type: "post",
		url: "https://eexxck49h4.execute-api.ca-central-1.amazonaws.com/Beta/getCard",
		data: JSON.stringify(linkCard),
		dataType: "json",
		async: false,
		success: function(data) {
			if(data.statusCode == '200') {
				//console.log(JSON.stringify(data.body));
				console.log(eval(data.body));
				window.open('cardpreview.html/'+data.body);
				//console.log(localStorage.getItem('cardInfo'));
			}
			else
				alert("Failed to show the link.");
		},
		error: function(jqXHR,textStatus,errorThrown){
			alert("Failed to show the link.");
			console.log(textStatus,errorThrown);
		}
	});*/
}