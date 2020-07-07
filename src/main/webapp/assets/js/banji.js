//---------------------------------------------业务功能开始（班级管理功能）---------------------


/*班级信息，添加提交保存并校验
 * _url 提交地址
 * _btn 保存按钮
 * _form 提交数据的表单
 * _modal 模态框
 */
function addBanjiSave(_url,_btn,_form,_modal){
	
	$('#'+_btn).click(function(){
		
		var objdata=$('#'+_form).serializeJSON();
		console.log(objdata);
		var name=$("#add_name").val();
		if(name==null|| name==''){
			layer.msg("班级名称不能为空",{icon:0});
			return false;
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

