var addTabs = function (options) {
    // 可以在此处验证session
    // var rand = Math.random().toString();
    // var id = rand.substring(rand.indexOf('.') + 1);
    // debugger;
    var url = window.location.protocol + '//' + window.location.host;
    if(options.url==""){
    	options.url="error.html"
    }
    console.log(options)
    // options.url = url + options.url;
    // options.url = options.url+"?lang="+getUrlParam("lang");
    id = "tab_" + options.id;
    var active_flag = false;
    if ($("#" + id)) {
        active_flag = $("#" + id).hasClass('active');
    }
    $(".duoji").removeClass('xuanzhongduoji');
    if(options.last == true){
    	 $(".click-status").find('div').css('color','#fff');
    	$(".click-status").addClass('menu-li');
        $(".click-status").removeClass('click-status');
        $(".sub-menu").hide(100);
    }
    
    $(".menu-li-title").removeClass('xuanzhong');
    $(".dianji"+options.id).addClass('xuanzhong');
   
    $(".dianjiduoji"+options.id).addClass('xuanzhongduoji');
    $(".active").removeClass("active");
    // 如果TAB不存在，创建一个新的TAB
    if (!$("#" + id)[0]) {
        // 固定TAB中IFRAME高度
        // mainHeight = $(document.body).height();
        // console.log(mainHeight)
        // 创建新TAB的title
        title = '<li role="presentation" id="tab_' + id + '"><a href="#' + id + '" aria-controls="' + id + '" role="tab" data-toggle="tab"><i class="' + options.icon + '"></i>' + options.title;
        // 是否允许关闭
        if (options.close) {
            title += ' <i class="icon-remove-sign" tabclose="' + id + '"></i>';
        }
        title += '</a></li>';
        // 是否指定TAB内容
        if (options.content) {
        		content = '<div role="tabpanel" class="tab-pane" id="' + id + '">' + options.content + '</div>';
        } else {// 没有内容，使用IFRAME打开链接
        	if(options.url.indexOf('html/yanzhengjuzhen.html')<0){
        		if(options.url.indexOf('/list')==0){
        			console.log(options.url)
            		content = '<div role="tabpanel" class="tab-pane" id="' + id + '"><iframe id="iframe_' + id + '" src="' + options.url +
                    '" width="100%" onload="changeFrameHeight(this)" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe></div>';
            	}else{
            		content = '<div role="tabpanel" class="tab-pane" id="' + id + '"><iframe id="iframe_' + id + '" src="'+'../office?url=' + options.url +
                    '" width="100%" onload="changeFrameHeight(this)" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe></div>';
            	}
        		
        	}else{
        		content = '<div role="tabpanel" class="tab-pane" id="' + id + '"><iframe id="iframe_' + id + '" src="' + options.url +
                '" width="100%" height="100%" onload="changeFrameHeight(this)" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe></div>';
        	}
        }
        // 加入TABS
        $(".nav-tabs").append(title);
        $(".tab-content").append(content);
    } else {
    	if(options.url.indexOf('/list')==0){
			console.log(options.url)
			$("#iframe_" + id).attr('src',options.url);
    	}else{
    		$("#iframe_" + id).attr('src', $("#iframe_" + id).attr('src'));
    	}
        //if (active_flag) {
          //  $("#iframe_" + id).attr('src', $("#iframe_" + id).attr('src'));
       // }
    }
    // 激活TAB
    $("#tab_" + id).addClass('active');
    $("#" + id).addClass("active");
    
    if(options.parentTitle!="undefined"&&options.parent2Title!="undefined"&&options.parentTitle!=""&&options.parent2Title!=""){
    	$("#text").text(options.parent2Title+">"+options.parentTitle);
    }else if(options.parentTitle!="undefined"&&options.parentTitle!=""&&options.title!="undefined"&&options.title!=""){
    	 $("#text").text(options.parentTitle+">"+options.title);
    }else if(options.title!="undefined"&&options.title!=""){
    	$("#text").text(options.title);
    }
    //$("#textId").val(options.parentTitle);
    
    // 激活左边菜单
    /*
	 * $('#menu li').removeClass('active'); $('#li_' +
	 * options.id).addClass("active");
	 */
};
var changeFrameHeight = function (that) {
    $(that).height(document.documentElement.clientHeight - 130);
    $(that).parent(".tab-pane").height(document.documentElement.clientHeight - 130);
}
 function getUrlParam(name) {
	        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	        var r = window.location.search.substr(1).match(reg);  // 匹配目标参数
	        if (r != null) return unescape(r[2]);
	        return null; // 返回参数值
	    }
var closeTab = function (id) {
    // 如果关闭的是当前激活的TAB，激活他的前一个TAB
    if ($("#ulheight li.active").attr('id') == "tab_" + id) {
        $("#tab_" + id).prev().addClass('active');
        $("#" + id).prev().addClass('active');
       /*
		 * var str = id.replace(id.substring(0, id.lastIndexOf("_") + 1), "");
		 * $('#li_' + str).removeClass("active"); if ($("#" + id).prev()[0].id !=
		 * "Index") { var s = $("#" + id).prev()[0].id; var str2 =
		 * s.replace(s.substring(0, s.lastIndexOf("_") + 1), ""); $('#li_' +
		 * str2).addClass("active"); }
		 */
    }
    // 关闭TAB
    $("#tab_" + id).remove();
    $("#" + id).remove();
    
    $("#text").text($("#ulheight li.active")[0].innerText);
};
$(function () {
    $("[addtabs]").click(function () {
        addTabs({id: $(this).attr("id"), title: $(this).attr('title'), close: true});
    });

    $(".nav-tabs").on("click", "[tabclose]", function (e) {
        id = $(this).attr("tabclose");
        closeTab(id);
    });
    $('.nav-tabs').click(function (e) {
        var eid = e.target.hash;
        if (eid != "Index") {
            /*
			 * var str = eid.split("_"); $('#menu li').removeClass('active');
			 * $('#li_' + str[1]).addClass("active");
			 */
        }
    });
    window.onresize = function () {
        var target = $(".tab-content .active iframe");
        changeFrameHeight(target);
    }

});
