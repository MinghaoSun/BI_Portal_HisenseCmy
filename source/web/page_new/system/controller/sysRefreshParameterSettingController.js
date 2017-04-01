/**
 * 报表参数js/controller
 */


infopowerWebApp.controller('sysRefreshParameterSettingCtrl', ['$scope', '$http', '$controller', '$routeParams','$stateParams',
    function ($scope, $http, $controller, $routeParams,$stateParams) {

        //$scope.paramid = $routeParams.id;
        $scope.paramid = $stateParams.id;
        $controller('baseCtrl', {$scope: $scope, $http: $http});
        $("head").append("<link id='multiselectCss' rel='stylesheet' type='text/css' href='js/bootstrapMultiselect/bootstrap-multiselect.css' />");
        $("head").append("<script id='multiselectJs' type='text/javascript' src='js/bootstrapMultiselect/bootstrap-multiselect.js'></script>");

        $scope.reload = function (isLoged) {

            var p = $scope.currentPage ? $scope.currentPage : 1; //页码

            //第一次加载table
            $scope.queryData('sysUserPersonalizedSetting/getReportParamListByPage.do?menuId=' + $scope.paramid + '&isLoged=' + isLoged, p, $scope.search);
        };

        $("#adminModal").on("hide.bs.modal", function () {

            $("#reportParamForm").data('bootstrapValidator').resetForm(true);
        });

        $scope.editBtn = function () {
            try {
                eval(arguments[0] + '("' + arguments[1] + '")')
            } catch (e) {
            }
        }

        function showTitle() {
            $("#hAdd").hide();
            $("#hEdit").hide();
            $("#hAdd").show();
        }

        //新增保存方法
        function addReportParam() {
            var isValid = true;
            var reportParam = $scope.reportParam;
            console.log("reportParam", reportParam);

            /*var reportMenuParams = [];
            $('input[name="positionBox"]:checked').each(function (i, box) {
                //alert($(box).data('id'))
                reportMenuParams.push($(box).data('id'));
            });
            //alert($("#singleSelectId  option:selected").text())
            reportMenuParams.push($("#singleSelectId  option:selected").val());
            reportParam.refreshValue = reportMenuParams.join(',');*/
            if (selectRefreshParamArray.length>1){
                reportParam.refreshValue = selectRefreshParamArray.join(',');
            }else{
                reportParam.refreshValue = selectRefreshParamArray[0];
            }

            console.log("sysUserParamSetting", reportParam);
            var interfaceName = 'sysUserPersonalizedSetting/confirmRefreshParamSetting.do';

            $scope.addData(isValid, interfaceName, reportParam, $('#adminModal'), function (data, status) {
                //$('#hintInfo').html("保存成功！");
                $('#adminModal').modal('hide');
                $('#hintInfo').html($scope.i18n("page_savesuccess"));
                $('#successModal').modal('show');
                $('#successBtn').click(function () {
                    $scope.reload();
                });
            }, function (data, status) {

            });
        }

        function updateReportParam(reportParamId) {
            $("#hAdd").hide();
            $("#hEdit").hide();
            $("#hEdit").show();

            $scope.reportParam = _.findWhere($scope.datas, {id: reportParamId});

            /***刷新参数设置begin***/
            var reportRefreshParam = $scope.reportParam;
            var tagContent = '';
            var tagContentTemp = '';
            //var tagBegin = '';
            //var tagEnd = '</div></div></div>';
            var paramValuesSplitTemp = '';
            var refreshValuesTemp = '';
            if (reportRefreshParam.paramOperatType == '0') {
                if (reportRefreshParam.paramValueRelate != null && reportRefreshParam.paramValueRelate != '') {
                    paramValuesSplitTemp = reportRefreshParam.paramValueRelate.split(',');
                    refreshValuesTemp = reportRefreshParam.refreshValue.split(',');
                    //tagContentTemp = '<label>' + reportRefreshParam.paramName + '</label>';
                    tagContentTemp = '<label>' + $.i18n.prop('page_reportparameter_refreshparametervalues') + '</label>';
                    tagContentTemp += '<select id="singleSelectId" onchange="onSingleSelChangeForRefreshParam(this)" class="form-control">';
                    tagContentTemp += '<option>选择</option>';
                    for (var x in paramValuesSplitTemp) {
                        var paramValuesSplitTemp2 = paramValuesSplitTemp[x].split(':');
                        //tagContentTemp += '<option value="' + paramValuesSplitTemp2[0] + '"' + '>' + paramValuesSplitTemp2[0] + ' ' + paramValuesSplitTemp2[1] + '</option>'
                        var isEqflag=false;
                        for (var y in refreshValuesTemp) {
                            if (refreshValuesTemp[y]==paramValuesSplitTemp2[0]){
                                isEqflag=true;
                                break;
                            }
                        }
                        if (isEqflag){
                            tagContentTemp += '<option selected="true" value="' + paramValuesSplitTemp2[0] + '"' + '>' + paramValuesSplitTemp2[0] + ' ' + paramValuesSplitTemp2[1] + '</option>'
                        }else{
                            tagContentTemp += '<option value="' + paramValuesSplitTemp2[0] + '"' + '>' + paramValuesSplitTemp2[0] + ' ' + paramValuesSplitTemp2[1] + '</option>'
                        }
                    }
                    tagContentTemp += '</select>';
                    tagContent = tagContentTemp;
                } else {
                    paramValuesSplitTemp = reportRefreshParam.paramValue.split(',');
                    refreshValuesTemp = reportRefreshParam.refreshValue.split(',');

                    //tagContentTemp = '<label>' + reportRefreshParam.paramName + '</label>';
                    tagContentTemp = '<label>' + $.i18n.prop('page_reportparameter_refreshparametervalues') + '</label>';
                    tagContentTemp += '<select  id="singleSelectId"  onchange="onSingleSelChangeForRefreshParam(this)" class="form-control">';
                    tagContentTemp += '<option>选择</option>';
                    for (var x in paramValuesSplitTemp) {
                        //tagContentTemp += '<option value="' + paramValuesSplitTemp[x] + '"' + '>' + paramValuesSplitTemp[x] + '</option>'
                        var isEqflag=false;
                        for (var y in refreshValuesTemp) {
                            if (refreshValuesTemp[y]==paramValuesSplitTemp2[x]){
                                isEqflag=true;
                                break;
                            }
                        }
                        if (isEqflag){
                            tagContentTemp += '<option selected="true" value="' + paramValuesSplitTemp[x] + '"' + '>' + paramValuesSplitTemp[x] + '</option>'
                        }else{
                            tagContentTemp += '<option value="' + paramValuesSplitTemp[x] + '"' + '>' + paramValuesSplitTemp[x] + '</option>'
                        }
                    }
                    tagContentTemp += '</select>';
                    //tagContent = tagBegin + tagContentTemp + tagEnd;
                    tagContent = tagContentTemp;
                }
                $("#specialRowId .form-group").empty();
                $("#specialRowId .form-group").append(tagContent);
            } else if (reportRefreshParam.paramOperatType == '1') {
                if (reportRefreshParam.paramValueRelate != null && reportRefreshParam.paramValueRelate != '') {
                    paramValuesSplitTemp = reportRefreshParam.paramValueRelate.split(',');
                    refreshValuesTemp = reportRefreshParam.refreshValue.split(',');

                    //tagContentTemp = '<label>' + reportRefreshParam.paramName + '</label>';
                    tagContentTemp = '<input type="hidden" ng-model="reportParam.refreshValue"/>';
                    tagContentTemp += '<label>' + $.i18n.prop('page_reportparameter_refreshparametervalues') + '</label>';
                    tagContentTemp += '<select class="form-control SlectBox" multiple="multiple" id="reportMenuParams' + reportRefreshParam.id + '"></select>';
                    //tagContent = tagBegin + tagContentTemp + tagEnd;
                    tagContent = tagContentTemp;
                    $("#specialRowId .form-group").empty();
                    $("#specialRowId .form-group").append(tagContent);

                    var reportMenuParams = [];
                    for (var x in paramValuesSplitTemp) {
                        var paramValuesSplitTemp2 = paramValuesSplitTemp[x].split(':');
                        var obj = {
                            lable: paramValuesSplitTemp2[0],
                            value: paramValuesSplitTemp2[0] + ' ' + paramValuesSplitTemp2[1],
                            id: paramValuesSplitTemp2[0]
                        };
                        for ( var j in refreshValuesTemp) {
                            if (refreshValuesTemp[j] == paramValuesSplitTemp2[0]) {
                                obj.selected = true;
                                break;
                            }
                        }
                        reportMenuParams.push(obj);
                    }
                    $('#reportMenuParams' + reportRefreshParam.id).multiselect({
                        maxHeight: 200,
                        buttonWidth: '100%',
                        checkboxName: 'positionBox',
                        nonSelectedText: $.i18n.prop('page_log_select'),
                        onChange: function (element, checked) {
                            var selValueTemp = $(element).data('id');
                            if (checked) {
                                if (selectRefreshParamArray.length>0) {
                                    if ($.inArray(selValueTemp, selectRefreshParamArray) < 0) {
                                        selectRefreshParamArray.push(selValueTemp);
                                    }
                                } else {
                                    selectRefreshParamArray.push($(element).data('id'));
                                }
                            } else {
                                selectRefreshParamArray.splice($.inArray(selValueTemp,selectRefreshParamArray),1);
                            }
                        }
                    });
                    $('#reportMenuParams' + reportRefreshParam.id).multiselect('dataprovider', reportMenuParams);
                    var style = $('.dropdown-toggle.btn-default').attr("style");
                    $('.dropdown-toggle.btn-default').attr({style: style + "text-align: left;"});
                } else {
                    paramValuesSplitTemp = reportRefreshParam.paramValueRelate.split(',');
                    refreshValuesTemp = reportRefreshParam.refreshValue.split(',');
                    //tagContentTemp = '<label>' + reportRefreshParam.paramName + '</label>';
                    tagContentTemp = '<input type="hidden" ng-model="reportParam.refreshValue"/>';
                    tagContentTemp += '<label>' + $.i18n.prop('page_reportparameter_refreshparametervalues') + '</label>';
                    tagContentTemp += '<select class="form-control SlectBox" multiple="multiple" id="reportMenuParams' + reportRefreshParam.id + '"></select>';
                    //tagContent = tagBegin + tagContentTemp + tagEnd;
                    tagContent = tagContentTemp;
                    $("#reportParamForm .form-group").empty();
                    $("#reportParamForm .form-group").append(tagContent);

                    var reportMenuParams = [];
                    for (var x in paramValuesSplitTemp) {
                        var obj = {
                            lable: paramValuesSplitTemp[x],
                            value: paramValuesSplitTemp[x],
                            id: paramValuesSplitTemp[x]
                        };
                        for ( var j in refreshValuesTemp) {
                            if (refreshValuesTemp[j] == paramValuesSplitTemp[x]) {
                                obj.selected = true;
                                break;
                            }
                        }
                        reportMenuParams.push(obj);
                    }
                    $('#reportMenuParams' + reportRefreshParam.id).multiselect({
                        maxHeight: 200,
                        buttonWidth: '100%',
                        checkboxName: 'positionBox',
                        nonSelectedText: $.i18n.prop('page_log_select'),
                        onChange: function (element, checked) {
                            var selValueTemp = $(element).data('id');
                            if (checked) {
                                if (selectRefreshParamArray.length>0) {
                                    if ($.inArray(selValueTemp, selectRefreshParamArray) < 0) {
                                        selectRefreshParamArray.push(selValueTemp);
                                    }
                                } else {
                                    selectRefreshParamArray.push($(element).data('id'));
                                }
                            } else {
                                selectRefreshParamArray.splice($.inArray(selValueTemp,selectRefreshParamArray),1);
                            }
                        }
                    });
                    $('#reportMenuParams' + reportRefreshParam.id).multiselect('dataprovider', reportMenuParams);
                    var style = $('.dropdown-toggle.btn-default').attr("style");
                    $('.dropdown-toggle.btn-default').attr({style: style + "text-align: left;"});
                }
            }
            /***刷新参数设置end***/
            $("#specialRowId button").css("background-color", "white");
            $("#paramName").val($scope.reportParam.paramName);
            $("#paramOrder").val($scope.reportParam.paramOrder);
            $("#paramKey").val($scope.reportParam.paramKey);
        }

        function deleteReportParam() {
            var id = $scope.delId;

            $scope.postApi('reportParam/deleteReportParamById.do?menuId=' + $scope.paramid, {id: id}, function (data) {
                $scope.reload();
                $('#deleteModal').modal('hide');
            });
        }

        //弹出删除确认对话框
        function showDelDiv(id) {
            $scope.delId = id;
        }

        $scope.reload();
    }
]);