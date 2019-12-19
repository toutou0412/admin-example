var searchBtnId = 'LAY-sys-copy-search';
var tableId = 'LAY-sys-copy-manage';
var optToolbarId = '#table-sys-copy-opt';
var dataViewSubmitId = 'LAY-sys-copy-submit'
var reqeustUriPrefix = '/sys/copy'

// 定义搜索、操作按钮的执行方法
listTemplateLayui.use(['index', 'table'], function () {
    var layuiSelector = layui.$
        , form = layui.form
        , laydate = layui.laydate
        , table = layui.table;

    //监听搜索
    form.on('submit(' + searchBtnId + ')', function (data) {
        var field = data.field;

        //执行重载
        table.reload(tableId, {
            where: field
        });
    });

    // 操作按钮事件
    var active = {
        // 删除
        batchdel: function () {
            var checkStatus = table.checkStatus(tableId)
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
                        url: reqeustUriPrefix + '/delete',
                        data: 'ids=' + ids,
                        type: 'DELETE',
                        success: function (result) {
                            // Do something with the result
                            if (!result) {
                                layer.msg('未知错误');
                            } else if (result.code == 0) {
                                table.reload(tableId);
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
                , content: reqeustUriPrefix + '/dataView?action=0'
                , maxmin: true
                , area: ['500px', '450px']
                , btn: ['确定', '取消']
                , yes: function (iframeIndex, layero) {
                    var iframeWindow = window['layui-layer-iframe' + iframeIndex]
                        , submitID = dataViewSubmitId
                        , submit = layero.find('iframe').contents().find('#' + submitID);

                    // 监听提交
                    iframeWindow.layui.form.on('submit(' + submitID + ')', function (iframeData) {
                        // 获取提交的字段
                        var formData = iframeData.field;

                        //提交 Ajax 成功后，静态更新表格中的数据
                        layuiSelector.ajax({
                            url: reqeustUriPrefix + '/add',
                            data: JSON.stringify(formData),
                            contentType: 'application/json',
                            type: 'PUT',
                            success: function (result) {
                                // Do something with the result
                                if (!result) {
                                    layer.msg('未知错误');
                                } else if (result.code == 0) {
                                    // 刷新数据
                                    table.reload(tableId);
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
listTemplateLayui.define(['table', 'form'], function (e) {
    var lay = layui.$, data_table = layui.table;
    layui.form;
    data_table.render({
        elem: '#' + tableId,
        url: reqeustUriPrefix + '/listData',
        cols: [
            [
                {type: 'checkbox', fixed: 'left'}
                , {field: 'id', width: 80, title: 'ID', sort: true}
                , {field: 'name', title: '登录名'}
                , {title: '操作', width: 250, align: 'center', fixed: 'right', toolbar: optToolbarId}
            ]
        ],
        page: true,
        limit: 10,
        // height: 'full-220',
        text: '对不起，加载出现异常！'
    }), data_table.on('tool(' + tableId +')', function (e) {
        // 操作栏各按钮方法
        var rowData = e.data;
        // 删除操作
        if ('del' === e.event) {
            layer.prompt({formType: 0, title: '敏感操作，请验证口令'}, function (t, i) {
                layer.close(i), layer.confirm('确定删除？', function (t) {
                    //执行 Ajax 后重载
                    $.ajax({
                        url: reqeustUriPrefix + '/delete',
                        data: 'ids=' + rowData.id,
                        type: 'DELETE',
                        success: function (result) {
                            // Do something with the result
                            if (!result) {
                                layer.msg('未知错误');
                            } else if (result.code == 0) {
                                e.del();
                                layer.close(t);
                                data_table.reload(tableId);
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
        } else if ('info' === e.event) {
            lay(e.tr);
            layer.open({
                type: 2,
                title: '查看',
                content: reqeustUriPrefix + '/dataView?action=1&id=' + e.data.id,
                maxmin: true,
                area: ['500px', '450px'],
                btn: ['确定'],
                yes: function (e, t) {
                    layer.close(e);
                },
                success: function (e, t) {
                }
            })
        } else if ('edit' === e.event) {
            lay(e.tr);
            layer.open({
                type: 2,
                title: '编辑',
                content: reqeustUriPrefix + '/dataView?action=2&id=' + e.data.id,
                maxmin: true,
                area: ['500px', '450px'],
                btn: ['确定', '取消'],
                yes: function (iframeIndex, t) {
                    var iframeWindow = window['layui-layer-iframe' + iframeIndex]
                        // 需要与编辑页确认提交按钮ID和lay-filter一致
                        , submitID = dataViewSubmitId
                        , submit = t.find('iframe').contents().find('#' + submitID);
                    // 监听提交,iframe提交更新后刷新表格
                    iframeWindow.layui.form.on('submit(' + submitID + ')', function (iframeData) {
                        var formData = iframeData.field;
                        $.ajax({
                            url: reqeustUriPrefix + '/update',
                            data: JSON.stringify(formData),
                            contentType: 'application/json',
                            type: 'POST',
                            success: function (result) {
                                // Do something with the result
                                if (!result) {
                                    layer.msg('未知错误');
                                } else if (result.code == 0) {
                                    data_table.reload(tableId);
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

                    }), submit.trigger('click')
                },
                success: function (e, t) {
                }
            })
        }
    }), e('sysRole', {})
});