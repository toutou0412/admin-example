/*对新密码和确认密码是否匹配做自定义校验规则*/
$.validator.addMethod("rePwd",function(value,element,params){
	var pwd = $("#pwd").val();
	if (value==pwd){
		return true;
	}else {
		return false;
	}
},"新密码和确认密码不一致");
jQuery.validator.addMethod("pwd", function(value, element) {
	var pwd = /^(?![a-zA-Z]+$)(?!\D+$)(?![^_]+$).{8,16}/;
	return (pwd.exec(value))? true:false;

}, "密码必须由6-16位数字、大写+小写字母和下划线组成");
/*对重置密码功能做表单校验*/
$("#resetPwdForm").validate({
            errorElement: "span",
            errorClass: "help-block font-red",
            rules: {
            	oldPwd: {
                    required: true,
                    maxlength:20
                },
                pwd:{
                    required: true,
                    maxlength:20,
                    pwd: true
                },
                rePwd:{
                    required: true,
                    rePwd:true,
                    maxlength:20
                }

            },
            messages: {
            	oldPwd: {
                    required: "请输填写原始密码",
                    maxlength:"密码最长20个字符"
                },
                pwd:{
                    required: "请填写新密码",
                    maxlength:"密码最长20个字符",
                    pwd: "密码必须由6-16位数字、大写+小写字母和下划线组成"
                },
                rePwd:{
                    required: "请填写确认密码",
                    maxlength:"密码最长20个字符"
                }
            },
            submitHandler: function (form) {
                form.submit();
            }
        });