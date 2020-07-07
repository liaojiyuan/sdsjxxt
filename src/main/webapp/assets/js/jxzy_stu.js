
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
