
//---------------------------------------------业务功能开始（教学作业管理功能）---------------------

//查看教学作业详情
function showJxzyDetail(id){
	sendJson("get","/jxzy/display/"+id,{},false,function(res){
		if(res.status){
			var data=res.data;
			//alert(data);
			console.log(data);
			//初始化模态框信息
			$("#inputCourseName").text(data.courseName);
			$("#inputCourseContent").text(data.courseContent); 
			$("#inputStage").text(data.stage==1?"课前":(data.stage==2?"课中":"课后"));
			$("#inputWeight").text(data.weight);
			$("#inputTotalScore").text(data.totalScore);
			$("#inputHomework").text(data.homework);
			$("#inputStartTime").text(data.startTime);
			$("#inputEndTime").text(data.endTime);
			//显示模态框
			$("#infoModel").modal("show");
		}else{
			layer.msg(res.info,{icon:2});
		}
	},function(){
		layer.msg("未知错误",{icon:2});
	});
}




/*教学作业信息，编辑后提交保存 或者  编辑并另存为新的教学作业  提交保存
* _url 提交地址
* _btn 保存按钮
* _form 提交数据的表单
* _modal 模态框
*/
function editJxzySave(_url,_btn,_form,_modal){
	$('#'+_btn).click(function(){
		var objdata=$('#'+_form).serializeJSON();
		console.log(objdata);
		//校验作业阶段是否选择
		var stage=$("#edit_jxzy_stage option:selected").val();
		if(isNull(stage)){
			layer.msg("请选择作业阶段",{icon:0});
			return ;
		}
		//判断是哪种编辑保存
		var type=_url.indexOf("New")>-1?2:1;
		//如果是编辑后另存为新，则验证阶段stage是否重复,需要再次从后台校验
		if(type==2){ 
			var jxnrId=$("#edit_jxzy_jxnr_id ").val();
			var unique=true;
			//以阶段stage和jxnrid为条件查询
			sendJson("get","/jsjxzy/queryStage",{"stage":stage,"jxnrId":jxnrId},false,
				function(res){
					if(!res.status){//如果不唯一，提示信息
						layer.msg(res.info,{icon:0});
						unique=false;
						return ;
					}
				},function(){
					layer.msg("未知错误",{icon:2});
					unique=false;
					return ;
				}
			);
			//如果不唯一，提示，并返回，不保存
			if(!unique){
				return ;
			}
		}
		//如果stage唯一，不管是哪种编辑保存，都再次校验，但是stage字段不需要验证了
		if(!validateEditJxzyForm(type)){
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

/*教学作业信息，编辑后提交保存 或者  编辑并另存为新的教学作业  提交保存，
* _type 类型，1 编辑保存   2 编辑另存为新
*/
function validateEditJxzyForm(_type){
	//权值验证 输入合法性，逻辑合法性
	var weight=$("#edit_jxzy_weight").val();
	var reg=/^[+]{0,1}(\d+)$|^[+]{0,1}(0\.(\d)+)$/;
	if(!reg.test(weight) || weight>1.00){
		layer.msg("权重输入不正确，只接收不超过1的正小数值",{icon:0});
		return false;
	}else{
		if(_type==1){ //编辑保存，验证权重值更改的合理性
			var old_weight=$("#old_weight").val(); //旧权值
			var jxnrLeave=$("p #edit_jxzy_leave").text(); //剩余权值
			if(weight!=old_weight){ //权值发生改变
				if(jxnrLeave + old_weight - weight < 0.0){
					 layer.msg("权重值修改后的值不正确，请仔细计算和查看提示信息",{icon:0});
					 return false;
				}
			}
		}else{ //编辑另存为新 ,验证权重值
			var jxnrLeave=$("p #edit_jxzy_leave").text();//剩余权值
			if(weight>jxnrLeave){
				layer.msg("教学作业的权重值设置不正确，请仔细查看权重值设置提示信息",{icon:0});
				return false;
			}
		}
	}
	var totalScore=$("#edit_jxzy_total_score").val();
	if(isNull(totalScore)){
		layer.msg("请设置作业总分值",{icon:0});
		return false;
	}
	var homework=$("#edit_jxzy_homework").val();
	if(isNull(homework)){
		layer.msg("请设置作业内容",{icon:0});
		return false;
	}
	
	var startTime=$("#edit_jxzy_start_time").val();
	if(isNull(startTime)){
		layer.msg("请设置作业开始时间",{icon:0});
		return false;
	}
	var endTime=$("#edit_jxzy_end_time").val();
	if(isNull(endTime)){
		layer.msg("请设置作业结束时间",{icon:0});
		return false;
	}
	return true;
}

