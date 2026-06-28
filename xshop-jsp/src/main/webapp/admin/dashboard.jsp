<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>后台首页</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css">
</head>
<body style="padding:20px;background:#f2f2f2;">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md6">
            <div class="layui-card">
                <div class="layui-card-header">系统概览</div>
                <div class="layui-card-body">
                    <table class="layui-table">
                        <tr><td>商品总数</td><td id="shopNum">加载中...</td></tr>
                        <tr><td>用户总数</td><td id="userNum">加载中...</td></tr>
                        <tr><td>管理员</td><td><%= session.getAttribute("admin") %></td></tr>
                        <tr><td>服务器</td><td><%= application.getServerInfo() %></td></tr>
                        <tr><td>Servlet版本</td><td><%= application.getMajorVersion() %>.<%= application.getMinorVersion() %></td></tr>
                    </table>
                </div>
            </div>
        </div>
        <div class="layui-col-md6">
            <div class="layui-card">
                <div class="layui-card-header">项目介绍</div>
                <div class="layui-card-body">
                    <p><strong>XShop 小米商城后台管理系统</strong></p>
                    <p>技术栈：JSP + Servlet + JavaBean + JDBC + DAO + MVC</p>
                    <p>数据库：MySQL</p>
                    <p>前端：Layui + HTML + CSS + JavaScript</p>
                    <hr>
                    <p>功能模块：</p>
                    <ul>
                        <li>管理员登录/登出</li>
                        <li>用户管理（列表/添加/修改/删除）</li>
                        <li>商品管理（列表/添加/修改/删除）</li>
                        <li>购物车管理（列表查看）</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/layui/layui.js"></script>
    <script>
        layui.use(['jquery','layer'],function(){
            var $ = layui.jquery;
            $.get('${pageContext.request.contextPath}/admin/api/getIndex', function(res){
                if(res.code === 1 && res.data){
                    $('#shopNum').text(res.data.shop);
                    $('#userNum').text(res.data.user);
                }
            });
        });
    </script>
</body>
</html>
