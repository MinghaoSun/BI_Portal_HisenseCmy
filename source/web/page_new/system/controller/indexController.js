/**
 * index加载 controller
 */
infopowerWebApp.controller('indexCtrl', ['$scope', '$http', '$controller', '$routeParams','$state',
    function ($scope, $http, $controller, $routeParams,$state) {
        $controller('baseCtrl', {$scope: $scope, $http: $http});
        $scope.leftMenuClickedId=null;
        $scope.createLeftMenu();
        $state.go('tab.main');
    }
]);
