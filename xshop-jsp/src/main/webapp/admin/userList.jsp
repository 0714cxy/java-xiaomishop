<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>用户管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css">
</head>
<body style="padding:15px;">
    <div style="margin-bottom:10px;">
        <button class="layui-btn layui-btn-normal" id="addUserBtn"><i class="layui-icon">&#xe654;</i> 添加用户</button>
    </div>
    <table id="userTable" lay-filter="userTable"></table>

    <script type="text/html" id="bar">
        <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
        <a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a>
    </script>

    <script src="${pageContext.request.contextPath}/layui/layui.js"></script>
    <script>
        layui.use(['table','layer','form'], function(){
            var table = layui.table, layer = layui.layer, form = layui.form, $ = layui.jquery;

            var tableIns = table.render({
                elem: '#userTable',
                url: '${pageContext.request.contextPath}/admin/api/getAllUser',
                page: true,
                limit: 20,
                limits: [10,20,30],
                cols: [[
                    {field: 'id', title: 'ID', width: 60},
                    {field: 'username', title: '用户名', width: 120},
                    {field: 'nickname', title: '昵称', width: 120},
                    {field: 'avatar', title: '头像', width: 100, templet: function(d){
                        return d.avatar ? '<img src="'+d.avatar+'" style="height:40px;border-radius:50%;">' : '无';
                    }},
                    {field: 'sign', title: '个性签名'},
                    {field: 'site', title: '地址', width: 150},
                    {field: 'lastLogin', title: '最后登录', width: 160},
                    {title: '操作', toolbar: '#bar', width: 150, fixed: 'right'}
                ]]
            });

            $('#addUserBtn').on('click', function(){
                layer.open({
                    title: '添加用户',
                    type: 1,
                    area: ['500px','400px'],
                    content: '<form class="layui-form" style="padding:20px;" id="addForm">'+
                        '<div class="layui-form-item"><label class="layui-form-label">用户名</label><div class="layui-input-block"><input name="username" class="layui-input" required></div></div>'+
                        '<div class="layui-form-item"><label class="layui-form-label">密码</label><div class="layui-input-block"><input name="password" type="password" class="layui-input" required></div></div>'+
                        '<div class="layui-form-item"><label class="layui-form-label">昵称</label><div class="layui-input-block"><input name="nickname" class="layui-input" required></div></div>'+
                        '<div class="layui-form-item"><div class="layui-input-block"><button class="layui-btn" lay-submit>保存</button></div></div>'+
                        '</form>',
                    success: function(layero){
                        form.render();
                        form.on('submit', function(data){
                            $.post('${pageContext.request.contextPath}/admin/api/addUser', data.field, function(res){
                                if(res.code === 1){
                                    layer.msg('添加成功', {icon:1, time:1000}, function(){
                                        layer.closeAll();
                                        tableIns.reload();
                                    });
                                } else {
                                    layer.msg('添加失败', {icon:2});
                                }
                            });
                            return false;
                        });
                    }
                });
            });

            table.on('tool(userTable)', function(obj){
                var data = obj.data;
                if(obj.event === 'del'){
                    layer.confirm('确定删除此用户？', function(index){
                        $.post('${pageContext.request.contextPath}/admin/api/deleteUser', {userID: data.id}, function(res){
                            if(res.code === 1){
                                layer.msg('删除成功');
                                tableIns.reload();
                            } else {
                                layer.msg('删除失败');
                            }
                        });
                        layer.close(index);
                    });
                } else if(obj.event === 'edit'){
                    layer.open({
                        title: '编辑用户',
                        type: 1,
                        area: ['500px','450px'],
                        content: '<form class="layui-form" style="padding:20px;" id="editForm">'+
                            '<input type="hidden" name="userID" value="'+data.id+'">'+
                            '<div class="layui-form-item"><label class="layui-form-label">用户名</label><div class="layui-input-block"><input name="username" class="layui-input" value="'+data.username+'"></div></div>'+
                            '<div class="layui-form-item"><label class="layui-form-label">密码</label><div class="layui-input-block"><input name="password" type="password" class="layui-input" placeholder="留空则不修改"></div></div>'+
                            '<div class="layui-form-item"><label class="layui-form-label">昵称</label><div class="layui-input-block"><input name="nickname" class="layui-input" value="'+(data.nickname||'')+'"></div></div>'+
                            '<div class="layui-form-item"><label class="layui-form-label">头像</label><div class="layui-input-block"><input name="avatar" class="layui-input" value="'+(data.avatar||'')+'"></div></div>'+
                            '<div class="layui-form-item"><label class="layui-form-label">签名</label><div class="layui-input-block"><input name="sign" class="layui-input" value="'+(data.sign||'')+'"></div></div>'+
                            '<div class="layui-form-item"><label class="layui-form-label">地址</label><div class="layui-input-block"><input name="site" class="layui-input" value="'+(data.site||'')+'"></div></div>'+
                            '<div class="layui-form-item"><div class="layui-input-block"><button class="layui-btn" lay-submit>保存</button></div></div>'+
                            '</form>',
                        success: function(layero){
                            form.render();
                            form.on('submit', function(data){
                                $.post('${pageContext.request.contextPath}/admin/api/changeUser', data.field, function(res){
                                    if(res.code === 1){
                                        layer.msg('修改成功', {icon:1, time:1000}, function(){
                                            layer.closeAll();
                                            tableIns.reload();
                                        });
                                    } else {
                                        layer.msg('修改失败', {icon:2});
                                    }
                                });
                                return false;
                            });
                        }
                    });
                }
            });
        });
    </script>
</body>
</html>
