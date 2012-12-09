//variable to keep track of which view is displayed
var currentView = "#timeline";

// intitialize view : timeline
$(document).ready(function() {

	// initialize radio button behaviour
	$("#radio").buttonset();
	$("#timeline-radio").click(function() {
		setView('#timeline');
	});
	$("#activity-radio").click(function() {
		setView('#activity');
	});
	$("#collaborators-radio").click(function() {
		setView('#collaborators');
	});

	// initialize view state
	$("#collaborators").hide();
	$("#activity").hide();
	$("#timeline").hide();
	setView(currentView);

});

/**
 * Function used to draw the timeline. Based on Plot library
 */
var drawTimeLine = function(data, placeholder) {

	var arrayData = data["timelineIntervals"];
	var chartData = new Array();
	var step = 0;
	if (arrayData.length > 0) {
		step = arrayData[0].end - arrayData[0].start;
	}

	for ( var i = 0; i < arrayData.length; i++) {
		if (arrayData[i].commitCount > 0) {
			chartData[i] = [ arrayData[i].start, arrayData[i].commitCount ];
		}
	}

	var timeformat;
	if (step < 1000 * 60 * 60 * 10) {
		timeformat = "%d-%m<br/> %H:%M";
	} else {
		timeformat = "%d-%b<br/>%y";
	}

	var options = {
		xaxis : {
			mode : "time",
			timeformat : timeformat,
			monthNames : [ "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec" ]
		},
		bars : {
			show : true,
			barWidth : step,
			fillColor : "rgba(164, 170, 4, 0.3)"
		},
		colors : [ "rgba(164, 170, 4, 1)" ]
	};

	$.plot($(placeholder), [ chartData ], options);

};

/**
 * Function used to draw the commiter activities. Based on Plot library
 */
var drawCommitterActivities = function(data, placeholder) {

	var arrayData = data["committerActivityList"];
	var chartData = new Array();
	for ( var i = 0; i < arrayData.length; i++) {
		chartData[i] = {
			label : arrayData[i].committer.login,
			data : [ [ 1, arrayData[i].percentage ] ]
		};
	}

	var options = {
		series : {
			pie : {
				show : true,
				label : {
					show : true,
					formatter : function(label, series) {
						return '<div style="font-size:10pt;">' + label + '<br/>' + Math.round(series.percent) + '%</div>';
					}
				},
				combine : {
					threshold : 0.04
				}
			}
		},
		legend : {
			show : false
		}
	};

	$.plot($(placeholder), chartData, options);
};

/**
 * Function invoked when switching views
 */
var setView = function(view) {
	$("#load-gif").show();
	var previousView = this.currentView;
	this.currentView = view;
	$(previousView).slideUp("slow", function() {
		switch (currentView) {
		case "#timeline":
			loadTimeline();
			break;
		case "#activity":
			loadCommittersActivity();
			break;
		case "#collaborators":
			showCurrentView();
		}
	});
};

/**
 * Function Show the panel associated to the current view
 */
var showCurrentView = function() {

	$(currentView).slideDown("slow",function(){
		$("#load-gif").hide();
	});
	
};

/**
 * Function invoked to load and display timeline data
 */
var loadTimeline = function() {
	$.ajax({
		url : rootContext + "/restful/repos/" + repoOwner + "/" + repoName + "/timeline",
		dataType : 'json',
		success : function(response) {
			showCurrentView();
			$('#timeline-commitCount').text(response.commitCount);
			$('#timeline-timelineDays').text(parseFloat(response.timelineDays).toFixed(1));
			$('#timeline-intervalDays').text(parseFloat(response.intervalDays).toFixed(1));
			drawTimeLine(response, '#timeline-chart');
		},
		error : handleError
	});
};

/**
 * Function invoked to load and display committers' activity
 */
var loadCommittersActivity = function() {
	$.ajax({
		url : rootContext + "/restful/repos/" + repoOwner + "/" + repoName + "/activity",
		dataType : 'json',
		success : function(response) {
			showCurrentView();
			$('#activity-commitCount').text(response.commitCount);
			drawCommitterActivities(response, '#activity-chart');
		},
		error : handleError
	});
};

var handleError = function(response) {
	alert('Error from server : ' + response.status);
	$("#load-gif").hide();
};
