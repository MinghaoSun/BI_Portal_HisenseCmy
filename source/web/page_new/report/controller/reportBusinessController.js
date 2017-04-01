/**
 * 报表业务展现JS/controller
 */
infopowerWebApp.controller('reportBusinessCtrl', ['$scope', '$http', '$controller', '$routeParams','$route','$stateParams','$state',
    function ($scope, $http, $controller, $routeParams,$route,$stateParams,$state) {
        $controller('baseCtrl', {$scope: $scope, $http: $http});
        //$scope.menuid = $routeParams.id;
        $scope.menuid = $stateParams.id;
        //selectRefreshParamMap['menuid']=$scope.menuid;
        globalMenuId=$scope.menuid;
        //alert("$scope.menuid:"+$scope.menuid);
        $("head").append("<link id='multiselectCss' rel='stylesheet' type='text/css' href='js/bootstrapMultiselect/bootstrap-multiselect.css' />");
        $("head").append("<script id='multiselectJs' type='text/javascript' src='js/bootstrapMultiselect/bootstrap-multiselect.js'></script>");

        /*$scope.reloadRoute = function() {
            $route.reload();
        }*/

        $scope.reloadRoute = function() {
            alert(123123);
            /*//alert('dfsf');
            //$state.reload('tab.reportTool');
            //$state.go(".",{id:'ssdsd'},{reload:true});
            //alert('33')
            var temp="tab.reportBusiness"+$scope.menuid;
            alert('goto:'+temp)
            var idTemp=$scope.menuid+'';
            $state.go("tab.reportBusiness20161013123214474",{id:'20161013123214474'});
            //$state.reload();
            //$state.go("tab.reportTool",{id:'ssdsd'});*/
        }
        
        $scope.reloadiframe = function(frameid) {
        	 var mframe=$('#'+frameid).attr("src");
             $('#'+frameid).attr("src",mframe);
        }

        $scope.reload = function () {
            var demo='<div id="demo'+$scope.menuid+'" style="display: none">'+
                '<div class="modal-header">'+
                '<h5 class="modal-title" style="text-align: center">全局参数设置</h5>'+
                '</div>'+
                '<form class="form-horizontal" role="form" style="margin-top: 50px">'+
                '<div class="form-body">'+
                '</div>'+
                '</form>'+
                '<div class="modal-footer" style="margin-top: 50px">'+
                '<button type="button" class="btn btn-default pull-left" data-dismiss="modal">重置</button>'+
                '<button type="button" class="btn btn-preserve" onclick="addGolablRefreshParam()">刷新</button>'+
                '</div></div>';
            $('#uiViewReportBusiness'+$scope.menuid).append(demo);

            var reportContent='<div id="reportContent'+$scope.menuid+'" name="reportContent" class="reportContentStyleSetting"></div>';
            $('#uiViewReportBusiness'+$scope.menuid).append(reportContent);

            var paramSelectContent='<div id="paramSelectContent'+$scope.menuid+'"></div>';
            $('#uiViewReportBusiness'+$scope.menuid).append(paramSelectContent);

            $('#uiViewReportBusiness'+$scope.menuid).find('.navbar-brand').attr('id','reloadCurrentPage'+$scope.menuid);

            $scope.getApi('sysUserPersonalizedSetting/getGlobalParamOfUserList.do', {menuId: $scope.menuid}, function (datas) {
                var resultDatas = eval(datas);
                var paramResultDatas = resultDatas.result;
                if (paramResultDatas == null || paramResultDatas == "") {
                    //$("#demo"+$scope.menuid).html('<div class="modal-header"><h5 class="modal-title" style="text-align: center">全局参数设置</h5></div><hr><div class="list-group" style="text-align: center; line-height: 500px; font-size: 16px;opacity: 0.8">没有刷新参数!</div>');
                    //$("#demo"+$scope.menuid).html('<div class="modal-header"><h5 class="modal-title" style="text-align: center">全局参数设置</h5></div><hr><div class="list-group" style="text-align: center; line-height: 500px; font-size: 16px;opacity: 0.8"><img src="static/img/globalRefreshParam/noData.png"></div><div class="modal-footer" style="margin-top: 50px"><button type="button" class="btn btn-default pull-left" data-dismiss="modal">重置</button><button type="button" class="btn btn-preserve" onclick="addGolablRefreshParam()">刷新</button></div>');
                    $("#demo"+$scope.menuid).html('<div class="modal-header"><h5 class="modal-title" style="text-align: center">全局参数设置</h5></div><hr><div class=form-body" style="text-align: center; "><img src="static/img/globalRefreshParam/noData.png"></div><div class="modal-footer" style="margin-top: 50px"><button type="button" class="btn btn-default pull-left" data-dismiss="modal">重置</button><button type="button" class="btn btn-preserve" onclick="addGolablRefreshParam()">刷新</button></div>');
                    $("#demo"+$scope.menuid).BootSideMenu({
                        side: "right", // left or right
                        autoClose: true // auto close when page loads
                    });
                } else {
                    $("#demo"+$scope.menuid).BootSideMenu({
                        side: "right", // left or right
                        autoClose: true // auto close when page loads
                    });
                    var tagContent = '';
                    var tagContentTemp = '';
                    var tagBegin = '<div class="form-group" style="text-align: center">';
                    var tagEnd = '</div>';

                    //$("#demo .list-group").empty();
                    $("#demo"+$scope.menuid).find(".form-body").empty();
                    /*初始化刷新参数值*/
                    for (var i in paramResultDatas) {
                        if(paramResultDatas[i].refreshValue!=null&&paramResultDatas[i].refreshValue.trim()!='') {
                            if (paramResultDatas[i].paramOperatType == '0') {         //单选
                                $('#paramSelectContent'+$scope.menuid).append('<input type="hidden" paramOperatType="0" id="'+$scope.menuid+'paramSelect'+paramResultDatas[i].id+'" paramSelectId="'+paramResultDatas[i].id+'" name="'+paramResultDatas[i].id+'" value="'+paramResultDatas[i].refreshValue+'"/>');
                                //selectRefreshParamMap[paramResultDatas[i].id] = paramResultDatas[i].refreshValue;
                            } else if (paramResultDatas[i].paramOperatType == '1') {  //多选
                                $('#paramSelectContent'+$scope.menuid).append('<input type="hidden" paramOperatType="1" id="'+$scope.menuid+'paramSelect'+paramResultDatas[i].id+'" paramSelectId="'+paramResultDatas[i].id+'" name="'+paramResultDatas[i].id+'" value="'+paramResultDatas[i].refreshValue+'"/>');
                                //selectRefreshParamMap[paramResultDatas[i].id] = paramResultDatas[i].refreshValue.split(',');
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
                                //tagContentTemp += '<div class="col-md-9" style="padding-left: 5px;padding-right: 25px"><select id="' + paramResultDatas[i].id + '" class="form-control" onchange="onSingleSelChangeForGlobal(this)">';
                                tagContentTemp += '<div class="col-md-9" style="padding-left: 5px;padding-right: 25px"><select onchange="onSingleSelChangeForGlobal(this,\''+paramResultDatas[i].id+'\')" class="form-control" >';
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
                                //tagContentTemp += '<div class="col-md-9" style="padding-left: 5px;padding-right: 25px"><select id="' + paramResultDatas[i].id + '" class="form-control" onchange="onSingleSelChangeForGlobal(this)">';
                                tagContentTemp += '<div class="col-md-9" style="padding-left: 5px;padding-right: 25px"><select onchange="onSingleSelChangeForGlobal(this,\'' + paramResultDatas[i].id + '\')" class="form-control" >';
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
                            $("#demo"+$scope.menuid).find(".form-body").append(tagContent);
                        } else if (paramResultDatas[i].paramOperatType == '1') {
                            var multiId=$scope.menuid+'reportMenuParams' + paramResultDatas[i].id;
                            //alert('mulId:'+multiId)
                            if (paramResultDatas[i].paramValueRelate != null && paramResultDatas[i].paramValueRelate != '') {
                                tagContentTemp = '<label style="padding-right: 5px" class="col-md-3 control-label" labelId="'+paramResultDatas[i].id+'">' + paramResultDatas[i].paramName + '</label>';
                                tagContentTemp += '<select class="form-control SlectBox" multiple="multiple" id="'+multiId + '"></select>';
                                tagContent = tagBegin + tagContentTemp + tagEnd;

                                //$("#demo .list-group").append(tagContent);
                                $("#demo"+$scope.menuid).find(".form-body").append(tagContent);


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
                                $('#'+multiId).multiselect({
                                    maxHeight: 200,
                                    buttonWidth: '100%',
                                    checkboxName: 'positionBox',
                                    nonSelectedText: $.i18n.prop('page_log_select'),
                                    onChange: function (element, checked) {
                                        var paramId = $(element).parent().prev("label").attr('labelId');
                                        var selValueTemp = $(element).data('id');
                                        //var hasSelValue = selectRefreshParamMap[paramId];
                                        //alert(globalMenuId)
                                        var hasSelValue=$('#'+globalMenuId+'paramSelect'+paramId).attr("value");
                                        //alert('hasSelValue:'+hasSelValue+'&&&&')
                                        if (checked) {
                                            if (hasSelValue) {
                                                var hasSelValueArray=hasSelValue.split(',');
                                                if ($.inArray(selValueTemp, hasSelValueArray) < 0) {
                                                    hasSelValueArray.push(selValueTemp);
                                                }
                                                var temp='';
                                                $.each(hasSelValueArray,function(index,obj){
                                                    if(hasSelValueArray.length-1==index){
                                                        temp=temp+hasSelValueArray[index];
                                                    }else{
                                                        temp=temp+hasSelValueArray[index]+',';
                                                    }
                                                });
                                                $('#'+globalMenuId+'paramSelect'+paramId).attr("value",temp);
                                            } else {
                                                var arrayTemp = [];
                                                arrayTemp[0] = $(element).data('id');
                                                $('#'+globalMenuId+'paramSelect'+paramId).attr("value",arrayTemp[0]);
                                                //selectRefreshParamMap[paramId] = arrayTemp;
                                            }
                                        } else {
                                            var hasSelValueArray=hasSelValue.split(',');
                                            hasSelValueArray.splice($.inArray(selValueTemp,hasSelValueArray),1);
                                            var temp='';
                                            $.each(hasSelValueArray,function(index,obj){
                                                if(hasSelValueArray.length-1==index){
                                                    temp=temp+hasSelValueArray[index];
                                                }else{
                                                    temp=temp+hasSelValueArray[index]+',';
                                                }
                                            });
                                            $('#'+globalMenuId+'paramSelect'+paramId).attr("value",temp);
                                        }
                                    }
                                });
                                $('#'+multiId).multiselect('dataprovider', reportMenuParams);
                                var style = $('.dropdown-toggle.btn-default').attr("style");
                                $('.dropdown-toggle.btn-default').attr({style: style + "text-align: left;"});
                                $('.dropdown-toggle.btn-default').parent().addClass("col-md-9");
                                $('.dropdown-toggle.btn-default').parent().removeAttr("style");
                                $('.dropdown-toggle.btn-default').parent().attr("style","padding-left: 5px;padding-right: 25px" );
                                $('.dropdown-toggle.btn-default').css("background-color","white");

                            } else {
                                var multiId=$scope.menuid+'reportMenuParams' + paramResultDatas[i].id;
                                alert('multiId:'+multiId)
                                tagContentTemp = '<label style="padding-right: 5px" class="col-md-3 control-label" labelId="'+paramResultDatas[i].id+'>' + paramResultDatas[i].paramName + '</label>';
                                tagContentTemp += '<select class="form-control SlectBox" multiple="multiple" id="'+multiId+ '"></select>';
                                tagContent = tagBegin + tagContentTemp + tagEnd;

                                //$("#demo .list-group").append(tagContent);
                                $("#demo"+$scope.menuid).find(".form-body").append(tagContent);

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
                                $('#'+multiId).multiselect({
                                    maxHeight: 200,
                                    buttonWidth: '100%',
                                    checkboxName: 'positionBox',
                                    nonSelectedText: $.i18n.prop('page_log_select'),
                                    onChange: function (element, checked) {
                                        var paramId = $(element).parent().prev("label").attr('labelId');
                                        var selValueTemp = $(element).data('id');
                                        //var hasSelValue = selectRefreshParamMap[paramId];
                                        //alert(globalMenuId)
                                        var hasSelValue=$('#'+globalMenuId+'paramSelect'+paramId).attr("value");
                                        //alert('hasSelValue:'+hasSelValue+'&&&&22')
                                        if (checked) {
                                            if (hasSelValue){
                                                var hasSelValueArray=hasSelValue.split(',');
                                                if ($.inArray(selValueTemp, hasSelValueArray) < 0) {
                                                    hasSelValueArray.push(selValueTemp);
                                                }
                                                var temp='';
                                                $.each(hasSelValueArray,function(index,obj){
                                                    if(hasSelValueArray.length-1==index){
                                                        temp=temp+hasSelValueArray[index];
                                                    }else{
                                                        temp=temp+hasSelValueArray[index]+',';
                                                    }
                                                });
                                                $('#'+globalMenuId+'paramSelect'+paramId).attr("value",temp);
                                            } else {
                                                var arrayTemp = [];
                                                arrayTemp[0] = $(element).data('id');
                                                $('#'+globalMenuId+'paramSelect'+paramId).attr("value",arrayTemp[0]);
                                                //selectRefreshParamMap[paramId] = arrayTemp;
                                            }
                                        } else {
                                            var hasSelValueArray=hasSelValue.split(',');
                                            hasSelValueArray.splice($.inArray(selValueTemp,hasSelValueArray),1);
                                            var temp='';
                                            $.each(hasSelValueArray,function(index,obj){
                                                if(hasSelValueArray.length-1==index){
                                                    temp=temp+hasSelValueArray[index];
                                                }else{
                                                    temp=temp+hasSelValueArray[index]+',';
                                                }
                                            });
                                            $('#'+globalMenuId+'paramSelect'+paramId).attr("value",temp);
                                        }
                                    }
                                });
                                $('#'+multiId).multiselect('dataprovider', reportMenuParams);
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

                document.getElementById("demo"+$scope.menuid).style.display="block";
            });

            //var h = parseInt(document.body.clientHeight) + 90;
            //判断是横向，还是纵向
            /*if ($('#main-sidebar').length>0){
             $('#main-sidebar').hide();
             $(".content-wrapper").css({"margin-left":0,"padding-top":0});
             h = h-50;
             }*/

            //$('#reportContent'+$scope.menuid).height(h);
            var pageContent = '<div id="load'+$scope.menuid+'" align="center" style="position: fixed;top: 60%;left: 50%;width:50%;height: 50%;-webkit-transform: translateX(-50%) translateY(-50%);" xmlns="http://www.w3.org/1999/html"><div><img src="static/img/loadReport/progressbar.gif" ></div><div>loading...</div></div></div>';
            $('#reportContent'+$scope.menuid).html(pageContent);
            $scope.getApi('sysMenu/getReportMenuPage.do', {menuId: $scope.menuid}, function (datas) {
                console.log('report:' + datas);
                console.log('reqLocation:' + datas.reqLocation);
                var clientHeightTemp= parseInt(document.documentElement.clientHeight)-parseInt($('.page-top').height() )-parseInt($('#navTabsId').height());
                console.log("浏览器高度："+document.documentElement.clientHeight)
                console.log("iframe高度"+clientHeightTemp);
                // alert(document.documentElement.clientHeight);
                // alert($('#navTabsId').height());
                // var clientWidthTemp=parseInt(document.documentElement.clientWidth);
                $('#contentModelId .reportContentStyleSetting').css("height",clientHeightTemp);
                //alert("clientHeight2:"+clientHeightTemp);
                var iframeid="reportContent2"+$scope.menuid;
                if (datas.reqStatus == "0") {
                    menuids=$scope.menuid;
                    //pageContent = '<iframe  id="reportContent2'+$scope.menuid+'" name="reportContent2'+$scope.menuid+'"   src="' + datas.reqLocation + '" frameborder="no" border="0px" width="100%" height="71%"   margin="0px" ></iframe>';
                    pageContent = '<iframe class="iframereport"  id="reportContent2'+$scope.menuid+'" name="reportContent2'+$scope.menuid+'"   src="' + datas.reqLocation + '" frameborder="no" border="0px" width="100%" height="'+clientHeightTemp+'"   margin="0px" allowFullScreen="allowFullScreen" ></iframe>';
                    $('#reportContent'+$scope.menuid).append(pageContent);
                } else {
                    //pageContent = '<iframe id="reportContent2" name="reportContent2" style="padding-left: 10px;"  src="common/error_new.jsp" frameborder="no" border="0px" width="100%" height="100%" margin="0px" ></iframe>';
                    //pageContent = '<iframe id="reportContent2'+$scope.menuid+'" name="reportContent2'+$scope.menuid+'"   src="http://www.sina.com.cn/" frameborder="no" border="0px" width="100%" height="100%" margin="0px" ></iframe>';
                    // pageContent = '<iframe id="reportContent2'+$scope.menuid+'" name="reportContent2'+$scope.menuid+'"   src="common/error_new.jsp" frameborder="no" border="0px" width="100%" height="100%" margin="0px" allowFullScreen="allowFullScreen" ></iframe>';
                    pageContent = '<iframe class="iframereport"  id="reportContent2'+$scope.menuid+'" name="reportContent2'+$scope.menuid+'"   src="404.jsp" frameborder="no" border="0px" width="100%" height="100%" margin="0px" allowFullScreen="allowFullScreen" ></iframe>';
                    $('#reportContent'+$scope.menuid).append(pageContent);
                }
                /*进度条*/
                var iframe01 = document.getElementById("reportContent2"+$scope.menuid);
                var load01 = document.getElementById("load"+$scope.menuid);
                iframe01.style.display = "none";
                if (iframe01.attachEvent) {
                    iframe01.attachEvent("onload", function () {
                        load01.style.display = "none";
                        iframe01.style.display = "block";
                        var src=$("#"+iframeid).attr("src");
                        var reloadtime=1000*60*30;
                        setTimeout("$('#"+iframeid+"').attr('src','"+src+"')",reloadtime);
                    });
                } else {
                    iframe01.onload = function () {
                        load01.style.display = "none";
                        iframe01.style.display = "block";
                        var src=$("#"+iframeid).attr("src");
                        var reloadtime=1000*60*30;
                        setTimeout("$('#"+iframeid+"').attr('src','"+src+"')",reloadtime);
                    };
                }
            });
        };

        $scope.reload();
    }
]);