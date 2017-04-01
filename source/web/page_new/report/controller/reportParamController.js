/**
 * 报表参数js/controller
 */


infopowerWebApp.controller('reportParamCtrl', ['$scope','$http','$controller','$routeParams','$stateParams',
    function($scope,$http,$controller,$routeParams,$stateParams) {
	
	 	//$scope.menuid = $routeParams.id;
	 	$scope.menuid = $stateParams.id;
        $controller('baseCtrl', {$scope: $scope,$http:$http});

		/**/
		//获取角色状态
		$scope.relateDataType = [];
		$scope.getApi('sysDic/getSysDic.do', {dicCode: "sys_param_relate_data"}, function (data) {
			//alert("data:"+data)
			for (var i in data.list) {
				var x_id = data.list[i].dicValue;
				var obj;
				if (x_id == '01') {
					obj = {
						name: $scope.i18n('sys_param_relate_data_company'),
						id: x_id
					};
				}
				if (x_id == '02') {
					obj = {
						name: $scope.i18n('sys_param_relate_data_category'),
						id: x_id
					};
				}
				if (x_id == '03') {
					obj = {
						name: $scope.i18n('sys_param_relate_data_calendar'),
						id: x_id
					};
				}
				$scope.relateDataType.push(obj);
			}

		});
		/**/

        $scope.reload = function(isLoged){
        	
        	var p = $scope.currentPage ? $scope.currentPage : 1; //页码
        	
        	//第一次加载table
        	$scope.queryData('reportParam/getReportParamListByPage.do?menuId='+ $scope.menuid+'&isLoged='+isLoged,p,$scope.search);
        };
        
    	$("#adminModal").on("hide.bs.modal", function() {  
    			
            $("#reportParamForm").data('bootstrapValidator').resetForm(true);
    	});
        
        $scope.editBtn = function(){
        	try{
        		eval(arguments[0]+'("'+arguments[1]+'")')
        	}catch(e){}
        }
        
        function showTitle(){
        	$("#hAdd").hide();
        	$("#hEdit").hide();
        	$("#hAdd").show();
        }
        
        //新增保存方法
        function addReportParam(){
        	
        	//保存操作
			if ($('#reportParamForm').data('bootstrapValidator').validate().isValid()) {
				var isValid=true;
				var reportParam = $scope.reportParam;
					
				var interfaceName='reportParam/addReportParam.do?menuId='+ $scope.menuid;
				
				//编辑 
				if(reportParam.id){
					interfaceName='reportParam/updateReportParam.do?menuId='+ $scope.menuid;
					
					$scope.addData(isValid,interfaceName,reportParam,$('#adminModal'),function(data,status){
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
					$scope.getApi('reportParam/getOnlyReportParamType.do',{reportParamKey:reportParam.paramKey},function(data){
	    				if(data && data.code==0){
	    					$('#tipModal').modal('show');		
	    				}else if(data && data.code==1){
					
							$scope.addData(isValid,interfaceName,reportParam,$('#adminModal'),function(data,status){
								//$('#hintInfo').html("保存成功！");
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
			} else {
				$(this).removeAttr('data-target');
			}
        }
        
        function updateReportParam(reportParamId){
        	$("#hAdd").hide();
        	$("#hEdit").hide();
        	$("#hEdit").show();
        	
        	$scope.reportParam = _.findWhere($scope.datas,{id:reportParamId});
        	
        	$("#paramName").val($scope.reportParam.paramName);
        	$("#paramOrder").val($scope.reportParam.paramOrder);
        	$("#paramKey").val($scope.reportParam.paramKey);
        }
       
        function deleteReportParam(){
        	var id=$scope.delId;

    		$scope.postApi('reportParam/deleteReportParamById.do?menuId='+ $scope.menuid,{id:id},function(data){
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