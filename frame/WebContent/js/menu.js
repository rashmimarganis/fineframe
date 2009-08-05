var refresh = 0;
var menuid = 0;
var firstMenuName="";
var totalCount=0;
var totalPage=1;
var limit=10;
var currentPage=1;
var pageUrl="";
function hideLoading(){
	setTimeout("$(\".loading\").css(\"display\",\"none\");",100);
}
function loadMenu(menuId,title){
	$("#nextMenus").html("正在加载菜单...");
	$("#menu_name").html(title);
	$("#firstMenu").html("<a href='#'>"+title+"</a>");
	$("#secondMenu").empty();
	setTimeout("loadMenuExt('"+menuId+"','"+title+"')",100);
}
function loadMenuExt(pn,title){
	$("#nextMenus").load("leftMenu.jhtm?pid="+pn,function(){
		$("a").mouseover(function(){
			window.status="FineCMS";
		}).mouseout(function(){
			window.status="FineCMS";
		});
		$(this).treeview();
	});
}
function loadPage(page){
	$(".loading").css("display","block");
	$("#right").empty();
	setTimeout("loadPageExt('"+page+"')",300);
}
function loadPageExt(page){
	$("#right").load(page,function(){
		hideLoading();
	});
}
function loadRight(page ,title){
	$("#right").empty();
	$(".loading").css("display","block");
	$("#secondMenu").html("<a href='#'>"+title+"</a>");
	setTimeout("loadRightExt('"+page+"','"+title+"')",300);
}
function loadRightExt(page,title){
	
	$("#right").load(page,function(){
		$("a").mouseover(function(){
			window.status="FineCMS";
		}).mouseout(function(){
			window.status="FineCMS";
		});
		hideLoading();
	});
}
function resize(){
	var height=window.document.body.clientHeight;
	height=height*1-100;
	$("#admin_left").css("height",height+'px');
	$("#right").css("height",height+'px');
}
$(document).ready(function(){
	$(".menu").click(function(){
		$(".menu").removeClass("selected");
		$(this).addClass("selected");
	});
	$(window).resize(function(){
		resize();
	}); 
	resize();
});

