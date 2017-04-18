/**
 * Created by admin on 2015/9/24.
 */

var infopowerWebApp = angular.module('infopowerWebApp', ['ngRoute', 'baseModule', 'platFilters', 'platDirective', 'ct.ui.router.extras']);
infopowerWebApp.run(function ($state, $rootScope) {
    $rootScope.$state = $state;
    /*$rootScope.$on('$stateChangeSuccess',
        function (event, toState, toParams, fromState, fromParams) {
            if ('' == fromState.name) {
                $state.go('tab.main');
            }
        });*/
})

infopowerWebApp.config(['$routeProvider', '$stateProvider', '$stickyStateProvider', '$urlRouterProvider',
    function ($routeProvider, $stateProvider, $stickyStateProvider, $urlRouterProvider) {
        //alert($('#page-sidebar-wrapper').text());
        //alert("%%%%%%%%%%^^^^^&&&&&&&***:"+userMenu)
        $urlRouterProvider.otherwise("/");
        $stateProvider.state('tab', {
            url: '/',
            sticky: true,
            //dsr: true,
            templateUrl: 'page_new/system/view/index.html',
            controller: 'indexCtrl'
        });
        $stateProvider.state('tab.main', {
            url: '/main',
            //sticky: true,
            //dsr: true,
            views: {
                'main': {
                    templateUrl: 'page_new/system/view/main.html',
                    controller: 'mainCtrl'
                }
            }
        });
        $stateProvider.state('tab.sysMenu', {
            url: '/sysMenu/:id',
            views: {
                'sysMenu': {
                    templateUrl: 'page_new/system/view/sysMenu.html',
                    controller: 'sysMenuCtrl'
                }
            }
        });
        $stateProvider.state('tab.reportTool', {
            url: '/reportTool/:id',
            //sticky: true,
            //dsr: true,
            views: {
                'reportTool': {
                    templateUrl: 'page_new/report/view/reportTool.html',
                    controller: 'reportToolCtrl'
                }
            }
        });

        $stateProvider.state('tab.reportParam', {
            url: '/reportParam/:id',
            //sticky: true,
            //dsr: true,
            views: {
                'reportParam': {
                    templateUrl: 'page_new/report/view/reportParam.html',
                    controller: 'reportParamCtrl'
                }
            }
        });
        $stateProvider.state('tab.sysLog', {
            url: '/sysLog/:id',
            views: {
                'sysLog': {
                    templateUrl: 'page_new/system/view/sysLog.html',
                    controller: 'sysLogCtrl'
                }
            }
        });
        $stateProvider.state('tab.sysUser', {
            url: '/sysUser/:id',
            views: {
                'sysUser': {
                    templateUrl: 'page_new/system/view/sysUser.html',
                    controller: 'sysUserCtrl'
                }
            }
        });
        $stateProvider.state('tab.sysRole', {
            url: '/sysRole/:id',
            views: {
                'sysRole': {
                    templateUrl: 'page_new/system/view/sysRole.html',
                    controller: 'sysRoleCtrl'
                }
            }
        });
        $stateProvider.state('tab.sysReport', {
            url: '/sysReport/:id',
            //sticky: true,
            views: {
                'sysReport': {
                    templateUrl: 'page_new/system/view/sysReport.html',
                    controller: 'sysReportCtrl'
                }
            }
        });
        $stateProvider.state('tab.sysCommonReportSetting', {
            url: '/sysCommonReportSetting/:id',
            /*templateUrl: 'page_new/system/view/sysCommonReportSetting.html',
             controller: 'sysCommonReportSettingCtrl'*/
            //sticky: true,
            views: {
                'sysCommonReportSetting': {
                    templateUrl: 'page_new/system/view/sysCommonReportSetting.html',
                    controller: 'sysCommonReportSettingCtrl'
                }
            }
        });
        $stateProvider.state('tab.sysDefaultReportSetting', {
            url: '/sysDefaultReportSetting/:id',
            /*templateUrl: 'page_new/system/view/sysDefaultReportSetting.html',
             controller: 'sysDefaultReportSettingCtrl'*/
            views: {
                'sysDefaultReportSetting': {
                    templateUrl: 'page_new/system/view/sysDefaultReportSetting.html',
                    controller: 'sysDefaultReportSettingCtrl'
                }
            }
        });
        $stateProvider.state('tab.sysRefreshParameterSetting', {
            url: '/sysRefreshParameterSetting/:id',
            /*templateUrl: 'page_new/system/view/sysRefreshParameterSetting.html',
             controller: 'sysRefreshParameterSettingCtrl'*/
            views: {
                'sysRefreshParameterSetting': {
                    templateUrl: 'page_new/system/view/sysRefreshParameterSetting.html',
                    controller: 'sysRefreshParameterSettingCtrl'
                }
            }
        });
        $stateProvider.state('reportList', {
            url: '/reportList/:id',
            views: {
                'reportList': {
                    templateUrl: 'page_new/report/view/reportList.html',
                    controller: 'reportListCtrl'
                }
            }
        });
        /*$stateProvider.state('tab.reportBusiness', {
         url: '/reportBusiness/:id',
         sticky: true,
         dsr: true,
         views: {
         'reportBusiness': {
         templateUrl: 'page_new/report/view/reportBusiness.html',
         controller: 'reportBusinessCtrl'
         }
         }
         });*/
        $stateProvider.state('tab.sysOther', {
            url: '/sysOther/:id',
            views: {
                'sysOther': {
                    templateUrl: 'page_new/system/view/sysOther.html',
                    controller: 'sysOtherCtrl'
                }
            }
        });
        /*TODO  依据js加载顺序的原理，在首页index3.html中进行的初始化unRepeatUserMenuId*/
        $.each(unRepeatUserReportMenuId, function (i, item) {
            //alert('######:'+item)
            var mapView = {};
            mapView['reportBusiness' + item] = {
                templateUrl: 'page_new/report/view/reportBusiness.html',
                controller: 'reportBusinessCtrl'
            };
            console.log(mapView);
            $stateProvider.state('tab.reportBusiness' + item, {
                url: '/reportBusiness/:id',
                sticky: true,
                //dsr: true,
                views: mapView
            });
        });
        $.each(unRepeatUserDIYReportMenuId, function (i, item) {
        	var mapView = {};
            mapView['BusinessDIY' + item] = {
                templateUrl: 'page_new/report/view/'+item+'/index.jsp'
                
            };
            console.log(mapView);
            $stateProvider.state('tab.' + item, {
                url: '/'+item+'/:id',
                sticky: true,
                //dsr: true,
                views: mapView
            });;
        	 
        });
        $stickyStateProvider.enableDebug(true);
        /*$routeProvider.
         when('/main', {templateUrl: 'page_new/system/view/main.html',controller: 'mainCtrl'}).
         when('/sysMenu/:id', {templateUrl: 'page_new/system/view/sysMenu.html',controller: 'sysMenuCtrl'}).
         when('/reportTool/:id', {templateUrl: 'page_new/report/view/reportTool.html',controller: 'reportToolCtrl'}).
         when('/reportParam/:id', {templateUrl: 'page_new/report/view/reportParam.html',controller: 'reportParamCtrl'}).
         when('/sysLog/:id', {templateUrl: 'page_new/system/view/sysLog.html',controller: 'sysLogCtrl'}).
         when('/sysUser/:id', {templateUrl: 'page_new/system/view/sysUser.html',controller: 'sysUserCtrl'}).
         when('/sysRole/:id', {templateUrl: 'page_new/system/view/sysRole.html',controller: 'sysRoleCtrl'}).
         when('/sysReport/:id', {templateUrl: 'page_new/system/view/sysReport.html',controller: 'sysReportCtrl'}).
         when('/sysCommonReportSetting/:id', {templateUrl: 'page_new/system/view/sysCommonReportSetting.html',controller: 'sysCommonReportSettingCtrl'}).
         when('/sysDefaultReportSetting/:id', {templateUrl: 'page_new/system/view/sysDefaultReportSetting.html',controller: 'sysDefaultReportSettingCtrl'}).
         when('/sysRefreshParameterSetting/:id', {templateUrl: 'page_new/system/view/sysRefreshParameterSetting.html',controller: 'sysRefreshParameterSettingCtrl'}).
         when('/reportList/:id', {templateUrl: 'page_new/report/view/reportList.html',controller: 'reportListCtrl'}).
         when('/reportBusiness/:id', {templateUrl: 'page_new/report/view/reportBusiness.html',controller: 'reportBusinessCtrl'}).
         when('/sysOther/:id', {templateUrl: 'page_new/system/view/sysOther.html',controller: 'sysOtherCtrl'}).
         otherwise({redirectTo: '/main'});*/
    }]);
