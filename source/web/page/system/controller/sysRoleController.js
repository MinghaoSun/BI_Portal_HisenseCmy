/**
 * 系统用户相关的controller
 */
infopowerWebApp.controller('sysRoleCtrl', ['$scope', '$http', '$controller', '$routeParams',
    function ($scope, $http, $controller, $routeParams) {
        var parentCtrl = $controller('baseCtrl', {$scope: $scope, $http: $http});
        $scope.menuid = $routeParams.id;
        $scope.editBtn = function () {
            try {
                eval(arguments[0] + '("' + arguments[1] + '")');
            } catch (e) {
            }
        };
        $scope.editBtn2 = function () {
            try {
                eval(arguments[0] + '("' + arguments[1] + '","'+arguments[2]+'")');
            } catch (e) {
            }
        };

        //获取角色状态
        $scope.roleStatus = [];
        $scope.getApi('sysDic/getSysDic.do', {dicCode: "sys_oper_stauts"}, function (data) {
            for (var i in data.list) {
                var x_id = data.list[i].dicValue;
                var obj;
                if (x_id == '0') {
                    obj = {
                        name: $scope.i18n('page_user_new_disable'),
                        id: x_id
                    };
                }
                if (x_id == '1') {
                    obj = {
                        name: $scope.i18n('page_user_new_enable'),
                        id: x_id
                    };
                }
                $scope.roleStatus.push(obj);
            }

        });

        //获取角色类型
        $scope.roleTypes = [];
        $scope.paramOperatTypes = [];
        $scope.paramValueTypes = [];
        $scope.getApi('sysDic/getSysDic.do', {dicCode: "sys_role_type"}, function (data) {
            for (var i in data.list) {
                var x_id = data.list[i].dicValue;
                var obj;
                if (x_id == '1') {
                    obj = {
                        name: $scope.i18n('page_user_newrole_functionrole'),
                        id: x_id
                    };
                }
                if (x_id == '3') {
                    obj = {
                        name: $scope.i18n('page_user_newrole_datarole'),
                        id: x_id
                    };
                }
                $scope.roleTypes.push(obj);
            }
        });
        $scope.getApi('sysDic/getSysDic.do', {dicCode: "sys_param_operat_type"}, function (data) {
            for (var i in data.list) {
                var x_id = data.list[i].dicValue;
                var obj;
                if (x_id == '0') {
                    obj = {
                        name: $scope.i18n('page_param_radio'),
                        id: x_id
                    };
                }
                if (x_id == '1') {
                    obj = {
                        name: $scope.i18n('page_param_multiselect'),
                        id: x_id
                    };
                }
                $scope.paramOperatTypes.push(obj);
            }
        });
        $scope.getApi('sysDic/getSysDic.do', {dicCode: "sys_param_value_type"}, function (data) {
            for (var i in data.list) {
                var x_id = data.list[i].dicValue;
                var obj;
                if (x_id == '0') {
                    obj = {
                        name: $scope.i18n('page_param_singlevalue'),
                        id: x_id
                    };
                }
                if (x_id == '1') {
                    obj = {
                        name: $scope.i18n('page_param_multivalue'),
                        id: x_id
                    };
                }
                $scope.paramValueTypes.push(obj);
            }
        });

        $scope.paramValues = [];
        $scope.getApi('sysDic/getSysDic.do', {dicCode: "object_param"}, function (data) {
            for (var i in data.list) {
                var x_id = data.list[i].dicValue;
                var obj;
                if (x_id == 'SYS_USER_LOGIN_NAME') {
                    obj = {
                        name: $scope.i18n('page_role_object_param_user'),
                        id: x_id
                    };
                }
                $scope.paramValues.push(obj);
            }

        });

        //初始加载
        $scope.reload = function () {
            var p = $scope.currentPage ? $scope.currentPage : 1;
            $('#adminModal').modal('hide');
            $('#roleModal').modal('hide');
            $('#roleModal1').modal('hide');
            $scope.queryData('sysRole/getSysRoleListByPage.do', p, $scope.search);
        };

        function reloadBtn(isLoged) {
            var p = $scope.currentPage ? $scope.currentPage : 1;
            $scope.queryData('sysRole/getSysRoleListByPage.do?menuId=' + $scope.menuid + '&isLoged=' + isLoged, p, $scope.search);
        }

        //切换角色类型
        function changeRoleType() {
            //alert('changeRoleType')
            var roleId = $scope.sysRole.id;
            var roleClassval = $scope.sysRole.roleType;
            if (roleClassval == "1") {
                $("#function-role").show();
                $("#data-role").hide();
                $scope.sysRole.roleMenuName = "";
                $scope.getApi('sysMenu/getSysMenuList.do', {menuStatus: "1"}, function (datas) {
                    var menuName = [];
                    for (var i = 0; i < datas.sysMenuList.length; i++) {
                        var obj = {
                            id: datas.sysMenuList[i].id,
                            pId: datas.sysMenuList[i].menuParentId,
                            name: datas.sysMenuList[i].menuName,
                            checked: false
                        };
                        menuName.push(obj);
                    }
                    $.fn.zTree.init($("#rolemenuNameTree"), {
                        data: {
                            simpleData: {
                                enable: true,
                                idKey: "id",
                                pIdKey: "pId",
                                rootPId: 0
                            }
                        },
                        check: {
                            enable: true
                        },
                        view: {
                            showIcon: false,
                            dblClickExpand: false
                        },
                        callback: {
                            onCheck: function (e, treeId, treeNode) {
                                var zTree = $.fn.zTree.getZTreeObj("rolemenuNameTree"),
                                    nodes = zTree.getCheckedNodes(true),
                                    ids = "",
                                    v = "";
                                for (var i = 0, l = nodes.length; i < l; i++) {
                                    v += nodes[i].name + ",";
                                    ids += nodes[i].id + ",";
                                }
                                if (v.length > 0) {
                                    v = v.substring(0, v.length - 1);
                                    ids = ids.substring(0, ids.length - 1);
                                }
                                ;
                                $("#rolemenuName").val(v);
                                $("#rolemenuName").attr("title", v);
                                $scope.sysRole.sysRoleMenuIds = ids;
                            }
                        }
                    }, menuName);

                    for (var i = 0; i < datas.sysMenuList.length; i++) {
                        var zTree = $.fn.zTree.getZTreeObj("rolemenuNameTree");
                        var node = zTree.getNodeByParam("id", datas.sysMenuList[i].id);
                        node.checked = false;
                        zTree.checkNode(node, false, false);
                    }
                    var rolemenuName = "";
                    if (roleId) {
                        var zTree = $.fn.zTree.getZTreeObj("rolemenuNameTree");
                        $scope.getApi('sysRoleMenu/getSysRoleMenu4RoleId.do', {sysRoleId: roleId}, function (data) {
                            zTree.checkAllNodes(false);
                            for (var i = 0; i < datas.sysMenuList.length; i++) {
                                var node = zTree.getNodeByParam("id", datas.sysMenuList[i].id);
                                zTree.checkNode(node, false, false);
                                node.checked = false;
                                for (var j = 0; j < data.length; j++) {
                                    if (datas.sysMenuList[i].id == data[j].menuId) {
                                        zTree.checkNode(node, true, true);
                                        if (rolemenuName == "") {
                                            rolemenuName = datas.sysMenuList[i].menuName;
                                        }
                                        else {
                                            rolemenuName += "," + datas.sysMenuList[i].menuName;
                                        }
                                    }

                                }
                            }
                            $("#rolemenuName").val(rolemenuName);
                        });


                    }
                });
            } else {

                $("#function-role").hide();
                $("#data-role").show();
                $scope.getApi('sysMenu/getReportMenuParamListByMenuId.do', {}, function (datas) {
                    $scope.reportParams = datas.reportParamList;
                    if (roleId) {
                        $scope.getApi('sysRoleParam/getSysRoleParam4RoleId.do', {sysRoleId: roleId}, function (data) {

                            for (var i = 0; i < $scope.reportParams.length; i++) {
                                $scope.reportParams[i].paramValue = "";
                                for (var j = 0; j < data.length; j++) {
                                    if ($scope.reportParams[i].id == data[j].paramId) {
                                        if (data[j].paramValue) {
                                            $scope.reportParams[i].paramValue = data[j].paramValue;
                                            for (var k = 0; k < $scope.paramValues.length; k++) {
                                                if ($scope.reportParams[i].paramValue == $scope.paramValues[k].id) {
                                                    $scope.reportParams[i].paramValue1 = $scope.reportParams[i].paramValue;
                                                    $("#" + $scope.reportParams[i].id + "id").prev().attr("readonly", true);
                                                }
                                            }
                                        }
                                        $scope.reportParams[i].paramValueRelate = data[j].paramValueRelate;
                                        $scope.reportParams[i].paramOperatType = data[j].paramOperatType;
                                        $scope.reportParams[i].paramValueType = data[j].paramValueType;
                                    }
                                }
                            }
                        });
                    }
                });
            }
        }

        $("#adminModal").on("hide.bs.modal", function (e) {
            $("#role").data('bootstrapValidator').resetForm(true);
        });

        //新增角色
        function addSysRole() {
            //alert(111);
            $("#newTitle").attr({style: "display: inline;"});
            $("#editTitle").attr({style: "display: none;"});
            $scope.sysRole = "";
            $scope.sysRole = {roleStatus: $scope.roleStatus[0].id, roleType: $scope.roleTypes[0].id};
            $("#roleclass").change(changeRoleType);
            changeRoleType();

        }

        //更新角色
        function updateSysRole(roleId) {
            $("#newTitle").attr({style: "display: none;"});
            $("#editTitle").attr({style: "display: inline;"});
            $scope.sysRole = _.findWhere($scope.datas, {id: roleId});
            //alert('paramOperatType:'+$scope.sysRole.paramOperatType)
            changeRoleType();
            $("#roleclass").change(changeRoleType);
            $("#roleName").val($scope.sysRole.roleName);
            $("#roleNameZh").val($scope.sysRole.roleNameZh);
            $("#roleNameEn").val($scope.sysRole.roleNameEn);
        }

        function changeParamValueByRelateData(reportParamId,relateDataType) {
            $('#relateDataModal').modal('show');
            var index = reportParamId.split(",")[1];
            reportParamId = reportParamId.split(",")[0];
            $('#paramSelectId').attr('value',reportParamId);
            $.ajax({
                url: 'sysRole/getRelateData.do?type='+relateDataType,
                async: false,
                type: "GET",
                dataType: "json",
                success: function (dataResult) {
                    console.log(dataResult)
                    var obj = eval(dataResult);
                    var cells = 3,
                        rows =100,
                        i, j, row,
                        columns = [],
                        data = [];

                    columns.push({
                        field: 'field0',
                        checkbox: true
                    });
                    columns.push({
                        field: 'field1',
                        title: '编号'
                    });
                    columns.push({
                        field: 'field2',
                        title: '名称'
                    });

                    var paramRelateValue=obj.data;
                    for(var i in paramRelateValue){
                        row = {};
                        row['field1'] = paramRelateValue[i].code;
                        row['field2'] = paramRelateValue[i].name;
                        data.push(row);
                    }
                    /**/
                    $('#table').bootstrapTable('destroy').bootstrapTable({
                        columns: columns,
                        data: data,
                        pageNumber: 1, //初始化加载第一页，默认第一页
                        pageSize: 5, //每页的记录行数（*）
                        pageList: [5, 10, 25, 50, 100],
                        cache: true
                    });
                }
            });
        }

        function changeParamValue(reportParamId) {
            var index = reportParamId.split(",")[1];
            reportParamId = reportParamId.split(",")[0];

            var docSelect = $("#" + reportParamId);
            changeValue(docSelect, $scope.reportParams[index]);
        }

        //保存角色
        function saveSysRole() {
            if ($('#role').data('bootstrapValidator').validate().isValid()) {
                var sysRole = $scope.sysRole;
                var roleType = sysRole.roleType;
                var sysRoleParams = new Array();
                if (roleType == 3) {
                    var reportParams = $scope.reportParams;
                    console.log("reportParams:"+reportParams)
                    for (var i in reportParams) {
                        if (reportParams[i].paramValue || reportParams[i].paramValue1) {
                            var sysRoleParam = new Object();
                            sysRoleParam.paramId = reportParams[i].id;
                            sysRoleParam.paramValue = reportParams[i].paramValue;
                            sysRoleParam.paramType = "0";
                            sysRoleParam.paramOperatType =reportParams[i].paramOperatType;
                            sysRoleParam.paramValueType = reportParams[i].paramValueType;
                            sysRoleParam.paramValueRelate = reportParams[i].paramValueRelate;
                            if (reportParams[i].paramValue1) {
                                sysRoleParam.paramValue = reportParams[i].paramValue1;
                            }
                            for (var j = 0; j < $scope.paramValues.length; j++) {
                                if (sysRoleParam.paramValue == $scope.paramValues[j].id) {
                                    sysRoleParam.paramType = "1";
                                }
                            }
                            sysRoleParams.push(sysRoleParam);
                        }
                    }
                    if (sysRoleParams.length > 0) {
                        sysRole.sysRoleParams = angular.toJson(sysRoleParams);
                    }
                }
                if (roleType == 1) {
                }
                var interfaceName = 'sysRole/addSysRole.do?menuId=' + $scope.menuid;
                if (sysRole.id) {
                    interfaceName = 'sysRole/updateSysRole.do?menuId=' + $scope.menuid;

                    $scope.addData(true, interfaceName, sysRole, $('#adminModal'), function (data, status) {
                        $('#adminModal').modal('hide');
                        $('#hintInfo').html($scope.i18n("page_savesuccess"));
                        $('#successModal').modal('show');
                        $('#successBtn').click(function () {
                            $scope.reload();
                        });
                    }, function (data, status) {

                    });
                }
                else {
                    $scope.getApi('sysRole/getOnlySysRole.do', {roleNameZh: sysRole.roleNameZh}, function (data) {
                        if (data && data.code == 0) {
                            $('#tipModal').modal('show');
                        } else if (data && data.code == 1) {

                            $scope.addData(true, interfaceName, sysRole, $('#adminModal'), function (data, status) {
                                $('#adminModal').modal('hide');
                                $('#hintInfo').html($scope.i18n("page_savesuccess"));
                                $('#successModal').modal('show');
                                $('#successBtn').click(function () {
                                    $scope.reload();
                                });
                            }, function (data, status) {

                            });
                        }
                    });
                }

            } else {
                $(this).removeAttr('data-target');
            }
        }

        //删除角色
        function deleteSysRole(roleId) {
            $('#btnConfirm').click(function () {
                $scope.postApi('sysRole/deleteSysRole.do', {menuId: $scope.menuid, id: roleId}, function (data) {
                    $scope.reload();
                });
                $('#deleteModal').modal('hide');
            });
        }

        //功能角色权限管理
        function manageSysRoleMenus(roleId) {
            $scope.roleMenu = {roleId: roleId};
            $scope.getApi('sysMenu/getSysMenuList.do', {menuStatus: "1"}, function (datas) {
                $scope.getApi('sysRoleMenu/getSysRoleMenu4RoleId.do', {sysRoleId: roleId}, function (data) {
                    var zNodes = [];
                    for (var i = 0; i < datas.sysMenuList.length; i++) {
                        var obj = {
                            id: datas.sysMenuList[i].id,
                            pId: datas.sysMenuList[i].menuParentId,
                            name: datas.sysMenuList[i].menuName
                        };
                        for (var j = 0; j < data.length; j++) {
                            if (datas.sysMenuList[i].id == data[j].menuId) {
                                obj.checked = true;
                            }
                        }
                        zNodes.push(obj);
                    }
                    $.fn.zTree.init($("#functionroleTree"), {
                        data: {
                            simpleData: {
                                enable: true,
                                idKey: "id",
                                pIdKey: "pId",
                                rootPId: 0
                            }
                        },
                        check: {
                            enable: true
                        },
                        view: {
                            showIcon: false
                        }
                    }, zNodes);

                });
            });
        }

        //保存功能角色
        function saveSysRoleMenu(roleId) {
            var sysRole = new Object();
            var zTreeObj = $.fn.zTree.getZTreeObj("functionroleTree");
            var selectedNodes = zTreeObj.getCheckedNodes(true);
            var selectedIds = [];
            $.each(selectedNodes, function (i, el) {
                selectedIds.push(el.id);
            });
            if (selectedIds.length > 0) {
                sysRole.sysRoleMenuIds = angular.toJson(selectedIds);
            }
            sysRole.id = roleId;

            $scope.addData(true, 'sysRoleMenu/updateSysRoleMenu4RoleId.do', sysRole, $('#roleModal'), function (data, status) {
                $('#roleModal').modal('hide');
                $('#hintInfo').html($scope.i18n("page_savesuccess"));
                $('#successModal').modal('show');
                $('#successBtn').click(function () {
                    $scope.reload();
                });
            }, function (data, status) {

            });

        };

        //数据角色权限管理
        function manageSysRoleParams(sysRoleId) {
            $scope.getApi('sysMenu/getReportMenuParamListByMenuId.do', {}, function (datas) {
                $scope.reportParams = datas.reportParamList;
                $scope.reportParams.roleId = sysRoleId;
                $scope.getApi('sysRoleParam/getSysRoleParam4RoleId.do', {sysRoleId: sysRoleId}, function (data) {
                    for (var i = 0; i < $scope.reportParams.length; i++) {
                        for (var j = 0; j < data.length; j++) {
                            if ($scope.reportParams[i].id == data[j].paramId) {
                                if (data[j].paramValue) {
                                    $scope.reportParams[i].paramValue = data[j].paramValue;
                                    for (var k = 0; k < $scope.paramValues.length; k++) {
                                        if ($scope.reportParams[i].paramValue == $scope.paramValues[k].id) {
                                            $scope.reportParams[i].paramValue1 = $scope.reportParams[i].paramValue;
                                            $("#" + $scope.reportParams[i].id + "id").prev().attr("readonly", true);
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
            });
        }

        function changeValue(docSelect, reportParam) {
            var paramValue = docSelect.val();
            paramValue = paramValue.replace("string:", "");
            var docInput = docSelect.prev();
            docInput.val(paramValue);
            if (docSelect.val()) {
                docInput.attr("readonly", true);
            } else {
                docInput.val("");
                reportParam.paramValue = ""
                docInput.attr("readonly", false);
            }
        }

        function changeParamValue1(reportParamId) {
            var index = reportParamId.split(",")[1];
            reportParamId = reportParamId.split(",")[0];
            var docSelect = $("#" + reportParamId + "id");
            changeValue(docSelect, $scope.reportParams[index]);
        }


        //保存数据角色
        function saveSysRoleParam(roleId) {
            $scope.paramValues.length;
            var sysRole = new Object();
            var reportParams = $scope.reportParams;
            var sysRoleParams = new Array();
            for (var i = 0; i < reportParams.length; i++) {
                if (reportParams[i].paramValue || reportParams[i].paramValue1) {
                    var sysRoleParam = new Object();
                    sysRoleParam.paramId = reportParams[i].id;
                    sysRoleParam.paramValue = reportParams[i].paramValue;
                    sysRoleParam.paramType = "0";
                    if (reportParams[i].paramValue1) {
                        sysRoleParam.paramValue = reportParams[i].paramValue1;
                    }
                    for (var j = 0; j < $scope.paramValues.length; j++) {
                        if (sysRoleParam.paramValue == $scope.paramValues[j].id) {
                            sysRoleParam.paramType = "1";
                        }
                    }
                    sysRoleParams.push(sysRoleParam);
                }
            }
            if (sysRoleParams.length > 0) {
                sysRole.sysRoleParams = angular.toJson(sysRoleParams);
            }

            sysRole.id = roleId;
            $scope.addData(true, 'sysRoleParam/updateSysRoleParam4RoleId.do', sysRole, $('#roleModal1'), function (data, status) {
                $('#roleModal1').modal('hide');
                $('#hintInfo').html($scope.i18n("page_savesuccess"));
                $('#successModal').modal('show');
                $('#successBtn').click(function () {
                    $scope.reload();
                });
            }, function (data, status) {

            });

        };

        $scope.reload();
    }
]);
