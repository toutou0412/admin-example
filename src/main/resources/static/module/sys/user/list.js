// 定义搜索、操作按钮的执行方法
listTemplateLayui.use(['index', 'table', 'laydate'], function () {
    var layuiSelector = layui.$
        , form = layui.form
        , laydate = layui.laydate
        , table = layui.table;

    //日期范围
    laydate.render({
        elem: '#test-laydate-range-date'
        ,range: true
    });

    //监听搜索
    form.on('submit(LAY-sys-user-search)', function (data) {
        var field = data.field;

        //执行重载
        table.reload('LAY-sys-user-manage', {
            where: field
        });
    });

    // 操作按钮事件
    var active = {
        // 删除
        batchdel: function () {
            var checkStatus = table.checkStatus('LAY-sys-user-manage')
                , checkData = checkStatus.data; //得到选中的数据

            if (checkData.length === 0) {
                return layer.msg('请选择数据');
            }

            layer.prompt({
                formType: 0
                , title: '敏感操作，请验证口令'
            }, function (value, index) {
                layer.close(index);

                var ids = [checkData.length];
                for (var i = 0; i < checkData.length; i++) {
                    ids[i] = checkData[i].id;
                }

                layer.confirm('确定删除吗？', function (index) {
                    //执行 Ajax 后重载
                    layuiSelector.ajax({
                        url: '/sys/user/delete',
                        data: 'ids=' + ids,
                        type: 'DELETE',
                        success: function (result) {
                            // Do something with the result
                            if (!result) {
                                layer.msg('未知错误');
                            } else if (result.code == 0) {
                                table.reload('LAY-sys-user-manage');
                                layer.msg('已删除');
                            } else {
                                layer.msg(result.msg);
                            }
                        },
                        error: function () {
                            layer.close(index);
                            layer.msg('请求错误');
                        }
                    });
                });
            });
        }
        , add: function () {
            layer.open({
                type: 2
                , title: '添加'
                , content: '/sys/user/dataView?action=0'
                , maxmin: true
                , area: ["500px", "450px"]
                , btn: ['确定', '取消']
                , yes: function (iframeIndex, layero) {
                    var iframeWindow = window['layui-layer-iframe' + iframeIndex]
                        , submitID = 'LAY-sys-user-submit'
                        , submit = layero.find('iframe').contents().find('#' + submitID);

                    // 监听提交
                    iframeWindow.layui.form.on('submit(' + submitID + ')', function (iframeData) {
                        // 获取提交的字段
                        var formData = iframeData.field;

                        //提交 Ajax 成功后，静态更新表格中的数据
                        layuiSelector.ajax({
                            url: '/sys/user/add',
                            data: JSON.stringify(formData),
                            contentType: 'application/json',
                            type: 'PUT',
                            success: function (result) {
                                // Do something with the result
                                if (!result) {
                                    layer.msg('未知错误');
                                } else if (result.code == 0) {
                                    // 刷新数据
                                    table.reload("LAY-sys-user-manage");
                                    // 关闭弹层
                                    layer.close(iframeIndex);
                                    layer.msg('添加成功');
                                } else {
                                    layer.msg(result.msg);
                                }
                            },
                            error: function () {

                                layer.msg('请求错误');
                            }
                        });

                    });

                    submit.trigger('click');
                }
            });
        }
    };

    layuiSelector('.layui-btn.layuiadmin-btn-opt').on('click', function () {
        var type = layuiSelector(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
});

// 渲染表格
listTemplateLayui.define(["table", "form"], function (e) {
    var lay = layui.$, data_table = layui.table;
    layui.form;
    data_table.render({
        elem: "#LAY-sys-user-manage",
        url: "/sys/user/listData",
        cols: [
            [
                {type: "checkbox", fixed: "left"}
                , {field: "id", width: 80, title: "ID", sort: true}
                , {field: "username", title: "登录名"}
                , {field: "password", title: "密码", sort: true}
                , {field: "phone", title: "手机"}
                , {field: "realName", title: "真实名称"}
                , {field: "sex", title: "性别", align: "center", templet: "#sexTpl"}
                , {field: "email", title: "邮箱"}
                , {title: "操作", width: 250, align: "center", fixed: "right", toolbar: "#table-sys-user-opt"}
            ]
        ],
        page: true,
        limit: 10,
        // height: "full-220",
        text: "对不起，加载出现异常！"
    }), data_table.on("tool(LAY-sys-user-manage)", function (e) {
        // 操作栏各按钮方法
        var rowData = e.data;
        // 删除操作
        if ("del" === e.event) {
            layer.prompt({formType: 0, title: "敏感操作，请验证口令"}, function (t, i) {
                layer.close(i), layer.confirm("确定删除？", function (t) {
                    //执行 Ajax 后重载
                    $.ajax({
                        url: '/sys/user/delete',
                        data: 'ids=' + rowData.id,
                        type: 'DELETE',
                        success: function (result) {
                            // Do something with the result
                            if (!result) {
                                layer.msg('未知错误');
                            } else if (result.code == 0) {
                                e.del();
                                layer.close(t);
                                data_table.reload('LAY-sys-user-manage');
                                layer.msg('已删除');
                            } else {
                                layer.msg(result.msg);
                            }
                        },
                        error: function () {
                            layer.close(index);
                            layer.msg('请求错误');
                        }
                    });
                })
            });
        } else if ("info" === e.event) {
            lay(e.tr);
            layer.open({
                type: 2,
                title: "查看",
                content: "/sys/user/dataView?action=1&id=" + e.data.id,
                maxmin: true,
                area: ["500px", "450px"],
                btn: ["确定"],
                yes: function (e, t) {
                    layer.close(e);
                },
                success: function (e, t) {
                }
            })
        } else if ("edit" === e.event) {
            lay(e.tr);
            layer.open({
                type: 2,
                title: "编辑",
                content: "/sys/user/dataView?action=2&id=" + e.data.id,
                maxmin: true,
                area: ["500px", "450px"],
                btn: ["确定", "取消"],
                yes: function (iframeIndex, t) {
                    var iframeWindow = window["layui-layer-iframe" + iframeIndex]
                        // 需要与编辑页确认提交按钮ID和lay-filter一致
                        , submitID = "LAY-sys-user-submit"
                        , submit = t.find("iframe").contents().find("#" + submitID);
                    // 监听提交,iframe提交更新后刷新表格
                    iframeWindow.layui.form.on("submit(" + submitID + ")", function (iframeData) {
                        var formData = iframeData.field;
                        $.ajax({
                            url: '/sys/user/update',
                            data: JSON.stringify(formData),
                            contentType: 'application/json',
                            type: 'POST',
                            success: function (result) {
                                // Do something with the result
                                if (!result) {
                                    layer.msg('未知错误');
                                } else if (result.code == 0) {
                                    data_table.reload("LAY-sys-user-manage");
                                    layer.close(iframeIndex);
                                    layer.msg('更新成功');
                                } else {
                                    layer.msg(result.msg);
                                }
                            },
                            error: function () {
                                layer.close(iframeIndex);
                                layer.msg('请求错误');
                            }
                        });

                    }), submit.trigger("click")
                },
                success: function (e, t) {
                }
            })
        }
    }), e("sysUser", {})
});