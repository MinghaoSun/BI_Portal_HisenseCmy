/**
 * 系统菜单相关的controller
 */
infopowerWebApp.controller('sysMenuCtrl', ['$scope','$http','$controller','$routeParams',
    function($scope,$http,$controller,$routeParams) {
        var parentCtrl = $controller('baseCtrl', {$scope: $scope,$http:$http});
      //获取菜单类型
        $scope.menuid = $routeParams.id;
        $scope.menuTypes =[];
    	$scope.getApi('sysDic/getSysDic.do',{dicCode:"sys_menu_type"},function(data){
	        for (var i in data.list) {
	        	var x_id=data.list[i].dicValue;
	        	var obj;
	        	if (x_id=='01'){
	        		obj={
			                name: $scope.i18n('page_index_menu_sysManagement'),
			                id:x_id		        				
	        		};
		        	$scope.menuTypes.push(obj);
	        	}
	        	if (x_id=='03'){
	        		obj={
			                name: $scope.i18n('page_index_menu_otherSystem'),
			                id:x_id		        				
	        		};
		        	$scope.menuTypes.push(obj);
	        	}
				if (x_id=='04'){
					obj={
						name: $scope.i18n('page_index_menu_personalizedSettings'),
						id:x_id
					};
					$scope.menuTypes.push(obj);
				}
	        }
	    });

        //获取菜单状态
        $scope.menuStatus =[];
    	$scope.getApi('sysDic/getSysDic.do',{dicCode:'sys_oper_stauts'},function(data){
	        for (var i in data.list) {
	        	var x_id=data.list[i].dicValue;
	        	var obj;
	        	if (x_id=='0'){
	        		obj={
			                name: $scope.i18n('page_user_new_disable'),
			                id:x_id		        				
	        		};
		        	$scope.menuStatus.push(obj);
	        	}
	        	if (x_id=='1'){
	        		obj={
			                name: $scope.i18n('page_user_new_enable'),
			                id:x_id		        				
	        		};
		        	$scope.menuStatus.push(obj);
	        	}
	        }
    	});

        $scope.editBtn = function(){
        	try{
        		eval(arguments[0]+'("'+arguments[1]+'")');
        	}catch(e){}
        };

    	//菜单查询
        $scope.getMenuList = function(){
        	var p = $scope.currentPage ? $scope.currentPage : 1;
        	if (!$scope.search) {
        		$scope.search = new Object();
			}
        	$scope.queryData('sysMenu/getSysMenuListByPage.do',p,$scope.search);
        };
        
        function getMenuListBtn(isLoged){
        	var p = $scope.currentPage ? $scope.currentPage : 1;
        	if (!$scope.search) {
        		$scope.search = new Object();
			}
        	$scope.queryData('sysMenu/getSysMenuListByPage.do?menuId='+ $scope.menuid+'&isLoged='+isLoged,p,$scope.search);
        }
        
        //获取上级菜单
    	$scope.getApi('sysMenu/getSysMenuList.do',{notMenuType:"02",menuStatus:"1"},function(datas){
    		var selNodes = [];
    		for (var i = 0; i < datas.sysMenuList.length; i++) {
				var obj = {
						id: datas.sysMenuList[i].id,
						pId:datas.sysMenuList[i].menuParentId,
						name:datas.sysMenuList[i].menuName,
						nocheck: true
				};
				selNodes.push(obj);
			}
    		$.fn.zTree.init($("#menuselTree"), {
    			data: {
    				simpleData: {
    					enable: true,
    					idKey: "id",
    					pIdKey: "pId",
    					rootPId: 0
    				}
    			},
    			check: {
    				enable: true,
    				chkStyle: "radio",
    				radioType: "all"
    			},
    			view: {
    				showIcon: false,
    				dblClickExpand: false
    			},
    			callback: {
    				onClick: function(e, treeId, treeNode) {
    					var zTree = $.fn.zTree.getZTreeObj("menuselTree"),
    					nodes = zTree.getSelectedNodes(true),
    					v = "",
    					id = "";
    					for (var i = 0, l = nodes.length; i < l; i++) {
    						v += nodes[i].name + ",";
    						id += nodes[i].id + ",";
    					}
    					if (v.length > 0) {
    						v = v.substring(0, v.length - 1);
    						id = id.substring(0, id.length - 1);
    					};
    					$("#selInput").val(v);
    					$scope.sysMenu.menuParentId = id;
    				}
    			}
    		}, selNodes);
    	});

		$("#adminModal").on("hide.bs.modal", function(e) {
	        $("#menu").data('bootstrapValidator').resetForm(true);
		});  

        //菜单删除
        $scope.deleteMenu = function(menuId){
        	$('#btnConfirm').click(function(){
        		$scope.postApi('sysMenu/deleteSysMenu.do',{menuId:$scope.menuid,deletemenuId:menuId},function(result){
        			$scope.getMenuList();
        		});
        		$('#deleteModal').modal('hide');
        	});
        };
        
        //菜单详情
        $scope.getMenuDetail = function(menuId){
        	$scope.sysMenu = _.findWhere($scope.datas,{id:menuId});
        	var sysMenu = $scope.sysMenu;

        	if (sysMenu.menuType == "01") {
        		$("#menuType").val($.i18n.prop('page_index_menu_sysManagement'));
			}
        	if (sysMenu.menuType == "03") {
        		$("#menuType").val($.i18n.prop('page_index_menu_otherSystem'));
			}
        	if (sysMenu.menuStatus == "1") {
        		$("#menuStatus").val($.i18n.prop('page_user_new_enable'));
			}
        	if (sysMenu.menuStatus == "0") {
        		$("#menuStatus").val($.i18n.prop('page_user_new_disable'));
			}
    		if (sysMenu.menuParentId) {
    			$scope.getApi('sysMenu/getSysMenu4MenuParentId.do',{menuParentId:sysMenu.menuParentId},function(data){
    				$("#menuParentCode").val(data.sysMenuList[0].code);
    				$("#menuParentName").val(data.sysMenuList[0].menuName);
    			});
			}
        };
    	//弹出树形下拉选择框
		$("#selInput").on("click", function(e) {
			e.stopPropagation();
			$("#menuselDiv").slideToggle();
		});
		//隐藏树形下拉选择框
		$("body").on("click", function() {
			$("#menuselDiv").slideUp();
		});
		$("#menuselDiv").on("click", function(e) {
			e.stopPropagation();
		});

        //新增菜单
        $scope.addMenu = function(){
        	$("#newTitle").attr({style:"display: inline;"}); 
        	$("#editTitle").attr({style:"display: none;"}); 
        	
        	$('#adminModal').modal('show');
        	$("#selInput").val("");
        	$scope.isEdit = false;
        	$scope.sysMenu= {menuType:$scope.menuTypes[0].id,menuStatus:$scope.menuStatus[0].id};
        };
        
    	//保存操作
		$('#menupreservebtn').click(function() {
			debugger;
			if ($('#menu').data('bootstrapValidator').validate().isValid()) {
				var isValid=true;
				var sysMenu = $scope.sysMenu;
				var interfaceName='sysMenu/saveSysMenu.do?menuId='+ $scope.menuid;
				if (sysMenu.id) {
					interfaceName='sysMenu/updateSysMenu.do?menuId='+ $scope.menuid;
					
					$scope.addData(isValid,interfaceName,sysMenu,$('#adminModal'),function(data,status){
						$('#adminModal').modal('hide');
						$('#hintInfo').html($.i18n.prop('page_savesuccess'));
						$('#successModal').modal('show');		
						$('#btnSave').click(function(){
							$scope.getMenuList();
						});
					},function(data,status){

					});
				}
				else{
					$scope.getApi('sysMenu/getOnlySysMenu.do',{menuNameZh:sysMenu.menuNameZh},function(data){
	    				if(data && data.code==0){
	    					$('#tipModal').modal('show');		
	    				}else if(data && data.code==1){
							$scope.addData(isValid,interfaceName,sysMenu,$('#adminModal'),function(data,status){
								$('#adminModal').modal('hide');
								$('#hintInfo').html($.i18n.prop('page_savesuccess'));
								$('#successModal').modal('show');		
								$('#btnSave').click(function(){
									$scope.getMenuList();
								});
							},function(data,status){
							});
	    				}
					});
				}
			} else {
				$(this).removeAttr('data-target');
			}
		});
        
        //编辑菜单
        $scope.editMenu = function(menuId){
        	$("#newTitle").attr({style:"display: none;"}); 
        	$("#editTitle").attr({style:"display: inline;"}); 
        	$('#adminModal').modal('show');
        	$("#selInput").val("");
        	
    		$scope.sysMenu = _.findWhere($scope.datas,{id:menuId});
        	var sysMenu = $scope.sysMenu;
        	$scope.isEdit=true;
        	
    		if (sysMenu.menuParentId) {
    			$scope.getApi('sysMenu/getSysMenu4MenuParentId.do',{menuParentId:sysMenu.menuParentId},function(data){
    				$("#selInput").val(data.sysMenuList[0].menuName);
    			});
			}
    		
    		$("#menuName").val($scope.sysMenu.menuName);
        	$("#menuOrder").val($scope.sysMenu.menuOrder);
        	$("#menuNameZh").val($scope.sysMenu.menuNameZh);
        	$("#menuNameEn").val($scope.sysMenu.menuNameEn);
        };
     
        //加载菜单列表
        $scope.getMenuList();
     
    }
]);