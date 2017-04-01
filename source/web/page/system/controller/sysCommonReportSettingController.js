/**
 * 系统菜单相关的controller
 */
infopowerWebApp.controller('sysCommonReportSettingCtrl', ['$scope','$http','$controller','$routeParams',
    function($scope,$http,$controller,$routeParams) {
        var parentCtrl = $controller('baseCtrl', {$scope: $scope,$http:$http});
        //菜单查询
        $scope.menuid = $routeParams.id;
        
        $("head").append("<link id='multiselectCss' rel='stylesheet' type='text/css' href='js/bootstrapMultiselect/bootstrap-multiselect.css' />");
        $("head").append("<script id='multiselectJs' type='text/javascript' src='js/bootstrapMultiselect/bootstrap-multiselect.js'></script>");
        //获取报表属性
    	$scope.menuAttributes =[];
    	$scope.getApi('sysDic/getSysDic.do',{dicCode:"report_menu_type"},function(data){
	        for (var i in data.list) {
	        	var x_id=data.list[i].dicValue;
	        	var obj;
	        	if (x_id=='0'){
	        		obj={
			                name: $scope.i18n('page_reportMan_catalog'),
			                id:x_id		        				
	        		};
		        	$scope.menuAttributes.push(obj);
	        	}
	        	if (x_id=='1'){
	        		obj={
			                name: $scope.i18n('page_role_detail_reportform'),
			                id:x_id		        				
	        		};
		        	$scope.menuAttributes.push(obj);
	        	}
	        }
    	});
      //获取报表状态
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
        
        $scope.getMenuList = function(){
        	var p = $scope.currentPage ? $scope.currentPage : 1;
        	if (!$scope.search) {
        		$scope.search = new Object();
			}
        	//$scope.queryData('sysMenu/getSysMenuListByPage.do',p,$scope.search);
        	$scope.queryData('sysUserPersonalizedSetting/getReportSettingListByUserId.do?settingType=01',p,$scope.search);
        };
        
        $scope.editBtn = function(){
        	try{
        		eval(arguments[0]+'("'+arguments[1]+'")');
        	}catch(e){}
        };

    	//菜单查询
        function getMenuListBtn(isLoged){
			var p = $scope.currentPage ? $scope.currentPage : 1;
			if (!$scope.search) {
				$scope.search = new Object();
			}
			$scope.queryData('sysUserPersonalizedSetting/getReportSettingListByUserId.do?settingType=01&menuId='+$scope.menuid,p,$scope.search);
        }

        //确认默认报表设置
        $scope.confirmCommonReportSetting = function(menuId){
			$('#btnConfirmCommonSettingReport').one('click',function(){
				//alert("btnConfirmCommonSettingReport is clicked!")
				$scope.postApi('sysUserPersonalizedSetting/confirmSettingReport.do',{menuId:$scope.menuid,confirmmenuId:menuId,settingType:'01'},function(result){
					$('#settingModal').modal('hide');
					$('#hintInfo').html($scope.i18n("page_savesuccess"));
					$('#successModal').modal('show');
					$('#btnSave').click(function(){
						$scope.getMenuList();
						$scope.createLeftMenu();
					});
				});
			});
        };
		//取消默认报表设置
		$scope.cancelCommonReportSetting = function(menuId){
			/*$('#btnCancelCommonConfirm').click(function(){
				$scope.postApi('sysUserPersonalizedSetting/cancelSettingReport.do',{menuId:$scope.menuid,cancelmenuId:menuId,settingType:'01'},function(result){
					$scope.getMenuList();
				});
				$('#cancelModal').modal('hide');
			});*/
			$('#btnCancelCommonConfirm').one('click',function(){
				$scope.postApi('sysUserPersonalizedSetting/cancelSettingReport.do',{menuId:$scope.menuid,cancelmenuId:menuId,settingType:'01'},function(result){
					$('#cancelModal').modal('hide');
					$('#hintInfo').html($scope.i18n("page_savesuccess"));
					$('#successModal').modal('show');
					$('#btnSave').click(function(){
						$scope.getMenuList();
						$scope.createLeftMenu();
					});
				});
			});
		};
        //菜单详情
        $scope.getMenuDetail = function(menuId){
        	$('#detailModal1').modal('show');
        	$("#menuReportParam").hide();
        	$scope.sysMenu = _.findWhere($scope.datas,{id:menuId});
        	var sysMenu = $scope.sysMenu;
        	
        	var paramHtml="";
        	
        	if (sysMenu.menuStatus == "1") {
        		$("#menuStatus").val($.i18n.prop('page_user_new_enable'));
			}
        	if (sysMenu.menuStatus == "0") {
        		$("#menuStatus").val($.i18n.prop('page_user_new_disable'));
			}
        	if (sysMenu.menuAttribute == "1") {
        		$("#menuReportParam").show();
				$("#reportParam").html("");
        		$("#menuAttribute").val($.i18n.prop('page_role_detail_reportform'));
        		$scope.getApi('sysMenu/getMenuDetailByMenuId.do',{menuId:menuId},function(data){
        			$("#menuReportTool").val(data.reportTool.reportToolName+""+data.reportTool.reportToolVersion);
        			paramHtml+="<tr>";
        			paramHtml+="<th>"+$scope.i18n('page_reportparameter_parametername')+"</th>";
        			paramHtml+="<th>"+$scope.i18n('page_reportparameter_parameterkey')+"</th>";
        			paramHtml+="</tr>";
        			if (data.reportParams.length > 0) {
            			for ( var i in data.reportParams) {
            				paramHtml+="<tr><td>"+data.reportParams[i].paramName+"</td><td>"+data.reportParams[i].paramKey+"</td></tr>";
    					}
        				$("#reportParam").html(paramHtml);
					}else{
						$("#menuReportParam").hide();
					}
        		});
        		
			}
        	if (sysMenu.menuAttribute == "0") {
        		$("#menuAttribute").val($.i18n.prop('page_reportMan_catalog'));
        		$("#reportParam").html(paramHtml);
			}
    		if (sysMenu.menuParentId) {
    			$scope.getApi('sysMenu/getSysMenu4MenuParentId.do',{menuParentId:sysMenu.menuParentId},function(data){
    				$("#menuParentCode").val(data.sysMenuList[0].code);
    				$("#menuParentName").val(data.sysMenuList[0].menuName);
    			});
			}
        };
      //获取上级报表
        function getSysMenuParent(){
        	$scope.getApi('sysMenu/getSysMenuList.do',{menuType:"02",menuAttribute:"0",menuStatus:"1"},function(datas){
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
        }
      //获取报表工具
        $scope.menuReportTools = [];
        	$scope.getApi('reportTool/getReportToolList.do',{},function(datas){
        		for (var i = 0; i < datas.result.length; i++) {
        			var obj = {
			                name: datas.result[i].reportToolName + " " + datas.result[i].reportToolVersion,
			                id:datas.result[i].id
					};
        			$scope.menuReportTools.push(obj);
        		}
        	});
        
        //获取报表参数
        function getReportMenuParams(){
        	$scope.getApi('sysMenu/getReportMenuParamListByMenuId.do',{},function(data){
        		var reportMenuParams = [];
        		for (var i = 0; i < data.reportParamList.length; i++) {
        			var obj = {
        					lable:data.reportParamList[i].id,
			                value: data.reportParamList[i].paramName+" "+data.reportParamList[i].paramKey,
			                id:data.reportParamList[i].id
					};
        			reportMenuParams.push(obj);
        		}
        		$('#reportMenuParams').multiselect({
        			maxHeight: 200,
        			buttonWidth: '100%',
        			checkboxName:'positionBox',
        			nonSelectedText:$.i18n.prop('page_log_select')
        		});
        		$('#reportMenuParams').multiselect('dataprovider',reportMenuParams);
        		var style = $('.dropdown-toggle.btn-default').attr("style");
        		$('.dropdown-toggle.btn-default').attr({style:style+"text-align: left;"}); 
        	});
        }
        //获取已选报表参数
        function getReportMenuParamsByMenuId(menuId){
        	$scope.getApi('sysMenu/getReportMenuParamListByMenuId.do',{menuId:menuId},function(data){
        		var reportMenuParams = [];
        		for ( var i = 0;i< data.reportParamList.length;i++) {
        			var obj = {
        					lable:data.reportParamList[i].id,
        					value: data.reportParamList[i].paramName+" "+data.reportParamList[i].paramKey,
        					id:data.reportParamList[i].id
        			};
					for ( var j in data.reportMenuParamList) {
						if (data.reportMenuParamList[j].paramId == data.reportParamList[i].id) {
							obj.selected = true;
							break;
						}
					}
					reportMenuParams.push(obj);
				}
        		$('#reportMenuParams').multiselect({
        			maxHeight: 200,
        			buttonWidth: '100%',
        			checkboxName:'positionBox',
        			nonSelectedText:$.i18n.prop('page_log_select')
        		});
        		$('#reportMenuParams').multiselect('dataprovider',reportMenuParams);
        		var style = $('.dropdown-toggle.btn-default').attr("style");
        		$('.dropdown-toggle.btn-default').attr({style:style+"text-align: left;"}); 
        	});
        }
        
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

		$("#adminModal").on("hide.bs.modal", function(e) {
	        $("#menu").data('bootstrapValidator').resetForm(true);
		});  
        //新增菜单
        $scope.addMenu = function(){
        	$("#newTitle").attr({style:"display: inline;"}); 
        	$("#editTitle").attr({style:"display: none;"}); 
        	
        	$('#adminModal').modal('show');
        	$("#selInput").val("");
        	$scope.sysMenu= {menuAttribute:$scope.menuAttributes[0].id,menuStatus:$scope.menuStatus[0].id};
        	getSysMenuParent();

        	$(".menu-report").hide();
    		$(".menuproperty>select").change(function() {
    			var menupropertyval = $scope.sysMenu.menuAttribute;
    			//报表
    			if (menupropertyval == "1") {
    				$(".menu-report").show();
    				getReportMenuParams();
    				$scope.sysMenu.menuReportTool=$scope.menuReportTools[0].id;
    				} else {
    				$(".menu-report").hide();
    			}
    		});
        };

        //编辑菜单
        $scope.editMenu = function(menuId){
        	$("#newTitle").attr({style:"display: none;"}); 
        	$("#editTitle").attr({style:"display: inline;"}); 
        	$('#settingModal').modal('show');
        	$("#selInput").val("");
        	$scope.sysMenu = _.findWhere($scope.datas,{id:menuId});
        	var sysMenu = $scope.sysMenu;
        	
        	getSysMenuParent();

    		if (sysMenu.menuParentId) {
    			$scope.getApi('sysMenu/getSysMenu4MenuParentId.do',{menuParentId:sysMenu.menuParentId},function(data){
    				$("#selInput").val(data.sysMenuList[0].menuName);
    			});
			}
    		
        	var menupropertyval = sysMenu.menuAttribute;
        	if (menupropertyval == "1") {
        		$(".menu-report").show();
        		getReportMenuParamsByMenuId(menuId);
        	}else{
        		$(".menu-report").hide();
        	}
    		$(".menuproperty>select").change(function() {
    			var menupropertyval = $scope.sysMenu.menuAttribute;
    			if (menupropertyval == "1") {
    				getReportMenuParamsByMenuId(menuId);
    				$(".menu-report").show();
    				} else {
    				$(".menu-report").hide();
    			}

    		});
    		
    		$("#menuName").val($scope.sysMenu.menuName);
        	$("#menuOrder").val($scope.sysMenu.menuOrder);
        	$("#menuNameZh").val($scope.sysMenu.menuNameZh);
        	$("#menuNameEn").val($scope.sysMenu.menuNameEn);
        };

        //加载菜单列表
        $scope.getMenuList();

     
    }
]);