
//---------------------------------------------业务功能开始（教学内容管理功能）---------------------
//查看教学内容详细信息

//操作栏格式化  当前登录用户只能看到自己创建的教学内容列表
// 编辑，删除，添加教学作业 操作，只有 row.jxzyCount<3  或者发布教学内容不超过7天 才显示
function actionFormatter(value,row,index){
	var result='';
	var ck='<button class="btn btn-xs btn-info" onclick="showJxnrDetail('+row.id+')" title="查看教学内容信息"><span class="glyphicon glyphicon-eye-open"></span></button>\n';
	var op='<button class="btn btn-xs btn-info" onclick="editJxnr('+row.id+')" title="编辑教学内容"><span class="glyphicon glyphicon-pencil"></span></button>\n'
		+'  <button class="btn btn-xs btn-info" onclick="deleteJxnr('+row.id+')" title="删除"><span class="glyphicon glyphicon-trash"></span></button>\n';
		
	var add='<button class="btn btn-xs btn-info" onclick="addJsjxzy('+row.id+')" title="添加教学作业"><span class="glyphicon glyphicon-plus">add</span></button>\n';
	if(row.jxzyCount<3 && moment(new Date()).subtract(7,'d').isBefore(row.createTime)){
		result=result+op+ck+add;
	}else{
		result=result+ck;
	}
	var cktj='<button class="btn btn-xs btn-info" onclick="analysisJxnr('+row.id+')" title="学生成绩分析"><span class="glyphicon glyphicon-stats"> 统计</span></button>\n';
	if(row.sfFinished==1 && row.sfTj==1){
		result=result+cktj;
	}
	return result; 
}

//点击查看教学内容统计表
function  analysisJxnr(id){
	sendJson("get","/jxnr/tjfx/"+id,{},false,function(res){
		if(res.status){
			window.open(res.data,"_blank");
		}
	})
}

//点击修改显示教学初始化信息（可编辑状态） 课程名称，课程号不能再次修改了，只能静态显示，可修改的有 教学目标，教学内容，权值
function editJxnr(id){
	sendJson("get","/jxnr/edit/"+id,{},false,function(res){
		if(res.status){
			var data=res.data;
			//初始化模态框信息
			$("#editform #edit_id").val(data.id);  //hidden
			$("#editform #edit_course_id").val(data.courseId); //hidden
			$("#editform #edit_create_user_id").val(data.createUserId); //hidden
			$("#editform #edit_course_name").val(data.courseName); //show static
			$("#editform #edit_course_aim ").val(data.courseAim); //edit
			$("#editform #edit_course_content ").val(data.courseContent); //edit
			$("#editform #edit_weight").val(data.weight);  //edit
			$("#editform #edit_old_weight").text(data.weight);  //hidden 记录旧的权值
			//显示模态框
			$("#editModel").modal("show");
		}else{
			layer.msg(res.info,{icon:2});
		}
	},function(){
		layer.msg("未知错误",{icon:2});
	});
}

//删除教学内容询问信息
function deleteJxnr(id){
	layer.confirm('确定要删除该条教学内容信息吗？', {
		  btn: ['确定','在想想'] //按钮
		}, function(){
		  sendJson("get","/jxnr/delete/"+id,{},false,function(res){
		  	if(res.status){
		  		layer.msg(res.info,{icon:1});
		  		flushTable();
		  	}else{
		  		layer.msg(res.info,{icon:2});
		  	}
		  },function(){
		  		layer.msg("发生未知错误",{icon:2});
		  });
		}, function(){
		  
	});
}
function showJxnrDetail(id){
	sendJson("get","/jxnr/display/"+id,{},false,function(res){
		if(res.status){
			var data=res.data;
			//alert(data);
			console.log(data);
			//初始化模态框信息
			$("#inputCourseId").text(data.courseId);
			$("#inputCourseName").text(data.courseName);
			$("#inputGrade2").text(data.grade2); //课程平时分成绩
			$("#inputCourseAim").text(data.courseAim);
			$("#inputCourseContent").text(data.courseContent);
			$("#inputWeight").text(data.weight);
			$("#inputGrade").text(data.grade);
			//显示模态框
			$("#infoModel").modal("show");
		}else{
			layer.msg(res.info,{icon:2});
		}
	},function(){
		layer.msg("未知错误",{icon:2});
	});
}

/*教学内容信息，添加提交保存并校验
* _url 提交地址
* _btn 保存按钮
* _form 提交数据的表单
* _modal 模态框
*/

function addJxnrSave(_url,_btn,_form,_modal){
	
	$('#'+_btn).click(function(){
		var objdata=$('#'+_form).serializeJSON();
		console.log(objdata);
		if(!validateAddJxnrForm()){
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

/*教学内容信息添加，提交后校验
* _url 提交地址
* _btn 保存按钮
* _form 提交数据的表单
* _modal 模态框
*/
function validateAddJxnrForm(){
	var courseId=$("#add_course_id option:selected").val();
	if(isNull(courseId)){
		layer.msg("必须为教学内容指定课程名",{icon:0});
		return false;
	}
	var courseAim=$("#add_course_aim").val(); //获取表单 textarea的值也是用val方法
	if(isNull(courseAim)){
		layer.msg("请填写教学目标",{icon:0});
		return false;
	}
	var courseContent=$("#add_course_content").val();
	if(isNull(courseContent)){
		layer.msg("请填写教学内容",{icon:0});
		return false;
	}
	var weight=$("#add_weight").val();
	var reg=/^[+]{0,1}(\d+)$|^[+]{0,1}(0\.(\d)+)$/;
	if(!reg.test(weight) || weight>1.00){
		layer.msg("权重输入不正确，只接受不超过1的正小数值",{icon:0});
		return false;
	}else{
		var courseLeave=$("p #add_leave").text();
		if(weight>courseLeave){
			layer.msg("教学内容的权重值设置不正确，请仔细查看权重值设置提示信息",{icon:0});
			return false;
		}
	}
	return true;
}

/*教学内容信息，编辑后提交保存 或者  编辑并另存为新的教学内容 提交保存
* _url 提交地址
* _btn 保存按钮
* _form 提交数据的表单
* _modal 模态框
*/
function editJxnrSave(_url,_btn,_form,_modal){
	$('#'+_btn).click(function(){
		var objdata=$('#'+_form).serializeJSON();
		console.log(objdata);
		var type=_url.indexOf("New")>-1?2:1; //判断是哪种编辑保存
		if(!validateEditJxnrForm(type)){
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

/*教学内容信息编辑保存，提交后校验  或者  编辑并另存为新的教学内容 提交后校验
* _type 类型，1 编辑保存   2 编辑另存为新
*/
function validateEditJxnrForm(_type){
	var courseAim=$("#edit_course_aim").val();
	if(isNull(courseAim)){
		layer.msg("请填写教学目标",{icon:0});
		return false;
	}
	var courseContent=$("#edit_course_content").val();
	if(isNull(courseContent)){
		layer.msg("请填写教学内容",{icon:0});
		return false;
	}
	var weight=$("#edit_weight").val();
	var reg=/^[+]{0,1}(\d+)$|^[+]{0,1}(0\.(\d)+)$/;
	if(!reg.test(weight) || weight>1.00){
		layer.msg("权重输入不正确，只接收不超过1的正小数值",{icon:0});
		return false;
	}else{
		if(_type==1){ //编辑保存，验证权重值更改的合理性
			var old_weight=$("#edit_old_weight").text(); //旧权值
			var courseLeave=$("p #edit_leave").text(); //剩余权值
			if(weight!=old_weight){ //权值发生改变
				if(courseLeave + old_weight - weight < 0.0){
					 layer.msg("权重值修改后的值不正确，请仔细计算和查看提示信息",{icon:0});
					 return false;
				}
			}
		}else{ //编辑另存为新 ,验证权重值
			var courseLeave=$("p #edit_leave").text();//剩余权值
			if(weight>courseLeave){
				layer.msg("教学内容的权重值设置不正确，请仔细查看权重值设置提示信息",{icon:0});
				return false;
			}
		}
	}
	return true;
}






/**
 * 验证 教学内容 操作  添加教学作业按钮   不用这个验证方法了（废弃）
 * 教学内容创建时间在5天之内都可以显示添加作业按钮
 * 使用 moment.js 提供的日期时间工具
 */
function valid(row){
	//不在开课日期范围内，返回false
	var flag=moment(new Date()).isBetween(row.startDate,row.endDate);
	if(!flag){
		return false;
	}
	//创建时间是否超过5天 是true，否 false
	flag=moment(new Date()).subtract(5,"d").isAfter(row.createTime);
	if(flag){
		return false;
	}
	return true;
}


//---------------------------------------------业务功能开始（教学作业添加功能）---------------------//

//添加教师教学作业 先通过jxnrid查询教学作业发布次数（课前，课中，课后，最多只能发布3次），如果次数小于3，则添加，否则提示不能添加
function addJsjxzy(jxnrId){
	sendJson("get","/jxnr/display/"+jxnrId,{},false,function(res){
		if(res.status){
			var obj=res.data;
			sendJson("get","/jsjxzy/beforeAdd/"+obj.id,{},false,
				function(res){
					if(res.status){
						var left=3-res.data;
						layer.alert("该教学内容当前已发布"+res.data+"次作业任务，还可以发布"+left+"次作业任务");
						//初始化一些信息
						$("#addJsjxzyModel #add_jxzy_jxnr_id").val(obj.id);
						$("#addJsjxzyModel #add_jxzy_course_id").val(obj.courseId);
						$("#addJsjxzyModel #add_jxzy_create_user_id").val(obj.createUserId);
						$("#addJsjxzyModel #add_jxzy_course_name").text(obj.courseName);
						$("#addJsjxzyModel #add_jxzy_course_content").text(obj.courseContent);
						$("#addJsjxzyModel #add_jxzy_leave").text(obj.leave);
						$("#add_jxzy_weight").attr('max',Number(obj.leave));
						//显示模态框
						$("#addJsjxzyModel").modal("show");
					}else{
						layer.msg(res.info,{icon:0});
					}
				},function(){
					layer.msg("未知错误",{icon:2});
				}
			);
		}
	},function(){
		layer.msg("未知错误",{icon:2});
	});
}


/*教师教学作业信息，添加提交保存并校验
* _url 提交地址
* _btn 保存按钮
* _form 提交数据的表单
* _modal 模态框
*/
function addJsjxzySave(_url,_btn,_form,_modal){
	$('#'+_btn).click(function(){
		var objdata=$('#'+_form).serializeJSON();
		console.log(objdata);
		var stage=$("#add_jxzy_stage option:selected").val();
		if(isNull(stage)){
			layer.msg("请选择教学作业阶段",{icon:0});
			return ;
		}
		var jxnrId=$("#add_jxzy_jxnr_id ").val();
		var unique=true;
		sendJson("get","/jsjxzy/queryStage",{"stage":stage,"jxnrId":jxnrId},false,
			function(res){
				if(!res.status){
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
		if(!unique){
			return ;
		}
		if(!validateAddJsjxzyForm()){
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
/*教师教学作业添加，提交后校验
*/
function validateAddJsjxzyForm(){
	var weight=$("#add_jxzy_weight").val();
	if(isNull(weight)){
		layer.msg("请输入权重值",{icon:0});
		return false;
	}
	var reg=/^[+]{0,1}(\d+)$|^[+]{0,1}(0\.(\d)+)$/;
	if(!reg.test(weight) || weight>1.00){
		layer.msg("权重输入不正确，只接受不超过1的正小数值",{icon:0});
		return false;
	}else{
		var jxnrLeave=$("p #add_jxzy_leave").text();
		if(weight>jxnrLeave){
			layer.msg("该阶段发布作业的权重值设置不正确，请仔细查看权重值设置提示信息",{icon:0});
			return false;
		}
	}
	var totalScore=$("#add_jxzy_total_score").val();
	if(isNull(totalScore)){
		layer.msg("请输入作业总分值",{icon:0});
		return false;
	}
	var homework=$("#add_jxzy_homework").val();
	if(isNull(homework)){
		layer.msg("请输入作业内容",{icon:0});
		return false;
	}
	var startTime=$("#add_jxzy_start_time").val();
	
	if(isNull(startTime)){
		layer.msg("请设置作业开始时间",{icon:0});
		return false;
	}
	var endTime=$("#add_jxzy_end_time").val();
	if(isNull(endTime)){
		layer.msg("请设置作业截止时间",{icon:0});
		return false;
	}
	return true;
}

