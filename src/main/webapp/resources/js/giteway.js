
var drawTimeLine = function(data,placeholder){
	
	var jsonData = jQuery.parseJSON(data);
	var chartData = new Array();
	for(var i=0;i<jsonData.length;i++){
		chartData[i] = [jsonData[i].timestamp,  jsonData[i].commits];
	}
	var options = {
			xaxis: { mode: "time",timeformat: "%y-%m-%d"}
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
                radius: 1,
                label: {
                    show: true,
                    radius: 1,
                    formatter: function(label, series){
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'+label+'<br/>'+Math.round(series.percent)+'%</div>';
                    },
                    background: { opacity: 0.5 }
                }
            }
        },
        legend: {
            show: false
        }
	});
};