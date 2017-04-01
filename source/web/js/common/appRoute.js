/**
 * Created by admin on 2015/9/24.
 */

var infopowerWebApp = angular.module('infopowerWebApp', ['ngRoute','baseModule','platFilters','platDirective']);

infopowerWebApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
        when('/main', {templateUrl: 'page/system/view/main.html',controller: 'mainCtrl'}).
        when('/sysMenu/:id', {templateUrl: 'page/system/view/sysMenu.html',controller: 'sysMenuCtrl'}).
        when('/reportTool/:id', {templateUrl: 'page/report/view/reportTool.html',controller: 'reportToolCtrl'}).
        when('/reportParam/:id', {templateUrl: 'page/report/view/reportParam.html',controller: 'reportParamCtrl'}).
        when('/sysLog/:id', {templateUrl: 'page/system/view/sysLog.html',controller: 'sysLogCtrl'}).
        when('/sysUser/:id', {templateUrl: 'page/system/view/sysUser.html',controller: 'sysUserCtrl'}).
        when('/sysRole/:id', {templateUrl: 'page/system/view/sysRole.html',controller: 'sysRoleCtrl'}).
        when('/sysReport/:id', {templateUrl: 'page/system/view/sysReport.html',controller: 'sysReportCtrl'}).
        when('/sysCommonReportSetting/:id', {templateUrl: 'page/system/view/sysCommonReportSetting.html',controller: 'sysCommonReportSettingCtrl'}).
        when('/sysDefaultReportSetting/:id', {templateUrl: 'page/system/view/sysDefaultReportSetting.html',controller: 'sysDefaultReportSettingCtrl'}).
        when('/sysRefreshParameterSetting/:id', {templateUrl: 'page/system/view/sysRefreshParameterSetting.html',controller: 'sysRefreshParameterSettingCtrl'}).
        when('/reportList/:id', {templateUrl: 'page/report/view/reportList.html',controller: 'reportListCtrl'}).
        when('/reportBusiness/:id', {templateUrl: 'page/report/view/reportBusiness.html',controller: 'reportBusinessCtrl'}).
        when('/sysOther/:id', {templateUrl: 'page/system/view/sysOther.html',controller: 'sysOtherCtrl'}).
        otherwise({redirectTo: '/main'});
    }]);
