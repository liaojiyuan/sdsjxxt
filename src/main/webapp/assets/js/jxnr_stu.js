/*
    desp：公共js
    author:gcc
    time:2019-05-21
 */
//---------------------------------------------业务功能开始（教学内容管理功能）---------------------
//查看教学内容详细信息
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


