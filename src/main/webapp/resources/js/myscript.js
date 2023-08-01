/**
 * myscript.js
 */
 
function toDateString(millisecond) {
	
	if (!millisecond) {
		return "";
	}
	
	var d = new Date(millisecond);
	// 2023-07-18 14:50:50
	var year = d.getFullYear();
	var month = d.getMonth() + 1;
	var date = d.getDate();
	var hours = d.getHours();
	var minutes =  d.getMinutes();
	var seconds = d.getSeconds();
	
	var dateString = year + "-" + make2digits(month) + "-" + make2digits(date) + " "
	dateString += make2digits(hours) + ":" + make2digits(minutes) + ":" + make2digits(seconds);
	return dateString;
}

function make2digits(num) {
	if (num < 10) {
		num = "0" + num;
	}
	return num;
}

function isImage(filename) {
	var formatName= getFormatName(filename);
	var uName = formatName.toUpperCase();
	if(uName =="JPG" || uName == "GIF" || uName == "PNG") {
		return true;
	}
	return false;
}

function getFormatName(filename) {
	var dotIndex = filename.lastIndexOf(".");
	var formatName = filename.substring(dotIndex + 1); // 확장자 
	return formatName;
}

function getThumbnailName(fullName) {
	var slashIndex = fullName.lastIndexOf("/");
	var front = fullName.substring(0, slashIndex + 1);
	var rear = fullName.substring(slashIndex + 1);
	var thumbnail = front + "s_" + rear;
	return thumbnail;
}