/**
 * index加载 controller
 */
infopowerWebApp.controller('initCtrl', ['$scope', '$http', '$controller', '$routeParams',
    function ($scope, $http, $controller, $routeParams) {
        $controller('baseCtrl', {$scope: $scope, $http: $http});

        $scope.reload = function () {
            $scope.createLeftMenu();

            $.ajax({
                url: 'sysUsers/isOnline.do',
                async: false,
                type: "GET",
                dataType: "json",
                success: function (code) {
                    if (code == -1) {
                        window.location.href = 'login.html';
                        return;
                    } else if (code.code == 200) {
                        $scope.loginUser = code.data;
                    }
                }
            });
        }

        $scope.changeTheme = function (theme) {
            $(".skin-link").removeClass("active");
            $("#" + theme).addClass("active");
            $scope.loginUser.userThemes = theme;

            $scope.postApi('sysUsers/changeTheme.do', {theme: theme}, function (data) {
            });
        }

        $(".dpicker").datepicker({
            format: 'yyyy-mm-dd',
            language: 'zh-CN',
            todayHighlight: true,
            autoclose: true,
        });

        $scope.getUserInfo = function () {
            $('form[name="updateSysUserInfoForm"]').bootstrapValidator({
                feedbackIcons: {
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields: {
                    firstName: {
                        validators: {
                            notEmpty: {
                                message: $scope.i18n('page_user_new_namevalidate')
                            }
                        }
                    },
                    lastName: {
                        validators: {
                            notEmpty: {
                                message: $scope.i18n('page_user_new_surnamevalidate')
                            }
                        }
                    },
                    userEmail: {
                        validators: {
                            notEmpty: {
                                message: $scope.i18n('page_user_new_emailvalidate')
                            },
                            emailAddress: {
                                message: $scope.i18n('page_user_new_validEmail')
                            }
                        }
                    },
                    userFullName: {validators: {notEmpty: {}}},
                    userMobile: {
                        validators: {
                            notEmpty: {
                                message: $scope.i18n('page_user_new_phonevalidate')
                            },
                            regexp: {
                                regexp: /^\d{8}-\d{1,9}$|^\d{11}-\d{1,9}$|^\d{8}$|^\d{11}$/,
                                message: $scope.i18n('page_user_new_validPhone')
                            }
                        }
                    },
                    newPassword: {
                        validators: {
                            notEmpty: {
                                message: $scope.i18n('page_user_new_pwdvalidate')
                            },
                            identical: {
                                field: 'confirmPassword',
                                message: $scope.i18n('page_user_new_pwdcompare')
                            }
                        }
                    },
                    confirmPassword: {
                        validators: {
                            notEmpty: {
                                message: $scope.i18n('page_user_new_confirmvalidate')
                            },
                            identical: {
                                field: 'newPassword',
                                message: $scope.i18n('page_user_new_pwdcompare')
                            }
                        }
                    }
                }
            }).on('success.form.bv', function (e) {
                $scope.postApi('sysUsers/updateSysUserInfo.do', $scope.updateUserInfo, function (data) {
                    if (data.code == 0) {
                        $scope.loginUser = data.data;
                        $('#updateUserInfoModal').modal('hide');
                        $('#successUpdateModal p.hintInfo').html($scope.i18n('page_savesuccess'));
                        $('#successUpdateModal').modal('show');
                    } else if (data.code == -1) {//修改失败
                        $('#updateUserInfoModal').modal('hide');
                        $('#tipSure').html($scope.i18n('page_userDetail_PWDfailed'));
                        $('#tipModal').modal('show');
                    }
                })
            });
            var user = $scope.loginUser;

            $scope.updateUserInfo = {
                loginName: user.loginName,
                firstName: user.firstName,
                lastName: user.lastName,
                userBirthday: user.userBirthday,
                userEmail: user.userEmail,
                userFullName: user.userFullName,
                userMobile: user.userMobile,
                newPassword: user.loginPassword,
                confirmPassword: user.loginPassword,
                oldPassword: user.loginPassword
            };
            $('#updateUserInfoModal').modal('show');
        }

        $('#updateUserInfoModal').on('hide.bs.modal', function (e) {
            $('form[name="updateSysUserInfoForm"]').data('bootstrapValidator').destroy();
            $('form[name="updateSysUserInfoForm"]').data('bootstrapValidator', null);
        });

        $scope.updateSysUserInfo = function () {
            $('form[name="updateSysUserInfoForm"]').bootstrapValidator('validate');
        };

        $scope.reload();
    }
]);

infopowerWebApp.controller('mainCtrl', ['$scope', '$http', '$controller', '$routeParams',
    function ($scope, $http, $controller, $routeParams) {
        $controller('baseCtrl', {$scope: $scope, $http: $http});
        $scope.reload = function () {

            $scope.getApi("sysUsers/getVisitReportListByUser.do", "", function (data, status) {
                $scope.dataLog = data;
            })
        }
        $scope.reload();
    }
]);

/**
 * 监听数据加载完
 */
infopowerWebApp.directive('loaddata', function ($timeout) {
    return {
        restrict: 'A',
        link: function (scope, element, attr) {

            if (scope.$last === true) {
                $timeout(function () {
                    var hashUrl = window.location.hash;
                    if (hashUrl) {
                        $('section.content-header .content-header-title').html($('.sys-menu-a[href="' + hashUrl + '"]').text());
                    }
                });
            }
        }
    };
});
