//---------------------------------------------业务功能开始（学生用户管理功能）---------------------
/*学生信息，添加提交保存并校验
 * _url 提交地址
 * _btn 保存按钮
 * _form 提交数据的表单
 * _modal 模态框
 */
function addStudentSave(_url,_btn,_form,_modal){
	
	$('#'+_btn).click(function(){
		
		var objdata=$('#'+_form).serializeJSON();
		console.log(objdata);
		if(!validateAddStudentForm()){
			return ;
		}
		$.ajax({
			type:"post",
			url:_url,
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(objdata),
			asyn:true,
			success:function(res){
				 if (!res.status) {
                	layer.msg(res.info,{icon:2});
                 } else{
                	layer.alert("保存成功",{icon:1});
                	$('#'+_modal).modal('toggle');
                    flushTable();//列表刷新
                 }
			},
			error:function(){
				layer.msg("未知错误",{icon:2});
		    }
			
		});
		
	});
	
}

//提交添加课程验证
function validateAddOrEditCourseForm(_method){
	var xuehao=$("#add_xuehao").val();
	if(xuehao==null|| xuehao==''){
		layer.msg("学生学号不能为空",{icon:0});
		return false;
	}
	//学号唯一性验证
	var flag=true;
	sendJson("get","/user/xuehaoUnique",{"xuehao":xuehao},false,function(res){
		if(res.status){
			flag=true;
		}else{
			layer.msg("该学号已经注册过了，请重新输入新学号",{icon:0});
		}
	},function(){
		layer.msg("未知错误",{icon:2});
	});
	if(!flag){
		return flag;
	}
	//学生姓名不能为空
	var name=$("#add_name").val();
	if(name==null|| name==''){
		layer.msg("学生姓名不能为空",{icon:0});
		return false;
	}
	var banjiId=$("#add_banji_ids").selectpicker('val');
	if(isNull(banjiId)){
		layer.msg("学生所属班级不能为空，请选择",{icon:0});
		return false;
	}
	return true;
}


function getBanjiName(){
	$("#add_banji_name").val($("#add_banji_ids option:selected").text());
}
