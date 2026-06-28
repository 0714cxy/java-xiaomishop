<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>商品管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css">
    <style>td img{max-height:60px;}</style>
</head>
<body style="padding:15px;">
    <div style="margin-bottom:10px;">
        <button class="layui-btn" id="addShopBtn"><i class="layui-icon">&#xe654;</i> 添加商品</button>
    </div>
    <table id="shopTable" lay-filter="shopTable"></table>

    <script type="text/html" id="bar">
        <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
        <a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a>
    </script>

    <script src="${pageContext.request.contextPath}/layui/layui.js"></script>
    <script>
        layui.use(['table','layer','form'], function(){
            var table = layui.table, layer = layui.layer, form = layui.form, $ = layui.jquery;

            var tableIns = table.render({
                elem: '#shopTable',
                url: '${pageContext.request.contextPath}/admin/api/getAllShop',
                page: true,
                limit: 10,
                limits: [5,10,20],
                cols: [[
                    {field: 'id', title: 'ID', width: 80},
                    {field: 'name', title: '商品名', minWidth: 150},
                    {field: 'price', title: '价格', width: 80},
                    {field: 'oldPrice', title: '原价', width: 80},
                    {field: 'description', title: '描述', minWidth: 200},
                    {field: 'img', title: '图片', width: 120, templet: function(d){
                        var imgs = d.img ? d.img.split('&&') : [];
                        return imgs.length > 0 ? '<img src="'+imgs[0]+'">' : '无';
                    }},
                    {field: 'sort', title: '类别', width: 80},
                    {fixed: 'right', title: '操作', toolbar: '#bar', width: 150}
                ]]
            });

            function getCategoryName(sort){
                var names = {1:'手机电话卡',2:'电视盒子',3:'笔记本/显示器',4:'家电',5:'出行穿戴',6:'智能路由',7:'电源配件',8:'健康儿童',9:'耳机音响',10:'生活箱包'};
                return names[sort] || '其他';
            }

            $('#addShopBtn').on('click', function(){
                layer.open({
                    title: '添加商品',
                    type: 1,
                    area: ['600px','550px'],
                    content: '<form class="layui-form" style="padding:20px;" id="addForm">'+
                        '<div class="layui-form-item"><label class="layui-form-label">ID</label><div class="layui-input-block"><input name="id" class="layui-input" required></div></div>'+
                        '<div class="layui-form-item"><label class="layui-form-label">名称</label><div class="layui-input-block"><input name="name" class="layui-input" required></div></div>'+
                        '<div class="layui-form-item"><label class="layui-form-label">价格</label><div class="layui-input-block"><input name="price" class="layui-input" required></div></div>'+
                        '<div class="layui-form-item"><label class="layui-form-label">原价</label><div class="layui-input-block"><input name="oldPrice" class="layui-input" required></div></div>'+
                        '<div class="layui-form-item"><label class="layui-form-label">描述</label><div class="layui-input-block"><input name="description" class="layui-input"></div></div>'+
                        '<div class="layui-form-item"><label class="layui-form-label">图片URL</label><div class="layui-input-block"><input name="img" class="layui-input"></div></div>'+
                        '<div class="layui-form-item"><label class="layui-form-label">类别</label><div class="layui-input-block"><select name="sort">'+
                            '<option value="1">手机电话卡</option><option value="2">电视盒子</option><option value="3">笔记本/显示器</option>'+
                            '<option value="4">家电</option><option value="5">出行穿戴</option><option value="6">智能路由</option>'+
                            '<option value="7">电源配件</option><option value="8">健康儿童</option><option value="9">耳机音响</option><option value="10">生活箱包</option>'+
                        '</select></div></div>'+
                        '<div class="layui-form-item"><label class="layui-form-label">其他</label><div class="layui-input-block"><input name="other" class="layui-input"></div></div>'+
                        '<div class="layui-form-item"><div class="layui-input-block"><button class="layui-btn" lay-submit>保存</button></div></div>'+
                        '</form>',
                    success: function(layero){
                        form.render();
                        form.on('submit', function(data){
                            $.post('${pageContext.request.contextPath}/admin/api/addShop', data.field, function(res){
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

            table.on('tool(shopTable)', function(obj){
                var data = obj.data;
                if(obj.event === 'del'){
                    layer.confirm('确定删除此商品？', function(index){
                        $.post('${pageContext.request.contextPath}/admin/api/deleteShop', {shopID: data.id}, function(res){
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
                        title: '编辑商品',
                        type: 1,
                        area: ['600px','550px'],
                        content: '<form class="layui-form" style="padding:20px;" id="editForm">'+
                            '<input type="hidden" name="shopID" value="'+data.id+'">'+
                            '<div class="layui-form-item"><label class="layui-form-label">名称</label><div class="layui-input-block"><input name="name" class="layui-input" value="'+(data.name||'')+'"></div></div>'+
                            '<div class="layui-form-item"><label class="layui-form-label">价格</label><div class="layui-input-block"><input name="price" class="layui-input" value="'+data.price+'"></div></div>'+
                            '<div class="layui-form-item"><label class="layui-form-label">原价</label><div class="layui-input-block"><input name="oldPrice" class="layui-input" value="'+data.oldPrice+'"></div></div>'+
                            '<div class="layui-form-item"><label class="layui-form-label">描述</label><div class="layui-input-block"><input name="description" class="layui-input" value="'+(data.description||'')+'"></div></div>'+
                            '<div class="layui-form-item"><label class="layui-form-label">图片URL</label><div class="layui-input-block"><input name="img" class="layui-input" value="'+(data.img||'')+'"></div></div>'+
                            '<div class="layui-form-item"><label class="layui-form-label">类别</label><div class="layui-input-block"><select name="sort">'+
                                '<option value="1">手机电话卡</option><option value="2">电视盒子</option><option value="3">笔记本/显示器</option>'+
                                '<option value="4">家电</option><option value="5">出行穿戴</option><option value="6">智能路由</option>'+
                                '<option value="7">电源配件</option><option value="8">健康儿童</option><option value="9">耳机音响</option><option value="10">生活箱包</option>'+
                            '</select></div></div>'+
                            '<div class="layui-form-item"><label class="layui-form-label">其他</label><div class="layui-input-block"><input name="other" class="layui-input" value="'+(data.other||'')+'"></div></div>'+
                            '<div class="layui-form-item"><div class="layui-input-block"><button class="layui-btn" lay-submit>保存</button></div></div>'+
                            '</form>',
                        success: function(layero){
                            form.render();
                            $('select[name="sort"]').val(data.sort);
                            form.render('select');
                            form.on('submit', function(data){
                                $.post('${pageContext.request.contextPath}/admin/api/changeShop', data.field, function(res){
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
