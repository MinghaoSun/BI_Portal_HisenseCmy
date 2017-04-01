/**
 * Created by XIANGYANG on 2016/11/24.
 */
var unRepeatUserReportMenuId = [];
var unRepeatUserSysMenuId = [];
var unRepeatUserDIYReportMenuId=[];

function initContentModel(menuChildObj,$stateProvider){
    //alert('sdfsdf')
    $.each(menuChildObj, function (i, item) {
        if (item.menuType == '01') {
            if (item.menuAttribute != '0') {
                var menuUrl=item.menuUrl.replace('/','');
                unRepeatUserSysMenuId.push(menuUrl);
            }
        }else if (item.menuType == '02') {
            if (item.menuAttribute != '0') {
            	if(item.designType != '1'){
            		 if(unRepeatUserReportMenuId.indexOf(item.id) == -1){
            			 unRepeatUserReportMenuId.push(item.id);
            		 }
            	}else{
            		 if(unRepeatUserDIYReportMenuId.indexOf(item.menuUrl) == -1){
            			 unRepeatUserDIYReportMenuId.push(item.menuUrl);
            		 }
            	}
            }
        }
        if (typeof item.menuChild != 'undefined') {
            initContentModel(item.menuChild);
        }
    });
}
$.ajax({
    url: 'sysMenu/getMenuHierarchyListByUserId.do',
    async: false,
    type: "GET",
    dataType: "json",
    success: function (data) {
        $.each(data.userMenu, function (i, item) {
            if (item.menuChild.length > 0) {
                initContentModel(item.menuChild);
            }
        });
    }
});