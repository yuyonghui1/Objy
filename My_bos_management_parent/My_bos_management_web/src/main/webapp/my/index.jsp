<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
</head>
<body>
	
	<div class="easyui-layout" fit="true" style="width:700px;height:350px;">
		<div data-options="region:'north'" style="height:110px">
			<h1>后台界面</h1>
			<a id="menu"  style="width:150px" class="easyui-menubutton" data-options="menu:'#mm',iconCls:'icon-edit'">
				-----菜单-----
			</a>
			<div id="mm" style="width:150px">
				<div data-options="iconCls:'icon-cancel'" onclick="closeAllTabs()">关闭所有标签页</div>
				<div>xxxxxxx</div>
				<div>yyyyyyy</div>
				<div data-options="iconCls:'icon-cancel'" onclick="logout()">退出登录</div>
			</div>
				<script type="text/javascript">
					function closeAllTabs(){
						$.messager.confirm('Confirm', '确认关闭?', 
								function(r){if (r){
									$.messager.alert("消息","关闭所有不会写,选不好");
								} });  
						
					}
					
					function logout(){
						$.messager.confirm('Confirm', '确认退出吗?', 
								function(r){if (r){

									location.href="${pageContext.request.contextPath }/user_logout.action";
								} });  
					}
					
						$.messager.show({  	
						  title:'测试消息弹出',  	
						  msg:'3秒后关闭',  	
						  timeout:3000,  	
						  showType:'slide'  
						}); 
				</script>
		</div>
		
		<div data-options="region:'south',split:true" style="height:50px;">
			<h1>xxxxxx</h1>
		</div>
		
		<div data-options="region:'west'" title="系统菜单" style="width:210px;">
			<!-- accordion,菜单 -->	
			<div class="easyui-accordion" data-options="fit:true,border:0" style="width:500px;height:300px;">
				<div title="操作选项" data-options="iconCls:'icon-edit'" style="padding:10px;">
					<!-- Ztree -->
					<ul id="treeDemo" class="ztree"></ul>
				</div>
				<shiro:hasRole name="admin">
					<div title="系统管理" data-options="iconCls:'icon-mini-add'" style="overflow:auto">
						<ul id="adminMenu" class="ztree"></ul>
					</div>
				</shiro:hasRole>
					<SCRIPT type="text/javascript">
						
						var setting = {
							data: {
								simpleData: {
									enable: true
								}
							},
							callback:{
								onClick:function(event, treeId, treeNode){
									
									if(treeNode.page){
										
										if($("#tabs").tabs("exists",treeNode.name)){
											$("#tabs").tabs("select",treeNode.name);
										}else{
											
											$("#tabs").tabs("add",
												{
													title: treeNode.name,
										         	closable:true,
										         	border:0,
										         	content:'<iframe frameborder="0" src="../'+treeNode.page+'" height="100%" width="100%"></iframe>',
										        	tools:[{ 
														iconCls:'icon-reload', // 刷新按钮
														handler : function(){
															var tab = $('#tabs').tabs('getTab',treeNode.name);
															//$("iframe[src='"+treeNode.page+"']").get(0).contentWindow.location.reload(true);
															
															$('#tabs').tabs('update', {
																tab: tab,
																options: {
																	title: treeNode.name,
																	content:'<iframe frameborder="0" src="../'+treeNode.page+'" height="100%" width="100%"></iframe>'
																}
															});

														}
													}] 
												}		
											);
										}
									}
								}
							}
						};
				
						$.post("${pageContext.request.contextPath }/menu_findSimpleTree.action",{"a":"a"},
							function(data){
								$.fn.zTree.init($("#treeDemo"), setting,data);
							}
						)
						
						$.post("${pageContext.request.contextPath }/data/admin.json",function(data){
							$.fn.zTree.init($("#adminMenu"), setting, data);
						},"json");
						
						
					</SCRIPT>
				<div title="About" data-options="iconCls:'icon-ok'" style="overflow:auto;padding:10px;">
				
				</div>
				<div title="Help" data-options="iconCls:'icon-help'" style="padding:10px;">
					<p>The accordion allows you to provide multiple panels and display one or more at a time. Each panel has built-in support for expanding and collapsing. Clicking on a panel header to expand or collapse that panel body. The panel content can be loaded via ajax by specifying a 'href' property. Users can define a panel to be selected. If it is not specified, then the first panel is taken by default.</p> 		
				</div>
			</div>
		</div>
		
		<div data-options="region:'center',title:'Main Title',iconCls:'icon-ok'">
			<!-- 中间显示模块 ,tabs-->
			<div id="tabs" border=0 class="easyui-tabs" fit="true" ></div>
		</div>
	
</body>
</html>