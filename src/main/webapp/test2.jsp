<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<script>
$(function() {
	$(window).bind("beforeunload", function() {
		$.get("/board/list");
	});
// 	window.onbeforeunload = function() {
// 		$.get("/board/list");
// 	}
});
</script>
</head>
<body>
<div id="test">test2</div>


</body>
</html>