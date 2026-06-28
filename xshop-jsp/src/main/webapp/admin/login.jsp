<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>管理员登录 - XShop管理系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css">
    <style>
        body{background:#f2f2f2;}
        .login-box{width:400px;margin:120px auto;padding:40px;background:#fff;border-radius:8px;box-shadow:0 2px 12px rgba(0,0,0,0.1);}
        .login-box h2{text-align:center;margin-bottom:30px;color:#333;}
    </style>
</head>
<body>
    <div class="login-box">
        <h2>XShop 后台管理系统</h2>
        <form class="layui-form" action="${pageContext.request.contextPath}/admin/login" method="post">
            <div class="layui-form-item">
                <label class="layui-form-label">用户名</label>
                <div class="layui-input-block">
                    <input type="text" name="username" required lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">密码</label>
                <div class="layui-input-block">
                    <input type="password" name="password" required lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit lay-filter="loginBtn">登录</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
            <% if (request.getAttribute("error") != null) { %>
                <div style="color:red;text-align:center;"><%= request.getAttribute("error") %></div>
            <% } %>
        </form>
    </div>
    <script src="${pageContext.request.contextPath}/layui/layui.js"></script>
    <script>
        layui.use(['form','layer'],function(){
            var form = layui.form, $ = layui.jquery, layer = layui.layer;
            form.on('submit(loginBtn)', function(data){
                $.ajax({
                    url: '${pageContext.request.contextPath}/admin/login',
                    type: 'post',
                    data: data.field,
                    success: function(res){
                        if(res.code === 1){
                            layer.msg('登录成功', {icon:1, time:1000}, function(){
                                window.location.href = '${pageContext.request.contextPath}/admin/index.jsp';
                            });
                        } else {
                            layer.msg(res.msg, {icon:2});
                        }
                    }
                });
                return false;
            });
        });
    </script>
</body>
</html>
