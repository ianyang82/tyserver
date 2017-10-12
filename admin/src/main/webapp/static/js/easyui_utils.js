/**普通grid展示
 * id:grid 的ID
 * dg_toolbar：grid的复杂操作部分id，"dg_area_id"
 * Url:获取数据URL地址,--"<{:U('Focus/focuslist')}>"--"a.json"
 * data_opt：grid属性，可省略 var data_opt = {
                    'pageList':[10, 20, 30,50],
                    'pageSize':10,
                };
 */
function baseGrid(Url, data_opt, id, dg_toolbar) {
    if (id == null) {
        id = "dg";
    }
    if (dg_toolbar == null) {
        dg_toolbar = "search_form";
    }
    var default_opt = {
        border: true,
        checkOnSelect: true,
        selectOnCheck: true,
        method: 'post',
        iconCls: 'icon-edit', //图标
        singleSelect: false, //多选
        fit: true,
        fitColumns: true, //自动调整各列，用了这个属性，下面各列的宽度值就只是一个比例。
        striped: true, //奇偶行颜色不同
        collapsible: false,//可折叠
        sortName: 'updateDate', //排序的列
        sortOrder: 'desc', //倒序
        remoteSort: true, //服务器端排序
        idField: 'id', //主键字段
        pagination: true, //显示分页
        pageSize: 10,                //每页记录数
        pageList: [5, 10, 15, 20], //分页记录数数组
        rownumbers: true,//显示行号
        queryParams: {},//查询参数
        nowrap: false,
        onLoadSuccess: function (data) {
            console.debug(data);
        },
        onLoadError: function (XMLHttpRequest) {
            console.error(XMLHttpRequest);
            $.messager.alert("系统错误", XMLHttpRequest.responseText);
        }

    };

    for (opt in data_opt) {
        default_opt[opt] = data_opt[opt];
    }
    $('#' + id).datagrid({
        url: Url,
        loadMsg: messageStrings.loading,
        pageSize: default_opt["pageSize"],
        pageList: default_opt["pageList"],
        pagination: default_opt["pagination"],
        singleSelect: default_opt["singleSelect"],
        fitColumns: default_opt["fitColumns"],
        checkOnSelect: default_opt["checkOnSelect"],
        selectOnCheck: default_opt["selectOnCheck"],
        sortName: default_opt["sortName"],
        sortOrder: default_opt["sortOrder"],
        border: default_opt["border"],
        method: default_opt["method"],
        iconCls: default_opt["iconCls"],
        fit: default_opt["fit"],
        striped: default_opt["striped"],
        collapsible: default_opt["collapsible"],
        remoteSort: default_opt["remoteSort"],
        idField: default_opt["idField"],
        rownumbers: default_opt["rownumbers"],
        toolbar: dg_toolbar == "" ? "" : "#" + dg_toolbar,
        queryParams: default_opt["queryParams"],
        nowrap: default_opt["nowrap"],
        onLoadSuccess: default_opt["onLoadSuccess"],
        onLoadError: default_opt["onLoadError"]
    });

}


/*
 * 查询方法，查询区域必须用<form></form>包裹住，方便提交服务器处理
 * 传入grid的id,that表示查询按钮，传入this即可
 */
function searchData(that, grid_id) {
    if (grid_id == null) {
        grid_id = "dg";
    }
    var data = $("#" + grid_id);
    var queryParams = data.datagrid('options').queryParams;

    $.each($(that).parents('form').serializeArray(), function () {//由于按钮的上一级不一定是form，使用parents
        queryParams[this['name']] = this['value'];
    });
    data.datagrid({
        pageNumber: 1
    });
}
//清空查询条件
function clearForm(that) {
    $(that).parents('form').form('clear');//由于按钮的上一级不一定是form，使用parents
}
//弹出窗口
function showWindow(options) {
    parent.$("#win").window(options);
}
//关闭弹出窗口
function closeWindow() {
    parent.$("#win").window('close');
}
//新增
function addrow(href, grid_id) {
    if (grid_id == null) {
        grid_id = "dg";
    }
    showWindow({
        title: messageStrings.record_create,
        href: href,
        width: 600,
        height: 550,
        // top: (screen.height - 550) / 2,
        // left: (screen.width - 600) / 2,
        onLoad: function () {
        },
        onLoadError: function (XMLHttpRequest) {
            console.error(XMLHttpRequest);
            $.messager.alert("系统错误", XMLHttpRequest.responseText);
            closeWindow();
        },
        onClose: function () {
            $("#" + grid_id).datagrid('reload');    // reload the user data
        }

    });
}
//更新
function updaterow(href, grid_id) {
    if (grid_id == null) {
        grid_id = "dg";
    }
    var rows = $("#" + grid_id).datagrid('getSelections');
//    console.debug(rows)
    if (rows.length == 0) {
        $.messager.alert(messageStrings.hint, messageStrings.update_hint_check, 'info');
        return;
    }
    if (rows.length > 1) {
        $.messager.alert(messageStrings.hint, messageStrings.update_hint_check_one, 'info');
        return;
    }
    showWindow({
        title: messageStrings.record_update,
        href: href,
        width: 600,
        height: 550,
        // top: (screen.height - 550) / 2,
        // left: (screen.width - 600) / 2,
        onLoad: function () {
            parent.$("#editForm").form('load', rows[0]);
        },
        onLoadError: function (XMLHttpRequest) {
            console.error(XMLHttpRequest);
            $.messager.alert("系统错误", XMLHttpRequest.responseText);
            closeWindow();
        },
        onClose: function () {
            $("#" + grid_id).datagrid('reload');    // reload the user data
        }
    });
}
//更新
function updaterow2(href, grid_id) {
    if (grid_id == null) {
        grid_id = "dg";
    }
    var rows = $("#" + grid_id).datagrid('getSelections');
    if (rows.length == 0) {
        $.messager.alert(messageStrings.hint, messageStrings.update_hint_check, 'info');
        return;
    }
    if (rows.length > 1) {
        $.messager.alert(messageStrings.hint, messageStrings.update_hint_check_one, 'info');
        return;
    }
    showWindow({
        title: messageStrings.record_update,
        href: href+"?id="+rows[0].id,
        width: 600,
        height: 550,
        onLoad: function () {
        },
        onLoadError: function (XMLHttpRequest) {
            console.error(XMLHttpRequest);
            $.messager.alert("系统错误", XMLHttpRequest.responseText);
            closeWindow();
        },
        onClose: function () {
            $("#" + grid_id).datagrid('reload');    // reload the user data
        }
    });
}
//保存
function saveobj(url, form_id) {
    if (form_id == null) {
        form_id = "editForm";
    }
    var form = $('#' + form_id);
    if (!form.form('validate')) {
        return;
    }
    $.ajax({
        url: url,
        type: 'POST',
        data: form.serialize(),
        dataType: "json",
        success: function (result) {
            console.debug(result);
            if (result.error != 0) {
                var errors = result.data;
                for (var o in result.data) {
                    $("[name='" + errors[o].field + "']").parent().after('<span style="color: red">' + errors[o].defaultMessage + '</span>');
                }
                $.messager.alert(messageStrings.hint, result.msg);
            } else {
                closeWindow();
            }
        },
        error: function (XMLHttpRequest) {
            console.error(XMLHttpRequest);
            $.messager.alert("系统错误", XMLHttpRequest.responseText);
        }
    });
}

//删除
function deleterows(href, grid_id) {
    if (grid_id == null) {
        grid_id = "dg";
    }
    var rows = $("#" + grid_id).datagrid('getSelections');
    if (rows.length == 0) {
        $.messager.alert(messageStrings.hint, messageStrings.delete_hint_check, 'info');
        return;
    }
    $.messager.confirm(messageStrings.hint, messageStrings.delete_confirm, function (result) {
        if (result) {
            var ps = "?ids=" + getSelections("id", grid_id);
            $.post(href + ps, function (data) {
                $("#" + grid_id).datagrid('reload');
                $.messager.alert(messageStrings.hint, data.msg, 'info');
                $("#" + grid_id).datagrid('clearSelections');//删除后，清空对删除行的选择
            }, "json");
        }
    });
}

/*获取grid的id串，以，隔开，如果是字符串，则以'',''进行隔开
 *grid_id  grid的id
 *id 要组拼的字段id
 */
function getSelections(id, grid_id) {
    if (grid_id == null) {
        grid_id = "dg";
    }
    var rows = $("#" + grid_id).datagrid('getSelections');
    var ss = [];
    $.each(rows, function (i, row) {
        ss.push(eval("row." + id));
    });
    return ss.join(',');
}

//easyUI list不支持属性的解决方法，根据user取name
function getUserName(value) {
    return value.loginName;
}

/** 取当前日期的前day天
 * @return {string}
 */
function GetDateStr(day) {
    var dd = new Date();
    dd.setDate(dd.getDate() + day);//获取AddDayCount天后的日期
    var y = dd.getFullYear();
    var m = dd.getMonth() + 1;//获取当前月份的日期
    var d = dd.getDate();
    return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
}


