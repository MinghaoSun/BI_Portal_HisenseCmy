/**
 * 报表业务展现JS/controller
 */
infopowerWebApp.controller('sysOtherCtrl', ['$scope','$http','$controller','$routeParams',
    function($scope,$http,$controller,$routeParams) {
        $controller('baseCtrl', {$scope: $scope,$http:$http});
        $scope.menuid = $routeParams.id;
        
        $scope.reload = function(){
			$('#main-header').hide();
			$('#main-footer').hide();
			var h = parseInt(document.body.clientHeight) + 90;
			//判断是横向，还是纵向
			if ($('#main-sidebar').length>0){
				$('#main-sidebar').hide();
				$(".content-wrapper").css({"margin-left":0,"padding-top":0});
				h = h-50;
			}
        	$('#sysOtherContent').height(h);
        	$scope.getApi('sysMenu/getOtherMenuPage.do',{menuId:$scope.menuid},function(datas){
        		$('#sysOtherContent').attr("src",datas.reqLocation);
        	});
        };
        $scope.reload();
	}
]);