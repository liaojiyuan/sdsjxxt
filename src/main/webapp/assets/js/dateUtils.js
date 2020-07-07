 
/* 日期解析，字符串转日期 
 * @param dateString 可以为2017-02-16，2017/02/16，2017.02.16 
 * @returns {Date} 返回对应的日期对象 
 */  
function dateParse(dateString){  
    var SEPARATOR_BAR = "-";  
    var SEPARATOR_SLASH = "/";  
    var SEPARATOR_DOT = ".";  
    var dateArray;  
    if(dateString.indexOf(SEPARATOR_BAR) > -1){  
        dateArray = dateString.split(SEPARATOR_BAR);    
    }else if(dateString.indexOf(SEPARATOR_SLASH) > -1){  
        dateArray = dateString.split(SEPARATOR_SLASH);  
    }else{  
        dateArray = dateString.split(SEPARATOR_DOT);  
    }  
    //date.getMonth();  获取月份(0-11,0代表1月,用的时候记得加上1)
    return new Date(dateArray[0], dateArray[1]-1, dateArray[2]);   
};  
 
/** 
 * 日期比较大小  1>=2 返回 1
 * compareDateString大于dateString，返回-1； 
 * 等于返回0； 
 * compareDateString小于dateString，返回1 
 * @param dateString 日期 
 * @param compareDateString 比较的日期 
 */  
function dateCompare(dateString, compareDateString){  
    if(isNull(compareDateString)){  
    	console.log("compareDateString不能为空");  
        return 1;  
    }  
    if(isNull(dateString)){  
        console.log("dateString不能为空");  
        return 1;  
    }  
    var dateTime = dateParse(dateString).getTime();  
    var compareDateTime = dateParse(compareDateString).getTime();  
    if(compareDateTime > dateTime){  
        return -1;  
    }else if(compareDateTime == dateTime){  
        return 0;  
    }else{  
        return 1;  
    }  
};  
  
/** 
 * 判断日期是否在区间内，在区间内返回true，否返回false 
 * @param dateString 日期字符串 
 * @param startDateString 区间开始日期字符串 
 * @param endDateString 区间结束日期字符串 
 * @returns {Number} 
 */  
function isDateBetween(dateString, startDateString, endDateString){  
    if(isNull(dateString)){  
       console.log("当前比较时间不能为空");  
        return;  
    }  
    if(isNull(startDateString)){  
    	console.log("开始时间不能为空");  
        return false;  
    }  
    if(isNull(endDateString)){  
    	console.log("结束不能为空");  
        return false;  
    }  
    var flag = false;  
    var startFlag = (dateCompare(dateString, startDateString)>-1);  
    var endFlag = (dateCompare(dateString, endDateString) <1);  
    if(startFlag && endFlag){  
        flag = true;  
    }  
    return flag;  
};  
 
//日期相减
function reduceDate(date,days) { 
	var d = new Date(date); 
	d.setDate(d.getDate() - days); 
	var m = d.getMonth() + 1;
	if (m < 10) {
		m = "0" + m;
	}
	var day = d.getDate();
	if (d.getDate() < 10) {
		day = "0" + day;
	}
	return d.getFullYear() + '-' + m + '-' + day; 
}
