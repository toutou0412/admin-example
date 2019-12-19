mainTemplateLayui.use(['index', 'form'], function () {
    var layuiSelector = layui.$
        , form = layui.form;
})

mainTemplateLayui.define(["form", "upload"], function (t) {
    var layuiSelector = layui.$, layer = layui.layer, form = layui.form;

    form.render();

    form.verify({
        password: [
            /^[\S]{6,12}$/,
            "密码必须6到12位，且不能出现空格"
        ]
        , repass: function (value) {
            if (value !== layuiSelector("#LAY_password").val()){
                return "两次密码输入不一致";
            }
        }
    });

    form.on('submit(setmypass)', function (data) {
        var formData = data.field;
        $.ajax({
            url: '/sys/user/setPassword',
            data: JSON.stringify(formData),
            contentType: 'application/json',
            type: 'POST',
            success: function (result) {
                // Do something with the result
                if (!result) {
                    layer.msg('未知错误');
                } else if (result.code == 0) {
                    layer.msg('修改成功,请重新登陆',function (obj) {
                        // window.parent.location.href = "/logout";
                    });
                } else {
                    layer.msg(result.msg);
                }
            },
            error: function () {
                layer.msg('请求错误');
            }
        });
    });

});