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

/* 操作栏格式化  管理员只有 添加课程，查看课程权限*/
function actionFormatter(value,row,index){
	var ck = '<button class="btn btn-xs btn-info" onclick="showCourseDetail('+row.id+')" title="查看"><span class="glyphicon glyphicon-eye-open"></span></button>\n';
	var btns='';
	btns+=ck;
	return btns; 
}

//教师下拉框选项改变时，给隐藏域teacherName赋值
function getTeacherName(){
	$("#add_teacher_name").val($("#add_teacher_id option:selected").text());
}

