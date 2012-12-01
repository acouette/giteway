var drawTimeLine = function(jsonData, placeholder) {

	
	var arrayData = jQuery.parseJSON(jsonData);
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
	if(step<1000*60*60*10){
		timeformat = "%d-%m<br/> %H:%M";
	}else{
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
			fillColor: "rgba(164, 170, 4, 0.3)"
		},
		colors: [ "rgba(164, 170, 4, 1)"]
	};

	$.plot($(placeholder), [chartData], options);

};

var drawCommitterActivities = function(jsonData, placeholder) {

	var arrayData = jQuery.parseJSON(jsonData);
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