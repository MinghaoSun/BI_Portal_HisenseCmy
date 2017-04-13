<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>demo</title> 
<style type="text/css"> 
body {font-size:12px; } 
.tab {background:url(img/gray.jpg); cursor:hand;} 
.nodeRect {
	stroke: white;
	stroke-width: 2px;
  }
  
  .nodeName {
	fill: white;
	font-size: 12px;
	font-family: simsun;
  }
  .treemap-box {
	position: relative;
}
.treemap-box .treemap-child {
	float: left;
	padding: 1px;
}
</style> 
<script type="text/javascript"> 
var colorConfig = [
    {
        color:'#FF560A',
        rise: {
            min: -65536,
            max: -20,
        },
        occupancy: {
            min: -65536,
            max: 1, 
        },
        name: 1
    },
    {
        color: '#FF8954',
        rise: {
            min: -20,
            max: -10,
        },
        occupancy: {
            min: 1,
            max: 3,
        },
        name: 2
    },
    {
        color: '#FFB59C',
        rise: {
            min: -10,
            max: -5,
        },
        occupancy: {
            min: 3,
            max: 5
        },
        name: 3
    },
    {
        color: '#FFE88F',
        rise: {
            min: -5,
            max: 0,
        },
        occupancy: {
            min: 5,
            max: 10
        },
        name: 4
    },
    {
        color: '#FDD21A',
        rise: {
            min: 0,
            max: 5,
        },
        occupancy: {
            min: 10,
            max: 15
        },
        name: 5
    },
    {
        color: '#FFAE2B',
        rise: {
            min: 5,
            max: 10,
        },
        occupancy: {
            min: 15,
            max: 20
        },
        name: 6
    },
    {
        color: '#ADCFA6',
        rise: {
            min: 10,
            max: 20,
        },
        occupancy: {
            min: 20,
            max: 25
        },
        name: 7
    },
    {
        color: '#7DD688',
        rise: {
            min: 20,
            max: 30,
        },
        occupancy: {
            min: 25,
            max: 30
        },
        name: 8
    },
    {
        color: '#4CAC59',
        rise: {
            min: 30,
            max: 65536
        },
        occupancy: {
            min: 30,
            max: 65536
        },
        name: 9
    }
];
var getColor = function(occupancy) {
    var color = null;
    for(var i=0; i<colorConfig.length; i++) {
        var item = colorConfig[i];
        if(occupancy >= item.occupancy.min && occupancy < item.occupancy.max) {
            color = item;
            break;
        }
    }
    return color;
}

var formatData = function(data, sum, filed, width, height) {
    if(!width) {
        width = 900;
    }
    if(!height) {
        height = 450;
    }

    //移除数据为0的元素
    var isZero = -1;
    $.each(data, function(index,item) {
        if(item[filed] == null || item[filed] == 0) {
            isZero = index;
        }
    });    
    
    while(isZero != -1)   {
        data.splice(isZero, 1);
        isZero = -1;
        $.each(data, function(index,item) {
            if(item[filed] == null || item[filed] == 0) {
                isZero = index;
            }
        }); 
    }

    // data = $filter('orderBy')(data, '-' + filed);

    if(data.length <= 3) {//小于三个排列一行
    	$.each(data, function(index,item) {
            item.style.width = (item[filed] / sum[filed]) * width;
            item.style.height = height;
            item.box.width = item.style.width + 2;
        });
    }else if(data.length > 3 && data.length <= 8) {//排列两行
        var middle = 3;
        if(data.length == 4) {
            middle = 2;
        }else if(data.length > 7) {
            middle = 4;
        }           
        var row1 = 0;
        for(var i=0; i<middle; i++) {
            row1 += data[i][filed];
        }
        for(var i=0; i<middle; i++) {
            data[i].style.height = (row1 / sum[filed]) * height;
            data[i].style.width = (data[i][filed] / row1) * width;
            data[i].box.width = data[i].style.width + 2;
        }

        var row2 = 0;
        for(var i=middle; i<data.length; i++) {
            row2 += data[i][filed];
        }
        for(var i=middle; i<data.length; i++) {
            data[i].style.height = height - data[0].style.height;
            data[i].style.width = (data[i][filed] / row2) * width;
            data[i].box.width = data[i].style.width + 2;
        }
    }else if(data.length > 8 && data.length <= 20) {//排列两行
        var middle1 = 4;
        var middle2 = 8;
        if(data.length == 9) {
            middle1 = 3;
            middle2 = 6;
        }else if(data.length == 10) {
            middle1 = 4;
            middle2 = 7;
        }
        var row1 = 0;
        for(var i=0; i<middle1; i++) {
            row1 += data[i][filed];
        }
        for(var i=0; i<middle1; i++) {
            data[i].style.height = (row1 / sum[filed]) * height;
            data[i].style.width = (data[i][filed] / row1) * width;
            data[i].box.width = data[i].style.width + 2;
        }

        var row2 = 0;
        for(var i=middle1; i<middle2; i++) {
            row2 += data[i][filed];
        }
        for(var i=middle1; i<middle2; i++) {
            data[i].style.height = (row2 / sum[filed]) * height;
            data[i].style.width = (data[i][filed] / row2) * width;
            data[i].box.width = data[i].style.width + 2;
        }

        var row3 = 0;
        for(var i=middle2; i<data.length; i++) {
            row3 += data[i][filed];
        }
        for(var i=middle2; i<data.length; i++) {
            data[i].style.height = (row3 / sum[filed]) * height;
            data[i].style.width = (data[i][filed] / row3) * width;
            data[i].box.width = data[i].style.width + 2;
        }
    }

    return data;
}
var formatPhone = function(data) {
    var total = 0;
    $.each(data, function(item) {
        if(item.name != "手机") {
            total = total + item.value;
        }
    });

    $.each(data, function(item) {
        if(item.name == "手机") {
            item.value = total / 3;
        }
    });
}

var viewDetail = function(areaName){
	alert(areaName);
	
}
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



var url = "<%=basePath%>diyreport/testvalue.do";
var list;
$
.ajax({
	type : "POST",
	async : false,
	url : url,
	success : function(data) { //回调函数，result，返回值
		var obj = eval('(' + data + ')');
		list = obj.list;
		 var areaList = [];
		 var areaCache = {};
         var filed = 'sale_num';
         var sum = {
        	        sale_num: 0,
        	        sale_amt: 0
        	    };
         list.map(function(info){
        	 var treeMapOptions = {
        		        title : {
        		            text: '',
        		        },
        		        toolbox: {
        		            show : false,
        		        },
        		        calculable : false,
        		        series : [
        		            {
        		                type:'treemap',
        		                size: ['100%', '100%'],
        		                itemStyle: {
        		                    normal: {
        		                        label: {
        		                            show: true,
        		                            formatter: "{b}"
        		                        },
        		                        borderWidth: 1
        		                    },
        		                    emphasis: {
        		                        label: {
        		                            show: false
        		                        }
        		                    }
        		                },
        		                data: []
        		            }
        		        ]
        		    };
			var shtml="";
			if(!areaCache[info.areaId]) {
                areaCache[info.areaId] = {
                    area_id: info.areaId,
                    area_name: info.areaName,
                    sale_num: 0,
                    sale_amt: 0,
                    options: treeMapOptions,
                    style: {},
                    box: {},
                }
                areaList.push(areaCache[info.areaId]); 
                debugger;
            }
			console.log(areaCache[info.areaId])
			areaCache[info.areaId].sale_num = parseInt(areaCache[info.areaId].sale_num)+parseInt(info.sale_num);
            areaCache[info.areaId].sale_amt = parseInt(areaCache[info.areaId].sale_amt)+parseInt(info.sale_amt);
			
            var s = {
                    name: info.productName ? info.productName : '未知',
                    value: parseInt(info[filed]),
                    hisenseValue: info.hisense_sale_num,
                    data: info
                };
            s.occupancy = s.hisenseValue * 100 / s.value;
            if(!s.occupancy) {
                s.occupancy = 0;
            }
            s.itemStyle = {
                    normal: {
                        color: getColor(s.occupancy).color
                    }
                }
            	
            	  areaCache[info.areaId].options.series[0].data.push(s);
                  formatPhone(areaCache[info.areaId].options.series[0].data);
            	  sum.sale_num = parseInt(sum.sale_num) + parseInt(info.sale_num);
           		  sum.sale_amt = parseInt(sum.sale_amt) + parseInt(info.sale_amt);
		});
         console.log(areaList)
		var result = formatData(areaList,sum,filed);
		$.each(result,function(j,obj){
   		shtml = '<div class="treemap-child"><div style="width:100%;height:100%;background-color:blue;text-align: center;" onclick="viewDetail(\''+obj.area_name+'\')"><a href="javascript:void(0)">'+obj.area_name+''+
   		'</a></div><div style="width:'+obj.style.width+'px;height:'+obj.style.height+'px;" id="'+obj.area_id+'"></div></div>';
   		$("#treeMap").append(shtml);
   		var chartContainer = document.getElementById(obj.area_id);  
        //加载图表  
        var myChart = echarts.init(chartContainer);  
        myChart.setOption(obj.options);  
		});
		
	}
});


}); 
</script> 
</head> 
<body> 
<div style="width: 100%;height: 100%;">
<div>
	<p id="test" style="font-size: medium;">17年1月全品类销售热力图</p>
</div>
<table border="0" height="10%" align="left" cellpadding="0" cellspacing="0"> 
<tr> 
<td width="80px" height="30px;" class="tab" id="tab1">综合</td> 
<td width="80px" height="30px;" class="tab" id="tab2">线上</td> 
<td width="80px" height="30px;" class="tab" id="tab3">线下</td> 
</tr> 
</table>
<table border="0" width="100%" height="30%" align="left" cellpadding="0" cellspacing="0" id="basicInfo" style="margin-top: 20px;">
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
 <div style="float: left;width: 100%;height: 100%;border-right: solid 1px;">
<div class="treemap-box" id="treeMap">

</div>
</div> 
<div  style="float: left;width: 0%;height: 100%;">
<table style="width: 100%;height: 100%;" cellpadding="0" cellspacing="0">
<tr>
	<td>区域:</td><td><input type="text" id="area"/></td>
</tr><tr>
	<td>品名:</td><td><input type="text" id="product"/></td>
</tr><tr>
	<td>市场销额(万):</td><td><input type="text" id="area"/></td>
</tr><tr>
	<td>同比:</td><td><input type="text" id="area"/></td>
</tr><tr>
	<td>环比:</td><td><input type="text" id="area"/></td>
</tr><tr>
	<td>占有率:</td><td><input type="text" id="area"/></td>
</tr><tr>
	<td>同比(百分点):</td><td><input type="text" id="area"/></td>
</tr><tr>
	<td>环比(百分点):</td><td><input type="text" id="area"/></td>
</tr>
</table>
</div>
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