/**
 * Created by admin on 2015/9/14.
 */
/*var httpGet = angular.module('HttpGet',[]);
httpGet.config(function($httpProvider){
    //删除后请求头里不再有 X-Requested-With 属性
    delete $httpProvider.defaults.headers.common['X-Requested-With'];
    $httpProvider.defaults.headers.common['Authorization'] = 'code_bunny';
    //没有效果,书上写错了.
    //$httpProvider.defaults.headers['DNT']='1';
    console.log($httpProvider.defaults.headers)
});*/


var baseModule = angular.module('baseModule', []);

baseModule.controller('baseCtrl', ['$scope', '$http',
    function ($scope, $http) {

        $scope.enabledOptions = [
            {name: '停用', id: '0'},
            {name: '启用', id: '1'}
        ];
        //国际化
        $scope.i18n = function (k) {
            return $.i18n.prop(k);
        };
        /**
         * 分页插件
         * currentPage:当前页
         * totalPages:总页数
         * numberOfPages:分页组件上显示的页数
         * onPageChanged:点击分页后的回调函数
         */
        $scope.renderPaginator = function (pagination, currentPage, totalPages, numberOfPages, onPageChanged) {
            if (typeof numberOfPages == 'function') {
                onPageChanged = numberOfPages;
                numberOfPages = 5;
            }
            var options = {
                bootstrapMajorVersion: 3,
                size: 'small',
                alignment: 'right',
                currentPage: currentPage,
                totalPages: totalPages,
                numberOfPages: numberOfPages,
                tooltipTitles: function (type, page, current) {
                    switch (type) {
                        case "first":
                            return $scope.i18n('page_data_first');
                        case "prev":
                            return $scope.i18n('page_data_previous');
                        case "next":
                            return $scope.i18n('page_data_next');
                        case "last":
                            return $scope.i18n('page_data_last');
                        case "page":
                            return $scope.i18n('page_data_no') + page + $scope.i18n('page_data_page');
                    }
                },
                itemTexts: function (type, page, current) { // <<  < ... > >>
                    switch (type) {
                        case "first":
                            return $scope.i18n('page_data_first');
                        case "prev":
                            return $scope.i18n('page_data_previous');
                        case "next":
                            return $scope.i18n('page_data_next');
                        case "last":
                            return $scope.i18n('page_data_last');
                        case "page":
                            return page;
                    }
                },
                pageUrl: function (type, page, current) {
                    return "javascript:;";
                },
                onPageChanged: onPageChanged
            }
            $(pagination).bootstrapPaginator(options);
        }

        /**
         * get请求服务端获取数据
         * @param interfaceName 服务端的接口名称
         * @param params    参数
         * @param callBackFunc  成功后的回调函数
         */
        $scope.getApi = function (interfaceName, params, callBackFunc) {
            var interfaceUrlName = GlobalConstant.serverPath + interfaceName;
            $.loading.show();
            $http.get(interfaceUrlName, {params: params},{headers : {'testvac' : 'true'}}).success(function (data, status) {
            	if("nologin"==data.content){
            		window.location.href="/login.html";
                	return;
            	}
                callBackFunc(data, status);
                $.loading.hide();
            }).error(function (data, status) {
                error("获取数据失败,error=" + angular.toJson(data) + ",status=" + status);
                $.loading.hide();
            });
        }

        /**
         * 分页从服务端查询数据
         * @param interfaceName 服务端的接口名称
         * @param pageNum   当前要查询的是第几页
         */
        $scope.queryData = function (interfaceName, pageNum, params) {
            if (params == null || params == "") {
                params = {};
            }
            params["currentPage"] = pageNum;
            params["pageSize"] = GlobalConstant.pageSize;
            params["searchInput"] = $scope.searchInput;

            var tip = $scope.i18n('page_data_loading');
            ZENG.msgbox.show(tip, 6, 3000);

            $scope.getApi(interfaceName, params, function (data, status) {
                $scope.datas = data.result;
                if ($("#pagination").length > 0) {
                    $scope.itemCount = data.count;
                    $scope.currentPage = data.currentPage;
                    $scope.pageCount = data.totalPages;
                    data.totalPages = data.totalPages == 0 ? 1 : data.totalPages;
                    if (pageNum != $scope.currentPage) {
                        pageNum = $scope.currentPage;
                    }
                    $scope.renderPaginator('#pagination', pageNum, data.totalPages, 5, function (event, oldPage, currentPage) {
                        if (pageNum != currentPage) {
                            $scope.queryData(interfaceName, currentPage, params);
                        }
                    });
                }

                ZENG.msgbox.hide();
            });
        }


        /**
         * 页面上多个分页
         * @param interfaceName 服务端的接口名称
         * @param pageNum   当前要查询的是第几页
         */
        $scope.queryDataPaginationNum = function (interfaceName, pageNum, params, paginationNum, name) {
            if (params == null || params == "") {
                params = {};
            }
            params["currentPage"] = pageNum;
            $scope.getApi(interfaceName, params, function (data, status) {
                if (name == 'hotTopics') {
                    $scope.hotTopics = data.result;
                } else if (name == 'hotTopicQQGroups') {
                    $scope.hotTopicQQGroups = data.result;
                }
                if ($(paginationNum).length > 0) {
                    if (name == 'hotTopics') {
                        $scope.itemCountTopic = data.count;
                    } else if (name == 'hotTopicQQGroups') {
                        $scope.itemCountQQGroups = data.count;
                    }
                    $scope.currentPage = data.currentPage;
                    $scope.pageCount = data.totalPages;
                    data.totalPages = data.totalPages == 0 ? 1 : data.totalPages;
                    $scope.renderPaginator(paginationNum, pageNum, data.totalPages, 5, function (event, oldPage, currentPage) {
                        if (pageNum != currentPage) {
                            $scope.queryDataPaginationNum(interfaceName, currentPage, params, paginationNum, name);
                        }
                    });
                }
            });
        }

        /**
         * post请求服务端获取数据
         * @param interfaceName 服务端的接口名称
         * @param params    参数
         * @param callBackFunc  成功后的回调函数
         */
        $scope.postApi = function (interfaceName, params, callBackFunc, errorCallBackFunc) {
            var interfaceUrlName = GlobalConstant.serverPath + interfaceName;
            var transFn = function (data) {
                return $.param(data);
            }
            var postCfg = {
                headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
                transformRequest: transFn
            };
            $.loading.show();
            $http.post(interfaceUrlName, params, postCfg).success(function (data, status) {
                callBackFunc(data, status);
                $.loading.hide();
            }).error(function (data, status) {
                if (typeof errorCallBackFunc == 'function') {
                    errorCallBackFunc(data, status);
                } else {
                    error("提交数据失败,error=" + data);
                }
                $.loading.hide();
            });
        }

        /**
         * put请求服务端获取数据
         * @param interfaceName 服务端的接口名称
         * @param params    参数
         * @param callBackFunc  成功后的回调函数
         */
        $scope.putApi = function (interfaceName, params, callBackFunc, errorCallBackFunc) {
            var interfaceUrlName = GlobalConstant.serverPath + interfaceName;
            $.loading.show();
            $http.put(interfaceUrlName, params).success(function (data, status) {
                callBackFunc(data, status);
                $.loading.hide();
            }).error(function (data, status) {
                if (typeof errorCallBackFunc == 'function') {
                    errorCallBackFunc(data, status);
                } else {
                    error("提交数据失败,error:" + angular.toJson(data));
                }
                $.loading.hide();
            });
        }


        /**
         * 添加数据到服务端
         * @param isValid 表单验证是否通过
         * @param interfaceName 请求的接口名称
         * @param params 参数对象
         * @param winModal 新增窗体对话框
         */
        $scope.addData = function (isValid, interfaceName, params, winModal, successFunc, errorFunc) {
            $scope.submitted = false;
            if (isValid) {//验证通过
                $scope.postApi(interfaceName, params, function (data, status) {
                    if (typeof successFunc == 'function') {
                        successFunc(data, status);
                    } else {
                        success("保存成功!!");
                        $scope.reload();
                        if (winModal != null) {
                            winModal.modal('hide');
                        }
                    }

                }, function (data, status) {
                    if (typeof errorFunc == 'function') {
                        errorFunc(data, status);
                    } else {
                        error("保存失败!!");
                    }
                })
            } else {
                $scope.submitted = true;
                return false;
            }

        }

        /**
         * 修改数据到服务端
         * @param isValid 表单验证是否通过
         * @param interfaceName 请求的接口名称
         * @param params 参数对象
         * @param winModal 修改窗体对话框
         */
        $scope.updateData = function (isValid, interfaceName, params, winModal, successFunc, errorFunc) {
            $scope.submitted = false;
            if (isValid) {//验证通过
                $scope.putApi(interfaceName, params, function (data, status) {
                    if (typeof successFunc == 'function') {
                        successFunc(data, status);
                    } else {
                        success("保存成功!!");
                        $scope.reload();
                        if (winModal != null) {
                            winModal.modal('hide');
                        }
                    }

                }, function (data, status) {
                    if (typeof errorFunc == 'function') {
                        errorFunc(data, status);
                    } else {
                        error("保存失败!!error:" + angular.toJson(data));
                    }
                })
            } else {
                $scope.submitted = true;
                return false;
            }

        }

        $scope.updateRecordData = function (interfaceName, params, successMessage, errorMessage) {
            $scope.putApi(interfaceName, params, function (data, status) {
                success(successMessage);
                $scope.reload();
            }, function (data, status) {
                error(errorMessage);
            })
        }

        /*菜单生成*/
        /*
         * 循环遍历生成左侧菜单
         * */
        $scope.initLeftMenu = function (menuChildObj,reportMenuType) {
            var temp1, temp2;
            //temp1 = '<ul class="treeview-menu">';
            temp1 = '<ul class="sub-menu">';
            $.each(menuChildObj, function (i, item) {
                if (menuChildObj.length>0) {
                    temp1 = temp1 + '<li class="nav-item  ">';
                } else {
                    temp1 = temp1 + '<li>';
                }
                if (item.menuType == '01') {
                    if (item.menuAttribute == '0') {
                        //temp1 = temp1 + '<a id="' + item.id + '" href="javascript:return false;"><span id="spanId"' + item.id + '>' + item.menuName + '</span></a>';
                        temp1 = temp1 + '<a class="nav-link " id="' + item.id + '" href="javascript:return false;"><span class="title" id="spanId"' + item.id + '>' + item.menuName + '</span></a>';
                    } else {
                        //temp1 = temp1 + '<a id="' + item.id + '" href="#' + item.menuUrl + '/' + item.id + '"onclick="add(this);"><span id="spanId' + item.id + '">' + item.menuName + '</span></a>';
                        //temp1 = temp1 + '<a id="' + item.id + '" href="#' + item.menuUrl + '/' + item.id + '"onclick="add(this);" class="nav-link "><span class="title" id="spanId' + item.id + '">' + item.menuName + '</span></a>';
                        temp1 = temp1 + '<a  href="javascript:return false;" reportId="'+ item.id + '" id="' + item.id + '" ' + 'onclick="add(this);" class="nav-link "><span class="title" id="spanId' + item.id + '">' + item.menuName + '</span></a>';
                    }
                } else if (item.menuType == '02') {
                    if (item.menuAttribute == '0') {
                        temp1 = temp1 + '<a href="javascript:;" class="nav-link nav-toggle"><i class="' + item.menuIcon + '"></i><span class="title">' + item.menuName + '</span><span class="arrow"></span></a>';
                        //temp1 = temp1 + '<a id="' + item.id + '" href="javascript:return false;" ><span id="spanId' + item.id + '">' + item.menuName + '</span><i class="fa fa-angle-right pull-right"></i></a>';
                        //temp1 = temp1 + '<a class="nav-link " id="' + item.id + '" href="javascript:return false;"><span class="title" id="spanId"' + item.id + '>' + item.menuName + '</span></a>';
                    } else {
                        //temp1 = temp1 + '<a id="' + item.id + '"  href="#/reportBusiness/' + item.id + '"onclick="add(this);"><span id="spanId' + item.id + '">' + item.menuName + '</span></a>';
                        //temp1 = temp1 + '<a id="' + item.id + '"  href="#/reportBusiness/' + item.id + '"onclick="add(this);" class="nav-link "><span class="title" id="spanId' + item.id + '">' + item.menuName + '</span></a>';
                        if(reportMenuType=='Default report'||reportMenuType=='Common report'){
                            temp1 = temp1 + '<a  href="javascript:return false;' + item.id + '" reportId="'+item.id+'" onclick="add(this);" class="nav-link "><span class="title">' + item.menuName + '</span></a>';
                        }else{
                            temp1 = temp1 + '<a id="' + item.id + '"  href="javascript:return false;' + item.id + '" reportId="'+item.id+'" onclick="add(this);" class="nav-link "><span class="title">' + item.menuName + '</span></a>';

                        }
                    }
                }
                if (typeof item.menuChild != 'undefined' && item.menuChild.length>0) {
                    temp1 = temp1 + $scope.initLeftMenu(item.menuChild,reportMenuType);
                }
            });
            temp1 = temp1 + '</li></ul>';
            return temp1;
        }
        $scope.createLeftMenu = function () {
            //$(".sidebar-menu").empty();
            $scope.getApi('sysMenu/getMenuHierarchyListByUserId.do', {}, function (data) {
                //$scope.datas = data;
                console.log(data)
                var temp1 = '';
                $.each(data.userMenu, function (i, item) {
                    var reportMenuType=item.menuNameEn;
                    if ('Default report' == item.menuNameEn) {
                        $("#navTabsId li").not(":first").remove();
                        $("#tab_seed_mainId").addClass("active");
                        temp1 = temp1 + '<li class="nav-item" style="display: none">';
                    } else {
                        temp1 = temp1 + '<li class="nav-item">';
                    }
                    if (item.menuChild.length > 0) {
                        //temp1 = temp1 + '<a href="javascript:return false;" ><i class="' + item.menuIcon + '"></i> <span>' + item.menuName + '</span><i class="fa fa-angle-right pull-right"></i></a>';
                        temp1 = temp1 + '<a href="javascript:;" class="nav-link nav-toggle"><i class="' + item.menuIcon + '"></i><span class="title">' + item.menuName + '</span><span class="arrow"></span></a>';

                        temp1 = temp1 + $scope.initLeftMenu(item.menuChild,reportMenuType);
                        if ('Default report' == item.menuNameEn) {
                            $.each(item.menuChild, function (i, itemTemp) {
                                var id = "tab_seed_" + itemTemp.id;
                                if (!$('#' + id)[0]) {
                                    var li_tab = '<li style="height: 25px;margin-top: 1px;padding: 2px 0px 0px 2px;" role="presentation" class="" id="' + id + '"><span onclick="controlTabClick(' + '\'' + itemTemp.id + '\'' + ')"  role="tab" data-toggle="tab" style="position: relative;padding:2px 20px 0px 15px;">' + itemTemp.menuName;
                                    li_tab = li_tab + '</span><i title="全屏显示" class="glyphicon glyphicon-resize-full small" tabenlarge="enlarge' + id + '"  onclick="closableTab.enlargeTab(this)"></i><i title="恢复正常显示" class="glyphicon glyphicon-resize-small small" tabenlarge="enlargeResize' + id + '" style="display: none" onclick="closableTab.resizeTab(this)"></i><i title="关闭当前窗口" class="fa fa-close small" tabclose="' + id + '"  onclick="closableTab.closeTab(this)"></i></li> ';

                                    $('#navTabsId').append(li_tab);
                                }
                            });
                        }
                    } else {
                        //temp1 = temp1 + '<a href="javascript:return false;" ><i class="' + item.menuIcon + '"></i> <span>' + item.menuName + '</span></a>';
                        temp1 = temp1 + '<a href="javascript:;" class="nav-link nav-toggle"><i class="' + item.menuIcon + '"></i><span class="title">' + item.menuName + '</span><span class="arrow"></span></a>';
                    }
                    temp1 = temp1 + '</li>';
                });
                /*$(".sidebar-menu").empty();
                 $(".sidebar-menu").html(temp1);*/
                $(".page-sidebar-menu").empty();
                $(".page-sidebar-menu").html(temp1);
            });
        };

        $scope.createLeftMenuForPersonalSetting = function () {
            //$(".sidebar-menu").empty();
            $scope.getApi('sysMenu/getMenuHierarchyListByUserId.do', {}, function (data) {
                //$scope.datas = data;
                console.log(data)
                var temp1 = '';
                $.each(data.userMenu, function (i, item) {
                    var reportMenuType=item.menuNameEn;
                    if ('Default report' == item.menuNameEn) {
                        //$("#navTabsId li").not(":first").remove();
                        //$("#tab_seed_mainId").addClass("active");
                        temp1 = temp1 + '<li class="nav-item" style="display: none">';
                    } else {
                        temp1 = temp1 + '<li class="nav-item">';
                    }
                    debugger;
                    // alert(item);
                    if (item.menuChild.length > 0) {
                        //temp1 = temp1 + '<a href="javascript:return false;" ><i class="' + item.menuIcon + '"></i> <span>' + item.menuName + '</span><i class="fa fa-angle-right pull-right"></i></a>';
                        temp1 = temp1 + '<a href="javascript:;" class="nav-link nav-toggle"><i class="' + item.menuIcon + '"></i><span class="title">' + item.menuName + '</span><span class="arrow"></span></a>';

                        temp1 = temp1 + $scope.initLeftMenu(item.menuChild,reportMenuType);
                        /*if ('Default report' == item.menuNameEn) {
                            $.each(item.menuChild, function (i, itemTemp) {
                                var id = "tab_seed_" + itemTemp.id;
                                if (!$('#' + id)[0]) {
                                    var li_tab = '<li style="margin-top: 1px;padding: 2px 0px 0px 2px;" role="presentation" class="" id="' + id + '"><span onclick="controlTabClick(' + '\'' + itemTemp.id + '\'' + ')"  role="tab" data-toggle="tab" style="position: relative;padding:2px 20px 0px 15px">' + itemTemp.menuName;
                                    li_tab = li_tab + '</span><i title="全屏显示" class="fa fa-search-plus " tabenlarge="enlarge' + id + '"  onclick="closableTab.enlargeTab(this)"></i><i title="恢复正常显示" class="fa fa-search-minus" tabenlarge="enlargeResize' + id + '" style="display: none" onclick="closableTab.resizeTab(this)"></i><i title="关闭当前窗口" class="fa fa-close small" tabclose="' + id + '"  onclick="closableTab.closeTab(this)"></i></li> ';

                                    $('#navTabsId').append(li_tab);
                                }
                            });
                        }*/
                    } else {
                        //temp1 = temp1 + '<a href="javascript:return false;" ><i class="' + item.menuIcon + '"></i> <span>' + item.menuName + '</span></a>';
                        temp1 = temp1 + '<a href="javascript:;" class="nav-link nav-toggle"><i class="' + item.menuIcon + '"></i><span class="title">' + item.menuName + '</span><span class="arrow"></span></a>';
                    }
                    temp1 = temp1 + '</li>';
                });
                /*$(".sidebar-menu").empty();
                 $(".sidebar-menu").html(temp1);*/
                $(".page-sidebar-menu").empty();
                $(".page-sidebar-menu").html(temp1);
            });
        };
    }
]);