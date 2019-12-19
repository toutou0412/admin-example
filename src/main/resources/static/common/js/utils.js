/*
//获取指定区间范围随机数，包括lowerValue和upperValue
function randomFrom(lowerValue,upperValue)
{
    return Math.floor(Math.random() * (upperValue - lowerValue + 1) + lowerValue);
}
//弹出提示层
function alertTip(msg,callback){
	layer.alert(msg, {
		skin: 'layui-layer-molv'
		,closeBtn: 0
		,anim: 0 //动画类型
	},callback);
}

//确认提示层
function confirmTip(msg,sureCallback,cancelCallback){
	layer.confirm(msg, {
		skin: 'layui-layer-molv'
		,btn:['确认','取消']
		,anim: 0 //动画类型
	},sureCallback,cancelCallback);
}



/!**
 *
 * ajax post提交表单
 * @param url 提交地址
 * @param data 提交数据
 * @param dataType json/text/xml等
 * @param successCallback
 * @param failCallback
 *
 * *!/
function ajaxPost(url,data,dataType,successCallback,failCallback){
	$.ajax({
        type: "POST",
        url: url,
        cache:false,
        async:false,
        data:data,
        dataType: dataType,
        success: function (message) {
            successCallback(message);
        },
        error: function (message) {
        	failCallback(message);
        }
    });
}
//身份证验证 引入的方法
function isIdCardNo(num) {
    var factorArr = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,8, 4, 2, 1);
    var parityBit = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4","3", "2");
    var varArray= new Array();
    var intValue;
    var lngProduct = 0;
    var intCheckDigit;
    var intStrLen = num.length;
    var idNumber= num;
    //initialize
    if((intStrLen != 15) && (intStrLen !=18)) {
       return false;
    }
    // check andset value
    for (i = 0;i < intStrLen; i++) {
       varArray[i] = idNumber.charAt(i);
       if ((varArray[i] < '0' || varArray[i]> '9') && (i != 17)){
           return false;
       } else if (i < 17) {
           varArray[i] = varArray[i] * factorArr[i];
       }
    }

    if(intStrLen == 18) {
       //check date
       var date8 = idNumber.substring(6, 14);
       if (isDate8(date8) == false) {
           return false;
       }
       // calculate the sum of the products
       for (i = 0; i < 17; i++) {
           lngProduct = lngProduct + varArray[i];
       }
       // calculate the check digit
       intCheckDigit = parityBit[lngProduct % 11];
       // check last digit
       if (varArray[17] != intCheckDigit) {
           return false;
       }
    }
    else{//length is 15
       //check date
       var date6 = idNumber.substring(6, 12);
       if (isDate6(date6) == false) {
           return false;
       }
    }
    return true;
}

function isDate6(sDate) {
    if(!/^[0-9]{6}$/.test(sDate)) {
       return false;
    }
    var year,month, day;
    year =sDate.substring(0, 4);
    month =sDate.substring(4, 6);
    if (year< 1700 || year > 2500) returnfalse
    if (month< 1 || month > 12) return false
    return true
}

function isDate8(sDate) {
    if(!/^[0-9]{8}$/.test(sDate)) {
       return false;
    }
    var year,month, day;
    year =sDate.substring(0, 4);
    month =sDate.substring(4, 6);
    day =sDate.substring(6, 8);
    var iaMonthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30,31]
    if (year< 1700 || year > 2500) return false
    if (((year %4 == 0) && (year % 100 != 0)) ||(year % 400 == 0)) iaMonthDays[1] = 29;
    if (month< 1 || month > 12) return false
    if (day< 1 || day > iaMonthDays[month - 1])return false
    return true
}
//只能输入英文
jQuery.validator.addMethod("english", function(value, element) {
    var chrnum = /^([a-zA-Z0-9]+)$/;
    return this.optional(element) || (chrnum.test(value));
}, "只能输入字母");
//手机号校验
jQuery.validator.addMethod("ismobile", function(value, element) {
	var length = value.length;
	var mobile = /^1[3|4|5|7|8][0-9]\d{4,8}$/;
	return (length == 11 && mobile.exec(value))? true:false;
}, "请正确填写手机号码");
//身份证校验
jQuery.validator.addMethod("identifyCard", function(value, element) {
	return this.optional(element) || isIdCardNo(value);
}, "请正确输入身份证号码");
*/
