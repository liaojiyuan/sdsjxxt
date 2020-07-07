/*批改学生作业 ，主要是输入学生得分分数保存
* _url 提交地址
* _btn 保存按钮
* _form 提交数据的表单
* _modal 模态框
*/
function editXszySave(_url,_btn,_form,_modal){
	$('#'+_btn).click(function(){
		var answer=$("#edit_xszy_answer ").val();
		if(isNull(answer)){
			layer.msg("作业答案不能为空",{icon:0});
			return ;
		}
		var id=$("#edit_xszy_id").val();
		sendJson("get",_url,{"id":id,"answer":answer},true,
			function(res){
				if (!res.status) {
	              	layer.msg(res.info,{icon:2});
	            } else{
	            	layer.alert("保存成功",{icon:1});
	            	$('#'+_modal).modal('toggle');
	            	flushTable();//列表刷新
	           }
			},function(){
				layer.msg("未知错误",{icon:2});
			}
		);
	});
}