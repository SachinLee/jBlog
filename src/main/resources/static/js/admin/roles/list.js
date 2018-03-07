
require(['jquery','jqueryValidate','page'], function ($) {

    jQuery.validator.addMethod("validateId", function (value, element) {
        return this.optional(element) || (validateId(value));
    }, "当前编码已经存在");

    $("#roleForm").validate({
        rules : {
            id : {
                required : true,
                validateId :true
            },
            roleName : {
                required : true
            }
        },
        messages : {
            id : {
                required : "角色编码必填",
                validateId : "角色编码已经存在"
            },
            roleName : {
                required : "角色名称必填"
            }
        }
    })

});

function doQuery() {
    $("#searchForm").submit();
}

function doReset() {
    $("#searchForm").find('input[name="id"]').val("");
    $("#searchForm").find("input[name='roleName']").val("");
}

function doAddRole() {
    $("#roleForm").find('input[name="id"]').val("");
    $("#roleForm").find("input[name='roleName']").val("");

    $("#myModal").modal('show');
}

function doEditRole(id, roleName) {

    $("#roleForm").find('input[name="id"]').val(id);
    $("#roleForm").find("input[name='roleName']").val(roleName);

    $("#myModal").modal('show');
}

function doSaveRole() {
    if (!$("#roleForm").validate().form()) {
        return false;
    }
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url : basePath + "roles/create",
        type : 'POST',
        data : $("#roleForm").serialize(),
        dataType : 'json',
        async : true,
        beforeSend : function (request) {
            request.setRequestHeader(csrfHeader, csrfToken);
        },
        success : function (data) {
            if (data.status == 0) {
                swal("角色添加成功","","success");
                doQuery();
            } else {
                alert(data.message);
            }
        },
        error : function () {
            swal("保存过程服务器异常","请重试","error");
        }
    })
}

function doDeleteRole(id) {
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");

    swal({
        title: "确认删除吗？",
        text: "删除后将无法恢复！",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "确定删除！",
        cancelButtonText: "取消删除！",
        closeOnConfirm: false,
        closeOnCancel: false
    }).then(function (isConfirm) {
        if (isConfirm) {
            $.ajax({
                url : basePath + 'roles/delete/'+id,
                type : 'DELETE',
                beforeSend : function (request) {
                    request.setRequestHeader(csrfHeader, csrfToken);
                },
                success : function (data) {
                    if (data.status == 0) {
                        doQuery();
                    } else {
                        swal(data.message,"","error");
                    }
                },
                error : function () {
                    swal("删除过程服务器异常","请重试","error");
                }
            });
        }
    })
}

//验证方法
function validateId(id) {
    //只有编辑时才验证，新增时不验证
    if ($("#roleForm").find("input[name='id']").val() != null) {
        $.ajax({
           url : basePath + 'roles/validateId',
            type : 'GET',
            data : {id : $("#roleForm").find("input[name='id']").val()},
            dataType : 'json',
            async : true,
            success : function (data) {
                if (data.status == 0) {
                    return true;
                } else {
                    return false;
                }
            },
            error : function () {
                swal("验证失败");
            }
        });
    }
    return true;
}