var lang = getUrlParam("lang");
// 弹出错误提示的登录框
$.showErr = function(str, func) {
	if(lang!= "en_US"){
		// 调用show方法
		BootstrapDialog.show({
			type : BootstrapDialog.TYPE_DANGER,
			title : '错误 ',
			message : str,
			size : BootstrapDialog.SIZE_SMALL,
		// size为小，默认的对话框比较宽
		// 对话框关闭时带入callback方法
		// onhide : func
		// 指定时间内可自动关闭
			onshown : function(dialogRef) {
				setTimeout(function() {
					dialogRef.close();
					$(".btn").css("background","#0085ff")
				}, 1000);
			},
		});
	}else{
		// 调用show方法
		BootstrapDialog.show({
			type : BootstrapDialog.TYPE_DANGER,
			title : 'Error ',
			message : str,
			size : BootstrapDialog.SIZE_SMALL,
		// size为小，默认的对话框比较宽
		// 对话框关闭时带入callback方法
		// onhide : func
		// 指定时间内可自动关闭
			onshown : function(dialogRef) {
				/*setTimeout(function() {
					dialogRef.close();
					$(".btn").css("background","#0085ff")
				}, 1000);*/
			},
		});
	}
	
};
// 弹出成功提示的登录框
$.showSuccessTimeout = function(str, func) {
	if(lang!= "en_US"){
	BootstrapDialog.show({
		type : BootstrapDialog.TYPE_SUCCESS,
		title : '成功 ',
		message : str,
		size : BootstrapDialog.SIZE_SMALL,
		// 指定时间内可自动关闭
		onshown : function(dialogRef) {
			setTimeout(function() {
				dialogRef.close();
				$(".btn").css("background","#0085ff")
			}, 1000);
		},
		onhide : func
	});
	}else{
		BootstrapDialog.show({
			type : BootstrapDialog.TYPE_SUCCESS,
			title : 'Success ',
			message : str,
			size : BootstrapDialog.SIZE_SMALL,
			// 指定时间内可自动关闭
			onshown : function(dialogRef) {
				setTimeout(function() {
					dialogRef.close();
					$(".btn").css("background","#0085ff")
				}, 1000);
			},
			onhide : func
		});
	}
};

function getChildren(array) {
	for (var i = 0; i < array.length; i++) {
		if (array[i].children != "undefined"
				|| array[i].children != undefined) {
			if (array[i].children!="null"&&array[i].children!=null&&array[i].children.length != 0) {
				getChildren(array[i].children)
			} else {
				delete array[i].children
			}
		}
	}
	return array;
}
// 获取地址栏的参数数组
function getUrlParams() {
    var search = parent.location.href;
    // 写入数据字典
    var tmparray = search.substr(0, search.length).split("&");
    var paramsArray = new Array;
    if (tmparray != null) {
        for (var i = 0; i < tmparray.length; i++) {
            var reg = /[=|^==]/;    // 用=进行拆分，但不包括==
            var set1 = tmparray[i].replace(reg, '&');
            var tmpStr2 = set1.split('&');
            var array = new Array;
            array[tmpStr2[0]] = tmpStr2[1];
            paramsArray.push(array);
        }
    }
    // 将参数数组进行返回
    return paramsArray;
}
 function getUrlParam(name) {
	    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	    var r = parent.location.search.substr(1).match(reg);  // 匹配目标参数
	    if (r != null) return unescape(r[2]);
	     return null; // 返回参数值
}


function deleteSuccess(){
	if (lang != "en_US") {
		$.showSuccessTimeout("删除成功");
	}else{
		$.showSuccessTimeout("Deleted Successfully");
	}
}

function operationSuccess(){
	if (lang != "en_US") {
		$.showSuccessTimeout("操作成功");
	}else{
		$.showSuccessTimeout("Operation Successfully");
	}
}
function operationErr(){
	if (lang != "en_US") {
		$.showErr("操作失败");
	}else{
		$.showErr("Operation Failed");
	}
}
function checkErr(){
	if (lang != "en_US") {
		$.showErr("未勾选数据");
	}else{
		$.showErr("Unchecked data");
	}
}
function deleteErr(){
	if (lang != "en_US") {
		$.showErr("删除失败");
	}else{
		$.showErr("Delete Failed");
	}
}
function deleteUserErr(){
	if (lang != "en_US") {
		$.showErr("admin为超级管理员,不可删除");
	}else{
		$.showErr("admin Is the super administrator, cannot delete");
	}
}
function addSuccess(){
	if (lang != "en_US") {
		$.showSuccessTimeout("添加成功");
	}else{
		$.showSuccessTimeout("Added Successfully");
	}
}
function addErr(){
	if (lang != "en_US") {
		$.showErr("添加失败");
	}else{
		$.showErr("Added Failed");
	}
}
function addNodeSuccess(){
	if (lang != "en_US") {
		$.showSuccessTimeout("新标签节点创建成功");
	}else{
		$.showSuccessTimeout("Added Successfully");
	}
}
function updateSuccess(){
	if (lang != "en_US") {
		$.showSuccessTimeout("修改成功");
	}else{
		$.showSuccessTimeout("Modify Successfully");
	}
}
function updateErr(){
	if (lang != "en_US") {
		$.showErr("修改失败");
	}else{
		$.showErr("Modify Failed");
	}
}
function roleEditSuccess(){
	if (lang != "en_US") {
		$.showSuccessTimeout("权限编辑成功");
	}else{
		$.showSuccessTimeout("Permissions Edited Successfully");
	}
}
function roleEditErr(){
	if (lang != "en_US") {
		$.showErr("权限编辑失败");
	}else{
		$.showErr("Permission Edit Failed");
	}
}
function fileMoveSuccess(){
	if (lang != "en_US") {
		$.showSuccessTimeout("文件移动成功");
	}else{
		$.showSuccessTimeout("File Moved Successfully");
	}
}
function fileMoveErr(){
	if (lang != "en_US") {
		$.showErr("文件移动失败");
	}else{
		$.showErr("File Move Failed");
	}
}
function collectSuccess(){
	if (lang != "en_US") {
		$.showSuccessTimeout("收藏成功");
	}else{
		$.showSuccessTimeout("Collection of success");
	}
}
function collectErr(){
	if (lang != "en_US") {
		$.showErr("已收藏");
	}else{
		$.showErr("have already collected");
	}
}
function emtyErr(){
	if (lang != "en_US") {
		$.showErr("内容不能为空");
	}else{
		$.showErr("The content cannot be empty");
	}
}
function selectErr(){
	if (lang != "en_US") {
		$.showErr("未选择节点");
	}else{
		$.showErr("Unselected node");
	}
}
function aliasSuccess(){
	if (lang != "en_US") {
		$.showSuccessTimeout("新建添加标签成功");
	}else{
		$.showSuccessTimeout("New add TAB successfully");
	}
}
function aliasErr(){
	if (lang != "en_US") {
		$.showErr("已添加过");
	}else{
		$.showErr("Have add");
	}
}
function existErr(){
	if (lang != "en_US") {
		$.showErr("已存在");
	}else{
		$.showErr("Already exist");
	}
}
function shareSuccess(){
	if (lang != "en_US") {
		$.showSuccessTimeout("分享成功");
	}else{
		$.showSuccessTimeout("Share Success");
	}
}
function shareErr(){
	if (lang != "en_US") {
		$.showErr("分享失败");
	}else{
		$.showErr("Share Failed");
	}
}
function nullErr(){
	if (lang != "en_US") {
		$.showErr("未选择数据");
	}else{
		$.showErr("Unselected Data");
	}
}
function restoreSuccess(){
	if (lang != "en_US") {
		$.showSuccessTimeout("还原成功");
	}else{
		$.showSuccessTimeout("Restoring Success");
	}
}
function syncSuccess(){
	if (lang != "en_US") {
		$.showSuccessTimeout("同步成功");
	}else{
		$.showSuccessTimeout("Sync Success");
	}
}
function syncErr(){
	if (lang != "en_US") {
		$.showErr("同步失败");
	}else{
		$.showErr("Sync Failed");
	}
}
function oneErr(){
	if (lang != "en_US") {
		$.showErr("只能选择一条数据");
	}else{
		$.showErr("Only one data item can be selected");
	}
}
function uploadErr(){
	if (lang != "en_US") {
		$.showErr("上传失败");
	}else{
		$.showErr("Upload Failed");
	}
}
function selectedErr(){
	if (lang != "en_US") {
		$.showErr("未选择密级，请先选择");
	}else{
		$.showErr("No secret class selected, please select first");
	}
}
function selectedRoleErr(){
	if (lang != "en_US") {
		$.showErr("未选择角色，请先选择");
	}else{
		$.showErr("No roles selected, please select first");
	}
}
function roleErr(){
	if (lang != "en_US") {
		$.showErr("您没有此权限");
	}else{
		$.showErr("You do not have this permission");
	}
}
function roleDeleteErr(){
	if (lang != "en_US") {
		$.showErr("角色有用户再使用,无法删除");
	}else{
		$.showErr("The role is in use by a user and cannot be deleted");
	}
}
function contentErr(){
	if (lang != "en_US") {
		$.showErr("未填写内容");
	}else{
		$.showErr("No content added");
	}
}
function dowErr(){
	if (lang != "en_US") {
		$.showErr("您无该文件操作权限");
	}else{
		$.showErr("You do not have file operation permission");
	}
}
function aliasChildrenErr(){
	if (lang != "en_US") {
		$.showErr("该节点存在数据，无法添加子级");
	}else{
		$.showErr("The node has data and cannot add children");
	}
}
function moverChildrenErr(){
	if (lang != "en_US") {
		$.showErr("该节点存在数据，无法移动");
	}else{
		$.showErr("The node has data and cannot be moved");
	}
}
function selectedUserErr(){
	if (lang != "en_US") {
		$.showErr("未选择用户");
	}else{
		$.showErr("Unselected user");
	}
}
function selectedMenuErr(){
	if (lang != "en_US") {
		$.showErr("未选择权限");
	}else{
		$.showErr("Permission not selected");
	}
}
function selectedNodeErr(){
	if (lang != "en_US") {
		$.showErr("请选择子节点");
	}else{
		$.showErr("Select the child node");
	}
}
function folderEmptyErr(){
	if (lang != "en_US") {
		$.showErr("没有收藏节点,需在个人收藏里添加");
	}else{
		$.showErr("No collection node, need to add in personal collection");
	}
}
function searchNullErr(){
	if (lang != "en_US") {
		$.showErr("您未填写搜索条件");
	}else{
		$.showErr("You did not fill in the search criteria");
	}
}
function WidthCheck(str, maxLen){ 
    var w = 0; 
    var tempCount = 0; 
    //length 获取字数数，不区分汉子和英文 
    for (var i=0; i<str.value.length; i++) { 
        //charCodeAt()获取字符串中某一个字符的编码 
        var c = str.value.charCodeAt(i); 
        //单字节加1 
        if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) { 
            w++; 
        } else { 
            w+=2; 
        } 
        if (w > maxLen) { 
            str.value = str.value.substr(0,i); 
            break; 
         } 
    } 
}