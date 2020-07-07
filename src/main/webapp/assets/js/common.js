/*
    desp：公共js
    author:gcc
    time:2019-05-21
 */
//--------------------------------------------公共功能----------------------------

/*
    功能：初始化下拉框   
    _url：请求路径
    _obj:下拉框对象
    _isInit:是否选择下拉框提示作为第一个option
    _key,选择哪个属性字段作为option的key，
    _value 选择哪个属性字段作为option的value
 */
function initSelectList(_url,_obj,_isInit,_key,_value){
	 sendJson("get", _url, {},false, function (res) {
         if (res.status ) {
        	 _obj.empty();
        	 var html='';
        	 if(res.data!=null){ //考虑data为null情况
        		 html=_isInit?'<option value="">请选择</option>':'';
        		 for(var i=0;i<res.data.length;i++) {
                     html += '<option value="'+res.data[i][_key]+'">'+res.data[i][_value]+'</option>';
                 }
        	 }else{
        		 html='<option value="">'+res.info+'</option>';
        	 }
             _obj.append(html);
         }
     }, function () {
         layer.msg("未知错误",{icon:2});
     });
}

/*
功能：重置密码

_obj:下拉框对象

*/
function resetpwd(_obj){
	
	_obj.validate({
		submitHandler: function (form) {
			var op=$("#oldPassword").val();
			var np=$("#newPassword").val();
			$("#btn-password-save").button('loading');
			 sendJson("post",
				"/password",
				{"oldPassword":op,"newPassword":np},
				false,
				function(res){
					 if (!res.status) {
	                	layer.msg(res.info,{icon:2});
	                 } else{
	                	layer.alert("修改密码成功",{icon:1});
	                    $("#updatePassword").modal('toggle');
	                 }
				},function(res){
					layer.msg("未知错误",{icon:2});
			}); 
        },
		rules:{
			oldPassword: {
                required: true,
                minlength: 6,
                maxlength: 18
            },
            newPassword: {
                required: true,
                minlength: 6,
                maxlength: 18
            },
            password_confirm: {
                required: true,
                equalTo: "#newPassword"
            }
		},
		errorClass: "text-danger",
    	errorElement: "span",
		messages:{
			oldPassword: {
                required: "请输入旧密码",
                minlength:
                    "密码长度不能小于 6 个字符",
                maxlength:
                    "密码长度不能超过 18 个字符"
            },
            newPassword: {
                required: "请输入新密码",
                minlength:
                    "密码长度不能小于 6 个字符",
                maxlength:
                    "密码长度不能超过 18 个字符"
            },
            password_confirm: {
                required: "请再次输入新密码",
                minlength:
                    "密码长度不能小于 6 个字母",
                equalTo:
                    "两次密码输入不一致"
            }
		}
	});
}

/*
功能：注销

_obj:注销按钮

*/
function logout(_obj){
	_obj.click(function () {
		layer.confirm('确认退出？',{
			btn:['确定','再想想']
			},function(){
				$.get("/logout", function (res) {
					window.location.href=res.data;
	            });
			},function(){
			}
		);
    });
	
}

/*
 * 获取登录名和保存当前用户的凭证学号
 */
function getUserInfo(){
    sendJson("get", "/currentUserInfo", {}, false, function (res) {
        if (res.status) {
        	var user=res.data;
        	var _text=user.name;
        	if(user.role==1){
        		_text=_text+"【"+user.banjiName+"】";
        	}else if(user.role==2){
        		_text=_text+"【教师】";
        	}else{
        		_text=_text+"【管理员】";
        	}
            $("#showUser").text(_text);
            $("#userXuehao").val(user.xuehao);
        }else{
        	layer.confirm(res.info,{btn:['确定']},function(){
           		window.location.href = res.data;
           	});
        }
    }, function () {
        layer.msg("未知错误",{icon:2});
    });
	
}

//个人信息显示模态框加载时给值
function showUserInfo(_modal){
	$('#'+_modal).on('shown.bs.modal', function (){
		sendJson("get","/currentUserInfo",{},false,function(res){
			if(res.status){
				var data=res.data;
				//alert(data);
				console.log(data);
				//初始化模态框信息
				$("#user_xuehao").text(data.xuehao);
				$("#user_name").text(data.name);
				if(data.role==2){
					$("#user_banjiName").addClass("hidden");
					$("#input_banjiName").addClass("hidden");
				}else{
					$("#user_banjiName").removeClass("hidden");
					$("#input_banjiName").removeClass("hidden");
					$("#user_banjiName").text(data.banjiName);
				}
				$("#user_createTime").text(data.createTime);
			}else{
				layer.msg(res.info,{icon:2});
			}
		},function(){
			layer.msg("未知错误",{icon:2});
		});
	});
}


/**
 * laydate渲染日期控件，格式为只选择年份,主体墨绿
 * _array:id数组
 */
function renderYear(_array){
	for(var i=0;i<_array.length;i++){
		var value=_array[i];
		laydate.render({
			elem:'#'+value,
			theme: 'molv',
			type:"year"
		});
	}
}
/**
 * laydate渲染日期控件，yyyy-MM-dd HH:mm:ss
 * _array:id数组
 */
function renderDateTime(_array){
	for(var i=0;i<_array.length;i++){
		var value=_array[i];
		laydate.render({
			elem:'#'+value,
			theme: 'molv',
			type:"datetime"
		});
	}
}
/**
 * laydate渲染日期控件，格式为_format
 * _array:id数组
 * _format
 */
function renderDate(_array,_format){
	for(var i=0;i<_array.length;i++){
		var value=_array[i];
		laydate.render({
			elem:'#'+value,
			theme: 'molv',
			format:_format
		});
	}
}
/**
 * laydate渲染日期控件，格式为_format 选择日期范围
 * _array:id数组
 * _format
 */
function renderDateRange(_array,_format){
	for(var i=0;i<_array.length;i++){
		var value=_array[i];
		laydate.render({
			elem:'#'+value,
			theme: 'molv',
			range:'~',
			format:_format
		});
	}
}

/**
 * laydate渲染日期控件，yyyy-MM-dd HH:mm:ss 选择日期时间范围
 * _array:id数组
 */
function renderDateTimeRange(_array){
	for(var i=0;i<_array.length;i++){
		var value=_array[i];
		laydate.render({
			elem:'#'+value,
			theme: 'molv',
			range:'~',
			type:"datetime"
		});
	}
}


/**
 * 验证value是否为空 ，是则返回true
 */
function isNull(_value){
	return _value==null || _value.trim()==''||  _value.trim()=="";
}


/**
 *为所有的select添加下拉选项限制条数为6
 */
function selectCountSize(){
	$(".selectpicker").selectpicker({//初始化
		size:6
	});

}


/**
 * 表格导出列内容公用方法
 * 处理导出内容,这个方法可以自定义某一行、某一列、甚至某个单元格的内容,也就是将其值设置为自己想要的内容
 * @param cell
 * @param row 对应的行
 * @param col 对应的列
 * @param data 单元格内容  如果没有span，则直接是 数据，否则是 <span  title="完整数据">简略数据</span>
 * @returns
 */
function DoOnCellHtmlData(cell, row, col, data){
    if(row == 0){
        return data;
    }
    
    //由于备注列超过6个字的话,通过span标签处理只显示前面6个字,如果直接导出的话会导致内容不完整,因此要将携带完整内容的span标签中title属性的值替换
    var spanObj = $(data);//将带 <span title="val"></span> 标签的字符串转换为jQuery对象
    var title = spanObj.attr("title");//读取<span title="val"></span>中title属性的值
    //var span = cell[0].firstElementChild;//读取cell数组中的第一个值下的第一个元素
    if(typeof(title) != 'undefined'){
        return title;
    }
    return data;
}

