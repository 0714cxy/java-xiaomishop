<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>购物车管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css">
    <style>td img{max-height:50px;}</style>
</head>
<body style="padding:15px;">
    <table id="cartTable" lay-filter="cartTable"></table>

    <script src="${pageContext.request.contextPath}/layui/layui.js"></script>
    <script>
        layui.use(['table','layer'], function(){
            var table = layui.table;

            table.render({
                elem: '#cartTable',
                url: '${pageContext.request.contextPath}/admin/api/getAllCart',
                page: true,
                limit: 20,
                limits: [10,20,30],
                cols: [[
                    {field: 'nickname', title: '用户昵称', width: 120},
                    {field: 'shopName', title: '商品名称', minWidth: 150},
                    {field: 'img', title: '商品图片', width: 150, templet: function(d){
                        var imgs = d.img ? d.img.split('&&') : [];
                        return imgs.length > 0 ? '<img src="'+imgs[0]+'" style="max-height:50px;">' : '无';
                    }},
                    {field: 'price', title: '价格', width: 80, templet: function(d){
                        return '<span style="color:#ff6700;">'+d.price+'元</span>';
                    }},
                    {field: 'count', title: '数量', width: 80}
                ]]
            });
        });
    </script>
</body>
</html>
