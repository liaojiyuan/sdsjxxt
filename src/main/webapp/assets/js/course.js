//---------------------------------------------业务功能开始（课程管理功能）---------------------
//查看课程详细信息
function showCourseDetail(id){
	sendJson("get","/course/display/"+id,{},false,function(res){
		if(res.status){
			var data=res.data;
			//alert(data);
			console.log(data);
			//初始化模态框信息
			$("#inputName").text(data.name);
			$("#inputStartYear").text(data.startYear);
			$("#inputTeacherName").text(data.teacherName);
			$("#inputBanjiNames").text(data.banjiNames);
			$("#inputTotalCount").text(data.totalCount);
			$("#inputGrade").text(data.grade);
			$("#inputStartDate").text(data.startDate);
			$("#inputEndDate").text(data.endDate);
			$("#inputJxnrCount").text(data.jxnrCount);
			$("#inputDescription").text(data.description);
			//显示模态框
			$("#infoModel").modal("show");
		}else{
			layer.msg(res.info,{icon:2});
		}
	},function(){
		layer.msg("未知错误",{icon:2});
	});
}

/*课程信息，添加，编辑提交保存并校验
 * _url 提交地址
 * _btn 保存按钮
 * _form 提交数据的表单
 * _modal 模态框
 */
function addOrEditCourseSave(_url,_btn,_form,_modal){
	
	$('#'+_btn).click(function(){
		
		var objdata=$('#'+_form).serializeJSON();
		console.log(objdata);
		var idArr=objdata["banjiIdArray"];
		if(idArr==null){
			layer.msg("授课班级不能为空",{icon:0});
			return ;
		}
		var banjiIds=idArr.join(",");
		objdata["banjiIds"]=banjiIds;
		var _method='';
		(_url.indexOf("toedit")>-1)?(_method='edit'):(_method='add');
		if(!validateAddOrEditCourseForm(_method)){
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


//提交添加课程,编辑课程验证
function validateAddOrEditCourseForm(_method){
	var name=$("#"+_method+"_name").val();
	if(name==null|| name==''){
		layer.msg("课程名不能为空",{icon:0});
		return false;
	}
	var startyear=$("#"+_method+"_start_year").val();
	if(startyear==null|| startyear==''){
		layer.msg("开课年份不能为空",{icon:0});
		return false;
	}
	var banjiids='';
	$("#"+_method+"_banji_ids option:selected").each(function(){
		banjiids=banjiids+$(this).val()+",";
	});
	if(banjiids==null|| banjiids==''){
		layer.msg("授课班级不能为空",{icon:0});
		return false;
	}
	var grade=$("#"+_method+"_grade").val();
	if(grade==null|| grade==''){
		layer.msg("平时分成绩不能为空",{icon:0});
		return false;
	}else {
		var reg=/^[0-9]{1,3}$/;
		if(reg.test(grade)==false){
			layer.msg("平时分成绩必须为整数",{icon:0});
			return false;
		}else if(grade>50||grade==0){
			layer.msg("平时分成绩介于20~50之间，请不要设置太大哦",{icon:0});
			return false;
		}
	}
	return true;
}

/* 操作栏格式化  当前用户创建的课程有修改，删除，查看功能，删除有条件限制，修改只能修改部分内容，其他用户创建的课程只显示查看功能按钮*/
function actionFormatter(value,row,index){
	var ck = '<button class="btn btn-xs btn-info" onclick="showCourseDetail('+row.id+')" title="查看"><span class="glyphicon glyphicon-eye-open"></span></button>\n';
	var btns='';
	var recordXuehao=row.teacherId;
	var currentUserId=$("#userXuehao").val();
	//当前用户创建的课程 ，并且创建课程1周之内显示修改，删除操作按钮，并且后台逻辑会对修改，删除做验证
	if(currentUserId==recordXuehao && moment(new Date()).subtract(7,'d').isBefore(row.create_time)){
		btns = '<button class="btn btn-xs btn-info" onclick="editCourse('+row.id+')" title="修改"><span class="glyphicon glyphicon-pencil"></span></button>\n'
		+'    <button class="btn btn-xs btn-info" onclick="deleteCourse('+row.id+')" title="删除"><span class="glyphicon glyphicon-trash"></span></button>\n';
		btns=btns+ck;
	}else{
		btns=ck;
	}
	var tj='<button class="btn btn-xs btn-info" onclick="analysisCourse('+row.id+')" title="学生成绩分析"><span class="glyphicon glyphicon-stats"> 统计</span></button>\n';
	if(currentUserId==recordXuehao ){
		if(row.sfTj==1){
			
		}
		btns+=tj;
	}
	return btns; 
}

//点击查看课程统计表
function  analysisCourse(id){
	sendJson("get","/course/tjfx/"+id,{},false,function(res){
		if(res.status){
			window.open(res.data,"_blank");
		}
	})
}

//点击修改显示课程初始化信息（可编辑状态）
function editCourse(id){
	sendJson("get","/course/edit/"+id,{},false,function(res){
		if(res.status){
			var data=res.data;
			//初始化模态框信息
			$("#editform #edit_id").val(data.id); //hidden
			$("#editform #edit_name").val(data.name); //课程名
			$("#editform #edit_start_year").val(data.startYear); //开课年份
			/*此处授课教师为单选，让下拉框内容被选中实现  */
			//var value=data.teacherId;
			//$("#editform #edit_teacher_id ").selectpicker('val',value);
			/*此处班级为多选下拉框，让多选下拉框内容被选中是难点，已实现  */
			var value2=data.banjiIds;
			//利用两条语句即可，单选下拉框也是
			var arr=value2.split(",");
			$("#editform #edit_banji_ids ").selectpicker('val',arr); //授课班级
			/* laydate日期控件展开有初始值 能直接实现 */
			$("#editform #edit_grade").val(data.grade);
			$("#editform #edit_start_date").val(data.startDate);
			$("#editform #edit_end_date").val(data.endDate);
			$("#editform #edit_description").val(data.description);
			//显示模态框
			$("#editModel").modal("show");
		}else{
			layer.msg(res.info,{icon:2});
		}
	},function(){
		layer.msg("未知错误",{icon:2});
	});
}

//删除课程询问信息
function deleteCourse(id){
	sendJson("get","/course/delete/"+id,{},false,
		function(res){ 
			if(res.status){//如果删除前后台校验通过，返回success，则再次请求后台进行删除
				layer.confirm('确定要删除该条课程信息吗？', {
					  btn: ['确定','在想想'] //按钮
					}, function(){
					  sendJson("get","/course/todelete/"+id,{},false,function(res){
					  	if(res.status){
					  		layer.msg(res.info,{icon:1});
					  		flushTable();
					  	}else{
					  		layer.msg(res.info);
					  	}
					  },function(){
					  		layer.msg("发生未知错误",{icon:2});
					  });
					}, function(){ //在想想
					  
					}
				);
			}else{ //如果删除前后台校验不通过，返回failure，则提示
				layer.alert(res.info,{icon:5});
			}
		},
		function(){
			layer.msg("发生未知错误",{icon:2});
		}
	)
	
}

