/**
 * 报表业务展现JS/controller
 */
infopowerWebApp.controller('reportBusinessCtrl', ['$scope', '$http', '$controller', '$routeParams','$route',
    function ($scope, $http, $controller, $routeParams,$route) {
        $controller('baseCtrl', {$scope: $scope, $http: $http});
        $scope.menuid = $routeParams.id;
        $("head").append("<link id='multiselectCss' rel='stylesheet' type='text/css' href='js/bootstrapMultiselect/bootstrap-multiselect.css' />");
        $("head").append("<script id='multiselectJs' type='text/javascript' src='js/bootstrapMultiselect/bootstrap-multiselect.js'></script>");

        $scope.reloadRoute = function() {
            $route.reload();
        }

        $scope.reload = function () {
            var h = parseInt(document.body.clientHeight) + 90;
            //判断是横向，还是纵向
            /*if ($('#main-sidebar').length>0){
             $('#main-sidebar').hide();
             $(".content-wrapper").css({"margin-left":0,"padding-top":0});
             h = h-50;
             }*/

            $('#reportContent').height(h);
            var pageContent = '<div id="load" align="center" style="position: fixed;top: 60%;left: 50%;width:50%;height: 50%;-webkit-transform: translateX(-50%) translateY(-50%);" xmlns="http://www.w3.org/1999/html"><div><img src="/static/img/loadReport/progressbar.gif" ></div><div>loading...</div></div></div>';
            $('#reportContent').html(pageContent);
            $scope.getApi('sysMenu/getReportMenuPage.do', {menuId: $scope.menuid}, function (datas) {
                //alert(datas.reqLocation);
                console.log('reqLocation:' + datas.reqLocation);

                if (datas.reqStatus == "0") {
                    //$('#reportContent').attr("src",datas.reqLocation);
                    pageContent = '<iframe id="reportContent2" name="reportContent2"   src="' + datas.reqLocation + '" frameborder="no" border="0px" width="100%" height="100%"  margin="0px" ></iframe>';
                    $('#reportContent').append(pageContent);
                } else {
                    //$('#reportContent').attr("src","common/error.jsp");
                    //$('#reportContent').html('<iframe id="reportContent2" name="reportContent2"   src="common/error.jsp" frameborder="no" border="0px" width="100%"  margin="0px" ></iframe>');
                    //$('#reportContent').html('<iframe id="reportContent2" name="reportContent2"   src="http://10.138.8.234:8099/DPTZJYKB690/" frameborder="no" border="0px" width="100%" height="100%" margin="0px" ></iframe>');
                    //$('#reportContent').html('<iframe id="reportContent2" name="reportContent2"   src="https://www.baidu.com/" frameborder="no" border="0px" width="100%" height="100%" margin="0px" ></iframe>');
                    //var pageContent='<div id="load" align="center"><img src="/static/img/loadReport/progressbar.gif" /> loading...</div>';
                    //pageContent+='<iframe id="reportContent2" name="reportContent2" style="padding-left: 10px;"  src="https://www.baidu.com/" frameborder="no" border="0px" width="100%" height="100%" margin="0px" ></iframe>';
                    pageContent = '<iframe id="reportContent2" name="reportContent2" style="padding-left: 10px;"  src="common/error.jsp" frameborder="no" border="0px" width="100%" height="100%" margin="0px" ></iframe>';
                    //pageContent+='<iframe id="reportContent2" name="reportContent2" styled"padding-left: 10px;"  frameborder="no" border="0px" width="100%" height="100%" margin="0px" ></iframe>';
                    $('#reportContent').append(pageContent);
                }
                /*进度条*/
                var iframe01 = document.getElementById("reportContent2");
                var load01 = document.getElementById("load");
                iframe01.style.display = "none";
                if (iframe01.attachEvent) {
                    iframe01.attachEvent("onload", function () {
                        load01.style.display = "none";
                        iframe01.style.display = "block";
                    });
                } else {
                    iframe01.onload = function () {
                        load01.style.display = "none";
                        iframe01.style.display = "block";
                    };
                }
            });
            /*全局参数设置新增start*/
            //$scope.queryData('sysUserPersonalizedSetting/getReportParamListByPage.do?menuId='+ $scope.menuid+'&isLoged='+isLoged,p,$scope.search);
            $scope.getApi('sysUserPersonalizedSetting/getGlobalParamOfUserList.do', {menuId: $scope.menuid}, function (datas) {
                var resultDatas = eval(datas);
                var paramResultDatas = resultDatas.result;
                if (paramResultDatas == null || paramResultDatas == "") {
                    $("#demo").html('<div class="modal-header"><h5 class="modal-title" style="text-align: center">全局参数设置</h5></div><hr><div class="list-group" style="text-align: center; line-height: 500px; font-size: 16px;opacity: 0.8">没有刷新参数!</div>');
                    $('#demo').BootSideMenu({
                        side: "right", // left or right
                        autoClose: true // auto close when page loads
                    });
                } else {
                    $('#demo').BootSideMenu({
                        side: "right", // left or right
                        autoClose: true // auto close when page loads
                    });
                    var tagContent = '';
                    var tagContentTemp = '';
                    var tagBegin = '<div class="form-group" style="text-align: center">';
                    var tagEnd = '</div>';

                    //$("#demo .list-group").empty();
                    $("#demo .form-body").empty();
                    /*初始化刷新参数值*/
                    for (var i in paramResultDatas) {
                        if(paramResultDatas[i].refreshValue!=null&&paramResultDatas[i].refreshValue.trim()!='') {
                            if (paramResultDatas[i].paramOperatType == '0') {         //单选
                                selectRefreshParamMap[paramResultDatas[i].id] = paramResultDatas[i].refreshValue;
                            } else if (paramResultDatas[i].paramOperatType == '1') {  //多选
                                selectRefreshParamMap[paramResultDatas[i].id] = paramResultDatas[i].refreshValue.split(',');
                            }
                        }
                    }
                    for (var i in paramResultDatas) {
                        var paramValuesSplitTemp = '';
                        var refreshValuesTemp = '';
                        paramValuesSplitTemp = paramResultDatas[i].paramValueRelate.split(',');
                        refreshValuesTemp = paramResultDatas[i].refreshValue.split(',');
                        if (paramResultDatas[i].paramOperatType == '0') {
                            if (paramResultDatas[i].paramValueRelate != null && paramResultDatas[i].paramValueRelate != '') {
                                tagContentTemp = '<label style="padding-right: 5px" class="col-md-3 control-label">' + paramResultDatas[i].paramName + '</label>';
                                //tagContentTemp+='<select id="'+paramResultDatas[i].id+'" class="form-control" onchange="onChangeForGlobal(this)">';
                                tagContentTemp += '<div class="col-md-9" style="padding-left: 5px;padding-right: 25px"><select id="' + paramResultDatas[i].id + '" class="form-control" onchange="onSingleSelChangeForGlobal(this)">';
                                tagContentTemp += '<option value="">选择</option>';
                                for (var x in paramValuesSplitTemp) {
                                    var paramValuesSplitTemp2 = paramValuesSplitTemp[x].split(':');
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
                                tagContentTemp += '</select></div>';
                                tagContent = tagBegin + tagContentTemp + tagEnd;
                            } else {
                                tagContentTemp = '<label style="padding-right: 5px" class="col-md-3 control-label">' + paramResultDatas[i].paramName + '</label>';
                                //tagContentTemp+='<select id="'+paramResultDatas[i].id+'" class="form-control" onchange="onChangeForGlobal(this)">';
                                tagContentTemp += '<div class="col-md-9" style="padding-left: 5px;padding-right: 25px"><select id="' + paramResultDatas[i].id + '" class="form-control" onchange="onSingleSelChangeForGlobal(this)">';
                                tagContentTemp += '<option value="">选择</option>';
                                for (var x in paramValuesSplitTemp) {
                                    tagContentTemp += '<option value="' + paramValuesSplitTemp[x] + '"' + '>' + paramValuesSplitTemp[x] + '</option>'
                                    var isEqflag=false;
                                    for (var y in refreshValuesTemp) {
                                        if (refreshValuesTemp[y]==paramValuesSplitTemp[x]){
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
                                tagContentTemp += '</select></div>';
                                tagContent = tagBegin + tagContentTemp + tagEnd;
                            }
                            //$("#demo .list-group").append(tagContent);
                            $("#demo .form-body").append(tagContent);
                        } else if (paramResultDatas[i].paramOperatType == '1') {
                            if (paramResultDatas[i].paramValueRelate != null && paramResultDatas[i].paramValueRelate != '') {
                                tagContentTemp = '<label style="padding-right: 5px" class="col-md-3 control-label" id="' + paramResultDatas[i].id + '">' + paramResultDatas[i].paramName + '</label>';
                                tagContentTemp += '<select class="form-control SlectBox" multiple="multiple" id="reportMenuParams' + paramResultDatas[i].id + '"></select>';
                                tagContent = tagBegin + tagContentTemp + tagEnd;

                                //$("#demo .list-group").append(tagContent);
                                $("#demo .form-body").append(tagContent);


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
                                $('#reportMenuParams' + paramResultDatas[i].id).multiselect({
                                    maxHeight: 200,
                                    buttonWidth: '100%',
                                    checkboxName: 'positionBox',
                                    nonSelectedText: $.i18n.prop('page_log_select'),
                                    onChange: function (element, checked) {
                                        var paramId = $(element).parent().prev("label").attr('id');
                                        var selValueTemp = $(element).data('id');
                                        var hasSelValue = selectRefreshParamMap[paramId];
                                        if (checked) {
                                            if (hasSelValue) {
                                                if ($.inArray(selValueTemp, hasSelValue) < 0) {
                                                    hasSelValue.push(selValueTemp);
                                                }
                                            } else {
                                                var arrayTemp = [];
                                                arrayTemp[0] = $(element).data('id');
                                                selectRefreshParamMap[paramId] = arrayTemp;
                                            }
                                        } else {
                                            hasSelValue.splice($.inArray(selValueTemp,hasSelValue),1);
                                        }
                                    }
                                });
                                $('#reportMenuParams' + paramResultDatas[i].id).multiselect('dataprovider', reportMenuParams);
                                var style = $('.dropdown-toggle.btn-default').attr("style");
                                $('.dropdown-toggle.btn-default').attr({style: style + "text-align: left;"});
                                $('.dropdown-toggle.btn-default').parent().addClass("col-md-9");
                                $('.dropdown-toggle.btn-default').parent().removeAttr("style");
                                $('.dropdown-toggle.btn-default').parent().attr("style","padding-left: 5px;padding-right: 25px" );
                                $('.dropdown-toggle.btn-default').css("background-color","white");

                            } else {
                                tagContentTemp = '<label style="padding-right: 5px" class="col-md-3 control-label" id="' + paramResultDatas[i].id + '">' + paramResultDatas[i].paramName + '</label>';
                                tagContentTemp += '<select class="form-control SlectBox" multiple="multiple" id="reportMenuParams' + paramResultDatas[i].id + '"></select>';
                                tagContent = tagBegin + tagContentTemp + tagEnd;

                                //$("#demo .list-group").append(tagContent);
                                $("#demo .form-body").append(tagContent);

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
                                $('#reportMenuParams' + paramResultDatas[i].id).multiselect({
                                    maxHeight: 200,
                                    buttonWidth: '100%',
                                    checkboxName: 'positionBox',
                                    nonSelectedText: $.i18n.prop('page_log_select'),
                                    onChange: function (element, checked) {
                                        var paramId = $(element).parent().prev("label").attr('id');
                                        var selValueTemp = $(element).data('id');
                                        var hasSelValue = selectRefreshParamMap[paramId];
                                        if (checked) {
                                            if (hasSelValue) {
                                                if ($.inArray(selValueTemp, hasSelValue) < 0) {
                                                    hasSelValue.push(selValueTemp);
                                                }
                                            } else {
                                                var arrayTemp = [];
                                                arrayTemp[0] = $(element).data('id');
                                                selectRefreshParamMap[paramId] = arrayTemp;
                                            }
                                        } else {
                                            hasSelValue.splice($.inArray(selValueTemp,hasSelValue),1);
                                        }
                                    }
                                });
                                $('#reportMenuParams' + paramResultDatas[i].id).multiselect('dataprovider', reportMenuParams);
                                var style = $('.dropdown-toggle.btn-default').attr("style");
                                $('.dropdown-toggle.btn-default').attr({style: style + "text-align: left;"});
                                $('.dropdown-toggle.btn-default').parent().addClass("col-md-9");
                                $('.dropdown-toggle.btn-default').parent().removeAttr("style");
                                $('.dropdown-toggle.btn-default').parent().attr("style","padding-left: 5px;padding-right: 25px" );
                                $('.dropdown-toggle.btn-default').css("background-color","white");
                            }
                        }
                        //alert('tagContent:'+tagContent);
                        //$("#demo .list-group").html(tagContent);
                    }
                }

                document.getElementById("demo").style.display="block";
                /*$('#demo').BootSideMenu({
                 side:"right", // left or right
                 autoClose:true // auto close when page loads
                 });*/
            });
            /*全局参数设置新增end*/
            /*$('#main-header').hide();
             $('#main-footer').hide();*/

        };

        $scope.reload();
    }
]);