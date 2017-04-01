/**
 * 系统用户相关的controller
 */
infopowerWebApp.controller('sysUserCtrl', ['$scope','$http','$controller','$routeParams',
    function($scope,$http,$controller,$routeParams) {
        var parentCtrl = $controller('baseCtrl', {$scope: $scope,$http:$http});
        $scope.userid = $routeParams.id;
        
        $('#multiselectCss').remove();
        $('#multiselectJs').remove();
        $('.btn-group').remove();
        $('#undo_redo').attr({style:"display: inline;"});
        $scope.reload = function(){
        	var p = $scope.currentPage ? $scope.currentPage : 1;
        	$('#roleModal').modal('hide');
        	$('#adminModal').modal('hide');
        	$scope.queryData('sysUsers/getSysUserListByPage.do',p,$scope.search);
        };
        
        function useradBtn(isLoged){
        	var p = $scope.currentPage ? $scope.currentPage : 1;
        	$scope.queryData('sysUsers/getSysUserListByPage.do?menuId='+ $scope.userid+'&isLoged='+isLoged,p,$scope.search);
        }
        
        $scope.saveSysUser = function(){
        	if ($('#user').data('bootstrapValidator').validate().isValid()) {
        	var isValid=true;
			var sysUser = $scope.user;
			
			var sysUserRoles = new Object();
        	var objResult =[];
        	$("#undo_redo_to option").each(function (i,box) {
        		objResult+= $(this).val()+',';
             });
        	sysUser.sysFunctionRole=objResult;
        	
        	
			var reportUserMappings = new Array();
			var reportTool = $scope.reportTool;
        	var reportUserMappings = new Array();
        	for (var i = 0; i < reportTool.length; i++) {
				if(reportTool[i].reportSysName && reportTool[i].reportSysPassword){
					var reportUserMapping = new Object();
					reportUserMapping.reportToolId=reportTool[i].id;
					reportUserMapping.reportSysName = reportTool[i].reportSysName;
					reportUserMapping.reportSysPassword = reportTool[i].reportSysPassword;
					reportUserMappings.push(reportUserMapping);
				}
			}
        	if(reportUserMappings.length > 0){
        		sysUser.sysUserReportTool = angular.toJson(reportUserMappings);
        	}
	
			var interfaceName = 'sysUsers/saveSysUser.do?menuId='+ $scope.userid;
			if(sysUser.id){
				var oldPassword=$("#oldPassword").val();
				interfaceName = 'sysUsers/updateSysUser.do?menuId='+ $scope.userid+'&oldPassword='+oldPassword;
				$scope.addData(isValid,interfaceName,sysUser,$('#adminModal'),function(data,status){
					$('#adminModal').modal('hide');
					$('#hintInfo').html($.i18n.prop('page_savesuccess'));
					$('#successModal').modal('show');
					$('#btnSave').click(function(){
							$scope.reload();
						});
					},function(data,status){
				
				});
			}else{
				$scope.getApi('sysUsers/getOnlyLoginName.do',{loginName:sysUser.loginName},function(data){
					if(data && data.code==0){
						$('#repeathintInfo').html($.i18n.prop('page_tip_repeat'));
						$('#repeathModal').modal('show');
						$('#repeathbtnSave').click(function(){
						$('#adminModal').modal('hide');
						});
					}else if(data && data.code==1){
						$scope.addData(isValid,interfaceName,sysUser,$('#adminModal'),function(data,status){
							$('#adminModal').modal('hide');
							$('#hintInfo').html($.i18n.prop('page_savesuccess'));
							$('#successModal').modal('show');
							$('#btnSave').click(function(){
									$scope.reload();
								});
							},function(data,status){
						
						});
					}
				});
			}
        }else {
        	$(this).removeAttr('data-target');
		}
	}
        $scope.editBtn = function(){
        	try{
        		eval(arguments[0]+'("'+arguments[1]+'")')
        	}catch(e){}
        }
        
        $( '#adminModal' ).on( 'hide.bs.modal' ,function(e){
        	if(e.target.id!='dpicker'){
        		$("#user").data('bootstrapValidator').resetForm(true);
        	}
		});
        
        function getMenuListBtn(isLoged){
        	var p = $scope.currentPage ? $scope.currentPage : 1;
        	if (!$scope.search) {
        		$scope.search = new Object();
			}
        	$scope.queryData('sysMenu/getSysMenuListByPage.do?menuId='+ $scope.menuid+'&isLoged='+isLoged,p,$scope.search);
        }
       
        function addUserBtn(id){
        	$("#usertitle").attr({style:"display: inline;"});
        	$("#usertitleEdit").attr({style:"display: none;"});
        	$scope.isEdit = false;
        	$scope.sysUserRoles={id:id};
        	$scope.user = {userEnabled:$scope.enabledOptions[0].id,userSex:$scope.userSex[0].id};
		    $scope.Loadsysrole();
		    addgetSysUserRole();
		    $scope.addUserReportTool();
        }
        
        function updateSysUser(userid){
        	$("#usertitle").attr({style:"display: none;"});
        	$("#usertitleEdit").attr({style:"display: inline;"});
        	getUserlogintype();
        	$scope.isEdit = true;
        	$scope.password="";
        	$scope.user = _.findWhere($scope.datas,{id:userid});
        	getDataRoleId(userid);
        	getSysUserRole(userid);
        	$scope.userReportTool(userid);
        	
        	$("#useraccount").val($scope.user.loginName);
        	$("#firstName").val($scope.user.firstName);
        	$("#lastName").val($scope.user.lastName);
        	$("#loginPassword").val($scope.user.loginPassword);
        	$("#password").val($scope.user.loginPassword);
        	$("#userEmail").val($scope.user.userEmail);
        	$("#userMobile").val($scope.user.userMobile);
        	$("#dpicker").val($scope.user.userBirthday);
        	$("#oldPassword").val($scope.user.loginPassword);
        }
        
        //用户详情
        $scope.getUserDetail = function(userid){
        	$scope.user = _.findWhere($scope.datas,{id:userid});
        	getDataRoleId(userid);
        	getSysUserRole(userid);
        	$scope.userReportTool(userid);
        }
        //获取当前用户
    	$scope.getApi('sysUsers/getCurUser.do','',function(data){
    		$scope.curUser=data.curUser;
    	});
        //获取用户状态
    	$scope.getApi('sysDic/getSysDic.do',{dicCode:"sys_oper_stauts"},function(data){
    		$scope.enabledOptions =[];
	        for (var i in data.list) {
	        	var x_id=data.list[i].dicValue;
	        	var obj;
	        	if (x_id=='0'){
	        		obj={
			                name: $scope.i18n('page_user_new_disable'),
			                id:x_id		        				
	        		};
		        	$scope.enabledOptions.push(obj);
	        	}
	        	if (x_id=='1'){
	        		obj={
			                name: $scope.i18n('page_user_new_enable'),
			                id:x_id		        				
	        		};
		        	$scope.enabledOptions.push(obj);
	        	}

	        }
	        $scope.user = {userEnabled:$scope.enabledOptions[0].id};
    	});
        
        //获取用户性别
        $scope.getApi('sysDic/getSysDic.do',{dicCode:"sys_user_sex"},function(data){
        		$scope.userSex =[];
		        for (var i in data.list) {
		        	var x_id=data.list[i].dicValue;
		        	var obj;
		        	if (x_id=='0'){
		        		obj={
				                name: $scope.i18n('page_user_sex_female'),
				                id:x_id		        				
		        		};
			        	$scope.userSex.push(obj);
		        	}
		        	if (x_id=='1'){
		        		obj={
				                name: $scope.i18n('page_user_sex_male'),
				                id:x_id		        				
		        		};
			        	$scope.userSex.push(obj);
		        	}
		        }
		        $scope.user=  {userSex:$scope.userSex[0].id};
		        
        	});
        
        //获取用户登录方式
        function getUserlogintype(){
        $scope.getApi('sysUsers/getlogInType.do',{},function(data){
        		if(data.result==1){
        			$("#passwods1").attr({style:"display: none;"});
        			$("#passwods").attr({style:"display: none;"});
        		}
        });
     }
        
        function delUser(id){
        	$('#btnConfirm').click(function(){
        		$scope.postApi('sysUsers/delSysUser.do',{menuId:$scope.userid,id:id},function(data){
        			$scope.reload();
        			$('#deleteModal').modal('hide');
        		});
        	});
        }
       
        //加载数据角色
   	   $scope.Loadsysrole = function(){
   			  $scope.getApi('sysRole/getSysRoleList.do',{roleType:3},function(data){
   				$scope.SysroleDatas=data.result;
   				for(var i=0;i<$scope.SysroleDatas.length;i++){
   				}
   				
   			   });
   		   }
        //加载功能角色
        function addgetSysUserRole(){
    		$('#undo_redo_to').empty();
         	$scope.getApi('sysRole/getSysRoleList.do',{roleType:"1"},function(datas){
        			var array = [];
        			array=datas.result;
        			$scope.roleDatas=array;
        		});
        	}
        
        //加载报表映射数据
        $scope.addUserReportTool=function(){
    		$scope.getApi('reportTool/getReportToolList.do',{},function(datas){
    			$scope.reportTool = datas.result;
    		});
         } 
       
        //数据角色管理
        function getDataRoleId(userid){
        	$scope.getApi('sysUsers/getSysUserList.do',{userId:userid},function(data){
        		$scope.getApi('sysRole/getSysRoleList.do',{roleType:"3"},function(datas){
        			var array = [];
        			for(var i in datas.result){
        				var obj = datas.result[i];
        				if(!_.findWhere(data,{dataRoleId:obj.id})){
        					array.push(obj);
        				}else{
        					array.push(obj);
        				}
        			}
        			$scope.SysroleDatas=array;
        		});
        		
        	});
        	
        }

        //功能角色管理
        function getSysUserRole(userid){
        var FunctionForm = new Object();
        $scope.FunctionForm = FunctionForm;
        $scope.FunctionForm.id = userid;
        $scope.getApi('sysUsers/getSysUserRoleUserId.do',{userId:userid},function(data){
         	$scope.getApi('sysRole/getSysRoleList.do',{roleType:"1"},function(datas){
        			var array = [];
        			var date=[];
        			for(var i in datas.result){
        				var obj = datas.result[i];
        				if(!_.findWhere(data,{roleId:obj.id})){
        					array.push(obj);
        				}else{
        					date.push(obj);
        				}
        			}
        			$scope.roleDatas=array;
        			$scope.UserRolesDatas=date;
        		});
        	});
        }
        //查询用户映射功能
        $scope.userReportTool=function(userid){
		$scope.getApi('reportTool/getReportToolList.do',{},function(datas){
			$scope.reportTool = datas.result;
			if(userid){
				$scope.getApi('sysUsers/getUserReportMappingByReport.do',{userId:userid},function(data){
					for (var i = 0; i < $scope.reportTool.length; i++) {
						$scope.reportTool[i].reportSysName = "";
						$scope.reportTool[i].reportSysPassword = "";
						for (var j = 0; j < data.length; j++) {
							if($scope.reportTool[i].id == data[j].reportToolId){
								if(data[j].reportSysName && data[j].reportSysPassword){
									$scope.reportTool[i].reportSysName = data[j].reportSysName;
									$scope.reportTool[i].reportSysPassword = data[j].reportSysPassword;
								}
							}
						}
					}
				});
			}
		});
     }
       //保存功能角色
        $scope.saveSysFunctionForm=function(id){
	        	var sysUserRoles = new Object();
	        	var objResult =[];
	        	$("#undo_redo3_to option").each(function (i,box) {
	        		objResult+= $(this).val()+',';
	             });
	        	sysUserRoles.roleId=objResult;
	        	sysUserRoles.userId=id;
	        	$scope.addData(true,'sysUsers/saveSysUserRole.do',sysUserRoles,$('#roleModal1'),function(data,status){
	        		$('#roleModal1').modal('hide');
	        		$('#hintInfo').html($scope.i18n("page_savesuccess"));
					$('#successModal').modal('show');
					$('#btnSave').click(function(){
						$scope.reload();
						});
	        		},function(data,status){
	        	});
        }
        
        $scope.saveReportUserMapping=function(userId){	
        	var ReportUserMappings = new Object();
        	var reportTool = $scope.reportTool;
        	var reportUserMappings = new Array();
        	for (var i = 0; i < reportTool.length; i++) {
				if(reportTool[i].reportSysName && reportTool[i].reportSysPassword){
					var reportUserMapping = new Object();
					reportUserMapping.reportToolId=reportTool[i].id;
					reportUserMapping.reportSysName = reportTool[i].reportSysName;
					reportUserMapping.reportSysPassword = reportTool[i].reportSysPassword;
					reportUserMappings.push(reportUserMapping);
				}
			}
        	if(reportUserMappings.length > 0){
        		ReportUserMappings.id = angular.toJson(reportUserMappings);
        	}
        	reportUserMappings.id = userId;
			$scope.addData(true,'sysUsers/saveReportUserMapping.do',ReportUserMappings,'',function(data,status){
				$scope.reload();
			});

        };
        
        $scope.reload();
	}
]);