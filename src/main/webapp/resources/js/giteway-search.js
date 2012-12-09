var searchType;

$(document).ready(function() {

	//define button as a jqueryUI button
	$("#submitKeyword").button();
	
	//initialize radio
	if(type=="keyword"){
		$("#keyword-radio").click();
	}else if(type=="owner"){
		$("#owner-radio").click();
	}
	
	//define radio behaviour
	$("#keyword-radio").click(function() {
		type = "keyword";
		$('.searchResult').html('');
	});
	$("#owner-radio").click(function() {
		type = "owner";
		$('.searchResult').html('');
	});

	// Checks for empty keyword every 500ms to clear the result grid
	setInterval(function() {
		if ($("#keyword").val().length == 0) {
			$('.searchResult').html('');
		}
	}, 500);

	// Stop a form submit if the keyword is empty
	$("form").submit(function() {
		$("#keyword").val($("#keyword").val().replace('/', ''));
		if ($("#keyword").val().length == 0) {
			return false;
		}
	});
	// hide animated gif
	$("#load-gif").hide();
	// Add watermark in search input
	$("#keyword").watermark('Search a GitHub repository');

	// Autosuggest
	$("#keyword").keyup(function() {
		autosuggest();
	});
});

/**
 * Function triggered when a user clicks on the extra search link Loads the
 * extra links
 */
var getExtraLink = function() {
	$("#load-gif").show();
	$.ajax({
		url : rootContext + "/search/extra/" + type + "/" + keyword,
		dataType : 'json',
		success : function(response) {
			for ( var i = 0; i < response.length; i++) {
				var row = response[i];
				var link = "<a href='" + rootContext + "/repos/" + row.owner + "/" + row.name + "'>" + row.name + "</a>";
				$("#searchTable tr:last").after("<tr><td>" + link + "</td><td>" + row.owner + "</td><td>" + row.description + "</td></tr>");
			}
			$("#load-gif").hide();
			$("#extralink").hide();
		},
		error : function(response) {
			alert('Error from server : ' + response.status);
		}
	});
};

/**
 * Function triggered when a user types words in the search bar Provides an ajax
 * support to autosuggest repository names
 */
var autosuggest = function() {
	$("#keyword").autocomplete({
		source : rootContext + "/search/autosuggest/"+type,
		minLength : 2,
		select : function(event, ui) {
			$("#keyword").val(ui.item.value);
			$("form").submit();
		}
	});
};