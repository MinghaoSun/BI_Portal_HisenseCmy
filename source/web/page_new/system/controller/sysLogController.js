/**
 * 系统用户相关的controller
 */
infopowerWebApp.controller('sysLogCtrl', ['$scope','$http','$controller','$routeParams','$stateParams',
    function($scope,$http,$controller,$routeParams,$stateParams) {
	
        var parentCtrl = $controller('baseCtrl', {$scope: $scope,$http:$http});
        $scope.operTypes =[];
    	$scope.getApi('sysDic/getSysDic.do',{dicCode:"sys_oper_type"},function(data){
	        for (var i in data.list) {
	        	debugger;
	        	var x_id=data.list[i].dicValue;
	        	var obj;
	        	if (x_id=='01'){
	        		obj={
			                name: $scope.i18n('page_login_btn_login'),
			                id:x_id		        				
	        		};
	        	}
	        	if (x_id=='02'){
	        		obj={
			                name: $scope.i18n('page_index_sign'),
			                id:x_id		        				
	        		};
	        	}
	        	if (x_id=='03'){
	        		obj={
			                name: $scope.i18n('sys_title_dlg_new'),
			                id:x_id		        				
	        		};
	        	}
	        	if (x_id=='04'){
	        		obj={
			                name: $scope.i18n('page_delete'),
			                id:x_id		        				
	        		};
	        	}
	        	if (x_id=='05'){
	        		obj={
			                name: $scope.i18n('sys_title_dlg_edit'),
			                id:x_id		        				
	        		};
	        	}
	        	if (x_id=='06'){
	        		obj={
			                name: $scope.i18n('page_index_query'),
			                id:x_id		        				
	        		};
	        	}
	        	$scope.operTypes.push(obj);
	        }
	    });
        $scope.reload = function(){
        	
        	var p = $scope.currentPage ? $scope.currentPage : 1;
        	
        	$scope.queryData('sysLog/getSysLogListByPage.do',p,$scope.search);
        };
             
        $scope.editBtn = function(){
        	try{
        		eval(arguments[0]+'("'+arguments[1]+'")')
        	}catch(e){}
        }
       
        $scope.reload();
    }
]);