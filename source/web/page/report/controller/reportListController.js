infopowerWebApp.controller('reportListCtrl', ['$scope','$http','$controller','$routeParams',
    function($scope,$http,$controller,$routeParams) {
        $controller('baseCtrl', {$scope: $scope,$http:$http});
        $scope.menuid = $routeParams.id;
        $scope.getApi('sysMenu/reportFolders.do',{menuId:$scope.menuid},function(data){
        	 var folderUl = $(".folder-ul");
             var navOl = $(".nav-ol");
             folderUl.onselectstart = function() {
             	return false;
             };
             
             /**
              * 根据名称和类型获得一个菜单条目
              */
             function getAnItem(text, type, href) {
             	return "<li data-type=\"" +type+ "\" " +(href && "data-href=#/reportBusiness/" + href)+ "><i class=\"fa fa-" 
    			+(type == 0 ? "folder" : "file-text-o")+ "\"></i>&nbsp;&nbsp;<span>" +text+ "</span></li>";
             }

             /**
              * 重置所有菜单条目
              */
             function resetAllItems(items) {
             	folderUl.html("");
             	var html = "";
             	for ( var i in items) {
             		var item = items[i];
             		html += getAnItem(item["name"], item["type"], item["id"]);
             	}
             	folderUl.html(html);
             	resetItemEvent();
             }

             /**
              * 重置菜单条目的双击/点击事件
              */
             function resetItemEvent() {
             	folderUl.children().each(
             			function() {
             				var $this = $(this);
             				var type = $this.attr("data-type");
             				if (type == 0) {
             					$this.dblclick(function() {
             						// 双击文件夹打开该文件夹
             						var text = $this.find("span").text();
             						setListByName(data, text);
             						// 索引+1
             						navOl.append("<li><a href=\"javascript:;\" title=\"" + text + "\" >"+ text + "</a></li>");
             						navOl.children().hover(function() {
             						})
             						navOl.children().click(function() {
             							// 记录此时点击的导航索引
             							var index = $(this).index();
             							// 循环所有的导航，索引大的删除
             							navOl.children().each(function(k, v) {
             								if (k > index) {
             									$(v).remove();
             								}
             							});
             							if (index == 0) {
             								resetAllItems(data);
             							} else {
             								setListByName(data, $(this).text());
             							}
             						});
             					});
             				} else {
             					$(this).click(function() {
             						window.open($(this).attr("data-href"));
             					});
             				}
             			});
             }
             resetAllItems(data);
             /**
              * 根据文件夹名称查找对应的文件夹内容
              */
             function setListByName(list, name) {
             	$(list).each(function(k, v) {
             		if (v["name"] == name) {
             			resetAllItems(v["list"]);
             		} else {
             			setListByName(v["list"], name);
             		}
             	});
             }
        })
}]);