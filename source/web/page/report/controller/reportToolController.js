/**
 * 报表工具js/controller
 */

infopowerWebApp.controller('reportToolCtrl', ['$scope','$http','$controller','$routeParams',
    function($scope,$http,$controller,$routeParams) {
		
		//获取全局变量菜单ID
		$scope.menuid = $routeParams.id;
		
        $controller('baseCtrl', {$scope: $scope,$http:$http});
        $scope.reload = function(isLoged){
        	
        	var p = $scope.currentPage ? $scope.currentPage : 1; //页码
        	//第一次加载table
        	$scope.queryData('reportTool/getReportToolListByPage.do?menuId='+ $scope.menuid+'&isLoged='+isLoged,p,$scope.search);
        	
        };
        
        //列表页下拉框绑定
        var dicCode='report_tool_category';
		var objTypesList=getDicType(dicCode);
		$scope.reportToolTypesList=objTypesList;
		
		//报表工具
		$scope.reportToolTypes =[];
        $scope.getApi('sysDic/getSysDic.do',{dicCode:"report_tool_category"},function(data){
	        for (var i in data.list) {
 	        	var x_id=data.list[i].dicValue;
 	        	var obj;
 	        	if (x_id == '01'){
 	        		obj={
 	 	 	                name: $scope.i18n('page_reporttool_bo'),
 	 	 	                id:x_id
 	        		};
 	        		$scope.reportToolTypes.push(obj);
 	        	}else if (x_id == '02'){
 	        		obj={
	 	 	                name: $scope.i18n('page_reporttool_tb'),
	 	 	                id:x_id
 	        		};
 	        		$scope.reportToolTypes.push(obj);
 	        	}else if (x_id == '03'){
 	        		obj={
	 	 	                name: $scope.i18n('page_reporttool_qv'),
	 	 	                id:x_id
 	        		};
 	        		$scope.reportToolTypes.push(obj);
 	        	}
	        }
    	});	
    	
        //展现方式
    	$scope.reportToolDisplays =[];
    	$scope.getApi('sysDic/getSysDic.do',{dicCode:"report_display_mode"},function(data){
    		for (var i in data.list) {
 	        	var x_id=data.list[i].dicValue;
 	        	var obj;
 	        	if (x_id == '0'){
 	        		obj={
 	 	 	                name: $scope.i18n('page_reporttool_tab'),
 	 	 	                id:x_id
 	        		};
 	        		$scope.reportToolDisplays.push(obj);
 	        	}else{
 	        		obj={
	 	 	                name: $scope.i18n('page_reporttool_window'),
	 	 	                id:x_id
 	        		};
 	        		$scope.reportToolDisplays.push(obj);
 	        	}
 	        }
     	});	

    	//给弹出窗绑定消息事件，清除表单数据
        $("#adminModal").on("hide.bs.modal", function() {  
            $("#reportToolForm").data('bootstrapValidator').resetForm(true);
    	});
        
        $scope.editBtn = function(){
        	try{
        		eval(arguments[0]+'("'+arguments[1]+'")');
        	}catch(e){}
        }
        
        //***新增/编辑事件
        function addReportTool(){
        	if ($('#reportToolForm').data('bootstrapValidator').validate().isValid()) {
        		var isValid=true;
       
        		var reportTool = $scope.reportTool;
				
				var interfaceName='reportTool/addReportTool.do?menuId='+ $scope.menuid; //新增
				//编辑 
				if(reportTool.id){
					interfaceName='reportTool/updateReportTool.do?menuId='+ $scope.menuid;
					
					$scope.addData(isValid,interfaceName,reportTool,$('#adminModal'),function(data,status){
						$('#adminModal').modal('hide');
						$('#hintInfo').html($scope.i18n("page_savesuccess"));
						$('#successModal').modal('show');		
						$('#successBtn').click(function(){
							$scope.reload();
						});
					},function(data,status){

					});
				}
				else{
					$scope.getApi('reportTool/getOnlyReportToolType.do',{reportToolType:reportTool.reportToolType,reportToolVersion:reportTool.reportToolVersion},function(data){
	    				if(data && data.code==0){
	    					$('#tipModal').modal('show');		
	    				}else if(data && data.code==1){
							$scope.addData(isValid,interfaceName,reportTool,$('#adminModal'),function(data,status){
								$('#adminModal').modal('hide');
								$('#hintInfo').html($scope.i18n("page_savesuccess"));
								$('#successModal').modal('show');		
								$('#successBtn').click(function(){
									$scope.reload();
								});
							},function(data,status){
							});
	    				}
					});
				}
        	}
        }
        
        //多级联动**下拉框change事件
        function changeType(type){
        	var configKey = $scope.reportTool.reportToolType;
        	
        	if(configKey=="01")configKey="BO_report_version";
        	else if(configKey=="02")configKey="Tableau_report_version";
        	else if(configKey=="03")configKey="QV_report_version";
        	
        	var objVersions=[];
        	$scope.getApi('reportTool/getConfigByKey.do',{configKey:configKey},function(data){
        		
        		for (var i in data.list) {
        			var objversion={
		                label: data.list[i].value,
		                name: data.list[i].value,
		                id:data.list[i].key
		            };
		        	objVersions.push(objversion);
		        }
        		$scope.reportToolVersions = objVersions;
        		
        		if(type==0){
        			$scope.reportTool.reportToolVersion=$scope.reportTool.reportToolVersion;
        		}
        		else{
        			$scope.reportTool.reportToolVersion=$scope.reportToolVersions[0].id;
        		}
    		});
        }
        
        //弹出编辑窗口事件
        function updateReportTool(reportToolId){
        	$("#hAdd").hide();
        	$("#hEdit").hide();
        	$("#hEdit").show();
        	
        	$("#Type").change(changeType);
        	
        	$scope.reportTool = _.findWhere($scope.datas,{id:reportToolId});
        	changeType(0);

        	$("#toolAddress").val($scope.reportTool.reportToolUrl);
        	$("#toolName").val($scope.reportTool.reportToolName);
        	$("#toolAuthAddress").val($scope.reportTool.reportToolAuthUrl);
        }
        
        //弹出新增窗口事件
        function showReportTool(){
        	
        	$('#adminModal').modal();
        	
        	$("#Type").change(changeType);
        	
        	$("#hAdd").hide();
        	$("#hEdit").hide();
        	$("#hAdd").show();
        	
        	$scope.reportTool={reportToolType:$scope.reportToolTypes[0].id,reportToolDisplay:$scope.reportToolDisplays[0].id};
        	
        	changeType(1);
        }
        
        //获取下拉框的值
        function getDicType(dicCode){
        	var dicObj=[{label:$scope.i18n('page_log_select'),name:$scope.i18n('page_log_select'),id:""}];
   
        	$scope.getApi('sysDic/getSysDic.do',{dicCode:dicCode},function(data){
        		
		        for (var i in data.list) {
	 	        	var x_id=data.list[i].dicValue;
	 	        	var obj;
	 	        	if (x_id == '01'){
	 	        		obj={
	 	 	 	                name: $scope.i18n('page_reporttool_bo'),
	 	 	 	                id:x_id
	 	        		};
	 	        		dicObj.push(obj);
	 	        	}else if (x_id == '02'){
	 	        		obj={
		 	 	                name: $scope.i18n('page_reporttool_tb'),
		 	 	                id:x_id
	 	        		};
	 	        		dicObj.push(obj);
	 	        	}else if (x_id == '03'){
	 	        		obj={
		 	 	                name: $scope.i18n('page_reporttool_qv'),
		 	 	                id:x_id
	 	        		};
	 	        		dicObj.push(obj);
	 	        	}
		        }
		        //设默认值
		        $scope.search ={reportToolType:$scope.reportToolTypesList[0].id};
        	});	
        	return dicObj;
        }

        //删除操作
        function deleteReportTool(){
        	var id=$scope.delId;
        	
    		$scope.postApi('reportTool/deleteReportToolById.do?menuId='+ $scope.menuid,{id:id},function(data){
    			$scope.reload();
    			$('#deleteModal').modal('hide');
    		});
        }
         
        //弹出删除确认对话框
        function showDelDiv(id){
        	$scope.delId = id;
        }
        
        $scope.reload();
    }
]);