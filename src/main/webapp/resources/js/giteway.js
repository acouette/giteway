
var drawTimeLine = function(data,placeholder){
	
	var jsonData = jQuery.parseJSON(data);
	var chartData = new Array();
	for(var i=0;i<jsonData.length;i++){
		chartData[i] = [jsonData[i].timestamp,  jsonData[i].commits];
	}
	var options = {
			xaxis: { 	mode: "time",
						timeformat: "%d-%b-%y",
						monthNames: ["jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"]
					}
	};
	$.plot($(placeholder), [chartData], options);
	
};


var drawCommitterActivities = function(data, placeholder){
	
	var jsonData = jQuery.parseJSON(data);
	var chartData = new Array();
    for (var i=0; i<jsonData.length; i++) {
    	chartData[i] = { label: jsonData[i].login,  data: [[1,jsonData[i].percentage]]};
    }
    $.plot($(placeholder), chartData, 
	{
    	series: {
            pie: { 
                show: true,
                label: {
                    show: true,
                    formatter: function(label, series){
                        return '<div style="font-size:10pt;">'+label+'<br/>'+Math.round(series.percent)+'%</div>';
                    }
                },
                combine: {
                    threshold: 0.03
                }
            }
        },
        legend: {
            show: false
        }
	});
};