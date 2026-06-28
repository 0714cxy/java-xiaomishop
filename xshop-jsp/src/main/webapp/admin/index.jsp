<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("admin") == null) {
        response.sendRedirect(request.getContextPath() + "/admin/login");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>XShop后台管理系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
</head>
<body class="main_body">
    <div class="layui-layout layui-layout-admin">
        <div class="layui-header header">
            <div class="layui-main mag0">
                <a href="#" class="logo">XShop后台管理系统</a>
                <ul class="layui-nav top_menu" style="position:absolute;right:0;top:0;">
                    <li class="layui-nav-item">
                        <a href="javascript:;">
                            <cite>管理员：${sessionScope.admin}</cite>
                        </a>
                        <dl class="layui-nav-child">
                            <dd><a href="${pageContext.request.contextPath}/admin/logout">退出登录</a></dd>
                        </dl>
                    </li>
                </ul>
            </div>
        </div>

        <div class="layui-side layui-bg-black">
            <div class="user-photo">
                <p style="text-align:center;color:#fff;padding:20px 0;">XShop 管理平台</p>
            </div>
            <div class="navBar layui-side-scroll">
                <ul class="layui-nav layui-nav-tree">
                    <li class="layui-nav-item layui-this">
                        <a href="javascript:;" data-url="dashboard.jsp"><i class="layui-icon">&#xe68e;</i> 后台首页</a>
                    </li>
                    <li class="layui-nav-item">
                        <a href="javascript:;" data-url="userList.jsp"><i class="layui-icon">&#xe612;</i> 用户管理</a>
                    </li>
                    <li class="layui-nav-item">
                        <a href="javascript:;" data-url="shopList.jsp"><i class="layui-icon">&#xe64a;</i> 商品管理</a>
                    </li>
                    <li class="layui-nav-item">
                        <a href="javascript:;" data-url="cartList.jsp"><i class="layui-icon">&#xe657;</i> 购物车管理</a>
                    </li>
                </ul>
            </div>
        </div>

        <div class="layui-body" style="padding:15px;">
            <iframe id="mainFrame" src="dashboard.jsp" style="width:100%;height:100%;border:none;"></iframe>
        </div>

        <div class="layui-footer footer" style="text-align:center;">
            <p>Copyright &copy; 2024 XShop - Java Web 课程设计</p>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/layui/layui.js"></script>
    <script>
        layui.use(['element','jquery'],function(){
            var element = layui.element, $ = layui.jquery;
            $('.layui-nav-item a[data-url]').on('click', function(){
                var url = $(this).data('url');
                $('#mainFrame').attr('src', url);
                $('.layui-nav-item').removeClass('layui-this');
                $(this).parent().addClass('layui-this');
            });
        });
    </script>
</body>
</html>
