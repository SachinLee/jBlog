require(['jquery', 'jqueryValidate', 'page'],function($){
    jQuery.validator.addMethod("emailPattern", function (value, element) {
        return this.optional(element) || (emailPattern(value));
    }, "邮箱格式不正确");

    jQuery.validator.addMethod("emailExist", function (value, element) {
        return this.optional(element) || (emailExist(value));
    }, "邮箱已经存在");

    jQuery.validator.addMethod("logiNameExist", function (value, element) {
        return this.optional(element) || (logiNameExist(value));
    }, "用户名已经存在");

    $("#userForm").validate({
        rules : {
            loginName : {
                required : true,
                logiNameExist : true
            },
            email : {
                required : true,
                emailPattern : true,
                emailExist : true
            },
            roles : {
                required : true
            }
        },
        messages : {
            loginName : {
                required : "登录名称必填",
                logiNameExist : "用户名已经存在"
            },
            email : {
                required : "邮箱必填",
                emailPattern : "请输入正确的邮箱",
                emailExist : "邮箱已经存在"
            },
            roles : {
                required : "角色必选"
            }
        }
    });
});

function logiNameExist(login) {
    if ($("#userForm").find("input[name='id']").val() == "") {
        $.ajax({
            url : basePath + "users/validateLogin/"+login,
            type : 'GET',
            success : function (result) {
                if (result.code == 0) {
                    return true;
                }
            }
        })
        return false;
    }
    return true;
}

function emailPattern(email) {
    //校验邮箱格式
    var pattern = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
    return isPattern = pattern.exec(email);
}

function emailExist(email) {
    if ($("#userForm").find("input[name='id']").val() == "") {
        $.ajax({
            url : basePath + "users/validateEmail/"+email,
            type : 'GET',
            success : function (result) {
                if (result.code == 0) {
                    return true;
                }
            }
        })
        return false;
    }
    return true;
}

function doQuery() {
    $("#searchForm").submit();
}

function doReset() {
    $("#searchForm").find("input[name='loginName']").val("");
    $("#searchForm").find("input[name='email']").val("");
}

function doAddUser() {
    $("#userForm").find("input[name='id']").val("");
    $("#userForm").find("input[name='loginNmae']").val("");
    $("#userForm").find("input[name='email']").val("");
    $("#userForm").find("select[name='roles']").val("");

    $("#myModal").modal('show');
}

function doEditUser(id,loginName,eamil,roles) {
    $("#userForm").find("input[name='id']").val(id);
    $("#userForm").find("input[name='loginName']").val(loginName);
    $("#userForm").find("input[name='email']").val(eamil);
    $("#userForm").find("select[name='roles']").val(roles);

    $("#myModal").modal('show');
}

function doDeleteUser(id) {
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
    }).then(function(isConfirm){
        if (isConfirm) {
            deleteUser(id);
        }
    });
}

function deleteUser(id) {
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
       url : basePath + 'users/delete/'+id,
       type : 'DELETE',
        beforeSend : function (request) {
            request.setRequestHeader(csrfHeader, csrfToken);
        },
        success : function (result) {
            if (result.status == 0) {
                swal("删除成功","","success");
                doQuery();
            } else {
                swal("删除失败","请重试","error");
            }
        },
        error : function () {
            swal("服务器异常，请重试", "error");
        }
    });
}

function doSaveUser() {
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url : basePath + "users/add",
        type : 'POST',
        data : $("#userForm").serialize(),
        dataType : 'json',
        async : true,
        beforeSend : function (request) {
            request.setRequestHeader(csrfHeader, csrfToken);
        },
        success : function (result) {
            if (result.status == 0) {
                swal("操作成功","","success");
                doQuery();
            } else {
                swal("操作失败","请重试","error");
            }
        },
        error : function () {
            swal("系统异常","请重试","error");
        }
    })
}