<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>demo</title> 
<style type="text/css"> 
body {font-size:12px; } 
.tab {background:url(images/gray.png); cursor:hand;} 
</style> 
<script type="text/javascript"> 
$(document).ready(function() 
{ 
//------------------------- 
var tabclass = ".tab"; //tab 数组 id 
var tabs = ["#tab1", "#tab2", "#tab3"]; //tab 数组 id 
var datas = ["#data1", "#data2", "#data3"]; 
var tab_selected_bgimg = "img/blue.jpg"; 
var tab_unselected_bgimg = "img/pink.jpg"; 
var tab_selected_txtcolor = "#ff6600"; 
var tab_unselected_txtcolor = "#666666"; 
var curr_index; 
$(tabclass).click(function() 
{ 
for(var i=0;i<tabs.length;i++) 
{ 
if($(tabs[i]).attr("id")==$(this).attr("id")) 
{ 
curr_index = parseInt(i)+1; 
} 
$(tabs[i]).css("background-image", "url("+ tab_unselected_bgimg +")"); 
$(tabs[i]).css("color", tab_unselected_txtcolor); 
$(datas[i]).hide(); 
} 
$(this).css("background-image", "url("+ tab_selected_bgimg +")"); 
$(this).css("color", tab_selected_txtcolor); 
$("#data"+curr_index).show(); 
}); 
$("#tab1").click(); 
//----------------- 
$("#salesMoney").val("18900335");
$("#hb").val("1.1%");
$("#tb").val("1.2%");
$("#occupancy").val("20%");
$("#allSalesMoney").val("768900335");
$("#allHb").val("-3.1%");
$("#allTb").val("-3.1%");
$("#index").val("3.1");

var cObj1 = {"冰箱":"1","空调":"2","电脑":"3"};
var cObj2 = {"冰箱":"5","空调":"4","电脑":"2"};
var sObj1 = {};
sObj1["青岛"] = cObj1;
sObj1["济南"]= cObj2;
var cObj3= {"冰箱":"1","空调":"2","电脑":"3"};
var cObj4 = {"冰箱":"5","空调":"4","电脑":"2"};

var sObj2 = {}
sObj2["秦皇岛"] = cObj3;
sObj2["石家庄"] = cObj4;
var obj = {}
obj["山东"] = sObj1;
obj["河北"] = sObj2;
var points = [],
region_p,
region_val,
region_i,
country_p,
country_i,
cause_p,
cause_i,
cause_name = [];
cause_name['冰箱'] = '冰箱';
cause_name['电脑'] = '电脑';
cause_name['空调'] = '空调';
region_i = 0;
for (var region in obj) {
region_val = 0;
region_p = {
	id: "id_" + region_i,
	name: region,
	color: Highcharts.getOptions().colors[region_i]
};
country_i = 0;
for (var country in obj[region]) {
	country_p = {
		id: region_p.id + "_" + country_i,
		name: country,
		parent: region_p.id
	};
	points.push(country_p);
	cause_i = 0;
	for (var cause in obj[region][country]) {
		cause_p = {
			id: country_p.id + "_" + cause_i,
			name: cause_name[cause],
			parent: country_p.id,
			value: Math.round(+obj[region][country][cause])
		};
		region_val += cause_p.value;
		points.push(cause_p);
		cause_i++;
	}
	country_i++;
}
region_p.value = Math.round(region_val / country_i);
points.push(region_p);
region_i++;
}
var chart = new Highcharts.Chart({
chart: {
	renderTo: 'data1'
},
series: [{
	type: "treemap",
	layoutAlgorithm: 'squarified',
	allowDrillToNode: true,
	dataLabels: {
		enabled: false
	},
	levelIsConstant: false,
	levels: [{
		level: 1,
		dataLabels: {
			enabled: true
		},
		borderWidth: 3
	}],
	data: points
}],
subtitle: {
	text: '报表'
},
title: {
	text: '海信渠道可视化项目全产品销售热力图'
}
});
}); 
</script> 
</head> 
<body> 
<div style="width: 100%;height: 100%">
<div>
	<label id="title">test</label>
</div>
<table border="0" height="10%" align="left" cellpadding="0" cellspacing="0"> 
<tr> 
<td width="50px" class="tab" id="tab1">综合</td> 
<td width="50px" class="tab" id="tab2">线上</td> 
<td width="50px" class="tab" id="tab3">线下</td> 
</tr> 
</table>
<table border="0" width="100%" height="30%" align="left" cellpadding="0" cellspacing="0" id="basicInfo">
<tr>
<td>海信旗下销额:</td><td><input type="text" style="border: none;" id="salesMoney" disabled="disabled"/></td>
<td>海信旗下环比:</td><td><input type="text" style="border: none;" id="hb" disabled="disabled"/></td>
<td>海信旗下同比:</td><td><input type="text" style="border: none;" id="tb" disabled="disabled"/></td>
<td>海信旗下占有率:</td><td><input type="text" style="border: none;" id="index" disabled="disabled"/></td>
</tr>
<tr>
<td>行业整体销额:</td><td><input type="text" style="border: none;" id="allSalesMoney" disabled="disabled"/></td>
<td>行业整体环比:</td><td><input type="text" style="border: none;" id="allHb" disabled="disabled"/></td>
<td>行业整体同比:</td><td><input type="text" style="border: none;" id="allTb" disabled="disabled"/></td>
<td>海信旗下品牌指数:</td><td><input type="text" style="border: none;" id="occupancy" disabled="disabled"/></td>
</tr>
</table> 
<table  width="100%" height="60%" border="0" align="left" cellpadding="0" cellspacing="0"> 
<tr> 
<td> 
<div id="data1"> 
 
</div> 
<div id="data2"> 
this is data2 
</div> 
<div id="data3"> 
this is data3 
</div> 
</td> 
</tr> 
</table> 
</div>

</body> 